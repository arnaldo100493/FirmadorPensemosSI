// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeaderTokenizer.java

package javax.mail.internet;


// Referenced classes of package javax.mail.internet:
//            ParseException

public class HeaderTokenizer
{
    public static class Token
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

        public Token(int type, String value)
        {
            this.type = type;
            this.value = value;
        }
    }


    public HeaderTokenizer(String header, String delimiters, boolean skipComments)
    {
        string = header != null ? header : "";
        this.skipComments = skipComments;
        this.delimiters = delimiters;
        currentPos = nextPos = peekPos = 0;
        maxPos = string.length();
    }

    public HeaderTokenizer(String header, String delimiters)
    {
        this(header, delimiters, true);
    }

    public HeaderTokenizer(String header)
    {
        this(header, "()<>@,;:\\\"\t .[]");
    }

    public Token next()
        throws ParseException
    {
        return next('\0', false);
    }

    public Token next(char endOfAtom)
        throws ParseException
    {
        return next(endOfAtom, false);
    }

    public Token next(char endOfAtom, boolean keepEscapes)
        throws ParseException
    {
        currentPos = nextPos;
        Token tk = getNext(endOfAtom, keepEscapes);
        nextPos = peekPos = currentPos;
        return tk;
    }

    public Token peek()
        throws ParseException
    {
        currentPos = peekPos;
        Token tk = getNext('\0', false);
        peekPos = currentPos;
        return tk;
    }

    public String getRemainder()
    {
        return string.substring(nextPos);
    }

    private Token getNext(char endOfAtom, boolean keepEscapes)
        throws ParseException
    {
        if(currentPos >= maxPos)
            return EOFToken;
        if(skipWhiteSpace() == -4)
            return EOFToken;
        boolean filter = false;
        char c;
        int start;
        for(c = string.charAt(currentPos); c == '('; c = string.charAt(currentPos))
        {
            start = ++currentPos;
            int nesting;
            for(nesting = 1; nesting > 0 && currentPos < maxPos; currentPos++)
            {
                c = string.charAt(currentPos);
                if(c == '\\')
                {
                    currentPos++;
                    filter = true;
                    continue;
                }
                if(c == '\r')
                {
                    filter = true;
                    continue;
                }
                if(c == '(')
                {
                    nesting++;
                    continue;
                }
                if(c == ')')
                    nesting--;
            }

            if(nesting != 0)
                throw new ParseException("Unbalanced comments");
            if(!skipComments)
            {
                String s;
                if(filter)
                    s = filterToken(string, start, currentPos - 1, keepEscapes);
                else
                    s = string.substring(start, currentPos - 1);
                return new Token(-3, s);
            }
            if(skipWhiteSpace() == -4)
                return EOFToken;
        }

        if(c == '"')
        {
            currentPos++;
            return collectString('"', keepEscapes);
        }
        if(c < ' ' || c >= '\177' || delimiters.indexOf(c) >= 0)
            if(endOfAtom > 0 && c != endOfAtom)
            {
                return collectString(endOfAtom, keepEscapes);
            } else
            {
                currentPos++;
                char ch[] = new char[1];
                ch[0] = c;
                return new Token(c, new String(ch));
            }
        start = currentPos;
        do
        {
            if(currentPos >= maxPos)
                break;
            c = string.charAt(currentPos);
            if(c < ' ' || c >= '\177' || c == '(' || c == ' ' || c == '"' || delimiters.indexOf(c) >= 0)
            {
                if(endOfAtom > 0 && c != endOfAtom)
                {
                    currentPos = start;
                    return collectString(endOfAtom, keepEscapes);
                }
                break;
            }
            currentPos++;
        } while(true);
        return new Token(-1, string.substring(start, currentPos));
    }

    private Token collectString(char eos, boolean keepEscapes)
        throws ParseException
    {
        boolean filter = false;
        int start = currentPos;
        for(; currentPos < maxPos; currentPos++)
        {
            char c = string.charAt(currentPos);
            if(c == '\\')
            {
                currentPos++;
                filter = true;
                continue;
            }
            if(c == '\r')
            {
                filter = true;
                continue;
            }
            if(c != eos)
                continue;
            currentPos++;
            String s;
            if(filter)
                s = filterToken(string, start, currentPos - 1, keepEscapes);
            else
                s = string.substring(start, currentPos - 1);
            if(c != '"')
            {
                s = trimWhiteSpace(s);
                currentPos--;
            }
            return new Token(-2, s);
        }

        if(eos == '"')
            throw new ParseException("Unbalanced quoted string");
        String s;
        if(filter)
            s = filterToken(string, start, currentPos, keepEscapes);
        else
            s = string.substring(start, currentPos);
        s = trimWhiteSpace(s);
        return new Token(-2, s);
    }

    private int skipWhiteSpace()
    {
        for(; currentPos < maxPos; currentPos++)
        {
            char c;
            if((c = string.charAt(currentPos)) != ' ' && c != '\t' && c != '\r' && c != '\n')
                return currentPos;
        }

        return -4;
    }

    private static String trimWhiteSpace(String s)
    {
        char c;
        int i;
        for(i = s.length() - 1; i >= 0 && ((c = s.charAt(i)) == ' ' || c == '\t' || c == '\r' || c == '\n'); i--);
        if(i <= 0)
            return "";
        else
            return s.substring(0, i + 1);
    }

    private static String filterToken(String s, int start, int end, boolean keepEscapes)
    {
        StringBuffer sb = new StringBuffer();
        boolean gotEscape = false;
        boolean gotCR = false;
        for(int i = start; i < end; i++)
        {
            char c = s.charAt(i);
            if(c == '\n' && gotCR)
            {
                gotCR = false;
                continue;
            }
            gotCR = false;
            if(!gotEscape)
            {
                if(c == '\\')
                {
                    gotEscape = true;
                    continue;
                }
                if(c == '\r')
                    gotCR = true;
                else
                    sb.append(c);
                continue;
            }
            if(keepEscapes)
                sb.append('\\');
            sb.append(c);
            gotEscape = false;
        }

        return sb.toString();
    }

    private String string;
    private boolean skipComments;
    private String delimiters;
    private int currentPos;
    private int maxPos;
    private int nextPos;
    private int peekPos;
    public static final String RFC822 = "()<>@,;:\\\"\t .[]";
    public static final String MIME = "()<>@,;:\\\"\t []/?=";
    private static final Token EOFToken = new Token(-4, null);

}
