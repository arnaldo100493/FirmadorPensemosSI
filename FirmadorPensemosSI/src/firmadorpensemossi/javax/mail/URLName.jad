// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   URLName.java

package javax.mail;

import java.io.*;
import java.net.*;
import java.util.BitSet;
import java.util.Locale;

public class URLName
{

    public URLName(String protocol, String host, int port, String file, String username, String password)
    {
        hostAddressKnown = false;
        this.port = -1;
        hashCode = 0;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        int refStart;
        if(file != null && (refStart = file.indexOf('#')) != -1)
        {
            this.file = file.substring(0, refStart);
            ref = file.substring(refStart + 1);
        } else
        {
            this.file = file;
            ref = null;
        }
        this.username = doEncode ? encode(username) : username;
        this.password = doEncode ? encode(password) : password;
    }

    public URLName(URL url)
    {
        this(url.toString());
    }

    public URLName(String url)
    {
        hostAddressKnown = false;
        port = -1;
        hashCode = 0;
        parseString(url);
    }

    public String toString()
    {
        if(fullURL == null)
        {
            StringBuffer tempURL = new StringBuffer();
            if(protocol != null)
            {
                tempURL.append(protocol);
                tempURL.append(":");
            }
            if(username != null || host != null)
            {
                tempURL.append("//");
                if(username != null)
                {
                    tempURL.append(username);
                    if(password != null)
                    {
                        tempURL.append(":");
                        tempURL.append(password);
                    }
                    tempURL.append("@");
                }
                if(host != null)
                    tempURL.append(host);
                if(port != -1)
                {
                    tempURL.append(":");
                    tempURL.append(Integer.toString(port));
                }
                if(file != null)
                    tempURL.append("/");
            }
            if(file != null)
                tempURL.append(file);
            if(ref != null)
            {
                tempURL.append("#");
                tempURL.append(ref);
            }
            fullURL = tempURL.toString();
        }
        return fullURL;
    }

    protected void parseString(String url)
    {
        protocol = file = ref = host = username = password = null;
        port = -1;
        int len = url.length();
        int protocolEnd = url.indexOf(':');
        if(protocolEnd != -1)
            protocol = url.substring(0, protocolEnd);
        if(url.regionMatches(protocolEnd + 1, "//", 0, 2))
        {
            String fullhost = null;
            int fileStart = url.indexOf('/', protocolEnd + 3);
            if(fileStart != -1)
            {
                fullhost = url.substring(protocolEnd + 3, fileStart);
                if(fileStart + 1 < len)
                    file = url.substring(fileStart + 1);
                else
                    file = "";
            } else
            {
                fullhost = url.substring(protocolEnd + 3);
            }
            int i = fullhost.indexOf('@');
            if(i != -1)
            {
                String fulluserpass = fullhost.substring(0, i);
                fullhost = fullhost.substring(i + 1);
                int passindex = fulluserpass.indexOf(':');
                if(passindex != -1)
                {
                    username = fulluserpass.substring(0, passindex);
                    password = fulluserpass.substring(passindex + 1);
                } else
                {
                    username = fulluserpass;
                }
            }
            int portindex;
            if(fullhost.length() > 0 && fullhost.charAt(0) == '[')
                portindex = fullhost.indexOf(':', fullhost.indexOf(']'));
            else
                portindex = fullhost.indexOf(':');
            if(portindex != -1)
            {
                String portstring = fullhost.substring(portindex + 1);
                if(portstring.length() > 0)
                    try
                    {
                        port = Integer.parseInt(portstring);
                    }
                    catch(NumberFormatException nfex)
                    {
                        port = -1;
                    }
                host = fullhost.substring(0, portindex);
            } else
            {
                host = fullhost;
            }
        } else
        if(protocolEnd + 1 < len)
            file = url.substring(protocolEnd + 1);
        int refStart;
        if(file != null && (refStart = file.indexOf('#')) != -1)
        {
            ref = file.substring(refStart + 1);
            file = file.substring(0, refStart);
        }
    }

