// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MimeUtility.java

package javax.mail.internet;

import com.sun.mail.util.*;
import java.io.*;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.EncodingAware;
import javax.mail.MessagingException;

// Referenced classes of package javax.mail.internet:
//            ContentType, AsciiOutputStream, ParseException

public class MimeUtility
{

    private MimeUtility()
    {
    }

    public static String getEncoding(DataSource ds)
    {
        InputStream is;
        String encoding;
        ContentType cType = null;
        is = null;
        encoding = null;
        if(ds instanceof EncodingAware)
        {
            encoding = ((EncodingAware)ds).getEncoding();
            if(encoding != null)
                return encoding;
        }
        ContentType cType = new ContentType(ds.getContentType());
        is = ds.getInputStream();
        boolean isText = cType.match("text/*");
        int i = checkAscii(is, -1, !isText);
        switch(i)
        {
        case 1: // '\001'
            encoding = "7bit";
            break;

        case 2: // '\002'
            if(isText && nonAsciiCharset(cType))
                encoding = "base64";
            else
                encoding = "quoted-printable";
            break;

        default:
            encoding = "base64";
            break;
        }
        try
        {
            if(is != null)
                is.close();
        }
        catch(IOException ioex) { }
        break MISSING_BLOCK_LABEL_193;
        Exception ex;
        ex;
        String s = "base64";
        try
        {
            if(is != null)
                is.close();
        }
        catch(IOException ioex) { }
        return s;
        Exception exception;
        exception;
        try
        {
            if(is != null)
                is.close();
        }
        catch(IOException ioex) { }
        throw exception;
        return encoding;
    }

    private static boolean nonAsciiCharset(ContentType ct)
    {
        String charset = ct.getParameter("charset");
        if(charset == null)
            return false;
        charset = charset.toLowerCase(Locale.ENGLISH);
        Boolean bool;
        synchronized(nonAsciiCharsetMap)
        {
            bool = (Boolean)nonAsciiCharsetMap.get(charset);
        }
        if(bool == null)
        {
            try
            {
                byte b[] = "\r\n".getBytes(charset);
                bool = Boolean.valueOf(b.length != 2 || b[0] != 13 || b[1] != 10);
            }
            catch(UnsupportedEncodingException uex)
            {
                bool = Boolean.FALSE;
            }
            catch(RuntimeException ex)
            {
                bool = Boolean.TRUE;
            }
            synchronized(nonAsciiCharsetMap)
            {
                nonAsciiCharsetMap.put(charset, bool);
            }
        }
        return bool.booleanValue();
    }

    public static String getEncoding(DataHandler dh)
    {
        ContentType cType = null;
        String encoding = null;
        if(dh.getName() != null)
            return getEncoding(dh.getDataSource());
        try
        {
            cType = new ContentType(dh.getContentType());
        }
        catch(Exception ex)
        {
            return "base64";
        }
        if(cType.match("text/*"))
        {
            AsciiOutputStream aos = new AsciiOutputStream(false, false);
            try
            {
                dh.writeTo(aos);
            }
            catch(IOException ex) { }
            switch(aos.getAscii())
            {
            case 1: // '\001'
                encoding = "7bit";
                break;

            case 2: // '\002'
                encoding = "quoted-printable";
                break;

            default:
                encoding = "base64";
                break;
            }
        } else
        {
            AsciiOutputStream aos = new AsciiOutputStream(true, encodeEolStrict);
            try
            {
                dh.writeTo(aos);
            }
            catch(IOException ex) { }
            if(aos.getAscii() == 1)
                encoding = "7bit";
            else
                encoding = "base64";
        }
        return encoding;
    }

    public static InputStream decode(InputStream is, String encoding)
        throws MessagingException
    {
        if(encoding.equalsIgnoreCase("base64"))
            return new BASE64DecoderStream(is);
        if(encoding.equalsIgnoreCase("quoted-printable"))
            return new QPDecoderStream(is);
        if(encoding.equalsIgnoreCase("uuencode") || encoding.equalsIgnoreCase("x-uuencode") || encoding.equalsIgnoreCase("x-uue"))
            return new UUDecoderStream(is);
        if(encoding.equalsIgnoreCase("binary") || encoding.equalsIgnoreCase("7bit") || encoding.equalsIgnoreCase("8bit"))
            return is;
        if(!ignoreUnknownEncoding)
            throw new MessagingException((new StringBuilder()).append("Unknown encoding: ").append(encoding).toString());
        else
            return is;
    }

