// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SizeTerm.java

package javax.mail.search;

import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            IntegerComparisonTerm

public final class SizeTerm extends IntegerComparisonTerm
{

    public SizeTerm(int comparison, int size)
    {
        super(comparison, size);
    }

    public boolean match(Message msg)
    {
        int size;
        try
        {
            size = msg.getSize();
        }
        catch(Exception e)
        {
            return false;
        }
        if(size == -1)
            return false;
        else
            return super.match(size);
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof SizeTerm))
            return false;
        else
            return super.equals(obj);
    }

    private static final long serialVersionUID = 0xdc867bf3e6e591a3L;
}
