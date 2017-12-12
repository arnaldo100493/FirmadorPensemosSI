// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ByteArrayDataSource.java

package javax.mail.util;

import java.io.*;
import javax.activation.DataSource;
import javax.mail.internet.*;

// Referenced classes of package javax.mail.util:
//            SharedByteArrayInputStream

public class ByteArrayDataSource
    implements DataSource
{
    static class DSByteArrayOutputStream extends ByteArrayOutputStream
    {

        public byte[] getBuf()
        {
            return buf;
        }

        public int getCount()
        {
            return count;
        }

        DSByteArrayOutputStream()
        {
        }
    }


    public ByteArrayDataSource(InputStream is, String type)
        throws IOException
    {
        this.len = -1;
        name = "";
        DSByteArrayOutputStream os = new DSByteArrayOutputStream();
        byte buf[] = new byte[8192];
        int len;
        while((len = is.read(buf)) > 0) 
            os.write(buf, 0, len);
        data = os.getBuf();
        this.len = os.getCount();
        if(data.length - this.len > 0x40000)
        {
            data = os.toByteArray();
            this.len = data.length;
        }
        this.type = type;
    }

    public ByteArrayDataSource(byte data[], String type)
    {
        len = -1;
        name = "";
        this.data = data;
        this.type = type;
    }

    public ByteArrayDataSource(String data, String type)
        throws IOException
    {
        len = -1;
        name = "";
        String charset = null;
        try
        {
            ContentType ct = new ContentType(type);
            charset = ct.getParameter("charset");
        }
        catch(ParseException pex) { }
        charset = MimeUtility.javaCharset(charset);
        if(charset == null)
            charset = MimeUtility.getDefaultJavaCharset();
        this.data = data.getBytes(charset);
        this.type = type;
    }

    public InputStream getInputStream()
        throws IOException
    {
        if(data == null)
            throw new IOException("no data");
        if(len < 0)
            len = data.length;
        return new SharedByteArrayInputStream(data, 0, len);
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        throw new IOException("cannot do this");
    }

    public String getContentType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private byte data[];
    private int len;
    private String type;
    private String name;
}
