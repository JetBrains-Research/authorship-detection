package model;


import java.awt.*;
import java.util.*;

/**
 * Represents a 3D model
 */
public class Model
{
  private RenderingBuffer renderBuf;
  private Vector3D []vertices;
    private boolean needVertexUpdating;
  Triangle triangles[];
  int numTriangles;


    public Model()
    { 
       triangles = new Triangle[100];
       numTriangles = 0;
       needVertexUpdating = true;
    }

    public void setRenderingBuffer(RenderingBuffer renderer)
    {
        for (int i = 0; i < numTriangles; i++)
            triangles[i].setRenderingBuffer(renderer);

        renderBuf = renderer;
    }

    public void addTriangle(Triangle t)
    {
        if (numTriangles == triangles.length)
        {
            Triangle []old = triangles;
            triangles = new Triangle[numTriangles*2];
            for (int i = 0; i < numTriangles; i++)
            {
                triangles[i] = old[i];
            }
        }
        triangles[numTriangles] = t;
        numTriangles++;
        needVertexUpdating=true; // indicate the vertex collection must be updated
    }

    /** updates the vertices array to refer to all vertexes in this model
     */
    public void updateVertexCollection()
    {
        if (!needVertexUpdating)
            return;

        if (triangles == null)
            return;

      Vector3DTree vt = new Vector3DTree(null);

       for (int i = 0; i < numTriangles; i++)
       {
          int offset = i * 3;
          for (int vi = 0; vi < 3;vi++ )
          {
            Vector3D v = Triangle.getVertex(triangles[i], vi); 
            if (!vt.add(v))
            {
               v = vt.getDuplicate(v);
               Triangle.setVertex(triangles[i],vi,v);
            }
          }
       }
       LinkedList<Vector3D> uniqueVertices = vt.asList();
       vertices = new Vector3D[uniqueVertices.size()];
       System.arraycopy(uniqueVertices.toArray(), 0, vertices,
           0, vertices.length);

       needVertexUpdating = false;
    }


    /** eliminates unused Triangle references in the triangles array
     */
    public void tightenTriangleArray()
    {
        if (triangles.length == numTriangles)
            return;

        Triangle[] newArray = new Triangle[numTriangles];
        for (int i = 0; i < numTriangles; i++)
            newArray[i] = triangles[i];

        triangles = newArray;
    }

    /** dots all vectors with the specified vector
     */
    public void scaleVectors(Vector3D v)
    {
        updateVertexCollection();
        for (int i = 0; i < vertices.length; i++)
        {
            vectorMath.scale(v, vertices[i], vertices[i]);
        }
        verticesUpdated();
    }

    /** rotates all points around the x,y, and z axis by angles 
     * in radians in the specified rotationVector
     */
    public void rotateModel(Vector3D rotationVector)
    {
        updateVertexCollection();
        vectorMath.rotateVectors(vertices,rotationVector);
        verticesUpdated();
    }

    public void verticesUpdated()
    {
        for (int i = 0; i < numTriangles; i++)
            triangles[i].verticesUpdated();
    }

    public Image getImage()
    {
        return renderBuf.getImage();
    }

    public int getNumberOfTriangles()
    {
       return numTriangles;
    }

    public Triangle[] getTriangles()
    {
       return triangles;
    }
}