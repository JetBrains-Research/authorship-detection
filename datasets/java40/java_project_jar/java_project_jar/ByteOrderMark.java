// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ByteOrderMark.java

package org.apache.commons.io;

import java.io.Serializable;

public class ByteOrderMark
    implements Serializable
{

    public transient ByteOrderMark(String charsetName, int bytes[])
    {
        if(charsetName == null || charsetName.length() == 0)
            throw new IllegalArgumentException("No charsetName specified");
        if(bytes == null || bytes.length == 0)
        {
            throw new IllegalArgumentException("No bytes specified");
        } else
        {
            this.charsetName = charsetName;
            this.bytes = new int[bytes.length];
            System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
            return;
        }
    }

    public String getCharsetName()
    {
        return charsetName;
    }

    public int length()
    {
        return bytes.length;
    }

    public int get(int pos)
    {
        return bytes[pos];
    }

    public byte[] getBytes()
    {
        byte copy[] = new byte[bytes.length];
        for(int i = 0; i < bytes.length; i++)
            copy[i] = (byte)bytes[i];

        return copy;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof ByteOrderMark))
            return false;
        ByteOrderMark bom = (ByteOrderMark)obj;
        if(bytes.length != bom.length())
            return false;
        for(int i = 0; i < bytes.length; i++)
            if(bytes[i] != bom.get(i))
                return false;

        return true;
    }

    public int hashCode()
    {
        int hashCode = getClass().hashCode();
        int arr$[] = bytes;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            int b = arr$[i$];
            hashCode += b;
        }

        return hashCode;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append('[');
        builder.append(charsetName);
        builder.append(": ");
        for(int i = 0; i < bytes.length; i++)
        {
            if(i > 0)
                builder.append(",");
            builder.append("0x");
            builder.append(Integer.toHexString(0xff & bytes[i]).toUpperCase());
        }

        builder.append(']');
        return builder.toString();
    }

    private static final long serialVersionUID = 1L;
    public static final ByteOrderMark UTF_8 = new ByteOrderMark("UTF-8", new int[] {
        239, 187, 191
    });
    public static final ByteOrderMark UTF_16BE = new ByteOrderMark("UTF-16BE", new int[] {
        254, 255
    });
    public static final ByteOrderMark UTF_16LE = new ByteOrderMark("UTF-16LE", new int[] {
        255, 254
    });
    private final String charsetName;
    private final int bytes[];

}
