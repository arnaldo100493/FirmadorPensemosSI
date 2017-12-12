// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NotTerm.java

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            SearchTerm

public final class NotTerm extends SearchTerm
{

    public NotTerm(SearchTerm t)
    {
        term = t;
    }

    public SearchTerm getTerm()
    {
        return term;
    }

    public boolean match(Message msg)
    {
        return !term.match(msg);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof NotTerm))
        {
            return false;
        } else
        {
            NotTerm nt = (NotTerm)obj;
            return nt.term.equals(term);
        }
    }

    public int hashCode()
    {
        return term.hashCode() << 1;
    }

    private SearchTerm term;
    private static final long serialVersionUID = 0x63420cc8aadc1008L;
}
