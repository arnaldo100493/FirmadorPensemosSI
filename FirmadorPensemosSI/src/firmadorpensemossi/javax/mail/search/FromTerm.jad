// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FromTerm.java

package javax.mail.search;

import javax.mail.Address;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            AddressTerm

public final class FromTerm extends AddressTerm
{

    public FromTerm(Address address)
    {
        super(address);
    }

    public boolean match(Message msg)
    {
        Address from[];
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
        if(!(obj instanceof FromTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0x485e7192407c3469L;
}
