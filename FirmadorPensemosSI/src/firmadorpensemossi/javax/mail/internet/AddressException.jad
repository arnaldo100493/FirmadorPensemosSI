// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AddressException.java

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            ParseException

public class AddressException extends ParseException
{

    public AddressException()
    {
        ref = null;
        pos = -1;
    }

    public AddressException(String s)
    {
        super(s);
        ref = null;
        pos = -1;
    }

    public AddressException(String s, String ref)
    {
        super(s);
        this.ref = null;
        pos = -1;
        this.ref = ref;
    }

    public AddressException(String s, String ref, int pos)
    {
        super(s);
        this.ref = null;
        this.pos = -1;
        this.ref = ref;
        this.pos = pos;
    }

    public String getRef()
    {
        return ref;
    }

    public int getPos()
    {
        return pos;
    }

    public String toString()
    {
        String s = super.toString();
        if(ref == null)
            return s;
        s = (new StringBuilder()).append(s).append(" in string ``").append(ref).append("''").toString();
        if(pos < 0)
            return s;
        else
            return (new StringBuilder()).append(s).append(" at position ").append(pos).toString();
    }

    protected String ref;
    protected int pos;
    private static final long serialVersionUID = 0x7ec48f3eab5368f0L;
}
