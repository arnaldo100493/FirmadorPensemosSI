// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StoreEvent.java

package javax.mail.event;

import javax.mail.Store;

// Referenced classes of package javax.mail.event:
//            MailEvent, StoreListener

public class StoreEvent extends MailEvent
{

    public StoreEvent(Store store, int type, String message)
    {
        super(store);
        this.type = type;
        this.message = message;
    }

    public int getMessageType()
    {
        return type;
    }

    public String getMessage()
    {
        return message;
    }

    public void dispatch(Object listener)
    {
        ((StoreListener)listener).notification(this);
    }

    public static final int ALERT = 1;
    public static final int NOTICE = 2;
    protected int type;
    protected String message;
    private static final long serialVersionUID = 0x1ae7a9da6074bb02L;
}
