// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileEntry.java

package org.apache.commons.io.monitor;

import java.io.File;
import java.io.Serializable;

public class FileEntry
    implements Serializable
{

    public FileEntry(File file)
    {
        this((FileEntry)null, file);
    }

    public FileEntry(FileEntry parent, File file)
    {
        if(file == null)
        {
            throw new IllegalArgumentException("File is missing");
        } else
        {
            this.file = file;
            this.parent = parent;
            name = file.getName();
            return;
        }
    }

    public boolean refresh(File file)
    {
        boolean origExists = exists;
        long origLastModified = lastModified;
        boolean origDirectory = directory;
        long origLength = length;
        name = file.getName();
        exists = file.exists();
        directory = exists ? file.isDirectory() : false;
        lastModified = exists ? file.lastModified() : 0L;
        length = !exists || directory ? 0L : file.length();
        return exists != origExists || lastModified != origLastModified || directory != origDirectory || length != origLength;
    }

    public FileEntry newChildInstance(File file)
    {
        return new FileEntry(this, file);
    }

    public FileEntry getParent()
    {
        return parent;
    }

    public int getLevel()
    {
        return parent != null ? parent.getLevel() + 1 : 0;
    }

    public FileEntry[] getChildren()
    {
        return children == null ? EMPTY_ENTRIES : children;
    }

    public void setChildren(FileEntry children[])
    {
        this.children = children;
    }

    public File getFile()
    {
        return file;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(long lastModified)
    {
        this.lastModified = lastModified;
    }

    public long getLength()
    {
        return length;
    }

    public void setLength(long length)
    {
        this.length = length;
    }

    public boolean isExists()
    {
        return exists;
    }

    public void setExists(boolean exists)
    {
        this.exists = exists;
    }

    public boolean isDirectory()
    {
        return directory;
    }

    public void setDirectory(boolean directory)
    {
        this.directory = directory;
    }

    static final FileEntry EMPTY_ENTRIES[] = new FileEntry[0];
    private final FileEntry parent;
    private FileEntry children[];
    private final File file;
    private String name;
    private boolean exists;
    private boolean directory;
    private long lastModified;
    private long length;

}
