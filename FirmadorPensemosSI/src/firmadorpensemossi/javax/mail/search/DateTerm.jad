// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateTerm.java

package javax.mail.search;

import java.util.Date;

// Referenced classes of package javax.mail.search:
//            ComparisonTerm

public abstract class DateTerm extends ComparisonTerm
{

    protected DateTerm(int comparison, Date date)
    {
        this.comparison = comparison;
        this.date = date;
    }

    public Date getDate()
    {
        return new Date(date.getTime());
    }

    public int getComparison()
    {
        return comparison;
    }

    protected boolean match(Date d)
    {
        switch(comparison)
        {
        case 1: // '\001'
            return d.before(date) || d.equals(date);

        case 2: // '\002'
            return d.before(date);

        case 3: // '\003'
            return d.equals(date);

        case 4: // '\004'
            return !d.equals(date);

        case 5: // '\005'
            return d.after(date);

        case 6: // '\006'
            return d.after(date) || d.equals(date);
        }
        return false;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof DateTerm))
        {
            return false;
        } else
        {
            DateTerm dt = (DateTerm)obj;
            return dt.date.equals(date) && super.equals(obj);
        }
    }

    public int hashCode()
    {
        return date.hashCode() + super.hashCode();
    }

    protected Date date;
    private static final long serialVersionUID = 0x42e013da6884266bL;
}
