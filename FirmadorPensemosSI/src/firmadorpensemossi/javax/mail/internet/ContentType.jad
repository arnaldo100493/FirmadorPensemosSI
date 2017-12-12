// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContentType.java

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            HeaderTokenizer, ParseException, ParameterList

public class ContentType
{

    public ContentType()
    {
    }

    public ContentType(String primaryType, String subType, ParameterList list)
    {
        this.primaryType = primaryType;
        this.subType = subType;
        this.list = list;
    }

    public ContentType(String s)
        throws ParseException
    {
        HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
        HeaderTokenizer.Token tk = h.next();
        if(tk.getType() != -1)
            throw new ParseException((new StringBuilder()).append("Expected MIME type, got ").append(tk.getValue()).toString());
        primaryType = tk.getValue();
        tk = h.next();
        if((char)tk.getType() != '/')
            throw new ParseException((new StringBuilder()).append("Expected '/', got ").append(tk.getValue()).toString());
        tk = h.next();
        if(tk.getType() != -1)
            throw new ParseException((new StringBuilder()).append("Expected MIME subtype, got ").append(tk.getValue()).toString());
        subType = tk.getValue();
        String rem = h.getRemainder();
        if(rem != null)
            list = new ParameterList(rem);
    }

    public String getPrimaryType()
    {
        return primaryType;
    }

    public String getSubType()
    {
        return subType;
    }

    public String getBaseType()
    {
        return (new StringBuilder()).append(primaryType).append('/').append(subType).toString();
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

    public void setPrimaryType(String primaryType)
    {
        this.primaryType = primaryType;
    }

    public void setSubType(String subType)
    {
        this.subType = subType;
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
        if(primaryType == null || subType == null)
            return "";
        StringBuffer sb = new StringBuffer();
        sb.append(primaryType).append('/').append(subType);
        if(list != null)
            sb.append(list.toString(sb.length() + 14));
        return sb.toString();
    }

    public boolean match(ContentType cType)
    {
        if(!primaryType.equalsIgnoreCase(cType.getPrimaryType()))
            return false;
        String sType = cType.getSubType();
        if(subType.charAt(0) == '*' || sType.charAt(0) == '*')
            return true;
        return subType.equalsIgnoreCase(sType);
    }

    public boolean match(String s)
    {
        try
        {
            return match(new ContentType(s));
        }
        catch(ParseException pex)
        {
            return false;
        }
    }

    private String primaryType;
    private String subType;
    private ParameterList list;
}
