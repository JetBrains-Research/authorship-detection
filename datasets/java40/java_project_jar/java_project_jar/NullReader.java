// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NullReader.java

package org.apache.commons.io.input;

import java.io.*;

public class NullReader extends Reader
{

    public NullReader(long size)
    {
        this(size, true, false);
    }

    public NullReader(long size, boolean markSupported, boolean throwEofException)
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
            return processChar();
        }
    }

    public int read(char chars[])
        throws IOException
    {
        return read(chars, 0, chars.length);
    }

    public int read(char chars[], int offset, int length)
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
        processChars(chars, offset, returnLength);
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

    public long skip(long numberOfChars)
        throws IOException
    {
        if(eof)
            throw new IOException("Skip after end of file");
        if(position == size)
            return (long)doEndOfFile();
        position += numberOfChars;
        long returnLength = numberOfChars;
        if(position > size)
        {
            returnLength = numberOfChars - (position - size);
            position = size;
        }
        return returnLength;
    }

    protected int processChar()
    {
        return 0;
    }

    protected void processChars(char ac[], int i, int j)
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
