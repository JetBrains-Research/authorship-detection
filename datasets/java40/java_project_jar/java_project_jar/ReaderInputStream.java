// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReaderInputStream.java

package org.apache.commons.io.input;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

public class ReaderInputStream extends InputStream
{

    public ReaderInputStream(Reader reader, Charset charset, int bufferSize)
    {
        encoderOut = ByteBuffer.allocate(128);
        this.reader = reader;
        encoder = charset.newEncoder();
        encoderIn = CharBuffer.allocate(bufferSize);
        encoderIn.flip();
    }

    public ReaderInputStream(Reader reader, Charset charset)
    {
        this(reader, charset, 1024);
    }

    public ReaderInputStream(Reader reader, String charsetName, int bufferSize)
    {
        this(reader, Charset.forName(charsetName), bufferSize);
    }

    public ReaderInputStream(Reader reader, String charsetName)
    {
        this(reader, charsetName, 1024);
    }

    public ReaderInputStream(Reader reader)
    {
        this(reader, Charset.defaultCharset());
    }

    public int read(byte b[], int off, int len)
        throws IOException
    {
        int read = 0;
label0:
        do
        {
            do
            {
                if(len <= 0)
                    break label0;
                if(encoderOut.position() <= 0)
                    break;
                encoderOut.flip();
                int c = Math.min(encoderOut.remaining(), len);
                encoderOut.get(b, off, c);
                off += c;
                len -= c;
                read += c;
                encoderOut.compact();
            } while(true);
            if(!endOfInput && (lastCoderResult == null || lastCoderResult.isUnderflow()))
            {
                encoderIn.compact();
                int position = encoderIn.position();
                int c = reader.read(encoderIn.array(), position, encoderIn.remaining());
                if(c == -1)
                    endOfInput = true;
                else
                    encoderIn.position(position + c);
                encoderIn.flip();
            }
            lastCoderResult = encoder.encode(encoderIn, encoderOut, endOfInput);
        } while(!endOfInput || encoderOut.position() != 0);
        return read != 0 || !endOfInput ? read : -1;
    }

    public int read(byte b[])
        throws IOException
    {
        return read(b, 0, b.length);
    }

    public int read()
        throws IOException
    {
        byte b[] = new byte[1];
        return read(b) != -1 ? b[0] & 0xff : -1;
    }

    public void close()
        throws IOException
    {
        reader.close();
    }

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final Reader reader;
    private final CharsetEncoder encoder;
    private final CharBuffer encoderIn;
    private final ByteBuffer encoderOut;
    private CoderResult lastCoderResult;
    private boolean endOfInput;
}
