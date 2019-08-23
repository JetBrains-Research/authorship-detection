// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProxyWriter.java

package org.apache.commons.io.output;

import java.io.*;

public class ProxyWriter extends FilterWriter
{

    public ProxyWriter(Writer proxy)
    {
        super(proxy);
    }

    public Writer append(char c)
        throws IOException
    {
        try
        {
            beforeWrite(1);
            out.append(c);
            afterWrite(1);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
        return this;
    }

    public Writer append(CharSequence csq, int start, int end)
        throws IOException
    {
        try
        {
            beforeWrite(end - start);
            out.append(csq, start, end);
            afterWrite(end - start);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
        return this;
    }

    public Writer append(CharSequence csq)
        throws IOException
    {
        try
        {
            int len = 0;
            if(csq != null)
                len = csq.length();
            beforeWrite(len);
            out.append(csq);
            afterWrite(len);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
        return this;
    }

    public void write(int idx)
        throws IOException
    {
        try
        {
            beforeWrite(1);
            out.write(idx);
            afterWrite(1);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public void write(char chr[])
        throws IOException
    {
        try
        {
            int len = 0;
            if(chr != null)
                len = chr.length;
            beforeWrite(len);
            out.write(chr);
            afterWrite(len);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public void write(char chr[], int st, int len)
        throws IOException
    {
        try
        {
            beforeWrite(len);
            out.write(chr, st, len);
            afterWrite(len);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public void write(String str)
        throws IOException
    {
        try
        {
            int len = 0;
            if(str != null)
                len = str.length();
            beforeWrite(len);
            out.write(str);
            afterWrite(len);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public void write(String str, int st, int len)
        throws IOException
    {
        try
        {
            beforeWrite(len);
            out.write(str, st, len);
            afterWrite(len);
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public void flush()
        throws IOException
    {
        try
        {
            out.flush();
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    public void close()
        throws IOException
    {
        try
        {
            out.close();
        }
        catch(IOException e)
        {
            handleIOException(e);
        }
    }

    protected void beforeWrite(int i)
        throws IOException
    {
    }

    protected void afterWrite(int i)
        throws IOException
    {
    }

    protected void handleIOException(IOException e)
        throws IOException
    {
        throw e;
    }

    public volatile Appendable append(char x0)
        throws IOException
    {
        return append(x0);
    }

    public volatile Appendable append(CharSequence x0, int x1, int x2)
        throws IOException
    {
        return append(x0, x1, x2);
    }

    public volatile Appendable append(CharSequence x0)
        throws IOException
    {
        return append(x0);
    }
}
