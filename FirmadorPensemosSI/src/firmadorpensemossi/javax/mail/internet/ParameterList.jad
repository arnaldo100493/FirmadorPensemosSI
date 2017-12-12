// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParameterList.java

package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.PropUtil;
import java.io.*;
import java.util.*;

// Referenced classes of package javax.mail.internet:
//            HeaderTokenizer, ParseException, MimeUtility

public class ParameterList
{
    private static class ToStringBuffer
    {

        public void addNV(String name, String value)
        {
            value = ParameterList.quote(value);
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

        public ToStringBuffer(int used)
        {
            sb = new StringBuffer();
            this.used = used;
        }
    }

    private static class ParamEnum
        implements Enumeration
    {

        public boolean hasMoreElements()
        {
            return it.hasNext();
        }

        public Object nextElement()
        {
            return it.next();
        }

        private Iterator it;

        ParamEnum(Iterator it)
        {
            this.it = it;
        }
    }

    private static class MultiValue extends ArrayList
    {

        String value;

        private MultiValue()
        {
        }

    }

    private static class Value
    {

        String value;
        String charset;
        String encodedValue;

        private Value()
        {
        }

    }


    public ParameterList()
    {
        list = new LinkedHashMap();
        lastName = null;
        if(decodeParameters)
        {
            multisegmentNames = new HashSet();
            slist = new HashMap();
        }
    }

    public ParameterList(String s)
        throws ParseException
    {
        this();
        HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
        do
        {
            HeaderTokenizer.Token tk = h.next();
            int type = tk.getType();
            if(type == -4)
                break;
            if((char)type == ';')
            {
                tk = h.next();
                if(tk.getType() == -4)
                    break;
                if(tk.getType() != -1)
                    throw new ParseException((new StringBuilder()).append("Expected parameter name, got \"").append(tk.getValue()).append("\"").toString());
                String name = tk.getValue().toLowerCase(Locale.ENGLISH);
                tk = h.next();
                if((char)tk.getType() != '=')
                    throw new ParseException((new StringBuilder()).append("Expected '=', got \"").append(tk.getValue()).append("\"").toString());
                if(windowshack && (name.equals("name") || name.equals("filename")))
                    tk = h.next(';', true);
                else
                if(parametersStrict)
                    tk = h.next();
                else
                    tk = h.next(';');
                type = tk.getType();
                if(type != -1 && type != -2)
                    throw new ParseException((new StringBuilder()).append("Expected parameter value, got \"").append(tk.getValue()).append("\"").toString());
                String value = tk.getValue();
                lastName = name;
                if(decodeParameters)
                    putEncodedName(name, value);
                else
                    list.put(name, value);
            } else
            if(type == -1 && lastName != null && (applehack && (lastName.equals("name") || lastName.equals("filename")) || !parametersStrict))
            {
                String lastValue = (String)list.get(lastName);
                String value = (new StringBuilder()).append(lastValue).append(" ").append(tk.getValue()).toString();
                list.put(lastName, value);
            } else
            {
                throw new ParseException((new StringBuilder()).append("Expected ';', got \"").append(tk.getValue()).append("\"").toString());
            }
        } while(true);
        if(decodeParameters)
            combineMultisegmentNames(false);
    }

    public void combineSegments()
    {
        if(decodeParameters && multisegmentNames.size() > 0)
            try
            {
                combineMultisegmentNames(true);
            }
            catch(ParseException pex) { }
    }

    private void putEncodedName(String name, String value)
        throws ParseException
    {
        int star = name.indexOf('*');
        if(star < 0)
            list.put(name, value);
        else
        if(star == name.length() - 1)
        {
            name = name.substring(0, star);
            Value v = extractCharset(value);
            try
            {
                v.value = decodeBytes(v.value, v.charset);
            }
            catch(UnsupportedEncodingException ex)
            {
                if(decodeParametersStrict)
                    throw new ParseException(ex.toString());
            }
            list.put(name, v);
        } else
        {
            String rname = name.substring(0, star);
            multisegmentNames.add(rname);
            list.put(rname, "");
            Object v;
            if(name.endsWith("*"))
            {
                if(name.endsWith("*0*"))
                {
                    v = extractCharset(value);
                } else
                {
                    v = new Value();
                    ((Value)v).encodedValue = value;
                    ((Value)v).value = value;
                }
                name = name.substring(0, name.length() - 1);
            } else
            {
                v = value;
            }
            slist.put(name, v);
        }
    }

