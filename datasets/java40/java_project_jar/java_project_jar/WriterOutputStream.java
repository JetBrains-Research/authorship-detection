// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WriterOutputStream.java

package org.apache.commons.io.output;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;

public class WriterOutputStream extends OutputStream
{

    public WriterOutputStream(Writer writer, Charset charset, int bufferSize, boolean writeImmediately)
    {
        decoderIn = ByteBuffer.allocate(128);
        this.writer = writer;
        decoder = charset.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPLACE);
        decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
        decoder.replaceWith("?");
        this.writeImmediately = writeImmediately;
        decoderOut = CharBuffer.allocate(bufferSize);
    }

    public WriterOutputStream(Writer writer, Charset charset)
    {
        this(writer, charset, 1024, false);
    }

    public WriterOutputStream(Writer writer, String charsetName, int bufferSize, boolean writeImmediately)
    {
        this(writer, Charset.forName(charsetName), bufferSize, writeImmediately);
    }

    public WriterOutputStream(Writer writer, String charsetName)
    {
        this(writer, charsetName, 1024, false);
    }

    public WriterOutputStream(Writer writer)
    {
        this(writer, Charset.defaultCharset(), 1024, false);
    }

    public void write(byte b[], int off, int len)
        throws IOException
    {
        while(len > 0) 
        {
            int c = Math.min(len, decoderIn.remaining());
            decoderIn.put(b, off, c);
            processInput(false);
            len -= c;
            off += c;
        }
        if(writeImmediately)
            flushOutput();
    }

    public void write(byte b[])
        throws IOException
    {
        write(b, 0, b.length);
    }

    public void write(int b)
        throws IOException
    {
        write(new byte[] {
            (byte)b
        }, 0, 1);
    }

    public void flush()
        throws IOException
    {
        flushOutput();
        writer.flush();
    }

    public void close()
        throws IOException
    {
        processInput(true);
        flushOutput();
        writer.close();
    }

    private void processInput(boolean endOfInput)
        throws IOException
    {
        decoderIn.flip();
        CoderResult coderResult;
        do
        {
            coderResult = decoder.decode(decoderIn, decoderOut, endOfInput);
            if(!coderResult.isOverflow())
                break;
            flushOutput();
        } while(true);
        if(!coderResult.isUnderflow())
        {
            throw new IOException("Unexpected coder result");
        } else
        {
            decoderIn.compact();
            return;
        }
    }

    private void flushOutput()
        throws IOException
    {
        if(decoderOut.position() > 0)
        {
            writer.write(decoderOut.array(), 0, decoderOut.position());
            decoderOut.rewind();
        }
    }

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final Writer writer;
    private final CharsetDecoder decoder;
    private final boolean writeImmediately;
    private final ByteBuffer decoderIn;
    private final CharBuffer decoderOut;
}
