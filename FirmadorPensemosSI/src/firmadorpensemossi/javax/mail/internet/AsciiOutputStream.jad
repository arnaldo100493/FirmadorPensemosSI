// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MimeUtility.java

package javax.mail.internet;

import java.io.*;

// Referenced classes of package javax.mail.internet:
//            MimeUtility

class AsciiOutputStream extends OutputStream
{

    public AsciiOutputStream(boolean breakOnNonAscii, boolean encodeEolStrict)
    {
        ascii = 0;
        non_ascii = 0;
        linelen = 0;
        longLine = false;
        badEOL = false;
        checkEOL = false;
        lastb = 0;
        ret = 0;
        this.breakOnNonAscii = breakOnNonAscii;
        checkEOL = encodeEolStrict && breakOnNonAscii;
    }

    public void write(int b)
        throws IOException
    {
        check(b);
    }

    public void write(byte b[])
        throws IOException
    {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len)
        throws IOException
    {
        len += off;
        for(int i = off; i < len; i++)
            check(b[i]);

    }

    private final void check(int b)
        throws IOException
    {
        b &= 0xff;
        if(checkEOL && (lastb == 13 && b != 10 || lastb != 13 && b == 10))
            badEOL = true;
        if(b == 13 || b == 10)
        {
            linelen = 0;
        } else
        {
            linelen++;
            if(linelen > 998)
                longLine = true;
        }
        if(MimeUtility.nonascii(b))
        {
            non_ascii++;
            if(breakOnNonAscii)
            {
                ret = 3;
                throw new EOFException();
            }
        } else
        {
            ascii++;
        }
        lastb = b;
    }

    public int getAscii()
    {
        if(ret != 0)
            return ret;
        if(badEOL)
            return 3;
        if(non_ascii == 0)
            return !longLine ? 1 : 2;
        return ascii <= non_ascii ? 3 : 2;
    }

    private boolean breakOnNonAscii;
    private int ascii;
    private int non_ascii;
    private int linelen;
    private boolean longLine;
    private boolean badEOL;
    private boolean checkEOL;
    private int lastb;
    private int ret;
}
