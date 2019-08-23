// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompositeFileComparator.java

package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.*;

// Referenced classes of package org.apache.commons.io.comparator:
//            AbstractFileComparator

public class CompositeFileComparator extends AbstractFileComparator
    implements Serializable
{

    public transient CompositeFileComparator(Comparator delegates[])
    {
        if(delegates == null)
        {
            this.delegates = (Comparator[])NO_COMPARATORS;
        } else
        {
            this.delegates = (Comparator[])new Comparator[delegates.length];
            System.arraycopy(delegates, 0, this.delegates, 0, delegates.length);
        }
    }

    public CompositeFileComparator(Iterable delegates)
    {
        if(delegates == null)
        {
            this.delegates = (Comparator[])NO_COMPARATORS;
        } else
        {
            List list = new ArrayList();
            Comparator comparator;
            for(Iterator i$ = delegates.iterator(); i$.hasNext(); list.add(comparator))
                comparator = (Comparator)i$.next();

            this.delegates = (Comparator[])(Comparator[])list.toArray(new Comparator[list.size()]);
        }
    }

    public int compare(File file1, File file2)
    {
        int result = 0;
        Comparator arr$[] = delegates;
        int len$ = arr$.length;
        int i$ = 0;
        do
        {
            if(i$ >= len$)
                break;
            Comparator delegate = arr$[i$];
            result = delegate.compare(file1, file2);
            if(result != 0)
                break;
            i$++;
        } while(true);
        return result;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append('{');
        for(int i = 0; i < delegates.length; i++)
        {
            if(i > 0)
                builder.append(',');
            builder.append(delegates[i]);
        }

        builder.append('}');
        return builder.toString();
    }

    public volatile int compare(Object x0, Object x1)
    {
        return compare((File)x0, (File)x1);
    }

    private static final Comparator NO_COMPARATORS[] = new Comparator[0];
    private final Comparator delegates[];

}
