package model;


public class vectorMath
{ 
    /** stores the result in the result parameter to save some processing time by not allocating a new Vector3D object
     */
  public static void cross(Vector3D v1,Vector3D v2,Vector3D result)
  {
      result.x = v1.y * v2.z - v1.z * v2.y;
      result.y = v1.z * v2.x - v1.x * v2.z;
      result.z = v1.x * v2.y - v1.y * v2.x;
  }

  /** 
   * Returns a vector v1 scaled in each component by the corresponding components in v2
   * (v1.x*v2.x,v1.y*v2.y,v1.z*v2.z) ie. result.x+result.y+result.z = dot(v1,v2)
   * stores the result in the result parameter to save some processing time
   * by not allocating a new Vector3D object
   */
  public static void scale(Vector3D v1, Vector3D v2, Vector3D result)
  {
      result.x = v1.x * v2.x;
      result.y = v1.y * v2.y;
      result.z = v1.z * v2.z;
  }

    /** creates a new vector to store the value of v1-v2
     */
  public static Vector3D subtract(Vector3D v1, Vector3D v2)
  {
    Vector3D result = Vector3D.getFromCoordinates(
        v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
     return result;
  }

    /** returns a vector with average component values from the ones in the parameter array
     */
  public static Vector3D getAverage(Vector3D[] vectors)
  {
    if (vectors.length == 0)
       return null;
      
    Vector3D result = Vector3D.getFromCoordinates(0, 0, 0);
    int len = vectors.length;
     // local int variables are quicker accessed than properties of classes

      for (int i = 0; i < len; i++)
      { 
          Vector3D v = vectors[i]; 
          /* don't make the virtual machine recalculate the subscript expression each time
           */
          result.x += v.x;
          result.y += v.y;
          result.z += v.z;
      }

      double d = 1.0 / vectors.length;
        result.x *= d;
        result.y *= d;
        result.z *= d;

      return result;
  }

    public static double dotProduct(Vector3D v1, Vector3D v2)
    {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    /** Returns cosine of the angle separating v from unit vector uv.
     * @param uv must be of magnitude 1 or this will not work properly.
     */
    public static double getCosSeparationWithUnitVector(Vector3D v, Vector3D uv)
    {
        double result =
            dotProduct(v, uv)/magnitude(v);
           ;
        return result;
    }

    public static double magnitude(Vector3D v)
    {
        return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
    }

    /** sets the components of the vector v to one of the same direction but magnitude of 1
     */
    public static void asUnitVector(Vector3D v)
    { 
      double m = magnitude(v);
      if (Math.abs(m) < 0.00001)
      {
          v.x = 1;
          v.y = 0;
          v.z = 0; // just some unit vector
      }
      else
      {
          m = 1 / m;
          v.x *= m;
          v.y *= m;
          v.z *= m;
      }
    }



    public static void rotateVectors(Vector3D[] vectors, Vector3D rotationVector)
    { 
      // create the rotation matrix
      matrix rotationM = matrix.get3By3RotationMatrix(rotationVector);

            // loop through vectors
      for (int i = 0; i < vectors.length; i++)
      {
          // map the current vector through the rotation matrix
          rotationM.multiplyAndStore3By3(vectors[i]);
      }
    }
}


