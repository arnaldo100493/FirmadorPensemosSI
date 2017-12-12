// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SharedFileInputStream.java

package javax.mail.util;

import java.io.*;
import javax.mail.internet.SharedInputStream;

public class SharedFileInputStream extends BufferedInputStream
    implements SharedInputStream
{
    static class SharedFile
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

        SharedFile(String file)
            throws IOException
        {
            in = new RandomAccessFile(file, "r");
        }

        SharedFile(File file)
            throws IOException
        {
            in = new RandomAccessFile(file, "r");
        }
    }


    private void ensureOpen()
        throws IOException
    {
        if(in == null)
            throw new IOException("Stream closed");
        else
            return;
    }

    public SharedFileInputStream(File file)
        throws IOException
    {
        this(file, defaultBufferSize);
    }

    public SharedFileInputStream(String file)
        throws IOException
    {
        this(file, defaultBufferSize);
    }

    public SharedFileInputStream(File file, int size)
        throws IOException
    {
        super(null);
        start = 0L;
        master = true;
        if(size <= 0)
        {
            throw new IllegalArgumentException("Buffer size <= 0");
        } else
        {
            init(new SharedFile(file), size);
            return;
        }
    }

    public SharedFileInputStream(String file, int size)
        throws IOException
    {
        super(null);
        start = 0L;
        master = true;
        if(size <= 0)
        {
            throw new IllegalArgumentException("Buffer size <= 0");
        } else
        {
            init(new SharedFile(file), size);
            return;
        }
    }

    private void init(SharedFile sf, int size)
        throws IOException
    {
        this.sf = sf;
        in = sf.open();
        start = 0L;
        datalen = in.length();
        bufsize = size;
        buf = new byte[size];
    }

    private SharedFileInputStream(SharedFile sf, long start, long len, int bufsize)
    {
        super(null);
        this.start = 0L;
        master = true;
        master = false;
        this.sf = sf;
        in = sf.open();
        this.start = start;
        bufpos = start;
        datalen = len;
        this.bufsize = bufsize;
        buf = new byte[bufsize];
    }

    private void fill()
        throws IOException
    {
        if(markpos < 0)
        {
            pos = 0;
            bufpos += count;
        } else
        if(pos >= buf.length)
            if(markpos > 0)
            {
                int sz = pos - markpos;
                System.arraycopy(buf, markpos, buf, 0, sz);
                pos = sz;
                bufpos += markpos;
                markpos = 0;
            } else
            if(buf.length >= marklimit)
            {
                markpos = -1;
                pos = 0;
                bufpos += count;
            } else
            {
                int nsz = pos * 2;
                if(nsz > marklimit)
                    nsz = marklimit;
                byte nbuf[] = new byte[nsz];
                System.arraycopy(buf, 0, nbuf, 0, pos);
                buf = nbuf;
            }
        count = pos;
        in.seek(bufpos + (long)pos);
        int len = buf.length - pos;
        if((bufpos - start) + (long)pos + (long)len > datalen)
            len = (int)(datalen - ((bufpos - start) + (long)pos));
        int n = in.read(buf, pos, len);
        if(n > 0)
            count = n + pos;
    }

    public synchronized int read()
        throws IOException
    {
        ensureOpen();
        if(pos >= count)
        {
            fill();
            if(pos >= count)
                return -1;
        }
        return buf[pos++] & 0xff;
    }

    private int read1(byte b[], int off, int len)
        throws IOException
    {
        int avail = count - pos;
        if(avail <= 0)
        {
            fill();
            avail = count - pos;
            if(avail <= 0)
                return -1;
        }
        int cnt = avail >= len ? len : avail;
        System.arraycopy(buf, pos, b, off, cnt);
        pos += cnt;
        return cnt;
    }

    public synchronized int read(byte b[], int off, int len)
        throws IOException
    {
        ensureOpen();
        if((off | len | off + len | b.length - (off + len)) < 0)
            throw new IndexOutOfBoundsException();
        if(len == 0)
            return 0;
        int n = read1(b, off, len);
        if(n <= 0)
            return n;
        do
        {
            if(n >= len)
                break;
            int n1 = read1(b, off + n, len - n);
            if(n1 <= 0)
                break;
            n += n1;
        } while(true);
        return n;
    }

    public synchronized long skip(long n)
        throws IOException
    {
        ensureOpen();
        if(n <= 0L)
            return 0L;
        long avail = count - pos;
        if(avail <= 0L)
        {
            fill();
            avail = count - pos;
            if(avail <= 0L)
                return 0L;
        }
        long skipped = avail >= n ? n : avail;
        pos += skipped;
        return skipped;
    }

    public synchronized int available()
        throws IOException
    {
        ensureOpen();
        return (count - pos) + in_available();
    }

    private int in_available()
        throws IOException
    {
        return (int)((start + datalen) - (bufpos + (long)count));
    }

    public synchronized void mark(int readlimit)
    {
        marklimit = readlimit;
        markpos = pos;
    }

    public synchronized void reset()
        throws IOException
    {
        ensureOpen();
        if(markpos < 0)
        {
            throw new IOException("Resetting to invalid mark");
        } else
        {
            pos = markpos;
            return;
        }
    }

    public boolean markSupported()
    {
        return true;
    }

    public void close()
        throws IOException
    {
        if(in == null)
            return;
        if(master)
            sf.forceClose();
        else
            sf.close();
        sf = null;
        in = null;
        buf = null;
        break MISSING_BLOCK_LABEL_68;
        Exception exception;
        exception;
        sf = null;
        in = null;
        buf = null;
        throw exception;
    }

    public long getPosition()
    {
        if(in == null)
            throw new RuntimeException("Stream closed");
        else
            return (bufpos + (long)pos) - start;
    }

    public synchronized InputStream newStream(long start, long end)
    {
        if(in == null)
            throw new RuntimeException("Stream closed");
        if(start < 0L)
            throw new IllegalArgumentException("start < 0");
        if(end == -1L)
            end = datalen;
        return new SharedFileInputStream(sf, this.start + (long)(int)start, (int)(end - start), bufsize);
    }

    protected void finalize()
        throws Throwable
    {
        super.finalize();
        close();
    }

    private static int defaultBufferSize = 2048;
    protected RandomAccessFile in;
    protected int bufsize;
    protected long bufpos;
    protected long start;
    protected long datalen;
    private boolean master;
    private SharedFile sf;

}
