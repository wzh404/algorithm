package com.algorithm;

import com.algorithm.hash.Hash;
import com.algorithm.hash.SimpleHash;

import java.util.BitSet;
/**
 * Created by wangzunhui on 2016/3/8.
 */
public class BloomFilter {
    private BitSet bitSet;
    private int power;

    public BloomFilter(int power){
        this.power = power;
        this.bitSet = new BitSet(2 << power);
    }

    public void set(String value){
        for (int  i = 0; i < 20; i++) {
            Hash hash = new SimpleHash(i + 1, 0xFFFFFFFF);
            int pos = getPos(hash, value);

            System.out.println((i+1) + ": " + pos);
            bitSet.set(pos, true);
        }
    }

    public boolean find(String value){
        for (int  i = 0; i < 20; i++) {
            Hash hash = new SimpleHash(i + 1, 0xFFFFFFFF);
            int pos = getPos(hash, value);

            if (!bitSet.get(pos))
                return false;
        }

        return true;
    }

    private int getPos(Hash hash, String value){
        long hashValue = getUnsignedInt(hash.hash(value));
        return (int) (hashValue % (2 << power));
    }

    public static void main(String[] args){
        BloomFilter bloomFilter = new BloomFilter(20);
        bloomFilter.set("wangzh ok");

        System.out.println(bloomFilter.find("wangzh ok"));
        System.out.println(bloomFilter.find("wangzh ok!"));
    }

    public static long getUnsignedInt(int x) {
        return x & 0x00000000ffffffffL;
    }
}
