// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SubjectTerm.java

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            StringTerm

public final class SubjectTerm extends StringTerm
{

    public SubjectTerm(String pattern)
    {
        super(pattern);
    }

    public boolean match(Message msg)
    {
        String subj;
        try
        {
            subj = msg.getSubject();
        }
        catch(Exception e)
        {
            return false;
        }
        if(subj == null)
            return false;
        else
            return super.match(subj);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof SubjectTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0x67d3df04e1e99fb8L;
}
