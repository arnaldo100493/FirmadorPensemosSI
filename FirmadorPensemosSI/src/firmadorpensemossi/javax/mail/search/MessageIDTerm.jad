// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageIDTerm.java

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            StringTerm

public final class MessageIDTerm extends StringTerm
{

    public MessageIDTerm(String msgid)
    {
        super(msgid);
    }

    public boolean match(Message msg)
    {
        String s[];
        try
        {
            s = msg.getHeader("Message-ID");
        }
        catch(Exception e)
        {
            return false;
        }
        if(s == null)
            return false;
        for(int i = 0; i < s.length; i++)
            if(super.match(s[i]))
                return true;

        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof MessageIDTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0xe2905a280b6be385L;
}
