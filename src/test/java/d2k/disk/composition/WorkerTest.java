package d2k.disk.composition;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import d2k.disk.composition.DiskCapacity;
import d2k.disk.composition.DiskComposition;
import d2k.disk.composition.DiskItem;
import d2k.disk.composition.Worker;

public class WorkerTest {

    @Test
    public void test() {

        List<DiskItem> items = Arrays.asList(
                new DiskItem("f1", 1),
                new DiskItem("f2", 2),
                new DiskItem("f3", DiskCapacity.DVD_MINUS_R_SL.getSize())
                );

        Worker worker = new Worker();
        worker.setCapacity(DiskCapacity.DVD_MINUS_R_SL);
        DiskComposition composition = worker.createCompositions(items);
        assertTrue(composition.getItems().size() == 2);

    }

    @Test
    public void testMove() throws Exception {

        String data="somedata";
        String file = "x-movie-file.avi";

        DiskItem dvdItem = new DiskItem(file, data.length());
        DiskComposition dvd = new DiskComposition(DiskCapacity.DVD_MINUS_R_SL);
        dvd.addItem(dvdItem);

        Path dir = Files.createTempDirectory("x-dvd-src");
        FileUtils.write(dir.resolve(Paths.get(file)).toFile(), data);

        Worker worker = new Worker();
        worker.setDirectory(dir.toFile());
        File toburndir = worker.moveCompositionToRoot(dvd);

        long sz = FileUtils.sizeOfDirectory(toburndir);
        assertTrue(sz>0);
        FileUtils.deleteDirectory(toburndir);

    }

}
