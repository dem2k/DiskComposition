package d2k.disk.composition;

import static org.junit.Assert.*;

import org.junit.Test;

import d2k.disk.composition.DiskItem;

public class DiskItemTest {

    @Test
    public void testToString() {
        DiskItem onekb = new DiskItem("xxx", 1025);
        assertTrue(onekb.toString().contains("KB/"));

        DiskItem onemb = new DiskItem("xxx", 1025*1024);
        assertTrue(onemb.toString().contains("MB/"));

        DiskItem onegb = new DiskItem("xxx", 1025*1024*1024);
        assertTrue(onegb.toString().contains("GB/"));
    }

}
