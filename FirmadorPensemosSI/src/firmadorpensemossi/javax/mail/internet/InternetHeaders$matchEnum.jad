// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InternetHeaders.java

package javax.mail.internet;

import java.util.*;
import javax.mail.Header;

// Referenced classes of package javax.mail.internet:
//            InternetHeaders

static class InternetHeaders$matchEnum
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
        ader h = next_header;
        next_header = null;
        if(want_line)
            return h.line;
        else
            return new Header(h.getName(), h.getValue());
    }

    private ader nextMatch()
    {
_L2:
        ader h;
        int i;
        do
        {
            if(!e.hasNext())
                break MISSING_BLOCK_LABEL_109;
            h = (ader)e.next();
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
    private ader next_header;

    InternetHeaders$matchEnum(List v, String n[], boolean m, boolean l)
    {
        e = v.iterator();
        names = n;
        match = m;
        want_line = l;
        next_header = null;
    }
}