    public static OutputStream encode(OutputStream os, String encoding)
        throws MessagingException
    {
        if(encoding == null)
            return os;
        if(encoding.equalsIgnoreCase("base64"))
            return new BASE64EncoderStream(os);
        if(encoding.equalsIgnoreCase("quoted-printable"))
            return new QPEncoderStream(os);
        if(encoding.equalsIgnoreCase("uuencode") || encoding.equalsIgnoreCase("x-uuencode") || encoding.equalsIgnoreCase("x-uue"))
            return new UUEncoderStream(os);
        if(encoding.equalsIgnoreCase("binary") || encoding.equalsIgnoreCase("7bit") || encoding.equalsIgnoreCase("8bit"))
            return os;
        else
            throw new MessagingException((new StringBuilder()).append("Unknown encoding: ").append(encoding).toString());
    }

    public static OutputStream encode(OutputStream os, String encoding, String filename)
        throws MessagingException
    {
        if(encoding == null)
            return os;
        if(encoding.equalsIgnoreCase("base64"))
            return new BASE64EncoderStream(os);
        if(encoding.equalsIgnoreCase("quoted-printable"))
            return new QPEncoderStream(os);
        if(encoding.equalsIgnoreCase("uuencode") || encoding.equalsIgnoreCase("x-uuencode") || encoding.equalsIgnoreCase("x-uue"))
            return new UUEncoderStream(os, filename);
        if(encoding.equalsIgnoreCase("binary") || encoding.equalsIgnoreCase("7bit") || encoding.equalsIgnoreCase("8bit"))
            return os;
        else
            throw new MessagingException((new StringBuilder()).append("Unknown encoding: ").append(encoding).toString());
    }

    public static String encodeText(String text)
        throws UnsupportedEncodingException
    {
        return encodeText(text, null, null);
    }

    public static String encodeText(String text, String charset, String encoding)
        throws UnsupportedEncodingException
    {
        return encodeWord(text, charset, encoding, false);
    }

    public static String decodeText(String etext)
        throws UnsupportedEncodingException
    {
        String lwsp = " \t\n\r";
        if(etext.indexOf("=?") == -1)
            return etext;
        StringTokenizer st = new StringTokenizer(etext, lwsp, true);
        StringBuffer sb = new StringBuffer();
        StringBuffer wsb = new StringBuffer();
        boolean prevWasEncoded = false;
        while(st.hasMoreTokens()) 
        {
            String s = st.nextToken();
            char c;
            if((c = s.charAt(0)) == ' ' || c == '\t' || c == '\r' || c == '\n')
            {
                wsb.append(c);
            } else
            {
                String word;
                try
                {
                    word = decodeWord(s);
                    if(!prevWasEncoded && wsb.length() > 0)
                        sb.append(wsb);
                    prevWasEncoded = true;
                }
                catch(ParseException pex)
                {
                    word = s;
                    if(!decodeStrict)
                    {
                        String dword = decodeInnerWords(word);
                        if(dword != word)
                        {
                            if((!prevWasEncoded || !word.startsWith("=?")) && wsb.length() > 0)
                                sb.append(wsb);
                            prevWasEncoded = word.endsWith("?=");
                            word = dword;
                        } else
                        {
                            if(wsb.length() > 0)
                                sb.append(wsb);
                            prevWasEncoded = false;
                        }
                    } else
                    {
                        if(wsb.length() > 0)
                            sb.append(wsb);
                        prevWasEncoded = false;
                    }
                }
                sb.append(word);
                wsb.setLength(0);
            }
        }
        sb.append(wsb);
        return sb.toString();
    }

    public static String encodeWord(String word)
        throws UnsupportedEncodingException
    {
        return encodeWord(word, null, null);
    }

