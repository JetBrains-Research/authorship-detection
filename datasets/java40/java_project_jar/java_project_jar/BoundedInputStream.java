// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BoundedInputStream.java

package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class BoundedInputStream extends InputStream
{

    public BoundedInputStream(InputStream in, long size)
    {
        pos = 0L;
        mark = -1L;
        propagateClose = true;
        max = size;
        this.in = in;
    }

    public BoundedInputStream(InputStream in)
    {
        this(in, -1L);
    }

    public int read()
        throws IOException
    {
        if(max >= 0L && pos == max)
        {
            return -1;
        } else
        {
            int result = in.read();
            pos++;
            return result;
        }
    }

    public int read(byte b[])
        throws IOException
    {
        return read(b, 0, b.length);
    }

    public int read(byte b[], int off, int len)
        throws IOException
    {
        if(max >= 0L && pos >= max)
            return -1;
        long maxRead = max < 0L ? len : Math.min(len, max - pos);
        int bytesRead = in.read(b, off, (int)maxRead);
        if(bytesRead == -1)
        {
            return -1;
        } else
        {
            pos += bytesRead;
            return bytesRead;
        }
    }

    public long skip(long n)
        throws IOException
    {
        long toSkip = max < 0L ? n : Math.min(n, max - pos);
        long skippedBytes = in.skip(toSkip);
        pos += skippedBytes;
        return skippedBytes;
    }

    public int available()
        throws IOException
    {
        if(max >= 0L && pos >= max)
            return 0;
        else
            return in.available();
    }

    public String toString()
    {
        return in.toString();
    }

    public void close()
        throws IOException
    {
        if(propagateClose)
            in.close();
    }

    public synchronized void reset()
        throws IOException
    {
        in.reset();
        pos = mark;
    }

    public synchronized void mark(int readlimit)
    {
        in.mark(readlimit);
        mark = pos;
    }

    public boolean markSupported()
    {
        return in.markSupported();
    }

    public boolean isPropagateClose()
    {
        return propagateClose;
    }

    public void setPropagateClose(boolean propagateClose)
    {
        this.propagateClose = propagateClose;
    }

    private final InputStream in;
    private final long max;
    private long pos;
    private long mark;
    private boolean propagateClose;
}
