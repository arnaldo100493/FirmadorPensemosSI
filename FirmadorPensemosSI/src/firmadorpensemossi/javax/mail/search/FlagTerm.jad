// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlagTerm.java

package javax.mail.search;

import javax.mail.Flags;
import javax.mail.Message;

// Referenced classes of package javax.mail.search:
//            SearchTerm

public final class FlagTerm extends SearchTerm
{

    public FlagTerm(Flags flags, boolean set)
    {
        this.flags = flags;
        this.set = set;
    }

    public Flags getFlags()
    {
        return (Flags)flags.clone();
    }

    public boolean getTestSet()
    {
        return set;
    }

    public boolean match(Message msg)
    {
        Flags f;
        f = msg.getFlags();
        if(!set)
            break MISSING_BLOCK_LABEL_27;
        if(f.contains(flags))
            return true;
        javax.mail.Flags.Flag sf[];
        int i;
        String s[];
        int i;
        try
        {
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
        sf = flags.getSystemFlags();
        i = 0;
_L1:
        if(i >= sf.length)
            break MISSING_BLOCK_LABEL_64;
        if(f.contains(sf[i]))
            return false;
        i++;
          goto _L1
        s = flags.getUserFlags();
        i = 0;
_L2:
        if(i >= s.length)
            break MISSING_BLOCK_LABEL_104;
        if(f.contains(s[i]))
            return false;
        i++;
          goto _L2
        return true;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof FlagTerm))
        {
            return false;
        } else
        {
            FlagTerm ft = (FlagTerm)obj;
            return ft.set == set && ft.flags.equals(flags);
        }
    }

    public int hashCode()
    {
        return set ? flags.hashCode() : ~flags.hashCode();
    }

    private boolean set;
    private Flags flags;
    private static final long serialVersionUID = 0xfe03fdfcf298e8c9L;
}