    private void combineMultisegmentNames(boolean keepConsistentOnFailure)
        throws ParseException
    {
        boolean success = false;
        Iterator it = multisegmentNames.iterator();
_L10:
        String name;
        MultiValue mv;
        String charset;
        ByteArrayOutputStream bos;
        int segment;
        if(!it.hasNext())
            break; /* Loop/switch isn't completed */
        name = (String)it.next();
        StringBuffer sb = new StringBuffer();
        mv = new MultiValue();
        charset = null;
        bos = new ByteArrayOutputStream();
        segment = 0;
_L8:
        String sname;
        Object v;
        sname = (new StringBuilder()).append(name).append("*").append(segment).toString();
        v = slist.get(sname);
        if(v == null)
            break; /* Loop/switch isn't completed */
        mv.add(v);
        if(!(v instanceof Value)) goto _L2; else goto _L1
_L1:
        Value vv = (Value)v;
        if(segment != 0) goto _L4; else goto _L3
_L3:
        charset = vv.charset;
          goto _L5
_L4:
        if(charset == null)
        {
            multisegmentNames.remove(name);
            break; /* Loop/switch isn't completed */
        }
_L5:
        decodeBytes(vv.value, bos);
          goto _L6
_L2:
        bos.write(ASCIIUtility.getBytes((String)v));
          goto _L6
        IOException ex;
        ex;
_L6:
        slist.remove(sname);
        segment++;
        if(true) goto _L8; else goto _L7
_L7:
        if(segment == 0)
        {
            list.remove(name);
        } else
        {
            try
            {
                if(charset != null)
                    mv.value = bos.toString(charset);
                else
                    mv.value = bos.toString();
            }
            catch(UnsupportedEncodingException uex)
            {
                if(decodeParametersStrict)
                    throw new ParseException(uex.toString());
                mv.value = bos.toString(0);
            }
            list.put(name, mv);
        }
        if(true) goto _L10; else goto _L9
_L9:
        success = true;
        if(keepConsistentOnFailure || success)
        {
            if(slist.size() > 0)
            {
                for(Iterator sit = slist.values().iterator(); sit.hasNext();)
                {
                    Object v = sit.next();
                    if(v instanceof Value)
                    {
                        Value vv = (Value)v;
                        try
                        {
                            vv.value = decodeBytes(vv.value, vv.charset);
                        }
                        catch(UnsupportedEncodingException ex)
                        {
                            if(decodeParametersStrict)
                                throw new ParseException(ex.toString());
                        }
                    }
                }

                list.putAll(slist);
            }
            multisegmentNames.clear();
            slist.clear();
        }
        break MISSING_BLOCK_LABEL_622;
        Exception exception;
        exception;
        if(keepConsistentOnFailure || success)
        {
            if(slist.size() > 0)
            {
                for(Iterator sit = slist.values().iterator(); sit.hasNext();)
                {
                    Object v = sit.next();
                    if(v instanceof Value)
                    {
                        Value vv = (Value)v;
                        try
                        {
                            vv.value = decodeBytes(vv.value, vv.charset);
                        }
                        catch(UnsupportedEncodingException ex)
                        {
                            if(decodeParametersStrict)
                                throw new ParseException(ex.toString());
                        }
                    }
                }

                list.putAll(slist);
            }
            multisegmentNames.clear();
            slist.clear();
        }
        throw exception;
    }

    public int size()
    {
        return list.size();
    }

    public String get(String name)
    {
        Object v = list.get(name.trim().toLowerCase(Locale.ENGLISH));
        String value;
        if(v instanceof MultiValue)
            value = ((MultiValue)v).value;
        else
        if(v instanceof Value)
            value = ((Value)v).value;
        else
            value = (String)v;
        return value;
    }

    public void set(String name, String value)
    {
        name = name.trim().toLowerCase(Locale.ENGLISH);
        if(decodeParameters)
            try
            {
                putEncodedName(name, value);
            }
            catch(ParseException pex)
            {
                list.put(name, value);
            }
        else
            list.put(name, value);
    }

    public void set(String name, String value, String charset)
    {
        if(encodeParameters)
        {
            Value ev = encodeValue(value, charset);
            if(ev != null)
                list.put(name.trim().toLowerCase(Locale.ENGLISH), ev);
            else
                set(name, value);
        } else
        {
            set(name, value);
        }
    }

    public void remove(String name)
    {
        list.remove(name.trim().toLowerCase(Locale.ENGLISH));
    }

    public Enumeration getNames()
    {
        return new ParamEnum(list.keySet().iterator());
    }

    public String toString()
    {
        return toString(0);
    }

    public String toString(int used)
    {
        ToStringBuffer sb = new ToStringBuffer(used);
        for(Iterator e = list.keySet().iterator(); e.hasNext();)
        {
            String name = (String)e.next();
            Object v = list.get(name);
            if(v instanceof MultiValue)
            {
                MultiValue vv = (MultiValue)v;
                String ns = (new StringBuilder()).append(name).append("*").toString();
                int i = 0;
                while(i < vv.size()) 
                {
                    Object va = vv.get(i);
                    if(va instanceof Value)
                        sb.addNV((new StringBuilder()).append(ns).append(i).append("*").toString(), ((Value)va).encodedValue);
                    else
                        sb.addNV((new StringBuilder()).append(ns).append(i).toString(), (String)va);
                    i++;
                }
            } else
            if(v instanceof Value)
                sb.addNV((new StringBuilder()).append(name).append("*").toString(), ((Value)v).encodedValue);
            else
                sb.addNV(name, (String)v);
        }

        return sb.toString();
    }

