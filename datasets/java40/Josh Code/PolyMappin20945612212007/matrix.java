package model;


public class matrix
{
    /** dimensions of this matrix
     * m = width, number of columns
     * n = height, number of rows
     */
  private int n, m;
  private double[][] values;

    public matrix(int n, int m)
    {
        this.n = n;
        this.m = m;
        allocateValues();
        fill(0); // initialize all elements to 0
    }

    private void allocateValues()
    {
        values = new double[m][];
        for (int i=0;i<m;i++)
            values[i]=new double[n];    
    }

    /** initializes by copying all values over
     */
    public void init(matrix mat)
    {
       m = mat.m;
       n= mat.n;
       allocateValues();
        for (int i=0;i<m;i++)
        {
           for (int j=0;j<n;j++)
               values[i][j] = mat.values[i][j];
        }
    }

    /**
     * creates a 3 by 3 matrix for rotating a three dimensional vector around the specified axis by the specified angle
     * @param axis is the number of dimensions into the vector to find the one being rotated around
     * @param angle is the angle of rotation
     */
    public static matrix get3By3RotationMatrix(int axis,double angle)
    {
        matrix result = getIdentity(3);

        double sin_Theta = Math.sin(angle);
        double cos_Theta = Math.cos(angle);
        double negative_sin_Theta = -sin_Theta;

        if (axis == 0 || axis == 2)
        { // x or z
            int startX, startY;
            if (axis == 0) // x axis
            {
                startX = 0;
                startY = 0;
            }
            else // z axis
            {
                startX = 1;
                startY = 1;
            }

            result.values[startX][startY] = cos_Theta;
            result.values[startX][startY + 1] = sin_Theta;
            result.values[startX + 1][startY] = negative_sin_Theta;
            result.values[startX + 1][startY + 1] = cos_Theta;
        }
        else
        {
            result.values[0][0] = cos_Theta;
            result.values[0][2] = negative_sin_Theta;
            result.values[2][0] = sin_Theta;
            result.values[2][2] = cos_Theta;        
        }

        return result;
    }

    /** returns a rotation matrix that will rotate around x,y, and z axis 
     * @param by radian angles specified in each component of rotationComponents vector
     */
    public static matrix get3By3RotationMatrix(Vector3D rotationComponents)
    {
      matrix xRotation = get3By3RotationMatrix(0,rotationComponents.x);
      matrix yRotation = get3By3RotationMatrix(1,rotationComponents.y);
      matrix zRotation = get3By3RotationMatrix(2,rotationComponents.z);
      matrix result = getIdentity(3);

      try
      {
          result.multiplyBy(xRotation);
          result.multiplyBy(yRotation);
          result.multiplyBy(zRotation);
      }
      catch (IncompatibleArgumentsException iae)
      {
          System.out.println("problem while creating rotation matrix: "+iae);
      }
        return result;
    }

    /** basically this = this*mat
     * @param m must be a matrix of compatable dimensions for multiplication
     */
    public void multiplyBy(matrix mat) throws IncompatibleArgumentsException
    {
      if (mat.m!=n)
          throw new IncompatibleArgumentsException(
              "Matrix multiplication can't be calculated "
              +"because of invalid dimensions.");
        
        matrix result= multiply(this,mat);

        init(result); // copy all information from result into this
    }

    /** returns m1*m2
     */
    public static matrix multiply(matrix m1,matrix m2) throws IncompatibleArgumentsException
    {
       if (m1.m!=m2.n)
          throw new IncompatibleArgumentsException(
              "Matrix multiplication can't be calculated "
              +"because of invalid dimensions.");

        int rm=m2.m,rn=m1.n;
        matrix result=new matrix(rm,rn);

        // do the tripple loop
        for (int i=0;i<rm;i++)
            for (int j=0;j<rn;j++)
            {
              double val=0;
               for (int t=0;t<m2.n;t++)
               {
                  val+=m1.values[t][j]*m2.values[i][t];             
               }
               result.values[i][j] = val;
            }
        return result;
    }


    /** returns a size by size identity matrix
     */
    public static matrix getIdentity(int size)
    {
        matrix result = new matrix(size, size);
        result.fill(0);
        for (int i = 0; i < size; i++)
            result.values[i][i] = 1;

        return result;
    }

    /** sets each value in this matrix to the specified value
     */
    public void fill(double value)
    {
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
                values[i][j] = value;
        }
    }

    /** Basically: v = this*v
     * Assumes this matrix is a 3 by 3 matrix for efficiency purposes
     */
    public void multiplyAndStore3By3(Vector3D v)
    {
        double []resultComponents = new double[3];
        double []vectorComponents = new double[3];
       
          vectorComponents[0] = v.x;
          vectorComponents[1] = v.y;
          vectorComponents[2] = v.z;

        for (int row = 0; row < 3; row++)
        { 
            resultComponents[row] = 0;
         // resultComponents[row] = matrix row dot v
            for (int c = 0; c < 3;c++)
            {
               resultComponents[row]+=
                   values[c][row]*vectorComponents[c];
            }
        }

        v.x = resultComponents[0];
        v.y = resultComponents[1];
        v.z = resultComponents[2];
    }

}


