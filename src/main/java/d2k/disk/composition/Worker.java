package d2k.disk.composition;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestLine;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import de.vandermeer.asciitable.v2.themes.V2_TableTheme;

public class Worker {

	private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(Worker.class);

	private File dir = null;
	private int trynum = 999999;
	private DiskCapacity capacity = DiskCapacity.DVD_MINUS_R_SL;

	public void setTryNum(int trynum) {
		this.trynum = trynum;
	}

	public void setDirectory(File directory) {
		this.dir = directory;
	}

	public DiskComposition start() {
		if (dir == null)
			throw new RuntimeException("directory isnt set properly.");

		// List<DvdItem> items = new ArrayList<>();
		// Arrays.asList(dir.listFiles()).forEach(f -> {
		// items.add(new DvdItem(f.getName(), FileUtils.sizeOf(f)));
		// });

		List<DiskItem> items = Arrays.asList(dir.listFiles()).stream().map(f -> {
			return new DiskItem(f.getName(), FileUtils.sizeOf(f));
		}).collect(Collectors.toList());

		log.debug("all items: {}", items);

		DiskComposition best = new DiskComposition(capacity);

		for (int i = 1; i <= trynum; i++) {
			log.debug("--- iteration {} of {} ---", i, trynum);
			DiskComposition composition = createCompositions(items);
			log.debug("1: bytes={}, {}", best.getActualSize(), best);
			log.debug("2: bytes={}, {}", composition.getActualSize(), composition);
			int cmpres = best.compareTo(composition);
			log.debug("1 compare to 2 = {}", cmpres);
			if (cmpres < 0) {
				log.debug("2 > 1");
				best = composition;
			}
		}

		System.out.println();
		System.out.println("Probably best choice fit to " + best.getCapacity() + " after " + trynum + " tests:");
		print(best);
		return best;
	}

	public File moveCompositionToRoot(DiskComposition dvd) {
		File dest = Paths.get(dir.getAbsolutePath()).getRoot().resolve("ToBurn-" + System.currentTimeMillis()).toFile();
		dvd.getItems().forEach(itm -> {
			File src = new File(dir.getAbsolutePath() + "/" + itm.getFile());
			try {
				log.debug("moveToDirectory( {}, {} );", src, dest);
				FileUtils.moveToDirectory(src, dest, true);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		});
		System.out.println("Files moved to " + dest);
		return dest;
	}

	private void print(DiskComposition best) {

		///////////////////////////////////////////////////////////////////////////////////////
		// IASCIITableAware asciiTableAware =
		// new CollectionASCIITableAware<DiskItem>(best.getItems(),
		// // properties to read
		// "size", "file");
		//
		// asciiTableAware.getHeaders().get(0).setHeaderAlign(ASCIITable.ALIGN_RIGHT);
		// asciiTableAware.getHeaders().get(1).setHeaderAlign(ASCIITable.ALIGN_LEFT);
		// asciiTableAware.getHeaders().get(1).setDataAlign(ASCIITable.ALIGN_LEFT);
		//
		// ASCIITable.getInstance().printTable(asciiTableAware);
		// String [] header = { "All" };
		// String[][] data = { { format,
		// MyUtils.humanReadableByteCount(best.getSizeOfComparition())}
		// };
		// ASCIITable.getInstance().printTable(header, data);
		///////////////////////////////////////////////////////////////////////////////////////

		char[] alig = { 'r', 'l' };
		V2_AsciiTable at = new V2_AsciiTable();
		at.addRule();
		at.addRow("SIZE", "FILE").setAlignment(alig);
		at.addStrongRule();
		for (DiskItem item : best.getItems()) {
			at.addRow(MyUtils.humanReadableByteCount(item.getSize()), item.getFile()).setAlignment(alig);
		}
		at.addRule();

		V2_TableTheme theme = V2_E_TableThemes.UTF_LIGHT.get();

		V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
		rend.setTheme(theme);
		rend.setWidth(new WidthLongestLine());

		RenderedTable rt = rend.render(at);

		System.out.println(rt);

		BigDecimal bigDecimal = new BigDecimal(best.getActualSize());
		String format = new DecimalFormat().format(bigDecimal);

		System.out.println("TOTAL: " + format + " Bytes / " + MyUtils.humanReadableByteCount(best.getActualSize()));

	}

	public DiskComposition createCompositions(List<DiskItem> items) {
		Collections.shuffle(items, new Random(System.currentTimeMillis()));

		DiskComposition composition = new DiskComposition(capacity);

		items.forEach(item -> {
			composition.addItem(item);
		});

		return composition;
	}

	public void setCapacity(DiskCapacity capacity) {
		this.capacity = capacity;
	}

}
