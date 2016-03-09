package com.algorithm.hash;

/**
 * Created by wangzunhui on 2016/3/9.
 */
public class SimpleHash implements Hash {
    private int shift = 0;
    private int mask = 0;

    public SimpleHash(){
    }

    public SimpleHash(int shift, int mask){
        this.shift = shift;
        this.mask = mask;
    }

    @Override
    public int hash(String value) {
        byte[] data = value.getBytes();
        int hash = (int) 2166136261L;

        for (byte b : data) {
            hash = (hash * 16777619) ^ b;
        }

        if (shift == 0)
            return hash;

        return (hash ^ (hash >> shift)) & mask;
    }
}