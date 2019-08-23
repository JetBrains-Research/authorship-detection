// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XmlStreamWriter.java

package org.apache.commons.io.output;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.input.XmlStreamReader;

public class XmlStreamWriter extends Writer
{

    public XmlStreamWriter(OutputStream out)
    {
        this(out, null);
    }

    public XmlStreamWriter(OutputStream out, String defaultEncoding)
    {
        xmlPrologWriter = new StringWriter(4096);
        this.out = out;
        this.defaultEncoding = defaultEncoding == null ? "UTF-8" : defaultEncoding;
    }

    public XmlStreamWriter(File file)
        throws FileNotFoundException
    {
        this(file, null);
    }

    public XmlStreamWriter(File file, String defaultEncoding)
        throws FileNotFoundException
    {
        this(((OutputStream) (new FileOutputStream(file))), defaultEncoding);
    }

    public String getEncoding()
    {
        return encoding;
    }

    public String getDefaultEncoding()
    {
        return defaultEncoding;
    }

    public void close()
        throws IOException
    {
        if(writer == null)
        {
            encoding = defaultEncoding;
            writer = new OutputStreamWriter(out, encoding);
            writer.write(xmlPrologWriter.toString());
        }
        writer.close();
    }

    public void flush()
        throws IOException
    {
        if(writer != null)
            writer.flush();
    }

    private void detectEncoding(char cbuf[], int off, int len)
        throws IOException
    {
        int size = len;
        StringBuffer xmlProlog = xmlPrologWriter.getBuffer();
        if(xmlProlog.length() + len > 4096)
            size = 4096 - xmlProlog.length();
        xmlPrologWriter.write(cbuf, off, size);
        if(xmlProlog.length() >= 5)
        {
            if(xmlProlog.substring(0, 5).equals("<?xml"))
            {
                int xmlPrologEnd = xmlProlog.indexOf("?>");
                if(xmlPrologEnd > 0)
                {
                    Matcher m = ENCODING_PATTERN.matcher(xmlProlog.substring(0, xmlPrologEnd));
                    if(m.find())
                    {
                        encoding = m.group(1).toUpperCase();
                        encoding = encoding.substring(1, encoding.length() - 1);
                    } else
                    {
                        encoding = defaultEncoding;
                    }
                } else
                if(xmlProlog.length() >= 4096)
                    encoding = defaultEncoding;
            } else
            {
                encoding = defaultEncoding;
            }
            if(encoding != null)
            {
                xmlPrologWriter = null;
                writer = new OutputStreamWriter(out, encoding);
                writer.write(xmlProlog.toString());
                if(len > size)
                    writer.write(cbuf, off + size, len - size);
            }
        }
    }

    public void write(char cbuf[], int off, int len)
        throws IOException
    {
        if(xmlPrologWriter != null)
            detectEncoding(cbuf, off, len);
        else
            writer.write(cbuf, off, len);
    }

    private static final int BUFFER_SIZE = 4096;
    private final OutputStream out;
    private final String defaultEncoding;
    private StringWriter xmlPrologWriter;
    private Writer writer;
    private String encoding;
    static final Pattern ENCODING_PATTERN;

    static 
    {
        ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;
    }
}
