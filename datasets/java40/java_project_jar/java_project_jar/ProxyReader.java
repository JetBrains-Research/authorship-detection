// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProxyReader.java

package org.apache.commons.io.input;

import java.io.*;
import java.nio.CharBuffer;

public abstract class ProxyReader extends FilterReader
{

    public ProxyReader(Reader proxy)
    {
        super(proxy);
    }

    public int read()
        throws IOException
    {
        int c;
        beforeRead(1);
        c = in.read();
        afterRead(c == -1 ? -1 : 1);
        return c;
        IOException e;
        e;
        handleIOException(e);
        return -1;
    }

    public int read(char chr[])
        throws IOException
    {
        int n;
        beforeRead(chr == null ? 0 : chr.length);
        n = in.read(chr);
        afterRead(n);
        return n;
        IOException e;
        e;
        handleIOException(e);
        return -1;
    }

    public int read(char chr[], int st, int len)
        throws IOException
    {
        int n;
        beforeRead(len);
        n = in.read(chr, st, len);
        afterRead(n);
        return n;
        IOException e;
        e;
        handleIOException(e);
        return -1;
    }

    public int read(CharBuffer target)
        throws IOException
    {
        int n;
        beforeRead(target == null ? 0 : target.length());
        n = in.read(target);
        afterRead(n);
        return n;
        IOException e;
        e;
        handleIOException(e);
        return -1;
    }

    public long skip(long ln)
        throws IOException
    {
        return in.skip(ln);
        IOException e;
        e;
        handleIOException(e);
        return 0L;
    }

    public boolean ready()
        throws IOException
    {
        return in.ready();
        IOException e;
        e;
        handleIOException(e);
        return false;
    }

    public void close()
        throws IOException
    {
        try
        {
            in.close();
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public synchronized void mark(int idx)
        throws IOException
    {
        try
        {
            in.mark(idx);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public synchronized void reset()
        throws IOException
    {
        try
        {
            in.reset();
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public boolean markSupported()
    {
        return in.markSupported();
    }

    protected void beforeRead(int i)
        throws IOException
    {
    }

    protected void afterRead(int i)
        throws IOException
    {
    }

    protected void handleIOException(IOException e)
        throws IOException
    {
        throw e;
    }
}
