// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrFileFilter.java

package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.*;

// Referenced classes of package org.apache.commons.io.filefilter:
//            AbstractFileFilter, IOFileFilter, ConditionalFileFilter

public class OrFileFilter extends AbstractFileFilter
    implements ConditionalFileFilter, Serializable
{

    public OrFileFilter()
    {
        fileFilters = new ArrayList();
    }

    public OrFileFilter(List fileFilters)
    {
        if(fileFilters == null)
            this.fileFilters = new ArrayList();
        else
            this.fileFilters = new ArrayList(fileFilters);
    }

    public OrFileFilter(IOFileFilter filter1, IOFileFilter filter2)
    {
        if(filter1 == null || filter2 == null)
        {
            throw new IllegalArgumentException("The filters must not be null");
        } else
        {
            fileFilters = new ArrayList(2);
            addFileFilter(filter1);
            addFileFilter(filter2);
            return;
        }
    }

    public void addFileFilter(IOFileFilter ioFileFilter)
    {
        fileFilters.add(ioFileFilter);
    }

    public List getFileFilters()
    {
        return Collections.unmodifiableList(fileFilters);
    }

    public boolean removeFileFilter(IOFileFilter ioFileFilter)
    {
        return fileFilters.remove(ioFileFilter);
    }

    public void setFileFilters(List fileFilters)
    {
        this.fileFilters.clear();
        this.fileFilters.addAll(fileFilters);
    }

    public boolean accept(File file)
    {
        for(Iterator i$ = fileFilters.iterator(); i$.hasNext();)
        {
            IOFileFilter fileFilter = (IOFileFilter)i$.next();
            if(fileFilter.accept(file))
                return true;
        }

        return false;
    }

    public boolean accept(File file, String name)
    {
        for(Iterator i$ = fileFilters.iterator(); i$.hasNext();)
        {
            IOFileFilter fileFilter = (IOFileFilter)i$.next();
            if(fileFilter.accept(file, name))
                return true;
        }

        return false;
    }

    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("(");
        if(fileFilters != null)
        {
            for(int i = 0; i < fileFilters.size(); i++)
            {
                if(i > 0)
                    buffer.append(",");
                Object filter = fileFilters.get(i);
                buffer.append(filter != null ? filter.toString() : "null");
            }

        }
        buffer.append(")");
        return buffer.toString();
    }

    private final List fileFilters;
}
