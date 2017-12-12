// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 12/12/2017 12:21:44 p. m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Json.java

package firmadorpensemossi.firmaralmacenwinpdf;

import firmadorpensemossi.firmaralmacenwinpdf.JsonNumber;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package firmaralmacenwinpdf:
//            JsonNumber

public final class Json
{
    private static final class SafeTreeMap extends TreeMap
    {

        public Object get(Object key)
        {
            if(!containsKey(key))
                throw new IllegalArgumentException((new StringBuilder()).append("Key does not exist: ").append(key).toString());
            else
                return super.get(key);
        }

        private SafeTreeMap()
        {
        }

    }

    private static class Symbol
    {

        public final int charValue;

        public Symbol(int chr)
        {
            if(chr < -1 || chr > 65535)
            {
                throw new IllegalArgumentException();
            } else
            {
                charValue = chr;
                return;
            }
        }
    }

    private static final class StringStream
    {

        public int nextChar()
        {
            if(index >= string.length())
            {
                return -1;
            } else
            {
                char result = string.charAt(index);
                index++;
                return result;
            }
        }

        public void previous()
        {
            if(index <= 0)
            {
                throw new IllegalStateException();
            } else
            {
                index--;
                return;
            }
        }

        public void skipWhitespace()
        {
            do
            {
                if(index >= string.length())
                    break;
                char c = string.charAt(index);
                if(c != ' ' && c != '\n' && c != '\r' && c != '\t')
                    break;
                index++;
            } while(true);
        }

        public void mark()
        {
            start = index;
        }

        public String substring()
        {
            if(start == -1)
                throw new IllegalStateException("Not marked");
            else
                return string.substring(start, index);
        }

        private final String string;
        private int index;
        private int start;

        public StringStream(String s)
        {
            if(s == null)
            {
                throw new NullPointerException();
            } else
            {
                string = s;
                index = 0;
                start = -1;
                return;
            }
        }
    }


    public Json()
    {
    }

