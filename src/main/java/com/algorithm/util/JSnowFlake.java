package com.algorithm.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangzunhui on 2016/3/21.
 *
 */
public class JSnowFlake {
    // 0               41	           51			 64
    // +---------------+----------------+------------+
    // |timestamp(ms)  | worker node id | sequence	 |
    // +---------------+----------------+------------+
    public final static long epoch = 1458555039022L;
    public final static int workerIdBits = 10;
    public final static int sequenceBits = 12;

    public final static int workerIdShift = 12;
    public final static int timestampShift = 22;
    public final static long sequenceMask = -1 ^ (-1 << sequenceBits);

    private Lock lock ;
    private long workerId;
    private long sequence;
    private long lastTimeStamp;

    public JSnowFlake(long workerId){
        lock = new ReentrantLock();

        this.workerId = workerId;
        this.lastTimeStamp = System.currentTimeMillis();
        this.sequence = 0;
    }

    private long nextTime(long lastTimeStamp){
        long timeStamp = System.currentTimeMillis();
        while (timeStamp <= lastTimeStamp){
            timeStamp = System.currentTimeMillis();
        }

        return timeStamp;
    }

    public long nextId(){
        long currentTime = System.currentTimeMillis();
        if (currentTime < this.lastTimeStamp){
            return -1L;
        }

        lock.lock();
        if (currentTime == this.lastTimeStamp){
            this.sequence = (this.sequence + 1) & sequenceMask;
            if (this.sequence == 0){
                currentTime = nextTime(this.lastTimeStamp);
            }
        } else {
            this.sequence = 0;
        }
        this.lastTimeStamp = currentTime;
        long seq = (this.lastTimeStamp - epoch) << timestampShift | this.workerId << workerIdShift | this.sequence;
        lock.unlock();

        return seq;
    }
}
