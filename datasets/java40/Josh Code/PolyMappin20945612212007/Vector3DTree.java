package model;

import java.util.*;

/**
 * A collection of vectors
 */
public class Vector3DTree
{
  /** the 8 directions
   */
  private Vector3DTree[] subtrees;

  /** the vector this root node is associated with
   */
  private Vector3D rootV;
  private int numVectors; 

  public Vector3DTree(Vector3D v)
  {
    clearSubtrees();

    rootV = v;
    if (rootV != null)
        numVectors = 1;
    else
        numVectors = 0;
  }

    /** clears the subtrees
     */
    private void clearSubtrees()
    {
        subtrees = new Vector3DTree[8];
        for (int i = 0; i < subtrees.length; i++)
            subtrees[i] = null;
    }

    public synchronized LinkedList<Vector3D> asList()
    {
      LinkedList<Vector3D> result=new LinkedList<Vector3D>();
      if (rootV == null)
          return result;

      result.add(rootV);
      for (int i = 0; i < subtrees.length; i++)
      {
          if (subtrees[i]!=null)
            result.addAll(subtrees[i].asList());
      }
      return result;
    }

    /** removes all vectors including the root vector
     */
    public synchronized void clear()
    {
       numVectors = 0;
       rootV = null;
       clearSubtrees();
    }

    /** @return true iff the vector was added
     * @return false when the vector is not added.  A vector is not added when a duplicate already exists in the tree
     */
    public synchronized boolean add(Vector3D v)
   {
       if (v==null) // adding a null vector? That can't be done.
           return false;

       if (rootV == null)
       { // root not initialized?  Initialize it. 
           rootV = v;
           numVectors++;
           return true;
       }
       int subtreeIndex = getSubtreeIndexForVector(v);
       if (subtreeIndex < 0)
           return false; // don't add a duplicate vector to this tree

      if (subtrees[subtreeIndex] == null)
      {
          subtrees[subtreeIndex] = new Vector3DTree(v);
          numVectors++;
          return true;
      }
      else
      {
          if (subtrees[subtreeIndex].add(v))
          {
              numVectors++;
              return true;
          }
      }
      return false;
   }

    /** returns true iff the specified vector is found in this tree
     */
    public boolean contains(Vector3D v)
    {
      if (v == null) return false;
      Vector3D dup = getDuplicate(v);
      if (dup == null)
          return false;
      else
          return true;
    }

    /** returns the duplicate of the specified vector
     * @return null if no duplicate was found
     */
    public synchronized Vector3D getDuplicate(Vector3D v)
    {
        if (v == null) return null;
        int subtreeIndex = getSubtreeIndexForVector(v);
        if (subtreeIndex < 0)
            return rootV; // match found because v==rootV

        if (subtrees[subtreeIndex] == null)
            return null;
        else
            return subtrees[subtreeIndex].getDuplicate(v);    
    }

    /** returns -1 if the specified vector = rootV
     */
    private int getSubtreeIndexForVector(Vector3D v)
    {
        int comparisonValue = compareVectors(rootV, v);
        if (comparisonValue == 0)
            return -1;
        else
            return getIndexForComparedRelationship(comparisonValue);
    }

    /** returns the index for the subtree corresponding with the specified 3 dimensional comparison
     */
    private static int getIndexForComparedRelationship(int comparison)
    { 
      /* only greater comparisons result in a 1 bit in the subtree index
       */
      int result=0;

        // loop through bits of the result
      for (int i = 0; i < 3; i++)
      {
          result = result << 1;
          result = result | (comparison & 1);
          // use a 1 bit only for "greater" comparisons

          comparison = comparison >> 2;
      }
      return result;
    }

  /** a 3 dimensional comparison
   * returns a 6 bit binary value.
     * XXYYZZ
     * 00 -> equal
     * 01 -> greater
     * 10 -> less
   */
  private static int compareVectors(Vector3D v1, Vector3D v2)
  { 
      int result=0;
      result = oneDimensionalComparison(v1.z, v2.z);
      result |= oneDimensionalComparison(v1.y, v2.y) << 2;
      result |= oneDimensionalComparison(v1.x, v2.x) << 4;
      return result;
  }

    /** returns 0 if values are equal
     * returns 1 if s1>s2
     * returns 2 if s1<s2
     */
    private static int oneDimensionalComparison(double s1, double s2)
    {
        if (s1 == s2)
            return 0;
        else if (s1 > s2)
            return 1;
        else
            return 2;
    }

}


