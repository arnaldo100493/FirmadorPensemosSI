// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReceivedDateTerm.java

package javax.mail.search;

import java.util.Date;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            DateTerm

public final class ReceivedDateTerm extends DateTerm
{

    public ReceivedDateTerm(int comparison, Date date)
    {
        super(comparison, date);
    }

    public boolean match(Message msg)
    {
        Date d;
        try
        {
            d = msg.getReceivedDate();
        }
        catch(Exception e)
        {
            return false;
        }
        if(d == null)
            return false;
        else
            return super.match(d);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof ReceivedDateTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0xd9be404778824fbeL;
}
