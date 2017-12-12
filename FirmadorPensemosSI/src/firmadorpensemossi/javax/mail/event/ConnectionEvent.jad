// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConnectionEvent.java

package javax.mail.event;


// Referenced classes of package javax.mail.event:
//            MailEvent, ConnectionListener

public class ConnectionEvent extends MailEvent
{

    public ConnectionEvent(Object source, int type)
    {
        super(source);
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    public void dispatch(Object listener)
    {
        if(type == 1)
            ((ConnectionListener)listener).opened(this);
        else
        if(type == 2)
            ((ConnectionListener)listener).disconnected(this);
        else
        if(type == 3)
            ((ConnectionListener)listener).closed(this);
    }

    public static final int OPENED = 1;
    public static final int DISCONNECTED = 2;
    public static final int CLOSED = 3;
    protected int type;
    private static final long serialVersionUID = 0xe640029d6ec9f983L;
}
