package com.algorithm.hash;

/**
 * Created by wangzunhui on 2016/3/11.
 */
public class CuckooFilter {
    public static int MAX_NUM_KICKS = 5;

    private int power;
    private Bucket[] buckets;

    class Entry {
        public final static byte EMPTY = 0;
        public final static byte USED = 1;
        public final static byte DELETED = 2;

        private int finger;
        private byte state;

        public byte getState() {
            return state;
        }

        public void setState(byte state) {
            this.state = state;
        }

        public Entry(){
            state = Entry.EMPTY;
        }

        public int get(){
            return finger;
        }

        public void set(int finger){
            this.finger = finger;
            this.state = Entry.USED;
        }
    }

    class Slot{
        private Entry[] entries;

        Slot(int size){
            entries = new Entry[size];
            for (int i = 0; i < size; i++){
                entries[i] = new Entry();
            }
        }
    }

    class Bucket {
        private Slot[] slots;

        Bucket(int slotNum){
            this.slots = new Slot[slotNum];
            for (int i = 0; i < slotNum; i++){
                slots[i] = new Slot(4);
            }
        }

        /**
         * check slot empty.
         *
         * @param pos
         * @return
         */
        public Entry getEmptyEntry(int pos){
            for (Entry entry : this.slots[pos].entries){
                if (entry.getState() == Entry.EMPTY){
                    return entry;
                }
            }

            return null;
        }
    }

    public CuckooFilter(int power){
        this.power = power;
        this.buckets = new Bucket[2];
        buckets[0] = new Bucket(1 << power);
        buckets[1] = new Bucket(1 << power);
    }

    /**
     * f = fingerprint(x)
     *
     * @param x
     * @return
     */
    public int fingerPrint(String x){
        Hash hash = new SimpleHash(1, 0xFFFFFFFF);
        return hash.hash(x);
    }

    /**
     * i1 = hash(x)
     *
     * @param x
     * @return
     */
    public int hash(String x){
        Hash hash = new SimpleHash(9, 0xFFFFFFFF);
        return hash.hash(x) % (1 << power);
    }

    /**
     * i2 = i1 ^ hash(f)
     *
     * @param x
     * @return
     */
    public int hash2(String x){
        int i1 = hash(x);
        int f = fingerPrint(x);

        return i1 ^ hash(new Integer(f).toString());
    }

    public void insert(String x){
        int f = fingerPrint(x);
        int i1 = hash(x);
        int i2 = hash2(x);

        Entry entry = buckets[0].getEmptyEntry(i1);
        if (entry != null){
            entry.set(f); // add f to bucket
            return;
        }

        entry = buckets[1].getEmptyEntry(i2);
        if (entry != null){
            entry.set(f);
            return;
        }

        /* relocate existing items */
        int i = i2;
        for (int n = 0; n < MAX_NUM_KICKS; n++){
            int ramdom = 3;
            Entry e = buckets[1].slots[i].entries[ramdom];
            int t = e.finger;
            e.finger = f;
            f = t;

            i = i ^ hash(new Integer(f).toString());
            entry = buckets[1].getEmptyEntry(i);
            if (entry != null){
                entry.set(f);
                return;
            }
        }
    }
}
