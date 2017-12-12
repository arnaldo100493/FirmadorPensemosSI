// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RecipientTerm.java

package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            AddressTerm

public final class RecipientTerm extends AddressTerm
{

    public RecipientTerm(javax.mail.Message.RecipientType type, Address address)
    {
        super(address);
        this.type = type;
    }

    public javax.mail.Message.RecipientType getRecipientType()
    {
        return type;
    }

    public boolean match(Message msg)
    {
        Address recipients[];
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
        if(!(obj instanceof RecipientTerm))
        {
            return false;
        } else
        {
            RecipientTerm rt = (RecipientTerm)obj;
            return rt.type.equals(type) && super.equals(obj);
        }
    }

    public int hashCode()
    {
        return type.hashCode() + super.hashCode();
    }

    private javax.mail.Message.RecipientType type;
    private static final long serialVersionUID = 0x5ae1a88c29bef694L;
}
