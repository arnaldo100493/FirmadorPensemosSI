// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BodyTerm.java

package javax.mail.search;

import javax.mail.*;

// Referenced classes of package javax.mail.search:
//            StringTerm

public final class BodyTerm extends StringTerm
{

    public BodyTerm(String pattern)
    {
        super(pattern);
    }

    public boolean match(Message msg)
    {
        return matchPart(msg);
    }

    private boolean matchPart(Part p)
    {
        String s;
        if(!p.isMimeType("text/*"))
            break MISSING_BLOCK_LABEL_33;
        s = (String)p.getContent();
        if(s == null)
            return false;
        Multipart mp;
        int count;
        int i;
        try
        {
            return super.match(s);
        }
        catch(Exception ex) { }
        break MISSING_BLOCK_LABEL_121;
        if(!p.isMimeType("multipart/*"))
            break MISSING_BLOCK_LABEL_92;
        mp = (Multipart)p.getContent();
        count = mp.getCount();
        i = 0;
_L1:
        if(i >= count)
            break MISSING_BLOCK_LABEL_121;
        if(matchPart(((Part) (mp.getBodyPart(i)))))
            return true;
        i++;
          goto _L1
        if(p.isMimeType("message/rfc822"))
            return matchPart((Part)p.getContent());
        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof BodyTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0xbc27456ee3cb54e7L;
}
