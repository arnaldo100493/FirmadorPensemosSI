// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RecipientStringTerm.java

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            AddressStringTerm

public final class RecipientStringTerm extends AddressStringTerm
{

    public RecipientStringTerm(javax.mail.Message.RecipientType type, String pattern)
    {
        super(pattern);
        this.type = type;
    }

    public javax.mail.Message.RecipientType getRecipientType()
    {
        return type;
    }

    public boolean match(Message msg)
    {
        javax.mail.Address recipients[];
        try
        {
            recipients = msg.getRecipients(type);
        }
        catch(Exception e)
        {
            return false;
        }
        if(recipients == null)
            return false;
        for(int i = 0; i < recipients.length; i++)
            if(super.match(recipients[i]))
                return true;

        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof RecipientStringTerm))
        {
            return false;
        } else
        {
            RecipientStringTerm rst = (RecipientStringTerm)obj;
            return rst.type.equals(type) && super.equals(obj);
        }
    }

    public int hashCode()
    {
        return type.hashCode() + super.hashCode();
    }

    private javax.mail.Message.RecipientType type;
    private static final long serialVersionUID = 0x8ce759387e0d95dfL;
}
