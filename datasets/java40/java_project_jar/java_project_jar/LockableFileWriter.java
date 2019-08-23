// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LockableFileWriter.java

package org.apache.commons.io.output;

import java.io.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class LockableFileWriter extends Writer
{

    public LockableFileWriter(String fileName)
        throws IOException
    {
        this(fileName, false, null);
    }

    public LockableFileWriter(String fileName, boolean append)
        throws IOException
    {
        this(fileName, append, null);
    }

    public LockableFileWriter(String fileName, boolean append, String lockDir)
        throws IOException
    {
        this(new File(fileName), append, lockDir);
    }

    public LockableFileWriter(File file)
        throws IOException
    {
        this(file, false, null);
    }

    public LockableFileWriter(File file, boolean append)
        throws IOException
    {
        this(file, append, null);
    }

    public LockableFileWriter(File file, boolean append, String lockDir)
        throws IOException
    {
        this(file, null, append, lockDir);
    }

    public LockableFileWriter(File file, String encoding)
        throws IOException
    {
        this(file, encoding, false, null);
    }

    public LockableFileWriter(File file, String encoding, boolean append, String lockDir)
        throws IOException
    {
        file = file.getAbsoluteFile();
        if(file.getParentFile() != null)
            FileUtils.forceMkdir(file.getParentFile());
        if(file.isDirectory())
            throw new IOException("File specified is a directory");
        if(lockDir == null)
            lockDir = System.getProperty("java.io.tmpdir");
        File lockDirFile = new File(lockDir);
        FileUtils.forceMkdir(lockDirFile);
        testLockDir(lockDirFile);
        lockFile = new File(lockDirFile, (new StringBuilder()).append(file.getName()).append(".lck").toString());
        createLock();
        out = initWriter(file, encoding, append);
    }

    private void testLockDir(File lockDir)
        throws IOException
    {
        if(!lockDir.exists())
            throw new IOException((new StringBuilder()).append("Could not find lockDir: ").append(lockDir.getAbsolutePath()).toString());
        if(!lockDir.canWrite())
            throw new IOException((new StringBuilder()).append("Could not write to lockDir: ").append(lockDir.getAbsolutePath()).toString());
        else
            return;
    }

    private void createLock()
        throws IOException
    {
        synchronized(org/apache/commons/io/output/LockableFileWriter)
        {
            if(!lockFile.createNewFile())
                throw new IOException((new StringBuilder()).append("Can't write file, lock ").append(lockFile.getAbsolutePath()).append(" exists").toString());
            lockFile.deleteOnExit();
        }
    }

    private Writer initWriter(File file, String encoding, boolean append)
        throws IOException
    {
        boolean fileExistedAlready = file.exists();
        java.io.OutputStream stream = null;
        Writer writer = null;
        try
        {
            if(encoding == null)
            {
                writer = new FileWriter(file.getAbsolutePath(), append);
            } else
            {
                stream = new FileOutputStream(file.getAbsolutePath(), append);
                writer = new OutputStreamWriter(stream, encoding);
            }
        }
        catch(IOException ex)
        {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(stream);
            FileUtils.deleteQuietly(lockFile);
            if(!fileExistedAlready)
                FileUtils.deleteQuietly(file);
            throw ex;
        }
        catch(RuntimeException ex)
        {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(stream);
            FileUtils.deleteQuietly(lockFile);
            if(!fileExistedAlready)
                FileUtils.deleteQuietly(file);
            throw ex;
        }
        return writer;
    }

    public void close()
        throws IOException
    {
        out.close();
        lockFile.delete();
        break MISSING_BLOCK_LABEL_29;
        Exception exception;
        exception;
        lockFile.delete();
        throw exception;
    }

    public void write(int idx)
        throws IOException
    {
        out.write(idx);
    }

    public void write(char chr[])
        throws IOException
    {
        out.write(chr);
    }

    public void write(char chr[], int st, int end)
        throws IOException
    {
        out.write(chr, st, end);
    }

    public void write(String str)
        throws IOException
    {
        out.write(str);
    }

    public void write(String str, int st, int end)
        throws IOException
    {
        out.write(str, st, end);
    }

    public void flush()
        throws IOException
    {
        out.flush();
    }

    private static final String LCK = ".lck";
    private final Writer out;
    private final File lockFile;
}