    public static String encodeWord(String word, String charset, String encoding)
        throws UnsupportedEncodingException
    {
        return encodeWord(word, charset, encoding, true);
    }

    private static String encodeWord(String string, String charset, String encoding, boolean encodingWord)
        throws UnsupportedEncodingException
    {
        int ascii = checkAscii(string);
        if(ascii == 1)
            return string;
        String jcharset;
        if(charset == null)
        {
            jcharset = getDefaultJavaCharset();
            charset = getDefaultMIMECharset();
        } else
        {
            jcharset = javaCharset(charset);
        }
        if(encoding == null)
            if(ascii != 3)
                encoding = "Q";
            else
                encoding = "B";
        boolean b64;
        if(encoding.equalsIgnoreCase("B"))
            b64 = true;
        else
        if(encoding.equalsIgnoreCase("Q"))
            b64 = false;
        else
            throw new UnsupportedEncodingException((new StringBuilder()).append("Unknown transfer encoding: ").append(encoding).toString());
        StringBuffer outb = new StringBuffer();
        doEncode(string, b64, jcharset, 68 - charset.length(), (new StringBuilder()).append("=?").append(charset).append("?").append(encoding).append("?").toString(), true, encodingWord, outb);
        return outb.toString();
    }

    private static void doEncode(String string, boolean b64, String jcharset, int avail, String prefix, boolean first, boolean encodingWord, StringBuffer buf)
        throws UnsupportedEncodingException
    {
        byte bytes[] = string.getBytes(jcharset);
        int len;
        if(b64)
            len = BEncoderStream.encodedLength(bytes);
        else
            len = QEncoderStream.encodedLength(bytes, encodingWord);
        int size;
        if(len > avail && (size = string.length()) > 1)
        {
            doEncode(string.substring(0, size / 2), b64, jcharset, avail, prefix, first, encodingWord, buf);
            doEncode(string.substring(size / 2, size), b64, jcharset, avail, prefix, false, encodingWord, buf);
        } else
        {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            OutputStream eos;
            if(b64)
                eos = new BEncoderStream(os);
            else
                eos = new QEncoderStream(os, encodingWord);
            try
            {
                eos.write(bytes);
                eos.close();
            }
            catch(IOException ioex) { }
            byte encodedBytes[] = os.toByteArray();
            if(!first)
                if(foldEncodedWords)
                    buf.append("\r\n ");
                else
                    buf.append(" ");
            buf.append(prefix);
            for(int i = 0; i < encodedBytes.length; i++)
                buf.append((char)encodedBytes[i]);

            buf.append("?=");
        }
    }

    public static String decodeWord(String eword)
        throws ParseException, UnsupportedEncodingException
    {
        if(!eword.startsWith("=?"))
            throw new ParseException((new StringBuilder()).append("encoded word does not start with \"=?\": ").append(eword).toString());
        int start = 2;
        int pos;
        if((pos = eword.indexOf('?', start)) == -1)
            throw new ParseException((new StringBuilder()).append("encoded word does not include charset: ").append(eword).toString());
        String charset = eword.substring(start, pos);
        int lpos = charset.indexOf('*');
        if(lpos >= 0)
            charset = charset.substring(0, lpos);
        charset = javaCharset(charset);
        start = pos + 1;
        if((pos = eword.indexOf('?', start)) == -1)
            throw new ParseException((new StringBuilder()).append("encoded word does not include encoding: ").append(eword).toString());
        String encoding = eword.substring(start, pos);
        start = pos + 1;
        if((pos = eword.indexOf("?=", start)) == -1)
            throw new ParseException((new StringBuilder()).append("encoded word does not end with \"?=\": ").append(eword).toString());
        String word = eword.substring(start, pos);
        try
        {
            String decodedWord;
            if(word.length() > 0)
            {
                ByteArrayInputStream bis = new ByteArrayInputStream(ASCIIUtility.getBytes(word));
                InputStream is;
                if(encoding.equalsIgnoreCase("B"))
                    is = new BASE64DecoderStream(bis);
                else
                if(encoding.equalsIgnoreCase("Q"))
                    is = new QDecoderStream(bis);
                else
                    throw new UnsupportedEncodingException((new StringBuilder()).append("unknown encoding: ").append(encoding).toString());
                int count = bis.available();
                byte bytes[] = new byte[count];
                count = is.read(bytes, 0, count);
                decodedWord = count > 0 ? new String(bytes, 0, count, charset) : "";
            } else
            {
                decodedWord = "";
            }
            if(pos + 2 < eword.length())
            {
                String rest = eword.substring(pos + 2);
                if(!decodeStrict)
                    rest = decodeInnerWords(rest);
                decodedWord = (new StringBuilder()).append(decodedWord).append(rest).toString();
            }
            return decodedWord;
        }
        catch(UnsupportedEncodingException uex)
        {
            throw uex;
        }
        catch(IOException ioex)
        {
            throw new ParseException(ioex.toString());
        }
        catch(IllegalArgumentException iex)
        {
            throw new UnsupportedEncodingException(charset);
        }
    }

