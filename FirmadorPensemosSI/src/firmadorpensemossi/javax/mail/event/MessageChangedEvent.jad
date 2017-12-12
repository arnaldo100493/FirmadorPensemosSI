// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageChangedEvent.java

package javax.mail.event;

import javax.mail.Message;

// Referenced classes of package javax.mail.event:
//            MailEvent, MessageChangedListener

public class MessageChangedEvent extends MailEvent
{

    public MessageChangedEvent(Object source, int type, Message msg)
    {
        super(source);
        this.msg = msg;
        this.type = type;
    }

    public int getMessageChangeType()
    {
        return type;
    }

    public Message getMessage()
    {
        return msg;
    }

    public void dispatch(Object listener)
    {
        ((MessageChangedListener)listener).messageChanged(this);
    }

    public static final int FLAGS_CHANGED = 1;
    public static final int ENVELOPE_CHANGED = 2;
    protected int type;
    protected transient Message msg;
    private static final long serialVersionUID = 0xbaf55870be7af17cL;
}
