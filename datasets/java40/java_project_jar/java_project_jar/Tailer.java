// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Tailer.java

package org.apache.commons.io.input;

import java.io.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

// Referenced classes of package org.apache.commons.io.input:
//            TailerListener

public class Tailer
    implements Runnable
{

    public Tailer(File file, TailerListener listener)
    {
        this(file, listener, 1000L);
    }

    public Tailer(File file, TailerListener listener, long delay)
    {
        this(file, listener, 1000L, false);
    }

    public Tailer(File file, TailerListener listener, long delay, boolean end)
    {
        run = true;
        this.file = file;
        this.delay = delay;
        this.end = end;
        this.listener = listener;
        listener.init(this);
    }

    public static Tailer create(File file, TailerListener listener, long delay, boolean end)
    {
        Tailer tailer = new Tailer(file, listener, delay, end);
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    public static Tailer create(File file, TailerListener listener, long delay)
    {
        return create(file, listener, delay, false);
    }

    public static Tailer create(File file, TailerListener listener)
    {
        return create(file, listener, 1000L, false);
    }

    public File getFile()
    {
        return file;
    }

    public long getDelay()
    {
        return delay;
    }

    public void run()
    {
        RandomAccessFile reader = null;
        long last = 0L;
        long position = 0L;
        while(run && reader == null) 
        {
            try
            {
                reader = new RandomAccessFile(file, "r");
            }
            catch(FileNotFoundException e)
            {
                listener.fileNotFound();
            }
            if(reader == null)
            {
                try
                {
                    Thread.sleep(delay);
                }
                catch(InterruptedException e) { }
            } else
            {
                position = end ? file.length() : 0L;
                last = System.currentTimeMillis();
                reader.seek(position);
            }
        }
        while(run) 
        {
            long length = file.length();
            if(length < position)
            {
                listener.fileRotated();
                try
                {
                    RandomAccessFile save = reader;
                    reader = new RandomAccessFile(file, "r");
                    position = 0L;
                    IOUtils.closeQuietly(save);
                }
                catch(FileNotFoundException e)
                {
                    listener.fileNotFound();
                }
            } else
            {
                if(length > position)
                {
                    last = System.currentTimeMillis();
                    position = readLines(reader);
                } else
                if(FileUtils.isFileNewer(file, last))
                {
                    position = 0L;
                    reader.seek(position);
                    last = System.currentTimeMillis();
                    position = readLines(reader);
                }
                try
                {
                    Thread.sleep(delay);
                }
                catch(InterruptedException e) { }
            }
        }
        IOUtils.closeQuietly(reader);
        break MISSING_BLOCK_LABEL_275;
        Exception e;
        e;
        listener.handle(e);
        IOUtils.closeQuietly(reader);
        break MISSING_BLOCK_LABEL_275;
        Exception exception;
        exception;
        IOUtils.closeQuietly(reader);
        throw exception;
    }

    public void stop()
    {
        run = false;
    }

    private long readLines(RandomAccessFile reader)
        throws IOException
    {
        for(String line = reader.readLine(); line != null; line = reader.readLine())
            listener.handle(line);

        return reader.getFilePointer();
    }

    private final File file;
    private final long delay;
    private final boolean end;
    private final TailerListener listener;
    private volatile boolean run;
}
