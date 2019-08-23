/*
 Author:     Andy, nkuwjg@gmail.com
 Date:       Dec 12, 2014
 Problem:    Binary Tree Level Order Traversal II
 Difficulty: easy
 Source:     https://oj.leetcode.com/problems/binary-tree-level-order-traversal-ii/
 Notes:
 Given a binary tree, return the bottom-up level order traversal of its nodes' values. 
 (ie, from left to right, level by level from leaf to root).

 For example:
 Given binary tree {3,9,20,#,#,15,7},
    3
   / \
  9  20
    /  \
   15   7
 return its bottom-up level order traversal as:
 [
  [15,7]
  [9,20],
  [3],
 ]
 
 Solution: Queue version. On the basis of 'Binary Tree Level Order Traversal', reverse the final vector.
 */

/**
* Definition for binary tree
* public class TreeNode {
*     int val;
*     TreeNode left;
*     TreeNode right;
*     TreeNode(int x) { val = x; }
* }
*/
public class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        q.offer(null);
        List<Integer> level = new ArrayList<Integer>();
        
        while(true) {
            TreeNode node = q.poll();
            if (node != null) {
                level.add(node.val);
                if(node.left!=null) q.offer(node.left);
                if(node.right!=null) q.offer(node.right);
            } else {
                res.add(level);
                level = new ArrayList<Integer>();
                if(q.isEmpty()==true) break;
                q.offer(null);
            }
        }
        Collections.reverse(res);
        return res;
    }
}