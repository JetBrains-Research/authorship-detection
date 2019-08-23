// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NullInputStream.java

package org.apache.commons.io.input;

import java.io.*;

public class NullInputStream extends InputStream
{

    public NullInputStream(long size)
    {
        this(size, true, false);
    }

    public NullInputStream(long size, boolean markSupported, boolean throwEofException)
    {
        mark = -1L;
        this.size = size;
        this.markSupported = markSupported;
        this.throwEofException = throwEofException;
    }

    public long getPosition()
    {
        return position;
    }

    public long getSize()
    {
        return size;
    }

    public int available()
    {
        long avail = size - position;
        if(avail <= 0L)
            return 0;
        if(avail > 0x7fffffffL)
            return 0x7fffffff;
        else
            return (int)avail;
    }

    public void close()
        throws IOException
    {
        eof = false;
        position = 0L;
        mark = -1L;
    }

    public synchronized void mark(int readlimit)
    {
        if(!markSupported)
        {
            throw new UnsupportedOperationException("Mark not supported");
        } else
        {
            mark = position;
            this.readlimit = readlimit;
            return;
        }
    }

    public boolean markSupported()
    {
        return markSupported;
    }

    public int read()
        throws IOException
    {
        if(eof)
            throw new IOException("Read after end of file");
        if(position == size)
        {
            return doEndOfFile();
        } else
        {
            position++;
            return processByte();
        }
    }

    public int read(byte bytes[])
        throws IOException
    {
        return read(bytes, 0, bytes.length);
    }

    public int read(byte bytes[], int offset, int length)
        throws IOException
    {
        if(eof)
            throw new IOException("Read after end of file");
        if(position == size)
            return doEndOfFile();
        position += length;
        int returnLength = length;
        if(position > size)
        {
            returnLength = length - (int)(position - size);
            position = size;
        }
        processBytes(bytes, offset, returnLength);
        return returnLength;
    }

    public synchronized void reset()
        throws IOException
    {
        if(!markSupported)
            throw new UnsupportedOperationException("Mark not supported");
        if(mark < 0L)
            throw new IOException("No position has been marked");
        if(position > mark + readlimit)
        {
            throw new IOException((new StringBuilder()).append("Marked position [").append(mark).append("] is no longer valid - passed the read limit [").append(readlimit).append("]").toString());
        } else
        {
            position = mark;
            eof = false;
            return;
        }
    }

    public long skip(long numberOfBytes)
        throws IOException
    {
        if(eof)
            throw new IOException("Skip after end of file");
        if(position == size)
            return (long)doEndOfFile();
        position += numberOfBytes;
        long returnLength = numberOfBytes;
        if(position > size)
        {
            returnLength = numberOfBytes - (position - size);
            position = size;
        }
        return returnLength;
    }

    protected int processByte()
    {
        return 0;
    }

    protected void processBytes(byte abyte0[], int i, int j)
    {
    }

    private int doEndOfFile()
        throws EOFException
    {
        eof = true;
        if(throwEofException)
            throw new EOFException();
        else
            return -1;
    }

    private final long size;
    private long position;
    private long mark;
    private long readlimit;
    private boolean eof;
    private final boolean throwEofException;
    private final boolean markSupported;
}
