// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MimePartDataSource.java

package javax.mail.internet;

import com.sun.mail.util.FolderClosedIOException;
import java.io.*;
import java.net.UnknownServiceException;
import javax.activation.DataSource;
import javax.mail.*;

// Referenced classes of package javax.mail.internet:
//            MimeBodyPart, MimeMessage, MimePart, MimeUtility

public class MimePartDataSource
    implements DataSource, MessageAware
{

    public MimePartDataSource(MimePart part)
    {
        this.part = part;
    }

    public InputStream getInputStream()
        throws IOException
    {
        InputStream is;
        try
        {
            if(part instanceof MimeBodyPart)
                is = ((MimeBodyPart)part).getContentStream();
            else
            if(part instanceof MimeMessage)
                is = ((MimeMessage)part).getContentStream();
            else
                throw new MessagingException("Unknown part");
            String encoding = MimeBodyPart.restrictEncoding(part, part.getEncoding());
            if(encoding != null)
                return MimeUtility.decode(is, encoding);
        }
        catch(FolderClosedException fex)
        {
            throw new FolderClosedIOException(fex.getFolder(), fex.getMessage());
        }
        catch(MessagingException mex)
        {
            throw new IOException(mex.getMessage());
        }
        return is;
    }

    public OutputStream getOutputStream()
        throws IOException
    {
        throw new UnknownServiceException("Writing not supported");
    }

    public String getContentType()
    {
        try
        {
            return part.getContentType();
        }
        catch(MessagingException mex)
        {
            return "application/octet-stream";
        }
    }

    public String getName()
    {
        try
        {
            if(part instanceof MimeBodyPart)
                return ((MimeBodyPart)part).getFileName();
        }
        catch(MessagingException mex) { }
        return "";
    }

    public synchronized MessageContext getMessageContext()
    {
        if(context == null)
            context = new MessageContext(part);
        return context;
    }

    protected MimePart part;
    private MessageContext context;
}
