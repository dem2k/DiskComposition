package d2k.disk.composition;

import static org.junit.Assert.*;

import org.junit.Test;

import d2k.disk.composition.DiskCapacity;
import d2k.disk.composition.DiskComposition;
import d2k.disk.composition.DiskItem;

public class DiskCompositionTest {

    @Test
    public void testAddItem() {
        
        DiskComposition c = new DiskComposition(DiskCapacity.DVD_MINUS_R_SL);
        
        DiskItem item = new DiskItem("xtest", DiskCapacity.DVD_MINUS_R_SL.getSize());
        assertTrue(        c.addItem(item));
        
        DiskItem nextitem = new DiskItem("next", 1);
        assertFalse(c.addItem(nextitem));
    }
    
    @Test
    public void testCompare(){
        DiskComposition klein = new DiskComposition(DiskCapacity.DVD_MINUS_R_SL);
        klein.addItem(new DiskItem("afile", 1));
        
        DiskComposition gross = new DiskComposition(DiskCapacity.DVD_MINUS_R_SL);
        gross.addItem(new DiskItem("afile", 3));
        
        int result = gross.compareTo(klein);
        assertTrue(result>0);
        
    }
    
    @Test
    public void testGetSizeOfComposition(){
        DiskComposition dvd = new DiskComposition(DiskCapacity.DVD_MINUS_R_SL);
        dvd.addItem(new DiskItem("afile", 123456789));
        assertTrue(dvd.getActualSize()==123456789);
    }

}
