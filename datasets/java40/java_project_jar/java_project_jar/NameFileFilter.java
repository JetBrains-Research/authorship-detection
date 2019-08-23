// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NameFileFilter.java

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter

public class NameFileFilter extends AbstractFileFilter
    implements Serializable
{

    public NameFileFilter(String name)
    {
        this(name, null);
    }

    public NameFileFilter(String name, IOCase caseSensitivity)
    {
        if(name == null)
        {
            throw new IllegalArgumentException("The wildcard must not be null");
        } else
        {
            names = (new String[] {
                name
            });
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public NameFileFilter(String names[])
    {
        this(names, null);
    }

    public NameFileFilter(String names[], IOCase caseSensitivity)
    {
        if(names == null)
        {
            throw new IllegalArgumentException("The array of names must not be null");
        } else
        {
            this.names = new String[names.length];
            System.arraycopy(names, 0, this.names, 0, names.length);
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public NameFileFilter(List names)
    {
        this(names, null);
    }

    public NameFileFilter(List names, IOCase caseSensitivity)
    {
        if(names == null)
        {
            throw new IllegalArgumentException("The list of names must not be null");
        } else
        {
            this.names = (String[])names.toArray(new String[names.size()]);
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public boolean accept(File file)
    {
        String name = file.getName();
        String arr$[] = names;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String name2 = arr$[i$];
            if(caseSensitivity.checkEquals(name, name2))
                return true;
        }

        return false;
    }

    public boolean accept(File file, String name)
    {
        String arr$[] = names;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String name2 = arr$[i$];
            if(caseSensitivity.checkEquals(name, name2))
                return true;
        }

        return false;
    }

    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        if(names != null)
        {
            for(int i = 0; i < names.length; i++)
            {
                if(i > 0)
                    buffer.append(",");
                buffer.append(names[i]);
            }

        }
        buffer.append(")");
        return buffer.toString();
    }

    private final String names[];
    private final IOCase caseSensitivity;
}
