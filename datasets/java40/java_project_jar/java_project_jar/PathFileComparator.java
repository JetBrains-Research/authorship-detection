// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PathFileComparator.java

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator, ReverseComparator

public class PathFileComparator extends AbstractFileComparator
    implements Serializable
{

    public PathFileComparator()
    {
        caseSensitivity = IOCase.SENSITIVE;
    }

    public PathFileComparator(IOCase caseSensitivity)
    {
        this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
    }

    public int compare(File file1, File file2)
    {
        return caseSensitivity.checkCompareTo(file1.getPath(), file2.getPath());
    }

    public String toString()
    {
        return (new StringBuilder()).append(super.toString()).append("[caseSensitivity=").append(caseSensitivity).append("]").toString();
    }

    public volatile int compare(Object x0, Object x1)
    {
        return compare((File)x0, (File)x1);
    }

    public static final Comparator PATH_COMPARATOR;
    public static final Comparator PATH_REVERSE;
    public static final Comparator PATH_INSENSITIVE_COMPARATOR;
    public static final Comparator PATH_INSENSITIVE_REVERSE;
    public static final Comparator PATH_SYSTEM_COMPARATOR;
    public static final Comparator PATH_SYSTEM_REVERSE;
    private final IOCase caseSensitivity;

    static 
    {
        PATH_COMPARATOR = new PathFileComparator();
        PATH_REVERSE = new ReverseComparator(PATH_COMPARATOR);
        PATH_INSENSITIVE_COMPARATOR = new PathFileComparator(IOCase.INSENSITIVE);
        PATH_INSENSITIVE_REVERSE = new ReverseComparator(PATH_INSENSITIVE_COMPARATOR);
        PATH_SYSTEM_COMPARATOR = new PathFileComparator(IOCase.SYSTEM);
        PATH_SYSTEM_REVERSE = new ReverseComparator(PATH_SYSTEM_COMPARATOR);
    }
}
