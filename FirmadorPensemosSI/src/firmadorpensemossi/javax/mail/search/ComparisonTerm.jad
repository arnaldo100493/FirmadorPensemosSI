// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComparisonTerm.java

package javax.mail.search;


// Referenced classes of package javax.mail.search:
//            SearchTerm

public abstract class ComparisonTerm extends SearchTerm
{

    public ComparisonTerm()
    {
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof ComparisonTerm))
        {
            return false;
        } else
        {
            ComparisonTerm ct = (ComparisonTerm)obj;
            return ct.comparison == comparison;
        }
    }

    public int hashCode()
    {
        return comparison;
    }

    public static final int LE = 1;
    public static final int LT = 2;
    public static final int EQ = 3;
    public static final int NE = 4;
    public static final int GT = 5;
    public static final int GE = 6;
    protected int comparison;
    private static final long serialVersionUID = 0x14370cafcc71f144L;
}
