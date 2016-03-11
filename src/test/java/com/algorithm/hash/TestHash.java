package com.algorithm.hash;

import com.algorithm.BloomFilter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wangzunhui on 2016/3/11.
 */
public class TestHash {
    @Test
    public void testBloomFilter(){
        BloomFilter bloomFilter = new BloomFilter(20);
        bloomFilter.set("wangzh ok");

        Assert.assertTrue(bloomFilter.find("wangzh ok"));
    }

    @Test
    public void testCuckooFilter(){
        CuckooFilter filter = new CuckooFilter(10);
        String x = "wang zunhui";
        int f = filter.fingerPrint(x);
        int i1 = filter.hash(x);
        int i2 = filter.hash2(x); // i1 ^ filter.hash(new Integer(f).toString());

        int i = i2 ^ filter.hash(new Integer(f).toString());
        System.out.println(f + " - " + i1 + " : " + i2 + " ******* " + i);

    }
}
