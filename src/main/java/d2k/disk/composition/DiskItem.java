package d2k.disk.composition;


public class DiskItem {

	private String file;
	private long size;
	
	public DiskItem(String file,long size){
		this.file=file;
		this.size=size;
	}
	
    public long getSize() {
        return size;
    }
    
    public String getFile() {
        return file;
    }


	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.file == null) ? 0 : this.file.hashCode());
        result = prime * result + (int) (this.size ^ (this.size >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"=["+MyUtils.humanReadableByteCount(size)+"/"+file+"]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DiskItem other = (DiskItem) obj;
        if (this.file == null) {
            if (other.file != null)
                return false;
        } else if (!this.file.equals(other.file))
            return false;
        if (this.size != other.size)
            return false;
        return true;
    }


    
	
	
	

}
