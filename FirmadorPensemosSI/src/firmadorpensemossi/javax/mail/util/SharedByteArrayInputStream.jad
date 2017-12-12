// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SharedByteArrayInputStream.java

package javax.mail.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.mail.internet.SharedInputStream;

public class SharedByteArrayInputStream extends ByteArrayInputStream
    implements SharedInputStream
{

    public SharedByteArrayInputStream(byte buf[])
    {
        super(buf);
        start = 0;
    }

    public SharedByteArrayInputStream(byte buf[], int offset, int length)
    {
        super(buf, offset, length);
        start = 0;
        start = offset;
    }

    public long getPosition()
    {
        return (long)(pos - start);
    }

    public InputStream newStream(long start, long end)
    {
        if(start < 0L)
            throw new IllegalArgumentException("start < 0");
        if(end == -1L)
            end = count - this.start;
        return new SharedByteArrayInputStream(buf, this.start + (int)start, (int)(end - start));
    }

    protected int start;
}
