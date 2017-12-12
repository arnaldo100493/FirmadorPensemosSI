// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringTerm.java

package javax.mail.search;


// Referenced classes of package javax.mail.search:
//            SearchTerm

public abstract class StringTerm extends SearchTerm
{

    protected StringTerm(String pattern)
    {
        this.pattern = pattern;
        ignoreCase = true;
    }

    protected StringTerm(String pattern, boolean ignoreCase)
    {
        this.pattern = pattern;
        this.ignoreCase = ignoreCase;
    }

    public String getPattern()
    {
        return pattern;
    }

    public boolean getIgnoreCase()
    {
        return ignoreCase;
    }

    protected boolean match(String s)
    {
        int len = s.length() - pattern.length();
        for(int i = 0; i <= len; i++)
            if(s.regionMatches(ignoreCase, i, pattern, 0, pattern.length()))
                return true;

        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof StringTerm))
            return false;
        StringTerm st = (StringTerm)obj;
        if(ignoreCase)
            return st.pattern.equalsIgnoreCase(pattern) && st.ignoreCase == ignoreCase;
        else
            return st.pattern.equals(pattern) && st.ignoreCase == ignoreCase;
    }

    public int hashCode()
    {
        return ignoreCase ? pattern.hashCode() : ~pattern.hashCode();
    }

    protected String pattern;
    protected boolean ignoreCase;
    private static final long serialVersionUID = 0x11ae4e90f062d98dL;
}
