// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TransportEvent.java

package javax.mail.event;

import javax.mail.*;

// Referenced classes of package javax.mail.event:
//            MailEvent, TransportListener

public class TransportEvent extends MailEvent
{

    public TransportEvent(Transport transport, int type, Address validSent[], Address validUnsent[], Address invalid[], Message msg)
    {
        super(transport);
        this.type = type;
        this.validSent = validSent;
        this.validUnsent = validUnsent;
        this.invalid = invalid;
        this.msg = msg;
    }

    public int getType()
    {
        return type;
    }

    public Address[] getValidSentAddresses()
    {
        return validSent;
    }

    public Address[] getValidUnsentAddresses()
    {
        return validUnsent;
    }

    public Address[] getInvalidAddresses()
    {
        return invalid;
    }

    public Message getMessage()
    {
        return msg;
    }

    public void dispatch(Object listener)
    {
        if(type == 1)
            ((TransportListener)listener).messageDelivered(this);
        else
        if(type == 2)
            ((TransportListener)listener).messageNotDelivered(this);
        else
            ((TransportListener)listener).messagePartiallyDelivered(this);
    }

    public static final int MESSAGE_DELIVERED = 1;
    public static final int MESSAGE_NOT_DELIVERED = 2;
    public static final int MESSAGE_PARTIALLY_DELIVERED = 3;
    protected int type;
    protected transient Address validSent[];
    protected transient Address validUnsent[];
    protected transient Address invalid[];
    protected transient Message msg;
    private static final long serialVersionUID = 0xbe5c30558af3da4fL;
}
