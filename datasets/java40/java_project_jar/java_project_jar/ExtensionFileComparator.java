// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExtensionFileComparator.java

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator, ReverseComparator

public class ExtensionFileComparator extends AbstractFileComparator
    implements Serializable
{

    public ExtensionFileComparator()
    {
        caseSensitivity = IOCase.SENSITIVE;
    }

    public ExtensionFileComparator(IOCase caseSensitivity)
    {
        this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
    }

    public int compare(File file1, File file2)
    {
        String suffix1 = FilenameUtils.getExtension(file1.getName());
        String suffix2 = FilenameUtils.getExtension(file2.getName());
        return caseSensitivity.checkCompareTo(suffix1, suffix2);
    }

    public String toString()
    {
        return (new StringBuilder()).append(super.toString()).append("[caseSensitivity=").append(caseSensitivity).append("]").toString();
    }

    public volatile int compare(Object x0, Object x1)
    {
        return compare((File)x0, (File)x1);
    }

    public static final Comparator EXTENSION_COMPARATOR;
    public static final Comparator EXTENSION_REVERSE;
    public static final Comparator EXTENSION_INSENSITIVE_COMPARATOR;
    public static final Comparator EXTENSION_INSENSITIVE_REVERSE;
    public static final Comparator EXTENSION_SYSTEM_COMPARATOR;
    public static final Comparator EXTENSION_SYSTEM_REVERSE;
    private final IOCase caseSensitivity;

    static 
    {
        EXTENSION_COMPARATOR = new ExtensionFileComparator();
        EXTENSION_REVERSE = new ReverseComparator(EXTENSION_COMPARATOR);
        EXTENSION_INSENSITIVE_COMPARATOR = new ExtensionFileComparator(IOCase.INSENSITIVE);
        EXTENSION_INSENSITIVE_REVERSE = new ReverseComparator(EXTENSION_INSENSITIVE_COMPARATOR);
        EXTENSION_SYSTEM_COMPARATOR = new ExtensionFileComparator(IOCase.SYSTEM);
        EXTENSION_SYSTEM_REVERSE = new ReverseComparator(EXTENSION_SYSTEM_COMPARATOR);
    }
}
