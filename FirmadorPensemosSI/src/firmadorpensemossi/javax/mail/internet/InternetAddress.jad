// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InternetAddress.java

package javax.mail.internet;

import com.sun.mail.util.PropUtil;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import javax.mail.Address;
import javax.mail.Session;

// Referenced classes of package javax.mail.internet:
//            AddressException, MimeUtility

public class InternetAddress extends Address
    implements Cloneable
{

    public InternetAddress()
    {
    }

    public InternetAddress(String address)
        throws AddressException
    {
        InternetAddress a[] = parse(address, true);
        if(a.length != 1)
        {
            throw new AddressException("Illegal address", address);
        } else
        {
            this.address = a[0].address;
            personal = a[0].personal;
            encodedPersonal = a[0].encodedPersonal;
            return;
        }
    }

    public InternetAddress(String address, boolean strict)
        throws AddressException
    {
        this(address);
        if(strict)
            if(isGroup())
                getGroup(true);
            else
                checkAddress(this.address, true, true);
    }

    public InternetAddress(String address, String personal)
        throws UnsupportedEncodingException
    {
        this(address, personal, null);
    }

    public InternetAddress(String address, String personal, String charset)
        throws UnsupportedEncodingException
    {
        this.address = address;
        setPersonal(personal, charset);
    }

    public Object clone()
    {
        InternetAddress a = null;
        try
        {
            a = (InternetAddress)super.clone();
        }
        catch(CloneNotSupportedException e) { }
        return a;
    }

    public String getType()
    {
        return "rfc822";
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setPersonal(String name, String charset)
        throws UnsupportedEncodingException
    {
        personal = name;
        if(name != null)
            encodedPersonal = MimeUtility.encodeWord(name, charset, null);
        else
            encodedPersonal = null;
    }

    public void setPersonal(String name)
        throws UnsupportedEncodingException
    {
        personal = name;
        if(name != null)
            encodedPersonal = MimeUtility.encodeWord(name);
        else
            encodedPersonal = null;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPersonal()
    {
        if(personal != null)
            return personal;
        if(encodedPersonal != null)
            try
            {
                personal = MimeUtility.decodeText(encodedPersonal);
                return personal;
            }
            catch(Exception ex)
            {
                return encodedPersonal;
            }
        else
            return null;
    }

    public String toString()
    {
        if(encodedPersonal == null && personal != null)
            try
            {
                encodedPersonal = MimeUtility.encodeWord(personal);
            }
            catch(UnsupportedEncodingException ex) { }
        if(encodedPersonal != null)
            return (new StringBuilder()).append(quotePhrase(encodedPersonal)).append(" <").append(address).append(">").toString();
        if(isGroup() || isSimple())
            return address;
        else
            return (new StringBuilder()).append("<").append(address).append(">").toString();
    }

    public String toUnicodeString()
    {
        String p = getPersonal();
        if(p != null)
            return (new StringBuilder()).append(quotePhrase(p)).append(" <").append(address).append(">").toString();
        if(isGroup() || isSimple())
            return address;
        else
            return (new StringBuilder()).append("<").append(address).append(">").toString();
    }

    private static String quotePhrase(String phrase)
    {
        int len = phrase.length();
        boolean needQuoting = false;
        for(int i = 0; i < len; i++)
        {
            char c = phrase.charAt(i);
            if(c == '"' || c == '\\')
            {
                StringBuffer sb = new StringBuffer(len + 3);
                sb.append('"');
                for(int j = 0; j < len; j++)
                {
                    char cc = phrase.charAt(j);
                    if(cc == '"' || cc == '\\')
                        sb.append('\\');
                    sb.append(cc);
                }

                sb.append('"');
                return sb.toString();
            }
            if(c < ' ' && c != '\r' && c != '\n' && c != '\t' || c >= '\177' || rfc822phrase.indexOf(c) >= 0)
                needQuoting = true;
        }

        if(needQuoting)
        {
            StringBuffer sb = new StringBuffer(len + 2);
            sb.append('"').append(phrase).append('"');
            return sb.toString();
        } else
        {
            return phrase;
        }
    }

    private static String unquote(String s)
    {
        if(s.startsWith("\"") && s.endsWith("\"") && s.length() > 1)
        {
            s = s.substring(1, s.length() - 1);
            if(s.indexOf('\\') >= 0)
            {
                StringBuffer sb = new StringBuffer(s.length());
                for(int i = 0; i < s.length(); i++)
                {
                    char c = s.charAt(i);
                    if(c == '\\' && i < s.length() - 1)
                        c = s.charAt(++i);
                    sb.append(c);
                }

                s = sb.toString();
            }
        }
        return s;
    }

    public boolean equals(Object a)
    {
        if(!(a instanceof InternetAddress))
            return false;
        String s = ((InternetAddress)a).getAddress();
        if(s == address)
            return true;
        return address != null && address.equalsIgnoreCase(s);
    }

    public int hashCode()
    {
        if(address == null)
            return 0;
        else
            return address.toLowerCase(Locale.ENGLISH).hashCode();
    }

    public static String toString(Address addresses[])
    {
        return toString(addresses, 0);
    }

    public static String toString(Address addresses[], int used)
    {
        if(addresses == null || addresses.length == 0)
            return null;
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < addresses.length; i++)
        {
            if(i != 0)
            {
                sb.append(", ");
                used += 2;
            }
            String s = addresses[i].toString();
            int len = lengthOfFirstSegment(s);
            if(used + len > 76)
            {
                sb.append("\r\n\t");
                used = 8;
            }
            sb.append(s);
            used = lengthOfLastSegment(s, used);
        }

        return sb.toString();
    }

    private static int lengthOfFirstSegment(String s)
    {
        int pos;
        if((pos = s.indexOf("\r\n")) != -1)
            return pos;
        else
            return s.length();
    }

    private static int lengthOfLastSegment(String s, int used)
    {
        int pos;
        if((pos = s.lastIndexOf("\r\n")) != -1)
            return s.length() - pos - 2;
        else
            return s.length() + used;
    }

    public static InternetAddress getLocalAddress(Session session)
    {
        try
        {
            return _getLocalAddress(session);
        }
        catch(SecurityException sex) { }
        catch(AddressException ex) { }
        catch(UnknownHostException ex) { }
        return null;
    }

    static InternetAddress _getLocalAddress(Session session)
        throws SecurityException, AddressException, UnknownHostException
    {
        String user = null;
        String host = null;
        String address = null;
        if(session == null)
        {
            user = System.getProperty("user.name");
            host = getLocalHostName();
        } else
        {
            address = session.getProperty("mail.from");
            if(address == null)
            {
                user = session.getProperty("mail.user");
                if(user == null || user.length() == 0)
                    user = session.getProperty("user.name");
                if(user == null || user.length() == 0)
                    user = System.getProperty("user.name");
                host = session.getProperty("mail.host");
                if(host == null || host.length() == 0)
                    host = getLocalHostName();
            }
        }
        if(address == null && user != null && user.length() != 0 && host != null && host.length() != 0)
            address = (new StringBuilder()).append(MimeUtility.quote(user.trim(), "()<>,;:\\\"[]@\t ")).append("@").append(host).toString();
        if(address == null)
            return null;
        else
            return new InternetAddress(address);
    }

    private static String getLocalHostName()
        throws UnknownHostException
    {
        String host = null;
        InetAddress me = InetAddress.getLocalHost();
        if(me != null)
        {
            host = me.getHostName();
            if(host != null && host.length() > 0 && isInetAddressLiteral(host))
                host = (new StringBuilder()).append('[').append(host).append(']').toString();
        }
        return host;
    }

    private static boolean isInetAddressLiteral(String addr)
    {
        boolean sawHex = false;
        boolean sawColon = false;
        for(int i = 0; i < addr.length(); i++)
        {
            char c = addr.charAt(i);
            if(c >= '0' && c <= '9' || c == '.')
                continue;
            if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
            {
                sawHex = true;
                continue;
            }
            if(c == ':')
                sawColon = true;
            else
                return false;
        }

        return !sawHex || sawColon;
    }

    public static InternetAddress[] parse(String addresslist)
        throws AddressException
    {
        return parse(addresslist, true);
    }

    public static InternetAddress[] parse(String addresslist, boolean strict)
        throws AddressException
    {
        return parse(addresslist, strict, false);
    }

    public static InternetAddress[] parseHeader(String addresslist, boolean strict)
        throws AddressException
    {
        return parse(addresslist, strict, true);
    }

    private static InternetAddress[] parse(String s, boolean strict, boolean parseHdr)
        throws AddressException
    {
        int start_personal = -1;
        int end_personal = -1;
        int length = s.length();
        boolean ignoreErrors = parseHdr && !strict;
        boolean in_group = false;
        boolean route_addr = false;
        boolean rfc822 = false;
        List v = new ArrayList();
        int end;
        int start = end = -1;
        for(int index = 0; index < length; index++)
        {
            char c = s.charAt(index);
            switch(c)
            {
            case 9: // '\t'
            case 10: // '\n'
            case 13: // '\r'
            case 32: // ' '
                break;

            case 40: // '('
                rfc822 = true;
                if(start >= 0 && end == -1)
                    end = index;
                int pindex = index;
                index++;
                int nesting;
                for(nesting = 1; index < length && nesting > 0; index++)
                {
                    c = s.charAt(index);
                    switch(c)
                    {
                    case 92: // '\\'
                        index++;
                        break;

                    case 40: // '('
                        nesting++;
                        break;

                    case 41: // ')'
                        nesting--;
                        break;
                    }
                }

                if(nesting > 0)
                {
                    if(!ignoreErrors)
                        throw new AddressException("Missing ')'", s, index);
                    index = pindex + 1;
                } else
                {
                    index--;
                    if(start_personal == -1)
                        start_personal = pindex + 1;
                    if(end_personal == -1)
                        end_personal = index;
                }
                break;

            case 41: // ')'
                if(!ignoreErrors)
                    throw new AddressException("Missing '('", s, index);
                if(start == -1)
                    start = index;
                break;

            case 60: // '<'
                rfc822 = true;
                if(route_addr)
                {
                    if(!ignoreErrors)
                        throw new AddressException("Extra route-addr", s, index);
                    if(start == -1)
                    {
                        route_addr = false;
                        rfc822 = false;
                        start = end = -1;
                        break;
                    }
                    if(!in_group)
                    {
                        if(end == -1)
                            end = index;
                        String addr = s.substring(start, end).trim();
                        InternetAddress ma = new InternetAddress();
                        ma.setAddress(addr);
                        if(start_personal >= 0)
                            ma.encodedPersonal = unquote(s.substring(start_personal, end_personal).trim());
                        v.add(ma);
                        route_addr = false;
                        rfc822 = false;
                        start = end = -1;
                        start_personal = end_personal = -1;
                    }
                }
                int rindex = index;
                boolean inquote = false;
                index++;
label0:
                do
                {
                    if(index >= length)
                        break;
                    c = s.charAt(index);
                    switch(c)
                    {
                    default:
                        break;

                    case 92: // '\\'
                        index++;
                        break;

                    case 34: // '"'
                        inquote = !inquote;
                        break;

                    case 62: // '>'
                        if(!inquote)
                            break label0;
                        break;
                    }
                    index++;
                } while(true);
                if(inquote)
                {
                    if(!ignoreErrors)
                        throw new AddressException("Missing '\"'", s, index);
                    for(index = rindex + 1; index < length; index++)
                    {
                        c = s.charAt(index);
                        if(c == '\\')
                        {
                            index++;
                            continue;
                        }
                        if(c == '>')
                            break;
                    }

                }
                if(index >= length)
                {
                    if(!ignoreErrors)
                        throw new AddressException("Missing '>'", s, index);
                    index = rindex + 1;
                    if(start == -1)
                        start = rindex;
                    break;
                }
                if(!in_group)
                {
                    start_personal = start;
                    if(start_personal >= 0)
                        end_personal = rindex;
                    start = rindex + 1;
                }
                route_addr = true;
                end = index;
                break;

            case 62: // '>'
                if(!ignoreErrors)
                    throw new AddressException("Missing '<'", s, index);
                if(start == -1)
                    start = index;
                break;

            case 34: // '"'
                int qindex = index;
                rfc822 = true;
                if(start == -1)
                    start = index;
                index++;
label1:
                do
                {
                    if(index >= length)
                        break;
                    c = s.charAt(index);
                    switch(c)
                    {
                    case 34: // '"'
                        break label1;

                    case 92: // '\\'
                        index++;
                        // fall through

                    default:
                        index++;
                        break;
                    }
                } while(true);
                if(index < length)
                    break;
                if(!ignoreErrors)
                    throw new AddressException("Missing '\"'", s, index);
                index = qindex + 1;
                break;

            case 91: // '['
                rfc822 = true;
                int lindex = index;
                index++;
label2:
                do
                {
                    if(index >= length)
                        break;
                    c = s.charAt(index);
                    switch(c)
                    {
                    case 93: // ']'
                        break label2;

                    case 92: // '\\'
                        index++;
                        // fall through

                    default:
                        index++;
                        break;
                    }
                } while(true);
                if(index < length)
                    break;
                if(!ignoreErrors)
                    throw new AddressException("Missing ']'", s, index);
                index = lindex + 1;
                break;

            case 59: // ';'
                if(start == -1)
                {
                    route_addr = false;
                    rfc822 = false;
                    start = end = -1;
                    break;
                }
                if(in_group)
                {
                    in_group = false;
                    if(!parseHdr || strict || index + 1 >= length || s.charAt(index + 1) != '@')
                    {
                        InternetAddress ma = new InternetAddress();
                        end = index + 1;
                        ma.setAddress(s.substring(start, end).trim());
                        v.add(ma);
                        route_addr = false;
                        rfc822 = false;
                        start = end = -1;
                        start_personal = end_personal = -1;
                    }
                    break;
                }
                if(!ignoreErrors)
                    throw new AddressException("Illegal semicolon, not in group", s, index);
                // fall through

            case 44: // ','
                if(start == -1)
                {
                    route_addr = false;
                    rfc822 = false;
                    start = end = -1;
                    break;
                }
                if(in_group)
                {
                    route_addr = false;
                    break;
                }
                if(end == -1)
                    end = index;
                String addr = s.substring(start, end).trim();
                String pers = null;
                if(rfc822 && start_personal >= 0)
                {
                    pers = unquote(s.substring(start_personal, end_personal).trim());
                    if(pers.trim().length() == 0)
                        pers = null;
                }
                if(parseHdr && !strict && pers != null && pers.indexOf('@') >= 0 && addr.indexOf('@') < 0 && addr.indexOf('!') < 0)
                {
                    String tmp = addr;
                    addr = pers;
                    pers = tmp;
                }
                if(rfc822 || strict || parseHdr)
                {
                    if(!ignoreErrors)
                        checkAddress(addr, route_addr, false);
                    InternetAddress ma = new InternetAddress();
                    ma.setAddress(addr);
                    if(pers != null)
                        ma.encodedPersonal = pers;
                    v.add(ma);
                } else
                {
                    InternetAddress ma;
                    for(StringTokenizer st = new StringTokenizer(addr); st.hasMoreTokens(); v.add(ma))
                    {
                        String a = st.nextToken();
                        checkAddress(a, false, false);
                        ma = new InternetAddress();
                        ma.setAddress(a);
                    }

                }
                route_addr = false;
                rfc822 = false;
                start = end = -1;
                start_personal = end_personal = -1;
                break;

            case 58: // ':'
                rfc822 = true;
                if(in_group && !ignoreErrors)
                    throw new AddressException("Nested group", s, index);
                if(start == -1)
                    start = index;
                if(parseHdr && !strict)
                {
                    if(index + 1 < length)
                    {
                        String addressSpecials = ")>[]:@\\,.";
                        char nc = s.charAt(index + 1);
                        if(addressSpecials.indexOf(nc) >= 0)
                        {
                            if(nc != '@')
                                break;
                            int i = index + 2;
                            do
                            {
                                if(i >= length)
                                    break;
                                nc = s.charAt(i);
                                if(nc == ';' || addressSpecials.indexOf(nc) >= 0)
                                    break;
                                i++;
                            } while(true);
                            if(nc == ';')
                                break;
                        }
                    }
                    String gname = s.substring(start, index);
                    if(ignoreBogusGroupName && (gname.equalsIgnoreCase("mailto") || gname.equalsIgnoreCase("From") || gname.equalsIgnoreCase("To") || gname.equalsIgnoreCase("Cc") || gname.equalsIgnoreCase("Subject") || gname.equalsIgnoreCase("Re")))
                        start = -1;
                    else
                        in_group = true;
                } else
                {
                    in_group = true;
                }
                break;

            default:
                if(start == -1)
                    start = index;
                break;
            }
        }

        if(start >= 0)
        {
            if(end == -1)
                end = length;
            String addr = s.substring(start, end).trim();
            String pers = null;
            if(rfc822 && start_personal >= 0)
            {
                pers = unquote(s.substring(start_personal, end_personal).trim());
                if(pers.trim().length() == 0)
                    pers = null;
            }
            if(parseHdr && !strict && pers != null && pers.indexOf('@') >= 0 && addr.indexOf('@') < 0 && addr.indexOf('!') < 0)
            {
                String tmp = addr;
                addr = pers;
                pers = tmp;
            }
            if(rfc822 || strict || parseHdr)
            {
                if(!ignoreErrors)
                    checkAddress(addr, route_addr, false);
                InternetAddress ma = new InternetAddress();
                ma.setAddress(addr);
                if(pers != null)
                    ma.encodedPersonal = pers;
                v.add(ma);
            } else
            {
                InternetAddress ma;
                for(StringTokenizer st = new StringTokenizer(addr); st.hasMoreTokens(); v.add(ma))
                {
                    String a = st.nextToken();
                    checkAddress(a, false, false);
                    ma = new InternetAddress();
                    ma.setAddress(a);
                }

            }
        }
        InternetAddress a[] = new InternetAddress[v.size()];
        v.toArray(a);
        return a;
    }

    public void validate()
        throws AddressException
    {
        if(isGroup())
            getGroup(true);
        else
            checkAddress(getAddress(), true, true);
    }

    private static void checkAddress(String addr, boolean routeAddr, boolean validate)
        throws AddressException
    {
        int start = 0;
        int len = addr.length();
        if(len == 0)
            throw new AddressException("Empty address", addr);
        int i;
        if(routeAddr && addr.charAt(0) == '@')
        {
            start = 0;
            do
            {
                if((i = indexOfAny(addr, ",:", start)) < 0)
                    break;
                if(addr.charAt(start) != '@')
                    throw new AddressException("Illegal route-addr", addr);
                if(addr.charAt(i) == ':')
                {
                    start = i + 1;
                    break;
                }
                start = i + 1;
            } while(true);
        }
        char c = '\uFFFF';
        char lastc = '\uFFFF';
        boolean inquote = false;
        for(i = start; i < len; i++)
        {
            lastc = c;
            c = addr.charAt(i);
            if(c == '\\' || lastc == '\\')
                continue;
            if(c == '"')
            {
                if(inquote)
                {
                    if(validate && i + 1 < len && addr.charAt(i + 1) != '@')
                        throw new AddressException("Quote not at end of local address", addr);
                    inquote = false;
                    continue;
                }
                if(validate && i != 0)
                    throw new AddressException("Quote not at start of local address", addr);
                inquote = true;
                continue;
            }
            if(inquote)
                continue;
            if(c == '@')
            {
                if(i == 0)
                    throw new AddressException("Missing local name", addr);
                break;
            }
            if(c <= ' ' || c >= '\177')
                throw new AddressException("Local address contains control or whitespace", addr);
            if("()<>,;:\\\"[]@".indexOf(c) >= 0)
                throw new AddressException("Local address contains illegal character", addr);
        }

        if(inquote)
            throw new AddressException("Unterminated quote", addr);
        if(c != '@')
            if(validate)
                throw new AddressException("Missing final '@domain'", addr);
            else
                return;
        start = i + 1;
        if(start >= len)
            throw new AddressException("Missing domain", addr);
        if(addr.charAt(start) == '.')
            throw new AddressException("Domain starts with dot", addr);
        for(i = start; i < len; i++)
        {
            c = addr.charAt(i);
            if(c == '[')
                return;
            if(c <= ' ' || c >= '\177')
                throw new AddressException("Domain contains control or whitespace", addr);
            if(!Character.isLetterOrDigit(c) && c != '-' && c != '.')
                throw new AddressException("Domain contains illegal character", addr);
            if(c == '.' && lastc == '.')
                throw new AddressException("Domain contains dot-dot", addr);
            lastc = c;
        }

        if(lastc == '.')
            throw new AddressException("Domain ends with dot", addr);
        else
            return;
    }

    private boolean isSimple()
    {
        return address == null || indexOfAny(address, "()<>,;:\\\"[]") < 0;
    }

    public boolean isGroup()
    {
        return address != null && address.endsWith(";") && address.indexOf(':') > 0;
    }

    public InternetAddress[] getGroup(boolean strict)
        throws AddressException
    {
        String addr = getAddress();
        if(!addr.endsWith(";"))
            return null;
        int ix = addr.indexOf(':');
        if(ix < 0)
        {
            return null;
        } else
        {
            String list = addr.substring(ix + 1, addr.length() - 1);
            return parseHeader(list, strict);
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

    protected String address;
    protected String personal;
    protected String encodedPersonal;
    private static final long serialVersionUID = 0x97cfa9a447d75349L;
    private static final boolean ignoreBogusGroupName = PropUtil.getBooleanSystemProperty("mail.mime.address.ignorebogusgroupname", true);
    private static final String rfc822phrase = "()<>@,;:\\\"\t .[]".replace(' ', '\0').replace('\t', '\0');
    private static final String specialsNoDotNoAt = "()<>,;:\\\"[]";
    private static final String specialsNoDot = "()<>,;:\\\"[]@";

}
