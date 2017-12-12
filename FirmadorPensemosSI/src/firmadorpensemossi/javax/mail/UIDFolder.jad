// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UIDFolder.java

package javax.mail;


// Referenced classes of package javax.mail:
//            MessagingException, Message, FetchProfile

public interface UIDFolder
{
    public static class FetchProfileItem extends FetchProfile.Item
    {

        public static final FetchProfileItem UID = new FetchProfileItem("UID");


        protected FetchProfileItem(String name)
        {
            super(name);
        }
    }


    public abstract long getUIDValidity()
        throws MessagingException;

    public abstract Message getMessageByUID(long l)
        throws MessagingException;

    public abstract Message[] getMessagesByUID(long l, long l1)
        throws MessagingException;

    public abstract Message[] getMessagesByUID(long al[])
        throws MessagingException;

    public abstract long getUID(Message message)
        throws MessagingException;

    public static final long LASTUID = -1L;
}
