// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WildcardFilter.java

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter

/**
 * @deprecated Class WildcardFilter is deprecated
 */

public class WildcardFilter extends AbstractFileFilter
    implements Serializable
{

    public WildcardFilter(String wildcard)
    {
        if(wildcard == null)
        {
            throw new IllegalArgumentException("The wildcard must not be null");
        } else
        {
            wildcards = (new String[] {
                wildcard
            });
            return;
        }
    }

    public WildcardFilter(String wildcards[])
    {
        if(wildcards == null)
        {
            throw new IllegalArgumentException("The wildcard array must not be null");
        } else
        {
            this.wildcards = new String[wildcards.length];
            System.arraycopy(wildcards, 0, this.wildcards, 0, wildcards.length);
            return;
        }
    }

    public WildcardFilter(List wildcards)
    {
        if(wildcards == null)
        {
            throw new IllegalArgumentException("The wildcard list must not be null");
        } else
        {
            this.wildcards = (String[])wildcards.toArray(new String[wildcards.size()]);
            return;
        }
    }

    public boolean accept(File dir, String name)
    {
        if(dir != null && (new File(dir, name)).isDirectory())
            return false;
        String arr$[] = wildcards;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String wildcard = arr$[i$];
            if(FilenameUtils.wildcardMatch(name, wildcard))
                return true;
        }

        return false;
    }

    public boolean accept(File file)
    {
        if(file.isDirectory())
            return false;
        String arr$[] = wildcards;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            String wildcard = arr$[i$];
            if(FilenameUtils.wildcardMatch(file.getName(), wildcard))
                return true;
        }

        return false;
    }

    private final String wildcards[];
}
