// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContentDisposition.java

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            HeaderTokenizer, ParseException, ParameterList

public class ContentDisposition
{

    public ContentDisposition()
    {
    }

    public ContentDisposition(String disposition, ParameterList list)
    {
        this.disposition = disposition;
        this.list = list;
    }

    public ContentDisposition(String s)
        throws ParseException
    {
        HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
        HeaderTokenizer.Token tk = h.next();
        if(tk.getType() != -1)
            throw new ParseException((new StringBuilder()).append("Expected disposition, got ").append(tk.getValue()).toString());
        disposition = tk.getValue();
        String rem = h.getRemainder();
        if(rem != null)
            list = new ParameterList(rem);
    }

    public String getDisposition()
    {
        return disposition;
    }

    public String getParameter(String name)
    {
        if(list == null)
            return null;
        else
            return list.get(name);
    }

    public ParameterList getParameterList()
    {
        return list;
    }

    public void setDisposition(String disposition)
    {
        this.disposition = disposition;
    }

    public void setParameter(String name, String value)
    {
        if(list == null)
            list = new ParameterList();
        list.set(name, value);
    }

    public void setParameterList(ParameterList list)
    {
        this.list = list;
    }

    public String toString()
    {
        if(disposition == null)
            return "";
        if(list == null)
        {
            return disposition;
        } else
        {
            StringBuffer sb = new StringBuffer(disposition);
            sb.append(list.toString(sb.length() + 21));
            return sb.toString();
        }
    }

    private String disposition;
    private ParameterList list;
}
