package fileIO;

import model.*;
import java.io.*;
import java.util.*;

public class STLModelFileLoader implements ModelFileLoader
{
  public boolean matchesFormat(File f)
  {
      String name = f.getName();
      name = name.toLowerCase();

      if (name.endsWith(".stl")||name.endsWith(".stla"))
          return true;
      else
          return false;
  }

  /** Loads the model from the specified file
   */
    public void loadModel(Model m, File f, loadProgressListener progressListener) throws IOException
  {
      loadFromSTL(m, f,progressListener);
  }

    public String getDescription()
    {
        return "(*.stl) Stereolithography";
    }

  /** loads from a stereolithic file
   * http://orion.math.iastate.edu/burkardt/data/stl/stl.html
   */
    public static Model loadFromSTL(Model m, File f, loadProgressListener progressListener) throws IOException
  {
      BufferedReader br = new BufferedReader(new FileReader(f));

      loadFromSTL(br, m,progressListener);

      return m;
  }

    private static void loadFromSTL(BufferedReader br, Model m, loadProgressListener progressListener)
     throws IOException
  {
      while (true)
      {
          String ln = br.readLine();
          if (ln == null)
              break;
          ln = ln.trim();
          if (ln.equals(""))
              continue;
          if (ln.startsWith("facet"))
              loadSTLFacet(br, m,progressListener);
      }
      // loop through facets

  }

    private static void loadSTLFacet(BufferedReader br, Model m, 
         loadProgressListener progressListener) throws IOException
  {
      br.readLine(); // outer loop statement
      LinkedList<Vector3D> vertices = new LinkedList<Vector3D>();

      while (true)
      {
          double x, y, z;
          String ln = br.readLine();
          if (ln == null)
              break;
          ln = ln.trim();
          if (ln.startsWith("endfacet"))
              break;

          if (ln.startsWith("endloop") || ln.equals(""))
              continue;

          if (ln.startsWith("vertex"))
              ln = ln.substring(7);
          /* remove the prefix "vertex" from ln so it won't be a token
          */

          StringTokenizer st = new StringTokenizer(ln, " \t");
          x = Double.parseDouble(st.nextToken());
          y = Double.parseDouble(st.nextToken());
          z = Double.parseDouble(st.nextToken());

          Vector3D v = Vector3D.getFromCoordinates(x, y, z);
          vertices.add(v);
      }
      Vector3D[] vertAsArray = new Vector3D[vertices.size()];
      System.arraycopy(vertices.toArray(), 0, vertAsArray, 0, vertAsArray.length);
      // loop through vertices
      for (int i = 2; i < vertAsArray.length; i++)
      {
          Triangle t = new Triangle(vertAsArray[0], vertAsArray[i - 1], vertAsArray[i]);
          m.addTriangle(t);
      }
  }

}