    private static String decodeInnerWords(String word)
        throws UnsupportedEncodingException
    {
        int start = 0;
        StringBuffer buf = new StringBuffer();
        do
        {
            int i;
            if((i = word.indexOf("=?", start)) < 0)
                break;
            buf.append(word.substring(start, i));
            int end = word.indexOf('?', i + 2);
            if(end < 0)
                break;
            end = word.indexOf('?', end + 1);
            if(end < 0)
                break;
            end = word.indexOf("?=", end + 1);
            if(end < 0)
                break;
            String s = word.substring(i, end + 2);
            try
            {
                s = decodeWord(s);
            }
            catch(ParseException pex) { }
            buf.append(s);
            start = end + 2;
        } while(true);
        if(start == 0)
            return word;
        if(start < word.length())
            buf.append(word.substring(start));
        return buf.toString();
    }

    public static String quote(String word, String specials)
    {
        int len = word.length();
        if(len == 0)
            return "\"\"";
        boolean needQuoting = false;
        for(int i = 0; i < len; i++)
        {
            char c = word.charAt(i);
            if(c == '"' || c == '\\' || c == '\r' || c == '\n')
            {
                StringBuffer sb = new StringBuffer(len + 3);
                sb.append('"');
                sb.append(word.substring(0, i));
                int lastc = 0;
                for(int j = i; j < len; j++)
                {
                    char cc = word.charAt(j);
                    if((cc == '"' || cc == '\\' || cc == '\r' || cc == '\n') && (cc != '\n' || lastc != 13))
                        sb.append('\\');
                    sb.append(cc);
                    lastc = cc;
                }

                sb.append('"');
                return sb.toString();
            }
            if(c < ' ' || c >= '\177' || specials.indexOf(c) >= 0)
                needQuoting = true;
        }

        if(needQuoting)
        {
            StringBuffer sb = new StringBuffer(len + 2);
            sb.append('"').append(word).append('"');
            return sb.toString();
        } else
        {
            return word;
        }
    }

    public static String fold(int used, String s)
    {
        if(!foldText)
            return s;
        int end = s.length() - 1;
        do
        {
            if(end < 0)
                break;
            char c = s.charAt(end);
            if(c != ' ' && c != '\t' && c != '\r' && c != '\n')
                break;
            end--;
        } while(true);
        if(end != s.length() - 1)
            s = s.substring(0, end + 1);
        if(used + s.length() <= 76)
            return s;
        StringBuffer sb = new StringBuffer(s.length() + 4);
        char lastc = '\0';
        do
        {
            if(used + s.length() <= 76)
                break;
            int lastspace = -1;
            for(int i = 0; i < s.length() && (lastspace == -1 || used + i <= 76); i++)
            {
                char c = s.charAt(i);
                if((c == ' ' || c == '\t') && lastc != ' ' && lastc != '\t')
                    lastspace = i;
                lastc = c;
            }

            if(lastspace == -1)
            {
                sb.append(s);
                s = "";
                used = 0;
                break;
            }
            sb.append(s.substring(0, lastspace));
            sb.append("\r\n");
            lastc = s.charAt(lastspace);
            sb.append(lastc);
            s = s.substring(lastspace + 1);
            used = 1;
        } while(true);
        sb.append(s);
        return sb.toString();
    }

