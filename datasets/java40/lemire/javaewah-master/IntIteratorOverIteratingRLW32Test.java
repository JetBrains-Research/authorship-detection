package com.googlecode.javaewah32;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Copyright 2009-2016, Daniel Lemire, Cliff Moon, David McIntosh, Robert Becho, Google Inc., Veronika Zenz, Owen Kaser, Gregory Ssi-Yan-Kai, Rory Graves
 * Licensed under the Apache License, Version 2.0.
 */

/**
 * Tests for utility class.
 */
@SuppressWarnings("javadoc")
public class IntIteratorOverIteratingRLW32Test {

    @Test
    // had problems with bitmaps beginning with two consecutive clean runs
    public void testConsecClean() {
        System.out.println("testing int iteration, 2 consec clean runs starting with zeros");
        EWAHCompressedBitmap32 e = new EWAHCompressedBitmap32();
        for (int i = 32; i < 64; ++i)
            e.set(i);
        IntIteratorOverIteratingRLW32 ii = new IntIteratorOverIteratingRLW32(e.getIteratingRLW());
        assertTrue(ii.hasNext());
        int ctr = 0;
        while (ii.hasNext()) {
            ++ctr;
            ii.next();
        }
        assertEquals(32, ctr);
    }

    @Test
    public void testConsecCleanStartOnes() {
        System.out
                .println("testing int iteration, 2 consec clean runs starting with ones");
        EWAHCompressedBitmap32 e = new EWAHCompressedBitmap32();
        for (int i = 0; i < 2 * 32; ++i)
            e.set(i);
        for (int i = 4 * 32; i < 5 * 32; ++i)
            e.set(i);

        IntIteratorOverIteratingRLW32 ii = new IntIteratorOverIteratingRLW32(e.getIteratingRLW());
        assertTrue(ii.hasNext());
        int ctr = 0;
        while (ii.hasNext()) {
            ++ctr;
            ii.next();
        }
        assertEquals(3 * 32, ctr);
    }

    @Test
    public void testStartDirty() {
        System.out.println("testing int iteration, no initial runs");
        EWAHCompressedBitmap32 e = new EWAHCompressedBitmap32();
        for (int i = 1; i < 2 * 32; ++i)
            e.set(i);
        for (int i = 4 * 32; i < 5 * 32; ++i)
            e.set(i);

        IntIteratorOverIteratingRLW32 ii = new IntIteratorOverIteratingRLW32(e.getIteratingRLW());
        assertTrue(ii.hasNext());
        int ctr = 0;
        while (ii.hasNext()) {
            ++ctr;
            ii.next();
        }
        assertEquals(3 * 32 - 1, ctr);
    }

    @Test
    public void testEmpty() {
        System.out.println("testing int iteration over empty bitmap");
        EWAHCompressedBitmap32 e = new EWAHCompressedBitmap32();

        IntIteratorOverIteratingRLW32 ii = new IntIteratorOverIteratingRLW32(e.getIteratingRLW());
        assertFalse(ii.hasNext());
    }

    @Test
    public void testRandomish() {
        EWAHCompressedBitmap32 e = new EWAHCompressedBitmap32();

        int upperLimit = 100000;
        for (int i = 0; i < upperLimit; ++i) {
            double probabilityOfOne = i / (double) (upperLimit / 2);
            if (probabilityOfOne > 1.0)
                probabilityOfOne = 1.0;
            if (Math.random() < probabilityOfOne) {
                e.set(i);
            }
        }

        IntIteratorOverIteratingRLW32 ii = new IntIteratorOverIteratingRLW32(e.getIteratingRLW());
        int ctr = 0;
        while (ii.hasNext()) {
            ++ctr;
            ii.next();
        }

        assertEquals(e.cardinality(), ctr);
        System.out.println("checking int iteration over a var density bitset of size " + e.cardinality());

    }

}
