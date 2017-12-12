// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageCountEvent.java

package javax.mail.event;

import javax.mail.Folder;
import javax.mail.Message;

// Referenced classes of package javax.mail.event:
//            MailEvent, MessageCountListener

public class MessageCountEvent extends MailEvent
{

    public MessageCountEvent(Folder folder, int type, boolean removed, Message msgs[])
    {
        super(folder);
        this.type = type;
        this.removed = removed;
        this.msgs = msgs;
    }

    public int getType()
    {
        return type;
    }

    public boolean isRemoved()
    {
        return removed;
    }

    public Message[] getMessages()
    {
        return msgs;
    }

    public void dispatch(Object listener)
    {
        if(type == 1)
            ((MessageCountListener)listener).messagesAdded(this);
        else
            ((MessageCountListener)listener).messagesRemoved(this);
    }

    public static final int ADDED = 1;
    public static final int REMOVED = 2;
    protected int type;
    protected boolean removed;
    protected transient Message msgs[];
    private static final long serialVersionUID = 0x98a6dca313f58b67L;
}
