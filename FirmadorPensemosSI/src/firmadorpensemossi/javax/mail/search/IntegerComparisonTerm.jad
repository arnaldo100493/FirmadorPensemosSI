// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntegerComparisonTerm.java

package javax.mail.search;


// Referenced classes of package javax.mail.search:
//            ComparisonTerm

public abstract class IntegerComparisonTerm extends ComparisonTerm
{

    protected IntegerComparisonTerm(int comparison, int number)
    {
        this.comparison = comparison;
        this.number = number;
    }

    public int getNumber()
    {
        return number;
    }

    public int getComparison()
    {
        return comparison;
    }

    protected boolean match(int i)
    {
        switch(comparison)
        {
        case 1: // '\001'
            return i <= number;

        case 2: // '\002'
            return i < number;

        case 3: // '\003'
            return i == number;

        case 4: // '\004'
            return i != number;

        case 5: // '\005'
            return i > number;

        case 6: // '\006'
            return i >= number;
        }
        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof IntegerComparisonTerm))
        {
            return false;
        } else
        {
            IntegerComparisonTerm ict = (IntegerComparisonTerm)obj;
            return ict.number == number && super.equals(obj);
        }
    }

    public int hashCode()
    {
        return number + super.hashCode();
    }

    protected int number;
    private static final long serialVersionUID = 0x9f5c6cda0679f7ecL;
}
