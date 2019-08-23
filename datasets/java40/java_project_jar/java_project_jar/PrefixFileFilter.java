// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrefixFileFilter.java

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter

public class PrefixFileFilter extends AbstractFileFilter
    implements Serializable
{

    public PrefixFileFilter(String prefix)
    {
        this(prefix, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(String prefix, IOCase caseSensitivity)
    {
        if(prefix == null)
        {
            throw new IllegalArgumentException("The prefix must not be null");
        } else
        {
            prefixes = (new String[] {
                prefix
            });
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public PrefixFileFilter(String prefixes[])
    {
        this(prefixes, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(String prefixes[], IOCase caseSensitivity)
    {
        if(prefixes == null)
        {
            throw new IllegalArgumentException("The array of prefixes must not be null");
        } else
        {
            this.prefixes = new String[prefixes.length];
            System.arraycopy(prefixes, 0, this.prefixes, 0, prefixes.length);
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public PrefixFileFilter(List prefixes)
    {
        this(prefixes, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(List prefixes, IOCase caseSensitivity)
    {
        if(prefixes == null)
        {
            throw new IllegalArgumentException("The list of prefixes must not be null");
        } else
        {
            this.prefixes = (String[])prefixes.toArray(new String[prefixes.size()]);
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public boolean accept(File file)
    {
        String name = file.getName();
        String arr$[] = prefixes;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String prefix = arr$[i$];
            if(caseSensitivity.checkStartsWith(name, prefix))
                return true;
        }

        return false;
    }

    public boolean accept(File file, String name)
    {
        String arr$[] = prefixes;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String prefix = arr$[i$];
            if(caseSensitivity.checkStartsWith(name, prefix))
                return true;
        }

        return false;
    }

    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        if(prefixes != null)
        {
            for(int i = 0; i < prefixes.length; i++)
            {
                if(i > 0)
                    buffer.append(",");
                buffer.append(prefixes[i]);
            }

        }
        buffer.append(")");
        return buffer.toString();
    }

    private final String prefixes[];
    private final IOCase caseSensitivity;
}
