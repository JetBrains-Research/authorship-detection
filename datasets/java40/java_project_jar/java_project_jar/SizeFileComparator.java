// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SizeFileComparator.java

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.FileUtils;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator, ReverseComparator

public class SizeFileComparator extends AbstractFileComparator
    implements Serializable
{

    public SizeFileComparator()
    {
        sumDirectoryContents = false;
    }

    public SizeFileComparator(boolean sumDirectoryContents)
    {
        this.sumDirectoryContents = sumDirectoryContents;
    }

    public int compare(File file1, File file2)
    {
        long size1 = 0L;
        if(file1.isDirectory())
            size1 = !sumDirectoryContents || !file1.exists() ? 0L : FileUtils.sizeOfDirectory(file1);
        else
            size1 = file1.length();
        long size2 = 0L;
        if(file2.isDirectory())
            size2 = !sumDirectoryContents || !file2.exists() ? 0L : FileUtils.sizeOfDirectory(file2);
        else
            size2 = file2.length();
        long result = size1 - size2;
        if(result < 0L)
            return -1;
        return result <= 0L ? 0 : 1;
    }

    public String toString()
    {
        return (new StringBuilder()).append(super.toString()).append("[sumDirectoryContents=").append(sumDirectoryContents).append("]").toString();
    }

    public volatile int compare(Object x0, Object x1)
    {
        return compare((File)x0, (File)x1);
    }

    public static final Comparator SIZE_COMPARATOR;
    public static final Comparator SIZE_REVERSE;
    public static final Comparator SIZE_SUMDIR_COMPARATOR;
    public static final Comparator SIZE_SUMDIR_REVERSE;
    private final boolean sumDirectoryContents;

    static 
    {
        SIZE_COMPARATOR = new SizeFileComparator();
        SIZE_REVERSE = new ReverseComparator(SIZE_COMPARATOR);
        SIZE_SUMDIR_COMPARATOR = new SizeFileComparator(true);
        SIZE_SUMDIR_REVERSE = new ReverseComparator(SIZE_SUMDIR_COMPARATOR);
    }
}
