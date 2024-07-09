package com.electra.server.service.ws.utils;

import org.springframework.stereotype.Component;

import java.util.BitSet;

/**
 * pool of integers from 0 to n with the best possible
 * complexity for both acquisition and release operations.
 * (O(1) time complexity for set, clear, nextSetBit, and cardinality operations),
 * making this implementation suitable for high-performance scenarios.
 */
@Component
public class SessionIdPool {

    private final int MIN_VALUE = 1;
    private final int MAX_VALUE = 10000;

    private final BitSet pool;

    public SessionIdPool() {
        // Initialize the BitSet with all bits set (true), indicating all integers are available
        pool = new BitSet(MAX_VALUE - MIN_VALUE + 1);
        pool.set(0, MAX_VALUE - MIN_VALUE + 1);
    }

    // O(1) time complexity
    public synchronized Integer acquire() {
        int index = pool.nextSetBit(0);
        if (index == -1) {
            throw new IllegalStateException("Session Pool exhausted");
        }
        pool.clear(index);
        return index + MIN_VALUE;
    }

    // O(1) time complexity
    public synchronized void release(Integer value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("Session Value out of pool range");
        }
        pool.set(value - MIN_VALUE);
    }

    // O(1) time complexity
    public synchronized int availableObjects() {
        return pool.cardinality();
    }

}
