// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AddressTerm.java

package javax.mail.search;

import javax.mail.Address;

// Referenced classes of package javax.mail.search:
//            SearchTerm

public abstract class AddressTerm extends SearchTerm
{

    protected AddressTerm(Address address)
    {
        this.address = address;
    }

    public Address getAddress()
    {
        return address;
    }

    protected boolean match(Address a)
    {
        return a.equals(address);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof AddressTerm))
        {
            return false;
        } else
        {
            AddressTerm at = (AddressTerm)obj;
            return at.address.equals(address);
        }
    }

    public int hashCode()
    {
        return address.hashCode();
    }

    protected Address address;
    private static final long serialVersionUID = 0x1bd4a1b9715ebffcL;
}
