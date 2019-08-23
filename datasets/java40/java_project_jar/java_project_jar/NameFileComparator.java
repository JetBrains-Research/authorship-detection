// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NameFileComparator.java

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator, ReverseComparator

public class NameFileComparator extends AbstractFileComparator
    implements Serializable
{

    public NameFileComparator()
    {
        caseSensitivity = IOCase.SENSITIVE;
    }

    public NameFileComparator(IOCase caseSensitivity)
    {
        this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
    }

    public int compare(File file1, File file2)
    {
        return caseSensitivity.checkCompareTo(file1.getName(), file2.getName());
    }

    public String toString()
    {
        return (new StringBuilder()).append(super.toString()).append("[caseSensitivity=").append(caseSensitivity).append("]").toString();
    }

    public volatile int compare(Object x0, Object x1)
    {
        return compare((File)x0, (File)x1);
    }

    public static final Comparator NAME_COMPARATOR;
    public static final Comparator NAME_REVERSE;
    public static final Comparator NAME_INSENSITIVE_COMPARATOR;
    public static final Comparator NAME_INSENSITIVE_REVERSE;
    public static final Comparator NAME_SYSTEM_COMPARATOR;
    public static final Comparator NAME_SYSTEM_REVERSE;
    private final IOCase caseSensitivity;

    static 
    {
        NAME_COMPARATOR = new NameFileComparator();
        NAME_REVERSE = new ReverseComparator(NAME_COMPARATOR);
        NAME_INSENSITIVE_COMPARATOR = new NameFileComparator(IOCase.INSENSITIVE);
        NAME_INSENSITIVE_REVERSE = new ReverseComparator(NAME_INSENSITIVE_COMPARATOR);
        NAME_SYSTEM_COMPARATOR = new NameFileComparator(IOCase.SYSTEM);
        NAME_SYSTEM_REVERSE = new ReverseComparator(NAME_SYSTEM_COMPARATOR);
    }
}
