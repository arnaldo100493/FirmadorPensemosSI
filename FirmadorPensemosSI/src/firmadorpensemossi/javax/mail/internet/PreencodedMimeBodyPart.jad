// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PreencodedMimeBodyPart.java

package javax.mail.internet;

import com.sun.mail.util.LineOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.activation.DataHandler;
import javax.mail.MessagingException;

// Referenced classes of package javax.mail.internet:
//            MimeBodyPart

public class PreencodedMimeBodyPart extends MimeBodyPart
{

    public PreencodedMimeBodyPart(String encoding)
    {
        this.encoding = encoding;
    }

    public String getEncoding()
        throws MessagingException
    {
        return encoding;
    }

    public void writeTo(OutputStream os)
        throws IOException, MessagingException
    {
        LineOutputStream los = null;
        if(os instanceof LineOutputStream)
            los = (LineOutputStream)os;
        else
            los = new LineOutputStream(os);
        for(Enumeration hdrLines = getAllHeaderLines(); hdrLines.hasMoreElements(); los.writeln((String)hdrLines.nextElement()));
        los.writeln();
        getDataHandler().writeTo(os);
        os.flush();
    }

    protected void updateHeaders()
        throws MessagingException
    {
        super.updateHeaders();
        MimeBodyPart.setEncoding(this, encoding);
    }

    private String encoding;
}
