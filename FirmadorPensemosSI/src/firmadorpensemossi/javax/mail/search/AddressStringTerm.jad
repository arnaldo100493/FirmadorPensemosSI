// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AddressStringTerm.java

package javax.mail.search;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

// Referenced classes of package javax.mail.search:
//            StringTerm

public abstract class AddressStringTerm extends StringTerm
{

    protected AddressStringTerm(String pattern)
    {
        super(pattern, true);
    }

    protected boolean match(Address a)
    {
        if(a instanceof InternetAddress)
        {
            InternetAddress ia = (InternetAddress)a;
            return super.match(ia.toUnicodeString());
        } else
        {
            return super.match(a.toString());
        }
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof AddressStringTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0x2ad6978ecdebb490L;
}
