package model;

/**
 A 3D vector.  Depending on detail of the model, there may be as much as a 
 * million vectors in RAM at a time so it is important to keep each instance as small as possible.
 * To save space used internally by method pointers, 
 * most methods related to vector have been defined in the vectorMath class.  Since static methods and overriding
*/
public class Vector3D
{
 double x,y,z;

    public static Vector3D getFromCoordinates(double x, double y, double z)
    {
        Vector3D result = new Vector3D();
        result.x = x;
        result.y = y;
        result.z = z;
        return result;
    }

    public static Vector3D getCloneOf(Vector3D v)
    {
        return (Vector3D)(v.clone());
    }

    protected Object clone()
    {
        return getFromCoordinates(x, y, z);
    }

    public boolean equals(Object o)
    {
        if (o instanceof Vector3D)
        {
            Vector3D v = (Vector3D)o;
            return (v.x == x) && (v.y == y) && (v.z == z);
        }
        else
            return false;
    }

    public String toString()
    {
        return getSpacedVectorCoordinates(this);
    }

    /** Sets the components of the specified vector
     */
    public static void setCoordinates(Vector3D v,double x, double y, double z)
    {
        v.x = x;
        v.y = y;
        v.z = z;
    }

    public static String getSpacedVectorCoordinates(Vector3D v)
    {
        return v.x + " " + v.y + " " + v.z;
    }

    public static double getX(Vector3D v)
    {
        return v.x;
    }

    public static double getY(Vector3D v)
    {
        return v.y;
    }

    public static double getZ(Vector3D v)
    {
        return v.z;
    }


}