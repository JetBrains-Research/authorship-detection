package com.googlecode.javaewah.synth;

/*
 * Copyright 2009-2016, Daniel Lemire, Cliff Moon, David McIntosh, Robert Becho, Google Inc., Veronika Zenz, Owen Kaser, Gregory Ssi-Yan-Kai, Rory Graves
 * Licensed under the Apache License, Version 2.0.
 */

import java.util.*;

/**
 * This class will generate "uniform" lists of random integers. This class will
 * generate "uniform" lists of random integers.
 *
 * @author Daniel Lemire
 */
public class UniformDataGenerator {
    /**
     * construct generator of random arrays.
     */
    public UniformDataGenerator() {
        this.rand = new Random();
    }

    /**
     * @param seed random seed
     */
    public UniformDataGenerator(final int seed) {
        this.rand = new Random(seed);
    }

    /**
     * generates randomly N distinct integers from 0 to Max.
     */
    int[] generateUniformHash(int N, int Max) {
        if (N > Max)
            throw new RuntimeException("not possible");
        int[] ans = new int[N];
        HashSet<Integer> s = new HashSet<Integer>();
        while (s.size() < N)
            s.add(this.rand.nextInt(Max));
        Iterator<Integer> i = s.iterator();
        for (int k = 0; k < N; ++k)
            ans[k] = i.next();
        Arrays.sort(ans);
        return ans;
    }

    /**
     * output all integers from the range [0,Max) that are not in the array
     */
    static int[] negate(int[] x, int Max) {
        int[] ans = new int[Max - x.length];
        int i = 0;
        int c = 0;
        for (int v : x) {
            for (; i < v; ++i)
                ans[c++] = i;
            ++i;
        }
        while (c < ans.length)
            ans[c++] = i++;
        return ans;
    }

    /**
     * generates randomly N distinct integers from 0 to Max.
     *
     * @param N   Number of integers to generate
     * @param Max Maximum value of the integers
     * @return array containing random integers
     */
    public int[] generateUniform(int N, int Max) {
        if (N * 2 > Max) {
            return negate(generateUniform(Max - N, Max), Max);
        }
        if (2048 * N > Max)
            return generateUniformBitmap(N, Max);
        return generateUniformHash(N, Max);
    }

    /**
     * generates randomly N distinct integers from 0 to Max using a bitmap.
     *
     * @param N   Number of integers to generate
     * @param Max Maximum value of the integers
     * @return array containing random integers
     */
    int[] generateUniformBitmap(int N, int Max) {
        if (N > Max)
            throw new RuntimeException("not possible");
        int[] ans = new int[N];
        BitSet bs = new BitSet(Max);
        int cardinality = 0;
        while (cardinality < N) {
            int v = this.rand.nextInt(Max);
            if (!bs.get(v)) {
                bs.set(v);
                cardinality++;
            }
        }
        int pos = 0;
        for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i + 1)) {
            ans[pos++] = i;
        }
        return ans;
    }

    Random rand = new Random();

}
