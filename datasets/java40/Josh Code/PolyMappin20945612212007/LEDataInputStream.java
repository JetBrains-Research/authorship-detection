
package fileIO;

import java.io.*;

/** Little Endian Data Input Stream is used mainly for compatability with software written in other languages such as c++.
 * This class is not safe for multithreaded access.
 */
public class LEDataInputStream
{
    /**
     * powersOfTwo[x+1023] = 2 to the power of x for -1024 < x < 1025
     */
 private static final double[] powersOfTwo = getPowersOfTwo();
 private InputStream is;
 private DataInputStream dis;

    /** used to speed up reading of lines
     */
 private char tempChars[] = new char[1000];


  public LEDataInputStream(InputStream is)
  {
     this.is = is;
     dis = new DataInputStream(is);
  }

    private int unsignedByte(byte b)
    {
        if (b >= 0)
            return b;
        else
            return 256+b;
    }

    public void close() throws IOException
    {
        is.close();
    }

    public int read() throws IOException
    {
        return is.read();
    }

    public int readUnsignedByte() throws IOException
    {
        return unsignedByte((byte)is.read());
    }

    public int read(byte[] bytes) throws IOException 
    {
        return is.read(bytes);
    }

    public int readShort() throws IOException
    {
        int result = is.read();
        if (result < 0)// end of file reached
            throw new EOFException();

        int r2 = is.read();
        if (r2 < 0)// end of file reached
            throw new EOFException();

        return result | (r2*256);
    }

    public int readShortBigEndian() throws IOException
    {
        int result = is.read();
        if (result < 0)// end of file reached
            throw new EOFException();

        int r2 = is.read();
        if (r2 < 0)// end of file reached
            throw new EOFException();

        return result*256 | r2;
    
    }

    public String readString(int len) throws IOException
    {
       if (len < 0) return "";
       byte[]resultBytes = new byte[len];
       if (is.read(resultBytes) < 0)
           throw new EOFException();
       else
           return new String(resultBytes);
    }

    /** reads a line up to and including the next '\n' but will omit the '\n' from the result
     */
    public String readLine() throws IOException
    {
      String result = "";
      int c = is.read();
      int index = 0;

      if (c < 0) // if nothing was read in
          return null; // indicate end of stream reached

      while (c != '\n' && (c>=0))
      {
          if (index>=tempChars.length)
          {
              result = result+(new String(tempChars));
              index = 0;
          }
          tempChars[index] = (char)c;
          index++;
          c = is.read();
      }
      result = result + (new String(tempChars, 0, index));
      return result;
    }

    public int readInt() throws IOException
  {
    int result;
    byte[] data = new byte[4];

     result = is.read(data);
     if (result < 4) // end of file reached
         throw new EOFException();

     result = (unsignedByte(data[2]) << 16) | (unsignedByte(data[1]) << 8) | unsignedByte(data[0]);
     result|=(unsignedByte(data[3]) << 24);

     return result;
  }

    /** Reads a 4-byte int from big endian
     */
    public int readIntBigEndian() throws IOException
    {
        int result;
        byte[] data = new byte[4];

        result = is.read(data);
        if (result < 4) // end of file reached
            throw new EOFException();

        result = (unsignedByte(data[1]) << 16) | (unsignedByte(data[2]) << 8) | unsignedByte(data[3]);
        result |=(unsignedByte(data[0]) << 24);
        return result;
    }

    private void reverseArray(byte[] a)
    {
        for (int i = 0; i < a.length / 2; i++)
        {
            byte temp = a[i];
            a[i] = a[a.length - i-1];
            a[a.length - i - 1] = temp;
        }
    }

    /** reads a little endian form of the 4 byte IEEE 754
     */
    public float readFloat() throws IOException
    {
        int bits = readInt();

        float result = Float.intBitsToFloat(bits);
        return result;
    }

    /** Reads a 4 byte IEEE 754 float in big endian format
     * This would be the same as the Java API's DataInputStream's readFloat
     */
    public float readFloatBigEndian() throws IOException 
    {
       int bitData = readIntBigEndian();
        
        return Float.intBitsToFloat(bitData);
    }

    /** Reads a little endian version of the 8 byte IEEE 754 type
     * This method has been timed and executes an average of only 90% to 120% 
     * the time of its big endian counterpart.
     */
    public double readDouble()throws IOException
  {
    //  return dis.readDouble();
        
     byte[] data = new byte[8];
     double result;
     double fraction;
     int e;
     // return dis.readDouble();
       
       is.read(data); // read the bytes of the double

     //  reverseArray(data);
        // long to avoid problems with missinterpretation of the sign bit from an int
       fraction = ((unsignedByte(data[6]) & 0x0f) << 8) | unsignedByte(data[5]);
       fraction *= 0x10000;
       fraction += (unsignedByte(data[4])<<8);
       fraction *= 0x1000000;
       fraction += (long)((unsignedByte(data[3]) << 24) | (unsignedByte(data[2]) << 16) | 
           (unsignedByte(data[1]) << 8) | unsignedByte(data[0]));

       fraction = fraction / powersOfTwo[52+1023];
       if (fraction > 1)
       {
           System.out.println("problem: fraction should be less than 1: "+fraction);
           fraction = 1;
       }
       e = ((unsignedByte(data[7])&0x007f) << 4) | (((unsignedByte(data[6])&0x00f0)>> 4));
       e = e - 1023;

       result = (1 + fraction) * powersOfTwo[e+1023];

       if (unsignedByte(data[7]) > 0x79)
          result = -result;

      // convert the bytes into the result
     return result;
  }    

    private static double[] getPowersOfTwo()
    { 
      double[]result = new double[2048];

      for (int i = 0; i < result.length; i++)
      {
          result[i] = Math.pow(2, i-1023);
      }
      return result;
    }
}