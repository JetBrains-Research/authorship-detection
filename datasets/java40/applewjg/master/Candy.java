/*
 Author:     King, wangjingui@outlook.com
 Date:       Dec 12, 2014
 Problem:    Candy
 Difficulty: Easy
 Source:     https://oj.leetcode.com/problems/candy/
 Notes:
 There are N children standing in a line. Each child is assigned a rating value.
 You are giving candies to these children subjected to the following requirements:
 - Each child must have at least one candy.
 - Children with a higher rating get more candies than their neighbors.
 What is the minimum candies you must give?

 Solution: You may refer to https://github.com/AnnieKim/ITint5/blob/master/031_%E5%88%86%E9%85%8D%E7%B3%96%E6%9E%9C.cpp
           1. O(n) space.
           2. traverse only once with O(1) space.
*/

public class Solution {
    public int candy(int[] ratings) {
        return candy_1(ratings);
    }
    public int candy_1(int[] ratings) {
        int N = ratings.length;
        if (N == 0) return 0;
        int[] height = new int[N];
        int res = 0;
        height[0] = 1;
        for (int i = 1; i < N; ++i) {
            height[i] = 1;
            if (ratings[i] > ratings[i - 1]) {
                height[i] = height[i - 1] + 1;
            }
        }
        for (int i = N - 2; i >= 0; --i) {
            if (ratings[i] > ratings[i + 1]) {
                height[i] = Math.max(height[i], height[i + 1] + 1);
            }
        }
        for (int i = 0; i < N; ++i) {
            res +=height[i];
        }
        return res;
    }
    public int candy_2(int[] ratings) {
        int N = ratings.length;
        if (N == 0) return 0;
        int candy = 1, res = 1;
        int maxVal = 1, maxIdx = 0;
        for (int i = 1; i < N; ++i) {
            if (ratings[i] >= ratings[i - 1]) {
                candy = ratings[i] == ratings[i - 1] ? 1 : candy + 1;
                maxVal = candy;
                maxIdx = i;
            } else {
                if (candy == 1) {
                    if (maxVal <= i - maxIdx) {
                        ++maxVal;
                        ++res;
                    }
                    res += i - maxIdx - 1;
                }
                candy = 1;
            }
            res += candy;
        }
        return res;
    }
}