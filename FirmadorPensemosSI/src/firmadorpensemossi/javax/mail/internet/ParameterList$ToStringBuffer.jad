// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParameterList.java

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            ParameterList, MimeUtility

private static class ParameterList$ToStringBuffer
{

    public void addNV(String name, String value)
    {
        value = ParameterList.access$200(value);
        sb.append("; ");
        used += 2;
        int len = name.length() + value.length() + 1;
        if(used + len > 76)
        {
            sb.append("\r\n\t");
            used = 8;
        }
        sb.append(name).append('=');
        used += name.length() + 1;
        if(used + value.length() > 76)
        {
            String s = MimeUtility.fold(used, value);
            sb.append(s);
            int lastlf = s.lastIndexOf('\n');
            if(lastlf >= 0)
                used += s.length() - lastlf - 1;
            else
                used += s.length();
        } else
        {
            sb.append(value);
            used += value.length();
        }
    }

    public String toString()
    {
        return sb.toString();
    }

    private int used;
    private StringBuffer sb;

    public ParameterList$ToStringBuffer(int used)
    {
        sb = new StringBuffer();
        this.used = used;
    }
}
