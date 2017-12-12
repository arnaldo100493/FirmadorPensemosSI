// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeaderTerm.java

package javax.mail.search;

import java.util.Locale;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            StringTerm

public final class HeaderTerm extends StringTerm
{

    public HeaderTerm(String headerName, String pattern)
    {
        super(pattern);
        this.headerName = headerName;
    }

    public String getHeaderName()
    {
        return headerName;
    }

    public boolean match(Message msg)
    {
        String headers[];
        try
        {
            headers = msg.getHeader(headerName);
        }
        catch(Exception e)
        {
            return false;
        }
        if(headers == null)
            return false;
        for(int i = 0; i < headers.length; i++)
            if(super.match(headers[i]))
                return true;

        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof HeaderTerm))
        {
            return false;
        } else
        {
            HeaderTerm ht = (HeaderTerm)obj;
            return ht.headerName.equalsIgnoreCase(headerName) && super.equals(ht);
        }
    }

    public int hashCode()
    {
        return headerName.toLowerCase(Locale.ENGLISH).hashCode() + super.hashCode();
    }

    private String headerName;
    private static final long serialVersionUID = 0x73c690dfba9d2142L;
}
