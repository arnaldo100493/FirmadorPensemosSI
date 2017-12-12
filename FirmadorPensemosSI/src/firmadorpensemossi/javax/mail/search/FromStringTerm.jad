// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FromStringTerm.java

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            AddressStringTerm

public final class FromStringTerm extends AddressStringTerm
{

    public FromStringTerm(String pattern)
    {
        super(pattern);
    }

    public boolean match(Message msg)
    {
        javax.mail.Address from[];
        try
        {
            from = msg.getFrom();
        }
        catch(Exception e)
        {
            return false;
        }
        if(from == null)
            return false;
        for(int i = 0; i < from.length; i++)
            if(super.match(from[i]))
                return true;

        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof FromStringTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0x5081bebf4a6fab34L;
}
