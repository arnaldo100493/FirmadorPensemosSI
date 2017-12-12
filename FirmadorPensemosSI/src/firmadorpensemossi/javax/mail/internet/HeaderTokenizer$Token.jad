// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeaderTokenizer.java

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            HeaderTokenizer

public static class HeaderTokenizer$Token
{

    public int getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    private int type;
    private String value;
    public static final int ATOM = -1;
    public static final int QUOTEDSTRING = -2;
    public static final int COMMENT = -3;
    public static final int EOF = -4;

    public HeaderTokenizer$Token(int type, String value)
    {
        this.type = type;
        this.value = value;
    }
}
