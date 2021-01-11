import org.apache.commons.collections.map.LRUMap;
import org.example.utils.LRUCache;
import org.junit.Assert;
import org.junit.Test;

public class TestLRU {
    @Test
    public void InsertSuccess(){
        LRUCache <String,String> Cache = new  LRUCache<>(15);
        Cache.add ("bilal","Thomas");
    }
    @Test
    public void InsertTooMuchElement(){
        LRUCache <String,String> Cache = new  LRUCache<>(1);
        Cache.add ("bilal","Thomas");
        Cache.add ("gg","bb");
        Assert.assertEquals(1,Cache.size());
        Assert.assertEquals("bb",Cache.get("gg"));
    }

}