    private static String quote(String value)
    {
        return MimeUtility.quote(value, "()<>@,;:\\\"\t []/?=");
    }

    private static Value encodeValue(String value, String charset)
    {
        if(MimeUtility.checkAscii(value) == 1)
            return null;
        byte b[];
        try
        {
            b = value.getBytes(MimeUtility.javaCharset(charset));
        }
        catch(UnsupportedEncodingException ex)
        {
            return null;
        }
        StringBuffer sb = new StringBuffer(b.length + charset.length() + 2);
        sb.append(charset).append("''");
        for(int i = 0; i < b.length; i++)
        {
            char c = (char)(b[i] & 0xff);
            if(c <= ' ' || c >= '\177' || c == '*' || c == '\'' || c == '%' || "()<>@,;:\\\"\t []/?=".indexOf(c) >= 0)
                sb.append('%').append(hex[c >> 4]).append(hex[c & 0xf]);
            else
                sb.append(c);
        }

        Value v = new Value();
        v.charset = charset;
        v.value = value;
        v.encodedValue = sb.toString();
        return v;
    }

    private static Value extractCharset(String value)
        throws ParseException
    {
        Value v;
        v = new Value();
        v.value = v.encodedValue = value;
        int i;
        i = value.indexOf('\'');
        if(i <= 0)
            if(decodeParametersStrict)
                throw new ParseException((new StringBuilder()).append("Missing charset in encoded value: ").append(value).toString());
            else
                return v;
        String charset;
        int li;
        charset = value.substring(0, i);
        li = value.indexOf('\'', i + 1);
        if(li < 0)
            if(decodeParametersStrict)
                throw new ParseException((new StringBuilder()).append("Missing language in encoded value: ").append(value).toString());
            else
                return v;
        try
        {
            String lang = value.substring(i + 1, li);
            v.value = value.substring(li + 1);
            v.charset = charset;
        }
        catch(NumberFormatException nex)
        {
            if(decodeParametersStrict)
                throw new ParseException(nex.toString());
        }
        catch(StringIndexOutOfBoundsException ex)
        {
            if(decodeParametersStrict)
                throw new ParseException(ex.toString());
        }
        return v;
    }

    private static String decodeBytes(String value, String charset)
        throws ParseException, UnsupportedEncodingException
    {
        byte b[] = new byte[value.length()];
        int i = 0;
        int bi = 0;
        for(; i < value.length(); i++)
        {
            char c = value.charAt(i);
            if(c == '%')
                try
                {
                    String hex = value.substring(i + 1, i + 3);
                    c = (char)Integer.parseInt(hex, 16);
                    i += 2;
                }
                catch(NumberFormatException ex)
                {
                    if(decodeParametersStrict)
                        throw new ParseException(ex.toString());
                }
                catch(StringIndexOutOfBoundsException ex)
                {
                    if(decodeParametersStrict)
                        throw new ParseException(ex.toString());
                }
            b[bi++] = (byte)c;
        }

        charset = MimeUtility.javaCharset(charset);
        if(charset == null)
            charset = MimeUtility.getDefaultJavaCharset();
        return new String(b, 0, bi, charset);
    }

    private static void decodeBytes(String value, OutputStream os)
        throws ParseException, IOException
    {
        for(int i = 0; i < value.length(); i++)
        {
            char c = value.charAt(i);
            if(c == '%')
                try
                {
                    String hex = value.substring(i + 1, i + 3);
                    c = (char)Integer.parseInt(hex, 16);
                    i += 2;
                }
                catch(NumberFormatException ex)
                {
                    if(decodeParametersStrict)
                        throw new ParseException(ex.toString());
                }
                catch(StringIndexOutOfBoundsException ex)
                {
                    if(decodeParametersStrict)
                        throw new ParseException(ex.toString());
                }
            os.write((byte)c);
        }

    }

    private Map list;
    private Set multisegmentNames;
    private Map slist;
    private String lastName;
    private static final boolean encodeParameters = PropUtil.getBooleanSystemProperty("mail.mime.encodeparameters", true);
    private static final boolean decodeParameters = PropUtil.getBooleanSystemProperty("mail.mime.decodeparameters", true);
    private static final boolean decodeParametersStrict = PropUtil.getBooleanSystemProperty("mail.mime.decodeparameters.strict", false);
    private static final boolean applehack = PropUtil.getBooleanSystemProperty("mail.mime.applefilenames", false);
    private static final boolean windowshack = PropUtil.getBooleanSystemProperty("mail.mime.windowsfilenames", false);
    private static final boolean parametersStrict = PropUtil.getBooleanSystemProperty("mail.mime.parameters.strict", true);
    private static final char hex[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };


}
