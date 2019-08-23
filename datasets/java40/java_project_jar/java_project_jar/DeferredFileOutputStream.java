// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeferredFileOutputStream.java

package org.apache.commons.io.output;

import java.io.*;
import org.apache.commons.io.IOUtils;

// Referenced classes of package org.apache.commons.io.output:
//            ThresholdingOutputStream, ByteArrayOutputStream

public class DeferredFileOutputStream extends ThresholdingOutputStream
{

    public DeferredFileOutputStream(int threshold, File outputFile)
    {
        this(threshold, outputFile, null, null, null);
    }

    public DeferredFileOutputStream(int threshold, String prefix, String suffix, File directory)
    {
        this(threshold, null, prefix, suffix, directory);
        if(prefix == null)
            throw new IllegalArgumentException("Temporary file prefix is missing");
        else
            return;
    }

    private DeferredFileOutputStream(int threshold, File outputFile, String prefix, String suffix, File directory)
    {
        super(threshold);
        closed = false;
        this.outputFile = outputFile;
        memoryOutputStream = new ByteArrayOutputStream();
        currentOutputStream = memoryOutputStream;
        this.prefix = prefix;
        this.suffix = suffix;
        this.directory = directory;
    }

    protected OutputStream getStream()
        throws IOException
    {
        return currentOutputStream;
    }

    protected void thresholdReached()
        throws IOException
    {
        if(prefix != null)
            outputFile = File.createTempFile(prefix, suffix, directory);
        FileOutputStream fos = new FileOutputStream(outputFile);
        memoryOutputStream.writeTo(fos);
        currentOutputStream = fos;
        memoryOutputStream = null;
    }

    public boolean isInMemory()
    {
        return !isThresholdExceeded();
    }

    public byte[] getData()
    {
        if(memoryOutputStream != null)
            return memoryOutputStream.toByteArray();
        else
            return null;
    }

    public File getFile()
    {
        return outputFile;
    }

    public void close()
        throws IOException
    {
        super.close();
        closed = true;
    }

    public void writeTo(OutputStream out)
        throws IOException
    {
        FileInputStream fis;
        if(!closed)
            throw new IOException("Stream not closed");
        if(isInMemory())
        {
            memoryOutputStream.writeTo(out);
            break MISSING_BLOCK_LABEL_67;
        }
        fis = new FileInputStream(outputFile);
        IOUtils.copy(fis, out);
        IOUtils.closeQuietly(fis);
        break MISSING_BLOCK_LABEL_67;
        Exception exception;
        exception;
        IOUtils.closeQuietly(fis);
        throw exception;
    }

    private ByteArrayOutputStream memoryOutputStream;
    private OutputStream currentOutputStream;
    private File outputFile;
    private final String prefix;
    private final String suffix;
    private final File directory;
    private boolean closed;
}
