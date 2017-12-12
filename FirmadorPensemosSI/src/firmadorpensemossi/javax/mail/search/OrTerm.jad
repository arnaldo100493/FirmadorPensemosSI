// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrTerm.java

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            SearchTerm

public final class OrTerm extends SearchTerm
{

    public OrTerm(SearchTerm t1, SearchTerm t2)
    {
        terms = new SearchTerm[2];
        terms[0] = t1;
        terms[1] = t2;
    }

    public OrTerm(SearchTerm t[])
    {
        terms = new SearchTerm[t.length];
        for(int i = 0; i < t.length; i++)
            terms[i] = t[i];

    }

    public SearchTerm[] getTerms()
    {
        return (SearchTerm[])(SearchTerm[])terms.clone();
    }

    public boolean match(Message msg)
    {
        for(int i = 0; i < terms.length; i++)
            if(terms[i].match(msg))
                return true;

        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof OrTerm))
            return false;
        OrTerm ot = (OrTerm)obj;
        if(ot.terms.length != terms.length)
            return false;
        for(int i = 0; i < terms.length; i++)
            if(!terms[i].equals(ot.terms[i]))
                return false;

        return true;
    }

    public int hashCode()
    {
        int hash = 0;
        for(int i = 0; i < terms.length; i++)
            hash += terms[i].hashCode();

        return hash;
    }

    private SearchTerm terms[];
    private static final long serialVersionUID = 0x4aab7f3a24a275d8L;
}
