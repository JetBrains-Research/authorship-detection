// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThresholdingOutputStream.java

package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ThresholdingOutputStream extends OutputStream
{

    public ThresholdingOutputStream(int threshold)
    {
        this.threshold = threshold;
    }

    public void write(int b)
        throws IOException
    {
        checkThreshold(1);
        getStream().write(b);
        written++;
    }

    public void write(byte b[])
        throws IOException
    {
        checkThreshold(b.length);
        getStream().write(b);
        written += b.length;
    }

    public void write(byte b[], int off, int len)
        throws IOException
    {
        checkThreshold(len);
        getStream().write(b, off, len);
        written += len;
    }

    public void flush()
        throws IOException
    {
        getStream().flush();
    }

    public void close()
        throws IOException
    {
        try
        {
            flush();
        }
        catch(IOException ignored) { }
        getStream().close();
    }

    public int getThreshold()
    {
        return threshold;
    }

    public long getByteCount()
    {
        return written;
    }

    public boolean isThresholdExceeded()
    {
        return written > (long)threshold;
    }

    protected void checkThreshold(int count)
        throws IOException
    {
        if(!thresholdExceeded && written + (long)count > (long)threshold)
        {
            thresholdExceeded = true;
            thresholdReached();
        }
    }

    protected void resetByteCount()
    {
        thresholdExceeded = false;
        written = 0L;
    }

    protected abstract OutputStream getStream()
        throws IOException;

    protected abstract void thresholdReached()
        throws IOException;

    private final int threshold;
    private long written;
    private boolean thresholdExceeded;
}
