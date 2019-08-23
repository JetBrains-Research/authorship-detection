// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOMInputStream.java

package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.apache.commons.io.ByteOrderMark;

// Referenced classes of package org.apache.commons.io.input:
//            ProxyInputStream

public class BOMInputStream extends ProxyInputStream
{

    public BOMInputStream(InputStream delegate)
    {
        this(delegate, false, new ByteOrderMark[] {
            ByteOrderMark.UTF_8
        });
    }

    public BOMInputStream(InputStream delegate, boolean include)
    {
        this(delegate, include, new ByteOrderMark[] {
            ByteOrderMark.UTF_8
        });
    }

    public transient BOMInputStream(InputStream delegate, ByteOrderMark boms[])
    {
        this(delegate, false, boms);
    }

    public transient BOMInputStream(InputStream delegate, boolean include, ByteOrderMark boms[])
    {
        super(delegate);
        if(boms == null || boms.length == 0)
        {
            throw new IllegalArgumentException("No BOMs specified");
        } else
        {
            this.include = include;
            this.boms = Arrays.asList(boms);
            return;
        }
    }

    public boolean hasBOM()
        throws IOException
    {
        return getBOM() != null;
    }

    public boolean hasBOM(ByteOrderMark bom)
        throws IOException
    {
        if(!boms.contains(bom))
            throw new IllegalArgumentException((new StringBuilder()).append("Stream not configure to detect ").append(bom).toString());
        else
            return byteOrderMark != null && getBOM().equals(bom);
    }

    public ByteOrderMark getBOM()
        throws IOException
    {
        if(firstBytes == null)
        {
            int max = 0;
            for(Iterator i$ = boms.iterator(); i$.hasNext();)
            {
                ByteOrderMark bom = (ByteOrderMark)i$.next();
                max = Math.max(max, bom.length());
            }

            firstBytes = new int[max];
            int i = 0;
            do
            {
                if(i >= firstBytes.length)
                    break;
                firstBytes[i] = in.read();
                fbLength++;
                if(firstBytes[i] < 0)
                    break;
                byteOrderMark = find();
                if(byteOrderMark != null)
                {
                    if(!include)
                        fbLength = 0;
                    break;
                }
                i++;
            } while(true);
        }
        return byteOrderMark;
    }

    public String getBOMCharsetName()
        throws IOException
    {
        getBOM();
        return byteOrderMark != null ? byteOrderMark.getCharsetName() : null;
    }

    private int readFirstBytes()
        throws IOException
    {
        getBOM();
        return fbIndex >= fbLength ? -1 : firstBytes[fbIndex++];
    }

    private ByteOrderMark find()
    {
        for(Iterator i$ = boms.iterator(); i$.hasNext();)
        {
            ByteOrderMark bom = (ByteOrderMark)i$.next();
            if(matches(bom))
                return bom;
        }

        return null;
    }

    private boolean matches(ByteOrderMark bom)
    {
        if(bom.length() != fbLength)
            return false;
        for(int i = 0; i < bom.length(); i++)
            if(bom.get(i) != firstBytes[i])
                return false;

        return true;
    }

    public int read()
        throws IOException
    {
        int b = readFirstBytes();
        return b < 0 ? in.read() : b;
    }

    public int read(byte buf[], int off, int len)
        throws IOException
    {
        int firstCount = 0;
        int b = 0;
        do
        {
            if(len <= 0 || b < 0)
                break;
            b = readFirstBytes();
            if(b >= 0)
            {
                buf[off++] = (byte)(b & 0xff);
                len--;
                firstCount++;
            }
        } while(true);
        int secondCount = in.read(buf, off, len);
        return secondCount >= 0 ? firstCount + secondCount : firstCount;
    }

    public int read(byte buf[])
        throws IOException
    {
        return read(buf, 0, buf.length);
    }

    public synchronized void mark(int readlimit)
    {
        markFbIndex = fbIndex;
        markedAtStart = firstBytes == null;
        in.mark(readlimit);
    }

    public synchronized void reset()
        throws IOException
    {
        fbIndex = markFbIndex;
        if(markedAtStart)
            firstBytes = null;
        in.reset();
    }

    public long skip(long n)
        throws IOException
    {
        for(; n > 0L && readFirstBytes() >= 0; n--);
        return in.skip(n);
    }

    private final boolean include;
    private final List boms;
    private ByteOrderMark byteOrderMark;
    private int firstBytes[];
    private int fbLength;
    private int fbIndex;
    private int markFbIndex;
    private boolean markedAtStart;
}
