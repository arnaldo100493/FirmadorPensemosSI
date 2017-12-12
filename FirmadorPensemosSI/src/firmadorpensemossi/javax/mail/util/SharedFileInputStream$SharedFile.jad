// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SharedFileInputStream.java

package javax.mail.util;

import java.io.*;

// Referenced classes of package javax.mail.util:
//            SharedFileInputStream

static class SharedFileInputStream$SharedFile
{

    public synchronized RandomAccessFile open()
    {
        cnt++;
        return in;
    }

    public synchronized void close()
        throws IOException
    {
        if(cnt > 0 && --cnt <= 0)
            in.close();
    }

    public synchronized void forceClose()
        throws IOException
    {
        if(cnt > 0)
        {
            cnt = 0;
            in.close();
        } else
        {
            try
            {
                in.close();
            }
            catch(IOException ioex) { }
        }
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
        in.close();
    }

    private int cnt;
    private RandomAccessFile in;

    SharedFileInputStream$SharedFile(String file)
        throws IOException
    {
        in = new RandomAccessFile(file, "r");
    }

    SharedFileInputStream$SharedFile(File file)
        throws IOException
    {
        in = new RandomAccessFile(file, "r");
    }
}
