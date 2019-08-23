// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CopyUtils.java

package org.apache.commons.io;

import java.io.*;

/**
 * @deprecated Class CopyUtils is deprecated
 */

public class CopyUtils
{

    public CopyUtils()
    {
    }

    public static void copy(byte input[], OutputStream output)
        throws IOException
    {
        output.write(input);
    }

    public static void copy(byte input[], Writer output)
        throws IOException
    {
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        copy(((InputStream) (in)), output);
    }

    public static void copy(byte input[], Writer output, String encoding)
        throws IOException
    {
        ByteArrayInputStream in = new ByteArrayInputStream(input);
        copy(((InputStream) (in)), output, encoding);
    }

    public static int copy(InputStream input, OutputStream output)
        throws IOException
    {
        byte buffer[] = new byte[4096];
        int count = 0;
        for(int n = 0; -1 != (n = input.read(buffer));)
        {
            output.write(buffer, 0, n);
            count += n;
        }

        return count;
    }

    public static int copy(Reader input, Writer output)
        throws IOException
    {
        char buffer[] = new char[4096];
        int count = 0;
        for(int n = 0; -1 != (n = input.read(buffer));)
        {
            output.write(buffer, 0, n);
            count += n;
        }

        return count;
    }

    public static void copy(InputStream input, Writer output)
        throws IOException
    {
        InputStreamReader in = new InputStreamReader(input);
        copy(((Reader) (in)), output);
    }

    public static void copy(InputStream input, Writer output, String encoding)
        throws IOException
    {
        InputStreamReader in = new InputStreamReader(input, encoding);
        copy(((Reader) (in)), output);
    }

    public static void copy(Reader input, OutputStream output)
        throws IOException
    {
        OutputStreamWriter out = new OutputStreamWriter(output);
        copy(input, ((Writer) (out)));
        out.flush();
    }

    public static void copy(String input, OutputStream output)
        throws IOException
    {
        StringReader in = new StringReader(input);
        OutputStreamWriter out = new OutputStreamWriter(output);
        copy(((Reader) (in)), ((Writer) (out)));
        out.flush();
    }

    public static void copy(String input, Writer output)
        throws IOException
    {
        output.write(input);
    }

    private static final int DEFAULT_BUFFER_SIZE = 4096;
}
