/*
 Author:     King, wangjingui@outlook.com
 Date:       Dec 14, 2014 
 Problem:    Maximum Gap
 Difficulty: Hard
 Source:     https://oj.leetcode.com/problems/maximum-gap/
 Notes:
 Given an unsorted array, find the maximum difference between the successive elements in its sorted form.

 Try to solve it in linear time/space.

 Return 0 if the array contains less than 2 elements.

 You may assume all elements in the array are non-negative integers and fit in the 32-bit signed integer range.

 Solution: 1. Time : O(nlogn). Space : O(1); 
              Sort the unsorted array, and find the maximum difference.
           2. Time : O(n). Space : O(n).
              Drawer Theory. If we put n numbers into (n+1) drawers, 
              then there must be at least one empty drawer. 
              So we can find the maximum difference between two succesive non-empty drawers.
 */
public class Solution {
    public int maximumGap_1(int[] num) {
        Arrays.sort(num);
        int res = 0;
        for (int i = 1; i < num.length; ++i) {
            res = Math.max(res, num[i] - num[i - 1]);
        }
        return res;
    }
    class node {
        public int low;
        public int high;
        public node() {
            low = -1;
            high = -1;
        }
    }
    public int maximumGap_2(int[] num) {
        int n = num.length;
        if (n < 2) return 0;
        int minVal = num[0], maxVal = num[0];
        for (int i = 1; i < n; ++i) {
            minVal = Math.min(minVal, num[i]);
            maxVal = Math.max(maxVal, num[i]);
        }
        //delta = (maxVal + 1 - minVal) / (n + 1)
        //idx = (val - minVal) / delta = (val - minVal) * (n + 1) / (maxVal + 1 - minVal)
        node[] pool = new node[n+2];
        for (int i = 0; i < n+2; ++i) pool[i] = new node();
        for (int i = 0; i < n; ++i) {
            int idx =(int)(Long.valueOf(num[i] - minVal)* Long.valueOf(n + 1) / Long.valueOf(maxVal + 1 - minVal));
            if (pool[idx].low == -1) {
                pool[idx].low = pool[idx].high = num[i];
            } else {
                pool[idx].low = Math.min(pool[idx].low, num[i]);
                pool[idx].high = Math.max(pool[idx].high, num[i]);
            }
        }
        int pre = pool[0].high;
        int res = 0;
        for (int i = 1; i < n + 2; ++i) {
            if (pool[i].low != -1) {
                res = Math.max(res, pool[i].low - pre);
                pre = pool[i].high;
            }
        }
        return res;
    }
}