    public static String serialize(Object obj)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            serializeJson(obj, sb);
        }
        catch(IOException e)
        {
            throw new AssertionError(e);
        }
        return sb.toString();
    }

    private static void serializeJson(Object obj, Appendable out)
        throws IOException
    {
        if(obj == null || (obj instanceof Boolean))
            out.append(String.valueOf(obj));
        else
        if(obj instanceof Number)
        {
            if((obj instanceof Float) || (obj instanceof Double))
            {
                double x = ((Number)obj).doubleValue();
                if(Double.isInfinite(x) || Double.isNaN(x))
                    throw new IllegalArgumentException("Cannot serialize infinite/NaN floating-point value");
            }
            String temp = obj.toString();
            if(!JsonNumber.SYNTAX.matcher(temp).matches())
                throw new IllegalArgumentException((new StringBuilder()).append("Number string cannot be serialized as JSON: ").append(temp).toString());
            out.append(temp);
        } else
        if(obj instanceof CharSequence)
        {
            if((obj instanceof List) || (obj instanceof Map))
                throw new IllegalArgumentException("Ambiguous object is both charseq and list/map");
            CharSequence str = (CharSequence)obj;
            out.append('"');
            for(int i = 0; i < str.length(); i++)
            {
                char c = str.charAt(i);
                switch(c)
                {
                case 8: // '\b'
                    out.append("\\b");
                    break;

                case 12: // '\f'
                    out.append("\\f");
                    break;

                case 10: // '\n'
                    out.append("\\n");
                    break;

                case 13: // '\r'
                    out.append("\\r");
                    break;

                case 9: // '\t'
                    out.append("\\t");
                    break;

                case 34: // '"'
                    out.append("\\\"");
                    break;

                case 92: // '\\'
                    out.append("\\\\");
                    break;

                default:
                    if(c >= ' ' && c < '\177')
                        out.append(c);
                    else
                        out.append(String.format("\\u%04X", new Object[] {
                            Integer.valueOf(c)
                        }));
                    break;
                }
            }

            out.append('"');
        } else
        if(obj instanceof List)
        {
            if(obj instanceof Map)
                throw new IllegalArgumentException("Ambiguous object is both list and map");
            out.append('[');
            boolean head = true;
            Object sub;
            for(Iterator iterator = ((List)obj).iterator(); iterator.hasNext(); serializeJson(sub, out))
            {
                sub = iterator.next();
                if(head)
                    head = false;
                else
                    out.append(", ");
            }

            out.append(']');
        } else
        if(obj instanceof Map)
        {
            out.append('{');
            boolean head = true;
            Map map = (Map)obj;
            java.util.Map.Entry entry;
            for(Object sub = map.entrySet().iterator(); ((Iterator)sub).hasNext(); serializeJson(entry.getValue(), out))
            {
                Object temp = ((Iterator)sub).next();
                entry = (java.util.Map.Entry)temp;
                Object key = entry.getKey();
                if(!(key instanceof CharSequence))
                    throw new IllegalArgumentException("Map key must be a String/CharSequence object");
                if(head)
                    head = false;
                else
                    out.append(", ");
                serializeJson(key, out);
                out.append(": ");
            }

            out.append('}');
        } else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Unrecognized value: ").append(obj.getClass()).append(" ").append(obj.toString()).toString());
        }
    }

    public static Object parse(String str)
    {
        StringStream ss = new StringStream(str);
        Object result = parseGeneral(ss);
        if(result instanceof Symbol)
            throw new IllegalArgumentException("Malformed JSON");
        if(!isSymbol(parseGeneral(ss), -1))
            throw new IllegalArgumentException("Malformed JSON");
        else
            return result;
    }

    private static Object parseGeneral(StringStream ss)
    {
        ss.skipWhitespace();
        ss.mark();
        int c = ss.nextChar();
        switch(c)
        {
        case 123: // '{'
            return parseObject(ss);

        case 91: // '['
            return parseArray(ss);

        case 34: // '"'
            return parseString(ss);

        case 102: // 'f'
        case 110: // 'n'
        case 116: // 't'
            return parseConstant(ss);

        case -1: 
        case 44: // ','
        case 58: // ':'
        case 93: // ']'
        case 125: // '}'
            return new Symbol(c);
        }
        if(c >= 48 && c <= 57 || c == 45)
            return parseNumber(ss);
        else
            throw new IllegalArgumentException("Malformed JSON");
    }

    private static SortedMap parseObject(StringStream ss)
    {
        SortedMap result = new SafeTreeMap();
        boolean head = true;
        do
        {
            Object key = parseGeneral(ss);
            if(!isSymbol(key, 125))
            {
                if(head)
                {
                    head = false;
                } else
                {
                    if(!isSymbol(key, 44))
                        throw new IllegalArgumentException("Malformed JSON");
                    key = parseGeneral(ss);
                }
                if(!(key instanceof String))
                    throw new IllegalArgumentException("Malformed JSON");
                if(result.containsKey(key))
                    throw new IllegalArgumentException("JSON object contains duplicate key");
                if(!isSymbol(parseGeneral(ss), 58))
                    throw new IllegalArgumentException("Malformed JSON");
                Object value = parseGeneral(ss);
                if(value instanceof Symbol)
                    throw new IllegalArgumentException("Malformed JSON");
                result.put((String)key, value);
            } else
            {
                return result;
            }
        } while(true);
    }

    private static List parseArray(StringStream ss)
    {
        List result = new ArrayList();
        boolean head = true;
        do
        {
            Object obj = parseGeneral(ss);
            if(!isSymbol(obj, 93))
            {
                if(head)
                {
                    head = false;
                } else
                {
                    if(!isSymbol(obj, 44))
                        throw new IllegalArgumentException("Malformed JSON");
                    obj = parseGeneral(ss);
                }
                if(obj instanceof Symbol)
                    throw new IllegalArgumentException("Malformed JSON");
                result.add(obj);
            } else
            {
                return result;
            }
        } while(true);
    }

    private static String parseString(StringStream ss)
    {
        StringBuilder sb = new StringBuilder();
label0:
        do
        {
            int c = ss.nextChar();
            switch(c)
            {
            case 34: // '"'
                break label0;

            case 92: // '\\'
                c = ss.nextChar();
                switch(c)
                {
                case 34: // '"'
                case 47: // '/'
                case 92: // '\\'
                    sb.append((char)c);
                    break;

                case 98: // 'b'
                    sb.append('\b');
                    break;

                case 102: // 'f'
                    sb.append('\f');
                    break;

                case 110: // 'n'
                    sb.append('\n');
                    break;

                case 114: // 'r'
                    sb.append('\r');
                    break;

                case 116: // 't'
                    sb.append('\t');
                    break;

                case 117: // 'u'
                    int w = ss.nextChar();
                    int x = ss.nextChar();
                    int y = ss.nextChar();
                    int z = ss.nextChar();
                    if(z == -1 || w == 43 || w == 45)
                        throw new IllegalArgumentException("Malformed JSON");
                    String hex = (new StringBuilder()).append("").append((char)w).append((char)x).append((char)y).append((char)z).toString();
                    sb.append((char)Integer.parseInt(hex, 16));
                    break;

                case -1: 
                default:
                    throw new IllegalArgumentException("Malformed JSON");
                }
                break;

            default:
                if(c >= 32)
                    sb.append((char)c);
                else
                    throw new IllegalArgumentException("Malformed JSON");
                break;
            }
        } while(true);
        return sb.toString();
    }

    private static Boolean parseConstant(StringStream ss)
    {
        do
        {
            int c = ss.nextChar();
            if(c == -1)
                break;
            if(c >= 97 && c <= 122)
                continue;
            ss.previous();
            break;
        } while(true);
        String val = ss.substring();
        if(val.equals("null"))
            return null;
        if(val.equals("false"))
            return Boolean.FALSE;
        if(val.equals("true"))
            return Boolean.TRUE;
        else
            throw new IllegalArgumentException("Malformed JSON");
    }

    private static JsonNumber parseNumber(StringStream ss)
    {
        do
        {
            int c = ss.nextChar();
            if(c == -1)
                break;
            if(c >= 48 && c <= 57 || c == 43 || c == 45 || c == 46 || c == 101 || c == 69)
                continue;
            ss.previous();
            break;
        } while(true);
        return new JsonNumber(ss.substring());
    }

    private static boolean isSymbol(Object obj, int chr)
    {
        return (obj instanceof Symbol) && ((Symbol)obj).charValue == chr;
    }

    public static Object parseFromFile(File file)
        throws IOException
    {
        return parseFromFile(file, Charset.forName("UTF-8"));
    }

    public static Object parseFromFile(File file, Charset cs)
        throws IOException
    {
        InputStream in = new FileInputStream(file);
        try {
            return parseFromStream(in, cs);
        } finally {
            in.close();
        }
    }

    public static Object parseFromUrl(URL url)
        throws IOException
    {
        return parseFromUrl(url, Charset.forName("UTF-8"));
    }

    public static Object parseFromUrl(URL url, Charset cs)
        throws IOException
    {
       InputStream in = url.openStream();
        try {
            return parseFromStream(in, cs);
        } finally {
            in.close();
        }
    }

    private static Object parseFromStream(InputStream in, Charset cs)
        throws IOException
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte buf[] = new byte[63];
        do
        {
            int n = in.read(buf);
            if(n != -1)
                bout.write(buf, 0, n);
            else
                return parse(new String(bout.toByteArray(), cs));
        } while(true);
    }

    public static void serializeToFile(Object obj, File file)
        throws IOException
    {
        serializeToFile(obj, file, Charset.forName("UTF-8"));
    }

    public static void serializeToFile(Object obj, File file, Charset cs)
        throws IOException
    {
        Writer out = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)), cs);
        try {
            serializeJson(obj, out);
        } finally {
            out.close();
        }
    }

    public static Object getObject(Object root, Object path[])
    {
        Object node = root;
        Object aobj[] = path;
        int i = aobj.length;
        for(int j = 0; j < i; j++)
        {
            Object key = aobj[j];
            if(key instanceof String)
            {
                if(!(node instanceof Map))
                    throw new IllegalArgumentException("Expected a map");
                Map map = (Map)node;
                if(!map.containsKey(key))
                    throw new IllegalArgumentException((new StringBuilder()).append("Map key not found: ").append(key).toString());
                node = map.get(key);
                continue;
            }
            if(key instanceof Integer)
            {
                if(!(node instanceof List))
                    throw new IllegalArgumentException("Expected a list");
                List list = (List)node;
                int index = ((Integer)key).intValue();
                if(index < 0 || index >= list.size())
                    throw new IndexOutOfBoundsException(key.toString());
                node = list.get(index);
                continue;
            }
            if(key == null)
                throw new NullPointerException();
            else
                throw new IllegalArgumentException((new StringBuilder()).append("Invalid path component: ").append(key).toString());
        }

        return node;
    }

    public static boolean getBoolean(Object root, Object path[])
    {
        return ((Boolean)getObject(root, path)).booleanValue();
    }

    public static int getInt(Object root, Object path[])
    {
        return ((Number)getObject(root, path)).intValue();
    }

    public static long getLong(Object root, Object path[])
    {
        return ((Number)getObject(root, path)).longValue();
    }

    public static float getFloat(Object root, Object path[])
    {
        return ((Number)getObject(root, path)).floatValue();
    }

    public static double getDouble(Object root, Object path[])
    {
        return ((Number)getObject(root, path)).doubleValue();
    }

    public static String getString(Object root, Object path[])
    {
        String result = (String)getObject(root, path);
        if(result == null)
            throw new NullPointerException();
        else
            return result;
    }

    public static List getList(Object root, Object path[])
    {
        List result = (List)getObject(root, path);
        if(result == null)
            throw new NullPointerException();
        else
            return result;
    }

    public static Map getMap(Object root, Object path[])
    {
        Map result = (Map)getObject(root, path);
        if(result == null)
            throw new NullPointerException();
        else
            return result;
    }
}