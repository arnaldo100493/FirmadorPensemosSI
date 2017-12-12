// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageNumberTerm.java

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            IntegerComparisonTerm

public final class MessageNumberTerm extends IntegerComparisonTerm
{

    public MessageNumberTerm(int number)
    {
        super(3, number);
    }

    public boolean match(Message msg)
    {
        int msgno;
        try
        {
            msgno = msg.getMessageNumber();
        }
        catch(Exception e)
        {
            return false;
        }
        return super.match(msgno);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof MessageNumberTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0xb557bacf76ae80bcL;
}
