// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DirectoryWalker.java

package org.apache.commons.io;

import java.io.*;
import java.util.Collection;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

public abstract class DirectoryWalker
{
    public static class CancelException extends IOException
    {

        public File getFile()
        {
            return file;
        }

        public int getDepth()
        {
            return depth;
        }

        private static final long serialVersionUID = 0x12b2b63ef9f577f0L;
        private final File file;
        private final int depth;

        public CancelException(File file, int depth)
        {
            this("Operation Cancelled", file, depth);
        }

        public CancelException(String message, File file, int depth)
        {
            super(message);
            this.file = file;
            this.depth = depth;
        }
    }


    protected DirectoryWalker()
    {
        this(null, -1);
    }

    protected DirectoryWalker(FileFilter filter, int depthLimit)
    {
        this.filter = filter;
        this.depthLimit = depthLimit;
    }

    protected DirectoryWalker(IOFileFilter directoryFilter, IOFileFilter fileFilter, int depthLimit)
    {
        if(directoryFilter == null && fileFilter == null)
        {
            filter = null;
        } else
        {
            directoryFilter = directoryFilter == null ? TrueFileFilter.TRUE : directoryFilter;
            fileFilter = fileFilter == null ? TrueFileFilter.TRUE : fileFilter;
            directoryFilter = FileFilterUtils.makeDirectoryOnly(directoryFilter);
            fileFilter = FileFilterUtils.makeFileOnly(fileFilter);
            filter = FileFilterUtils.or(new IOFileFilter[] {
                directoryFilter, fileFilter
            });
        }
        this.depthLimit = depthLimit;
    }

    protected final void walk(File startDirectory, Collection results)
        throws IOException
    {
        if(startDirectory == null)
            throw new NullPointerException("Start Directory is null");
        try
        {
            handleStart(startDirectory, results);
            walk(startDirectory, 0, results);
            handleEnd(results);
        }
        catch(CancelException cancel)
        {
            handleCancelled(startDirectory, results, cancel);
        }
    }

    private void walk(File directory, int depth, Collection results)
        throws IOException
    {
        checkIfCancelled(directory, depth, results);
        if(handleDirectory(directory, depth, results))
        {
            handleDirectoryStart(directory, depth, results);
            int childDepth = depth + 1;
            if(depthLimit < 0 || childDepth <= depthLimit)
            {
                checkIfCancelled(directory, depth, results);
                File childFiles[] = filter != null ? directory.listFiles(filter) : directory.listFiles();
                childFiles = filterDirectoryContents(directory, depth, childFiles);
                if(childFiles == null)
                {
                    handleRestricted(directory, childDepth, results);
                } else
                {
                    File arr$[] = childFiles;
                    int len$ = arr$.length;
                    for(int i$ = 0; i$ < len$; i$++)
                    {
                        File childFile = arr$[i$];
                        if(childFile.isDirectory())
                        {
                            walk(childFile, childDepth, results);
                        } else
                        {
                            checkIfCancelled(childFile, childDepth, results);
                            handleFile(childFile, childDepth, results);
                            checkIfCancelled(childFile, childDepth, results);
                        }
                    }

                }
            }
            handleDirectoryEnd(directory, depth, results);
        }
        checkIfCancelled(directory, depth, results);
    }

    protected final void checkIfCancelled(File file, int depth, Collection results)
        throws IOException
    {
        if(handleIsCancelled(file, depth, results))
            throw new CancelException(file, depth);
        else
            return;
    }

    protected boolean handleIsCancelled(File file, int depth, Collection results)
        throws IOException
    {
        return false;
    }

    protected void handleCancelled(File startDirectory, Collection results, CancelException cancel)
        throws IOException
    {
        throw cancel;
    }

    protected void handleStart(File file, Collection collection)
        throws IOException
    {
    }

    protected boolean handleDirectory(File directory, int depth, Collection results)
        throws IOException
    {
        return true;
    }

    protected void handleDirectoryStart(File file, int i, Collection collection)
        throws IOException
    {
    }

    protected File[] filterDirectoryContents(File directory, int depth, File files[])
        throws IOException
    {
        return files;
    }

    protected void handleFile(File file1, int i, Collection collection)
        throws IOException
    {
    }

    protected void handleRestricted(File file, int i, Collection collection)
        throws IOException
    {
    }

    protected void handleDirectoryEnd(File file, int i, Collection collection)
        throws IOException
    {
    }

    protected void handleEnd(Collection collection)
        throws IOException
    {
    }

    private final FileFilter filter;
    private final int depthLimit;
}
