// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewsAddress.java

package javax.mail.internet;

import java.util.*;
import javax.mail.Address;

// Referenced classes of package javax.mail.internet:
//            AddressException

public class NewsAddress extends Address
{

    public NewsAddress()
    {
    }

    public NewsAddress(String newsgroup)
    {
        this(newsgroup, null);
    }

    public NewsAddress(String newsgroup, String host)
    {
        this.newsgroup = newsgroup;
        this.host = host;
    }

    public String getType()
    {
        return "news";
    }

    public void setNewsgroup(String newsgroup)
    {
        this.newsgroup = newsgroup;
    }

    public String getNewsgroup()
    {
        return newsgroup;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getHost()
    {
        return host;
    }

    public String toString()
    {
        return newsgroup;
    }

    public boolean equals(Object a)
    {
        if(!(a instanceof NewsAddress))
        {
            return false;
        } else
        {
            NewsAddress s = (NewsAddress)a;
            return newsgroup.equals(s.newsgroup) && (host == null && s.host == null || host != null && s.host != null && host.equalsIgnoreCase(s.host));
        }
    }

    public int hashCode()
    {
        int hash = 0;
        if(newsgroup != null)
            hash += newsgroup.hashCode();
        if(host != null)
            hash += host.toLowerCase(Locale.ENGLISH).hashCode();
        return hash;
    }

    public static String toString(Address addresses[])
    {
        if(addresses == null || addresses.length == 0)
            return null;
        StringBuffer s = new StringBuffer(((NewsAddress)addresses[0]).toString());
        for(int i = 1; i < addresses.length; i++)
            s.append(",").append(((NewsAddress)addresses[i]).toString());

        return s.toString();
    }

    public static NewsAddress[] parse(String newsgroups)
        throws AddressException
    {
        StringTokenizer st = new StringTokenizer(newsgroups, ",");
        Vector nglist = new Vector();
        String ng;
        for(; st.hasMoreTokens(); nglist.addElement(new NewsAddress(ng)))
            ng = st.nextToken();

        int size = nglist.size();
        NewsAddress na[] = new NewsAddress[size];
        if(size > 0)
            nglist.copyInto(na);
        return na;
    }

    protected String newsgroup;
    protected String host;
    private static final long serialVersionUID = 0xc5a91ca0e4341391L;
}