    public int getPort()
    {
        return port;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public String getFile()
    {
        return file;
    }

    public String getRef()
    {
        return ref;
    }

    public String getHost()
    {
        return host;
    }

    public String getUsername()
    {
        return doEncode ? decode(username) : username;
    }

    public String getPassword()
    {
        return doEncode ? decode(password) : password;
    }

    public URL getURL()
        throws MalformedURLException
    {
        return new URL(getProtocol(), getHost(), getPort(), getFile());
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof URLName))
            return false;
        URLName u2 = (URLName)obj;
        if(u2.protocol == null || !u2.protocol.equals(protocol))
            return false;
        InetAddress a1 = getHostAddress();
        InetAddress a2 = u2.getHostAddress();
        if(a1 != null && a2 != null)
        {
            if(!a1.equals(a2))
                return false;
        } else
        if(host != null && u2.host != null)
        {
            if(!host.equalsIgnoreCase(u2.host))
                return false;
        } else
        if(host != u2.host)
            return false;
        if(username != u2.username && (username == null || !username.equals(u2.username)))
            return false;
        String f1 = file != null ? file : "";
        String f2 = u2.file != null ? u2.file : "";
        if(!f1.equals(f2))
            return false;
        return port == u2.port;
    }

    public int hashCode()
    {
        if(hashCode != 0)
            return hashCode;
        if(protocol != null)
            hashCode += protocol.hashCode();
        InetAddress addr = getHostAddress();
        if(addr != null)
            hashCode += addr.hashCode();
        else
        if(host != null)
            hashCode += host.toLowerCase(Locale.ENGLISH).hashCode();
        if(username != null)
            hashCode += username.hashCode();
        if(file != null)
            hashCode += file.hashCode();
        hashCode += port;
        return hashCode;
    }

    private synchronized InetAddress getHostAddress()
    {
        if(hostAddressKnown)
            return hostAddress;
        if(host == null)
            return null;
        try
        {
            hostAddress = InetAddress.getByName(host);
        }
        catch(UnknownHostException ex)
        {
            hostAddress = null;
        }
        hostAddressKnown = true;
        return hostAddress;
    }

    static String encode(String s)
    {
        if(s == null)
            return null;
        for(int i = 0; i < s.length(); i++)
        {
            int c = s.charAt(i);
            if(c == 32 || !dontNeedEncoding.get(c))
                return _encode(s);
        }

        return s;
    }

    private static String _encode(String s)
    {
        int maxBytesPerChar = 10;
        StringBuffer out = new StringBuffer(s.length());
        ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);
        OutputStreamWriter writer = new OutputStreamWriter(buf);
        for(int i = 0; i < s.length(); i++)
        {
            int c = s.charAt(i);
            if(dontNeedEncoding.get(c))
            {
                if(c == 32)
                    c = 43;
                out.append((char)c);
                continue;
            }
            try
            {
                writer.write(c);
                writer.flush();
            }
            catch(IOException e)
            {
                buf.reset();
                continue;
            }
            byte ba[] = buf.toByteArray();
            for(int j = 0; j < ba.length; j++)
            {
                out.append('%');
                char ch = Character.forDigit(ba[j] >> 4 & 0xf, 16);
                if(Character.isLetter(ch))
                    ch -= ' ';
                out.append(ch);
                ch = Character.forDigit(ba[j] & 0xf, 16);
                if(Character.isLetter(ch))
                    ch -= ' ';
                out.append(ch);
            }

            buf.reset();
        }

        return out.toString();
    }

    static String decode(String s)
    {
        if(s == null)
            return null;
        if(indexOfAny(s, "+%") == -1)
            return s;
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            switch(c)
            {
            case 43: // '+'
                sb.append(' ');
                break;

            case 37: // '%'
                try
                {
                    sb.append((char)Integer.parseInt(s.substring(i + 1, i + 3), 16));
                }
                catch(NumberFormatException e)
                {
                    throw new IllegalArgumentException((new StringBuilder()).append("Illegal URL encoded value: ").append(s.substring(i, i + 3)).toString());
                }
                i += 2;
                break;

            default:
                sb.append(c);
                break;
            }
        }

        String result = sb.toString();
        try
        {
            byte inputBytes[] = result.getBytes("8859_1");
            result = new String(inputBytes);
        }
        catch(UnsupportedEncodingException e) { }
        return result;
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

    protected String fullURL;
    private String protocol;
    private String username;
    private String password;
    private String host;
    private InetAddress hostAddress;
    private boolean hostAddressKnown;
    private int port;
    private String file;
    private String ref;
    private int hashCode;
    private static boolean doEncode = true;
    static BitSet dontNeedEncoding;
    static final int caseDiff = 32;

    static 
    {
        try
        {
            doEncode = !Boolean.getBoolean("mail.URLName.dontencode");
        }
        catch(Exception ex) { }
        dontNeedEncoding = new BitSet(256);
        for(int i = 97; i <= 122; i++)
            dontNeedEncoding.set(i);

        for(int i = 65; i <= 90; i++)
            dontNeedEncoding.set(i);

        for(int i = 48; i <= 57; i++)
            dontNeedEncoding.set(i);

        dontNeedEncoding.set(32);
        dontNeedEncoding.set(45);
        dontNeedEncoding.set(95);
        dontNeedEncoding.set(46);
        dontNeedEncoding.set(42);
    }
}