    public static String unfold(String s)
    {
        if(!foldText)
            return s;
        StringBuffer sb = null;
        int i;
        while((i = indexOfAny(s, "\r\n")) >= 0) 
        {
            int start = i;
            int l = s.length();
            if(++i < l && s.charAt(i - 1) == '\r' && s.charAt(i) == '\n')
                i++;
            char c;
            if(start == 0 || s.charAt(start - 1) != '\\')
            {
                if(i < l && ((c = s.charAt(i)) == ' ' || c == '\t'))
                {
                    for(i++; i < l && ((c = s.charAt(i)) == ' ' || c == '\t'); i++);
                    if(sb == null)
                        sb = new StringBuffer(s.length());
                    if(start != 0)
                    {
                        sb.append(s.substring(0, start));
                        sb.append(' ');
                    }
                    s = s.substring(i);
                } else
                {
                    if(sb == null)
                        sb = new StringBuffer(s.length());
                    sb.append(s.substring(0, i));
                    s = s.substring(i);
                }
            } else
            {
                if(sb == null)
                    sb = new StringBuffer(s.length());
                sb.append(s.substring(0, start - 1));
                sb.append(s.substring(start, i));
                s = s.substring(i);
            }
        }
        if(sb != null)
        {
            sb.append(s);
            return sb.toString();
        } else
        {
            return s;
        }
    }

    private static int indexOfAny(String s, String any)
    {
        return indexOfAny(s, any, 0);
    }

    private static int indexOfAny(String s, String any, int start)
    {
        int len;
        int i;
        len = s.length();
        i = start;
_L1:
        if(i >= len)
            break MISSING_BLOCK_LABEL_36;
        if(any.indexOf(s.charAt(i)) >= 0)
            return i;
        i++;
          goto _L1
        return -1;
        StringIndexOutOfBoundsException e;
        e;
        return -1;
    }

    public static String javaCharset(String charset)
    {
        if(mime2java == null || charset == null)
        {
            return charset;
        } else
        {
            String alias = (String)mime2java.get(charset.toLowerCase(Locale.ENGLISH));
            return alias != null ? alias : charset;
        }
    }

    public static String mimeCharset(String charset)
    {
        if(java2mime == null || charset == null)
        {
            return charset;
        } else
        {
            String alias = (String)java2mime.get(charset.toLowerCase(Locale.ENGLISH));
            return alias != null ? alias : charset;
        }
    }

    public static String getDefaultJavaCharset()
    {
        if(defaultJavaCharset == null)
        {
            String mimecs = null;
            try
            {
                mimecs = System.getProperty("mail.mime.charset");
            }
            catch(SecurityException ex) { }
            if(mimecs != null && mimecs.length() > 0)
            {
                defaultJavaCharset = javaCharset(mimecs);
                return defaultJavaCharset;
            }
            try
            {
                defaultJavaCharset = System.getProperty("file.encoding", "8859_1");
            }
            catch(SecurityException sex)
            {
                class _cls1NullInputStream extends InputStream
                {

                    public int read()
                    {
                        return 0;
                    }

            _cls1NullInputStream()
            {
            }
                }

                InputStreamReader reader = new InputStreamReader(new _cls1NullInputStream());
                defaultJavaCharset = reader.getEncoding();
                if(defaultJavaCharset == null)
                    defaultJavaCharset = "8859_1";
            }
        }
        return defaultJavaCharset;
    }

    static String getDefaultMIMECharset()
    {
        if(defaultMIMECharset == null)
            try
            {
                defaultMIMECharset = System.getProperty("mail.mime.charset");
            }
            catch(SecurityException ex) { }
        if(defaultMIMECharset == null)
            defaultMIMECharset = mimeCharset(getDefaultJavaCharset());
        return defaultMIMECharset;
    }

