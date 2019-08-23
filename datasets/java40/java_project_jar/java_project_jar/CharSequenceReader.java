// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharSequenceReader.java

package org.apache.commons.io.input;

import java.io.Reader;
import java.io.Serializable;

public class CharSequenceReader extends Reader
    implements Serializable
{

    public CharSequenceReader(CharSequence charSequence)
    {
        this.charSequence = charSequence == null ? "" : charSequence;
    }

    public void close()
    {
        idx = 0;
        mark = 0;
    }

    public void mark(int readAheadLimit)
    {
        mark = idx;
    }

    public boolean markSupported()
    {
        return true;
    }

    public int read()
    {
        if(idx >= charSequence.length())
            return -1;
        else
            return charSequence.charAt(idx++);
    }

    public int read(char array[], int offset, int length)
    {
        if(idx >= charSequence.length())
            return -1;
        if(array == null)
            throw new NullPointerException("Character array is missing");
        if(length < 0 || offset + length > array.length)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("Array Size=").append(array.length).append(", offset=").append(offset).append(", length=").append(length).toString());
        int count = 0;
        for(int i = 0; i < length; i++)
        {
            int c = read();
            if(c == -1)
                return count;
            array[offset + i] = (char)c;
            count++;
        }

        return count;
    }

    public void reset()
    {
        idx = mark;
    }

    public long skip(long n)
    {
        if(n < 0L)
            throw new IllegalArgumentException((new StringBuilder()).append("Number of characters to skip is less than zero: ").append(n).toString());
        if(idx >= charSequence.length())
        {
            return -1L;
        } else
        {
            int dest = (int)Math.min(charSequence.length(), (long)idx + n);
            int count = dest - idx;
            idx = dest;
            return (long)count;
        }
    }

    public String toString()
    {
        return charSequence.toString();
    }

    private final CharSequence charSequence;
    private int idx;
    private int mark;
}
