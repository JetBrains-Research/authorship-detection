// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MagicNumberFileFilter.java

package org.apache.commons.io.filefilter;

import java.io.*;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter

public class MagicNumberFileFilter extends AbstractFileFilter
    implements Serializable
{

    public MagicNumberFileFilter(byte magicNumber[])
    {
        this(magicNumber, 0L);
    }

    public MagicNumberFileFilter(String magicNumber)
    {
        this(magicNumber, 0L);
    }

    public MagicNumberFileFilter(String magicNumber, long offset)
    {
        if(magicNumber == null)
            throw new IllegalArgumentException("The magic number cannot be null");
        if(magicNumber.length() == 0)
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        if(offset < 0L)
        {
            throw new IllegalArgumentException("The offset cannot be negative");
        } else
        {
            magicNumbers = magicNumber.getBytes();
            byteOffset = offset;
            return;
        }
    }

    public MagicNumberFileFilter(byte magicNumber[], long offset)
    {
        if(magicNumber == null)
            throw new IllegalArgumentException("The magic number cannot be null");
        if(magicNumber.length == 0)
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        if(offset < 0L)
        {
            throw new IllegalArgumentException("The offset cannot be negative");
        } else
        {
            magicNumbers = new byte[magicNumber.length];
            System.arraycopy(magicNumber, 0, magicNumbers, 0, magicNumber.length);
            byteOffset = offset;
            return;
        }
    }

    public boolean accept(File file)
    {
        RandomAccessFile randomAccessFile;
        if(file == null || !file.isFile() || !file.canRead())
            break MISSING_BLOCK_LABEL_108;
        randomAccessFile = null;
        byte fileBytes[];
        boolean flag;
        fileBytes = new byte[magicNumbers.length];
        randomAccessFile = new RandomAccessFile(file, "r");
        randomAccessFile.seek(byteOffset);
        int read = randomAccessFile.read(fileBytes);
        if(read == magicNumbers.length)
            break MISSING_BLOCK_LABEL_74;
        flag = false;
        IOUtils.closeQuietly(randomAccessFile);
        return flag;
        boolean flag1;
        try
        {
            flag1 = Arrays.equals(magicNumbers, fileBytes);
        }
        catch(IOException ioe)
        {
            IOUtils.closeQuietly(randomAccessFile);
            break MISSING_BLOCK_LABEL_108;
        }
        finally
        {
            IOUtils.closeQuietly(randomAccessFile);
            throw exception;
        }
        IOUtils.closeQuietly(randomAccessFile);
        return flag1;
        return false;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append("(");
        builder.append(new String(magicNumbers));
        builder.append(",");
        builder.append(byteOffset);
        builder.append(")");
        return builder.toString();
    }

    private static final long serialVersionUID = 0xf8660f93da39d554L;
    private final byte magicNumbers[];
    private final long byteOffset;
}
