// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InternetHeaders.java

package javax.mail.internet;

import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.PropUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.Header;
import javax.mail.MessagingException;

public class InternetHeaders
{
    static class matchEnum
        implements Enumeration
    {

        public boolean hasMoreElements()
        {
            if(next_header == null)
                next_header = nextMatch();
            return next_header != null;
        }

        public Object nextElement()
        {
            if(next_header == null)
                next_header = nextMatch();
            if(next_header == null)
                throw new NoSuchElementException("No more headers");
            InternetHeader h = next_header;
            next_header = null;
            if(want_line)
                return h.line;
            else
                return new Header(h.getName(), h.getValue());
        }

        private InternetHeader nextMatch()
        {
_L2:
            InternetHeader h;
            int i;
            do
            {
                if(!e.hasNext())
                    break MISSING_BLOCK_LABEL_109;
                h = (InternetHeader)e.next();
            } while(h.line == null);
            if(names == null)
                return match ? null : h;
            i = 0;
_L3:
label0:
            {
                if(i >= names.length)
                    continue; /* Loop/switch isn't completed */
                if(!names[i].equalsIgnoreCase(h.getName()))
                    break label0;
                if(match)
                    return h;
            }
            if(true) goto _L2; else goto _L1
_L1:
            i++;
              goto _L3
            if(match) goto _L2; else goto _L4
_L4:
            return h;
            return null;
        }

        private Iterator e;
        private String names[];
        private boolean match;
        private boolean want_line;
        private InternetHeader next_header;

        matchEnum(List v, String n[], boolean m, boolean l)
        {
            e = v.iterator();
            names = n;
            match = m;
            want_line = l;
            next_header = null;
        }
    }

    protected static final class InternetHeader extends Header
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

        public InternetHeader(String l)
        {
            super("", "");
            int i = l.indexOf(':');
            if(i < 0)
                name = l.trim();
            else
                name = l.substring(0, i).trim();
            line = l;
        }