    private static void loadMappings(LineInputStream is, Hashtable table)
    {
        do
        {
            String currLine;
            try
            {
                currLine = is.readLine();
            }
            catch(IOException ioex)
            {
                break;
            }
            if(currLine == null || currLine.startsWith("--") && currLine.endsWith("--"))
                break;
            if(currLine.trim().length() != 0 && !currLine.startsWith("#"))
            {
                StringTokenizer tk = new StringTokenizer(currLine, " \t");
                try
                {
                    String key = tk.nextToken();
                    String value = tk.nextToken();
                    table.put(key.toLowerCase(Locale.ENGLISH), value);
                }
                catch(NoSuchElementException nex) { }
            }
        } while(true);
    }

    static int checkAscii(String s)
    {
        int ascii = 0;
        int non_ascii = 0;
        int l = s.length();
        for(int i = 0; i < l; i++)
            if(nonascii(s.charAt(i)))
                non_ascii++;
            else
                ascii++;

        if(non_ascii == 0)
            return 1;
        return ascii <= non_ascii ? 3 : 2;
    }

    static int checkAscii(byte b[])
    {
        int ascii = 0;
        int non_ascii = 0;
        for(int i = 0; i < b.length; i++)
            if(nonascii(b[i] & 0xff))
                non_ascii++;
            else
                ascii++;

        if(non_ascii == 0)
            return 1;
        return ascii <= non_ascii ? 3 : 2;
    }

    static int checkAscii(InputStream is, int max, boolean breakOnNonAscii)
    {
        int ascii;
        int non_ascii;
        int block;
        int linelen;
        boolean longLine;
        boolean badEOL;
        boolean checkEOL;
        byte buf[];
        ascii = 0;
        non_ascii = 0;
        block = 4096;
        linelen = 0;
        longLine = false;
        badEOL = false;
        checkEOL = encodeEolStrict && breakOnNonAscii;
        buf = null;
        if(max != 0)
        {
            block = max != -1 ? Math.min(max, 4096) : 4096;
            buf = new byte[block];
        }
_L5:
        int len;
        if(max == 0)
            break; /* Loop/switch isn't completed */
        int lastb;
        int i;
        int b;
        try
        {
            if((len = is.read(buf, 0, block)) == -1)
                break; /* Loop/switch isn't completed */
        }
        catch(IOException ioex)
        {
            break; /* Loop/switch isn't completed */
        }
        lastb = 0;
        i = 0;
_L3:
        if(i >= len)
            break MISSING_BLOCK_LABEL_227;
        b = buf[i] & 0xff;
        if(checkEOL && (lastb == 13 && b != 10 || lastb != 13 && b == 10))
            badEOL = true;
        if(b == 13 || b == 10)
            linelen = 0;
        else
        if(++linelen > 998)
            longLine = true;
        if(!nonascii(b)) goto _L2; else goto _L1
_L1:
        if(breakOnNonAscii)
            return 3;
        non_ascii++;
        break MISSING_BLOCK_LABEL_209;
_L2:
        ascii++;
        lastb = b;
        i++;
          goto _L3
        if(max != -1)
            max -= len;
        if(true) goto _L5; else goto _L4
_L4:
        if(max == 0 && breakOnNonAscii)
            return 3;
        if(non_ascii == 0)
        {
            if(badEOL)
                return 3;
            return !longLine ? 1 : 2;
        }
        return ascii <= non_ascii ? 3 : 2;
    }

    static final boolean nonascii(int b)
    {
        return b >= 127 || b < 32 && b != 13 && b != 10 && b != 9;
    }

    public static final int ALL = -1;
    private static final Map nonAsciiCharsetMap;
    private static final boolean decodeStrict;
    private static final boolean encodeEolStrict;
    private static final boolean ignoreUnknownEncoding;
    private static final boolean foldEncodedWords;
    private static final boolean foldText;
    private static String defaultJavaCharset;
    private static String defaultMIMECharset;
    private static Hashtable mime2java;
    private static Hashtable java2mime;
    static final int ALL_ASCII = 1;
    static final int MOSTLY_ASCII = 2;
    static final int MOSTLY_NONASCII = 3;

