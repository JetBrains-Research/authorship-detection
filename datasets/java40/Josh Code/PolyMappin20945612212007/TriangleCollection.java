package model;

import java.util.*;

/** a collection of Triangle objects.
 * This is used for sorting polygons for painting
 */
public class TriangleCollection
    implements Comparator<Triangle>
{

  /** viewpoint and view direction needed to be known for comparing 
   * polygons in sorting algorithms
  */
 private RenderingBuffer buffer;

    public void setRenderingBuffer(RenderingBuffer buffer)
    {
        this.buffer = buffer;
    }


   public boolean equals(Object obj) 
   {
       return false;  
   }

   /** compares 
    */
   public int compare(Triangle t1, Triangle t2)
   {
       Vector3D v1 = t1.getCentre();
       Vector3D v2 = t2.getCentre();
         if (v1.z>v2.z)
             return 1;
         else if (v1.z<v2.z)
             return -1;
         else
            return 0;
   }

   private void bubbleSort(Triangle []triangles)
   { 
     boolean sorted = false;
     int i;
     int maxI = triangles.length;
     Triangle t;

       while (!sorted)    
       {
           sorted = true;
           for (i=1;i<maxI; i++)
           {
               t = triangles[i - 1];
               if (compare(t,triangles[i])>0)
               {// swap
                   triangles[i-1]=triangles[i];
                   triangles[i]=t;
                   sorted = false;
                   // indicate not sorted
               }
           }
           maxI--; // don't go all the way to the end next time
       }
    }

    private void insertionSort(Triangle[] triangles)
    { 
      int len = triangles.length;
      int j;

      Triangle value;
      for (int i = 1; i < len;i++ )
      {
          value = triangles[i];
          j = i - 1;
          while (j >= 0 && compare(triangles[j], value) > 0)
          {
              triangles[j + 1] = triangles[j];
              j--;
          }
          triangles[j + 1] = value;
      }
    }

    private void quickSort(Triangle[] triangles)
    {
        Arrays.sort(triangles,this);
    }

    /** Counts the number of pairs that are out of order
     * and swaps any that are to bring them
     */
    private int countOutOfOrder(Triangle []triangles)
    {
      int result = 0;
        for (int i = 1; i < triangles.length; i++)
        {
            if (compare(triangles[i - 1], triangles[i]) > 0)
                result++;
        }
        return result;
    }

    /** sorts using either bubble sort or quick sort depending on how out of order the list is
     */
   public void sort(Triangle []triangles)
   {
    int numOutofOrder = countOutOfOrder(triangles);

      if (numOutofOrder > 10)
          quickSort(triangles);
      else
          insertionSort(triangles);
   }

}