        public InternetHeader(String n, String v)
        {
            super(n, "");
            if(v != null)
                line = (new StringBuilder()).append(n).append(": ").append(v).toString();
            else
                line = null;
        }
    }


    public InternetHeaders()
    {
        headers = new ArrayList(40);
        headers.add(new InternetHeader("Return-Path", null));
        headers.add(new InternetHeader("Received", null));
        headers.add(new InternetHeader("Resent-Date", null));
        headers.add(new InternetHeader("Resent-From", null));
        headers.add(new InternetHeader("Resent-Sender", null));
        headers.add(new InternetHeader("Resent-To", null));
        headers.add(new InternetHeader("Resent-Cc", null));
        headers.add(new InternetHeader("Resent-Bcc", null));
        headers.add(new InternetHeader("Resent-Message-Id", null));
        headers.add(new InternetHeader("Date", null));
        headers.add(new InternetHeader("From", null));
        headers.add(new InternetHeader("Sender", null));
        headers.add(new InternetHeader("Reply-To", null));
        headers.add(new InternetHeader("To", null));
        headers.add(new InternetHeader("Cc", null));
        headers.add(new InternetHeader("Bcc", null));
        headers.add(new InternetHeader("Message-Id", null));
        headers.add(new InternetHeader("In-Reply-To", null));
        headers.add(new InternetHeader("References", null));
        headers.add(new InternetHeader("Subject", null));
        headers.add(new InternetHeader("Comments", null));
        headers.add(new InternetHeader("Keywords", null));
        headers.add(new InternetHeader("Errors-To", null));
        headers.add(new InternetHeader("MIME-Version", null));
        headers.add(new InternetHeader("Content-Type", null));
        headers.add(new InternetHeader("Content-Transfer-Encoding", null));
        headers.add(new InternetHeader("Content-MD5", null));
        headers.add(new InternetHeader(":", null));
        headers.add(new InternetHeader("Content-Length", null));
        headers.add(new InternetHeader("Status", null));
    }

    public InternetHeaders(InputStream is)
        throws MessagingException
    {
        headers = new ArrayList(40);
        load(is);
    }

    public void load(InputStream is)
        throws MessagingException
    {
        LineInputStream lis = new LineInputStream(is);
        String prevline = null;
        StringBuffer lineBuffer = new StringBuffer();
        String line;
        try
        {
            do
            {
                line = lis.readLine();
                if(line != null && (line.startsWith(" ") || line.startsWith("\t")))
                {
                    if(prevline != null)
                    {
                        lineBuffer.append(prevline);
                        prevline = null;
                    }
                    lineBuffer.append("\r\n");
                    lineBuffer.append(line);
                } else
                {
                    if(prevline != null)
                        addHeaderLine(prevline);
                    else
                    if(lineBuffer.length() > 0)
                    {
                        addHeaderLine(lineBuffer.toString());
                        lineBuffer.setLength(0);
                    }
                    prevline = line;
                }
            } while(line != null && !isEmpty(line));
        }
        catch(IOException ioex)
        {
            throw new MessagingException("Error in input stream", ioex);
        }
    }

    private static final boolean isEmpty(String line)
    {
        return line.length() == 0 || ignoreWhitespaceLines && line.trim().length() == 0;
    }

    public String[] getHeader(String name)
    {
        Iterator e = headers.iterator();
        List v = new ArrayList();
        do
        {
            if(!e.hasNext())
                break;
            InternetHeader h = (InternetHeader)e.next();
            if(name.equalsIgnoreCase(h.getName()) && h.line != null)
                v.add(h.getValue());
        } while(true);
        if(v.size() == 0)
        {
            return null;
        } else
        {
            String r[] = new String[v.size()];
            r = (String[])(String[])v.toArray(r);
            return r;
        }
    }

    public String getHeader(String name, String delimiter)
    {
        String s[] = getHeader(name);
        if(s == null)
            return null;
        if(s.length == 1 || delimiter == null)
            return s[0];
        StringBuffer r = new StringBuffer(s[0]);
        for(int i = 1; i < s.length; i++)
        {
            r.append(delimiter);
            r.append(s[i]);
        }

        return r.toString();
    }

    public void setHeader(String name, String value)
    {
        boolean found = false;
        for(int i = 0; i < headers.size(); i++)
        {
            InternetHeader h = (InternetHeader)headers.get(i);
            if(!name.equalsIgnoreCase(h.getName()))
                continue;
            if(!found)
            {
                int j;
                if(h.line != null && (j = h.line.indexOf(':')) >= 0)
                    h.line = (new StringBuilder()).append(h.line.substring(0, j + 1)).append(" ").append(value).toString();
                else
                    h.line = (new StringBuilder()).append(name).append(": ").append(value).toString();
                found = true;
            } else
            {
                headers.remove(i);
                i--;
            }
        }

        if(!found)
            addHeader(name, value);
    }

    public void addHeader(String name, String value)
    {
        int pos = headers.size();
        boolean addReverse = name.equalsIgnoreCase("Received") || name.equalsIgnoreCase("Return-Path");
        if(addReverse)
            pos = 0;
        for(int i = headers.size() - 1; i >= 0; i--)
        {
            InternetHeader h = (InternetHeader)headers.get(i);
            if(name.equalsIgnoreCase(h.getName()))
                if(addReverse)
                {
                    pos = i;
                } else
                {
                    headers.add(i + 1, new InternetHeader(name, value));
                    return;
                }
            if(!addReverse && h.getName().equals(":"))
                pos = i;
        }

        headers.add(pos, new InternetHeader(name, value));
    }

    public void removeHeader(String name)
    {
        for(int i = 0; i < headers.size(); i++)
        {
            InternetHeader h = (InternetHeader)headers.get(i);
            if(name.equalsIgnoreCase(h.getName()))
                h.line = null;
        }

    }

    public Enumeration getAllHeaders()
    {
        return new matchEnum(headers, null, false, false);
    }

    public Enumeration getMatchingHeaders(String names[])
    {
        return new matchEnum(headers, names, true, false);
    }

    public Enumeration getNonMatchingHeaders(String names[])
    {
        return new matchEnum(headers, names, false, false);
    }

    public void addHeaderLine(String line)
    {
        InternetHeader h;
        char c = line.charAt(0);
        if(c != ' ' && c != '\t')
            break MISSING_BLOCK_LABEL_75;
        h = (InternetHeader)headers.get(headers.size() - 1);
        new StringBuilder();
        h;
        JVM INSTR dup_x1 ;
        line;
        append();
        "\r\n";
        append();
        line;
        append();
        toString();
        line;
        break MISSING_BLOCK_LABEL_99;
        headers.add(new InternetHeader(line));
        break MISSING_BLOCK_LABEL_99;
        StringIndexOutOfBoundsException e;
        e;
        return;
        e;
    }

    public Enumeration getAllHeaderLines()
    {
        return getNonMatchingHeaderLines(null);
    }

    public Enumeration getMatchingHeaderLines(String names[])
    {
        return new matchEnum(headers, names, true, true);
    }

    public Enumeration getNonMatchingHeaderLines(String names[])
    {
        return new matchEnum(headers, names, false, true);
    }

    private static final boolean ignoreWhitespaceLines = PropUtil.getBooleanSystemProperty("mail.mime.ignorewhitespacelines", false);
    protected List headers;

}