    static 
    {
        nonAsciiCharsetMap = new HashMap();
        decodeStrict = PropUtil.getBooleanSystemProperty("mail.mime.decodetext.strict", true);
        encodeEolStrict = PropUtil.getBooleanSystemProperty("mail.mime.encodeeol.strict", false);
        ignoreUnknownEncoding = PropUtil.getBooleanSystemProperty("mail.mime.ignoreunknownencoding", false);
        foldEncodedWords = PropUtil.getBooleanSystemProperty("mail.mime.foldencodedwords", false);
        foldText = PropUtil.getBooleanSystemProperty("mail.mime.foldtext", true);
        java2mime = new Hashtable(40);
        mime2java = new Hashtable(10);
        InputStream is;
        is = javax/mail/internet/MimeUtility.getResourceAsStream("/META-INF/javamail.charset.map");
        if(is == null)
            break MISSING_BLOCK_LABEL_147;
        is = new LineInputStream(is);
        loadMappings((LineInputStream)is, java2mime);
        loadMappings((LineInputStream)is, mime2java);
        try
        {
            is.close();
        }
        catch(Exception cex) { }
        break MISSING_BLOCK_LABEL_147;
        Exception exception;
        exception;
        try
        {
            is.close();
        }
        catch(Exception cex) { }
        throw exception;
        Exception ex;
        ex;
        if(java2mime.isEmpty())
        {
            java2mime.put("8859_1", "ISO-8859-1");
            java2mime.put("iso8859_1", "ISO-8859-1");
            java2mime.put("iso8859-1", "ISO-8859-1");
            java2mime.put("8859_2", "ISO-8859-2");
            java2mime.put("iso8859_2", "ISO-8859-2");
            java2mime.put("iso8859-2", "ISO-8859-2");
            java2mime.put("8859_3", "ISO-8859-3");
            java2mime.put("iso8859_3", "ISO-8859-3");
            java2mime.put("iso8859-3", "ISO-8859-3");
            java2mime.put("8859_4", "ISO-8859-4");
            java2mime.put("iso8859_4", "ISO-8859-4");
            java2mime.put("iso8859-4", "ISO-8859-4");
            java2mime.put("8859_5", "ISO-8859-5");
            java2mime.put("iso8859_5", "ISO-8859-5");
            java2mime.put("iso8859-5", "ISO-8859-5");
            java2mime.put("8859_6", "ISO-8859-6");
            java2mime.put("iso8859_6", "ISO-8859-6");
            java2mime.put("iso8859-6", "ISO-8859-6");
            java2mime.put("8859_7", "ISO-8859-7");
            java2mime.put("iso8859_7", "ISO-8859-7");
            java2mime.put("iso8859-7", "ISO-8859-7");
            java2mime.put("8859_8", "ISO-8859-8");
            java2mime.put("iso8859_8", "ISO-8859-8");
            java2mime.put("iso8859-8", "ISO-8859-8");
            java2mime.put("8859_9", "ISO-8859-9");
            java2mime.put("iso8859_9", "ISO-8859-9");
            java2mime.put("iso8859-9", "ISO-8859-9");
            java2mime.put("sjis", "Shift_JIS");
            java2mime.put("jis", "ISO-2022-JP");
            java2mime.put("iso2022jp", "ISO-2022-JP");
            java2mime.put("euc_jp", "euc-jp");
            java2mime.put("koi8_r", "koi8-r");
            java2mime.put("euc_cn", "euc-cn");
            java2mime.put("euc_tw", "euc-tw");
            java2mime.put("euc_kr", "euc-kr");
        }
        if(mime2java.isEmpty())
        {
            mime2java.put("iso-2022-cn", "ISO2022CN");
            mime2java.put("iso-2022-kr", "ISO2022KR");
            mime2java.put("utf-8", "UTF8");
            mime2java.put("utf8", "UTF8");
            mime2java.put("ja_jp.iso2022-7", "ISO2022JP");
            mime2java.put("ja_jp.eucjp", "EUCJIS");
            mime2java.put("euc-kr", "KSC5601");
            mime2java.put("euckr", "KSC5601");
            mime2java.put("us-ascii", "ISO-8859-1");
            mime2java.put("x-us-ascii", "ISO-8859-1");
        }
    }
}
