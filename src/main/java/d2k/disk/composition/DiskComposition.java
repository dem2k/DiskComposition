package d2k.disk.composition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class DiskComposition implements Comparable<DiskComposition> {
    
    private static final org.apache.logging.log4j.Logger log =
            org.apache.logging.log4j.LogManager.getLogger(DiskComposition.class);

    private DiskCapacity capacity;
    
    public DiskComposition(DiskCapacity capacity){
        this.capacity = capacity;
    }

    private List<DiskItem> items = new ArrayList<>();

    public Boolean addItem(DiskItem item) {
        if (item.getSize() == 0 ){
            log.debug("item not added, size is 0: {}",item);
            return false;
        }
        if (getActualSize() + item.getSize() > capacity.getSize()) {
            log.debug("item not added, composition full: {}",item);
            return false;
        }
        
        if(items.add(item)){
            log.debug("added: {}", item);
            return true;
        }
        
        log.error("somethig went wrong. items.add({}) returned false.",item);
        return false;
    }
    
    

    public DiskCapacity getCapacity() {
        return this.capacity;
    }



    @Override
    public String toString() {
        String c = this.getClass().getSimpleName() +
                "=[" + MyUtils.humanReadableByteCount(getActualSize()) + "]" +
                items.stream().map(item -> item.toString()).collect(Collectors.joining(",", "[", "]"));
        return c;
    }

    @Override
    public int compareTo(DiskComposition o) {
        Long left = this.getActualSize();
        Long right = o.getActualSize();
        return left.compareTo(right);
    }

    public long getActualSize() {
        return this.items.stream().collect(Collectors.summingLong(DiskItem::getSize));
    }

    public List<DiskItem> getItems() {
        return items;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.capacity == null) ? 0 : this.capacity.hashCode());
        result = prime * result + ((this.items == null) ? 0 : this.items.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DiskComposition other = (DiskComposition) obj;
        if (this.capacity != other.capacity)
            return false;
        if (this.items == null) {
            if (other.items != null)
                return false;
        } else if (!this.items.equals(other.items))
            return false;
        return true;
    }
    
    
}
