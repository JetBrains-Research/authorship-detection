/*
 Author:     King, wangjingui@outlook.com
 Date:       Dec 25, 2014
 Problem:    Spiral Matrix
 Difficulty: Easy
 Source:     https://oj.leetcode.com/problems/spiral-matrix/
 Notes:
 Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
 For example,
 Given the following matrix:
 [
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
 ]
 You should return [1,2,3,6,9,8,7,4,5].

 Solution: ...
 */
public class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (matrix.length == 0 || matrix[0].length == 0) return res;
        int n = matrix.length, m = matrix[0].length, row = 0, col = -1;
        while (true) {
            for (int i = 0; i < m; ++i) res.add(matrix[row][++col]);
            if (--n == 0) break;
            for (int i = 0; i < n; ++i) res.add(matrix[++row][col]);
            if (--m == 0) break;
            for (int i = 0; i < m; ++i) res.add(matrix[row][--col]);
            if (--n == 0) break;
            for (int i = 0; i < n; ++i) res.add(matrix[--row][col]);
            if (--m == 0) break;
        }
        return res;
    }
}