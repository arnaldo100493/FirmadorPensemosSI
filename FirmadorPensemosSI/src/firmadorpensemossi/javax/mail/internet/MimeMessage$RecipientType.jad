// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MimeMessage.java

package javax.mail.internet;

import java.io.ObjectStreamException;
import javax.mail.Message;

// Referenced classes of package javax.mail.internet:
//            MimeMessage

public static class MimeMessage$RecipientType extends javax.mail.Message.RecipientType
{

    protected Object readResolve()
        throws ObjectStreamException
    {
        if(type.equals("Newsgroups"))
            return NEWSGROUPS;
        else
            return super.readResolve();
    }

    private static final long serialVersionUID = 0xb41cba943bbdee69L;
    public static final MimeMessage$RecipientType NEWSGROUPS = new MimeMessage$RecipientType("Newsgroups");


    protected MimeMessage$RecipientType(String type)
    {
        super(type);
    }
}
