package d2k.disk.composition;

import org.apache.commons.io.FileUtils;

public enum DiskCapacity {
	BDR25(25025314816L), BDR50(50050629632L),
	DVD_MINUS_R_SL(4707319808L), DVD_PLUS_R_SL(4700372992L);

	private long size;

	private DiskCapacity(long size) {
		this.size = size - FileUtils.ONE_MB - (FileUtils.ONE_KB * 256);
	}

	public long getSize() {
		return size;
	}

}
