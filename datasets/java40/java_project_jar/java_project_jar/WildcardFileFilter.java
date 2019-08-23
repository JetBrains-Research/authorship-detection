// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WildcardFileFilter.java

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter

public class WildcardFileFilter extends AbstractFileFilter
    implements Serializable
{

    public WildcardFileFilter(String wildcard)
    {
        this(wildcard, null);
    }

    public WildcardFileFilter(String wildcard, IOCase caseSensitivity)
    {
        if(wildcard == null)
        {
            throw new IllegalArgumentException("The wildcard must not be null");
        } else
        {
            wildcards = (new String[] {
                wildcard
            });
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public WildcardFileFilter(String wildcards[])
    {
        this(wildcards, null);
    }

    public WildcardFileFilter(String wildcards[], IOCase caseSensitivity)
    {
        if(wildcards == null)
        {
            throw new IllegalArgumentException("The wildcard array must not be null");
        } else
        {
            this.wildcards = new String[wildcards.length];
            System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public WildcardFileFilter(List wildcards)
    {
        this(wildcards, null);
    }

    public WildcardFileFilter(List wildcards, IOCase caseSensitivity)
    {
        if(wildcards == null)
        {
            throw new IllegalArgumentException("The wildcard list must not be null");
        } else
        {
            this.wildcards = (String[])wildcards.toArray(new String[wildcards.size()]);
            this.caseSensitivity = caseSensitivity != null ? caseSensitivity : IOCase.SENSITIVE;
            return;
        }
    }

    public boolean accept(File dir, String name)
    {
        String arr$[] = wildcards;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String wildcard = arr$[i$];
            if(FilenameUtils.wildcardMatch(name, wildcard, caseSensitivity))
                return true;
        }

        return false;
    }

    public boolean accept(File file)
    {
        String name = file.getName();
        String arr$[] = wildcards;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String wildcard = arr$[i$];
            if(FilenameUtils.wildcardMatch(name, wildcard, caseSensitivity))
                return true;
        }

        return false;
    }

    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        if(wildcards != null)
        {
            for(int i = 0; i < wildcards.length; i++)
            {
                if(i > 0)
                    buffer.append(",");
                buffer.append(wildcards[i]);
            }

        }
        buffer.append(")");
        return buffer.toString();
    }

    private final String wildcards[];
    private final IOCase caseSensitivity;
}
