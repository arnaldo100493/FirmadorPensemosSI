// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InternetHeaders.java

package javax.mail.internet;

import javax.mail.Header;

// Referenced classes of package javax.mail.internet:
//            InternetHeaders

protected static final class InternetHeaders$InternetHeader extends Header
{

    public String getValue()
    {
        int i = line.indexOf(':');
        if(i < 0)
            return line;
        int j = i + 1;
        do
        {
            if(j >= line.length())
                break;
            char c = line.charAt(j);
            if(c != ' ' && c != '\t' && c != '\r' && c != '\n')
                break;
            j++;
        } while(true);
        return line.substring(j);
    }

    String line;

    public InternetHeaders$InternetHeader(String l)
    {
        super("", "");
        int i = l.indexOf(':');
        if(i < 0)
            name = l.trim();
        else
            name = l.substring(0, i).trim();
        line = l;
    }

    public InternetHeaders$InternetHeader(String n, String v)
    {
        super(n, "");
        if(v != null)
            line = (new StringBuilder()).append(n).append(": ").append(v).toString();
        else
            line = null;
    }
}
