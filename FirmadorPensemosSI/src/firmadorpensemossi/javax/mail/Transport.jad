// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Transport.java

package javax.mail;

import java.util.*;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

// Referenced classes of package javax.mail:
//            Service, SendFailedException, Address, MessagingException, 
//            Message, Session, URLName

public abstract class Transport extends Service
{

    public Transport(Session session, URLName urlname)
    {
        super(session, urlname);
        transportListeners = null;
    }

    public static void send(Message msg)
        throws MessagingException
    {
        msg.saveChanges();
        send0(msg, msg.getAllRecipients(), null, null);
    }

    public static void send(Message msg, Address addresses[])
        throws MessagingException
    {
        msg.saveChanges();
        send0(msg, addresses, null, null);
    }

    public static void send(Message msg, String user, String password)
        throws MessagingException
    {
        msg.saveChanges();
        send0(msg, msg.getAllRecipients(), user, password);
    }

    public static void send(Message msg, Address addresses[], String user, String password)
        throws MessagingException
    {
        msg.saveChanges();
        send0(msg, addresses, user, password);
    }

    private static void send0(Message msg, Address addresses[], String user, String password)
        throws MessagingException
    {
        Hashtable protocols;
        Vector invalid;
        Vector validSent;
        Vector validUnsent;
        Session s;
        Transport transport;
        if(addresses == null || addresses.length == 0)
            throw new SendFailedException("No recipient addresses");
        protocols = new Hashtable();
        invalid = new Vector();
        validSent = new Vector();
        validUnsent = new Vector();
        for(int i = 0; i < addresses.length; i++)
            if(protocols.containsKey(addresses[i].getType()))
            {
                Vector v = (Vector)protocols.get(addresses[i].getType());
                v.addElement(addresses[i]);
            } else
            {
                Vector w = new Vector();
                w.addElement(addresses[i]);
                protocols.put(addresses[i].getType(), w);
            }

        int dsize = protocols.size();
        if(dsize == 0)
            throw new SendFailedException("No recipient addresses");
        s = msg.session == null ? Session.getDefaultInstance(System.getProperties(), null) : msg.session;
        if(dsize != 1)
            break MISSING_BLOCK_LABEL_254;
        transport = s.getTransport(addresses[0]);
        if(user != null)
            transport.connect(user, password);
        else
            transport.connect();
        transport.sendMessage(msg, addresses);
        transport.close();
        break MISSING_BLOCK_LABEL_253;
        Exception exception;
        exception;
        transport.close();
        throw exception;
        return;
        MessagingException chainedEx;
        boolean sendFailed;
        Enumeration e;
        chainedEx = null;
        sendFailed = false;
        e = protocols.elements();
_L1:
        Address protaddresses[];
        do
        {
label0:
            {
                if(!e.hasMoreElements())
                    break MISSING_BLOCK_LABEL_568;
                Vector v = (Vector)e.nextElement();
                protaddresses = new Address[v.size()];
                v.copyInto(protaddresses);
                if((transport = s.getTransport(protaddresses[0])) != null)
                    break label0;
                int j = 0;
                while(j < protaddresses.length) 
                {
                    invalid.addElement(protaddresses[j]);
                    j++;
                }
            }
        } while(true);
        transport.connect();
        transport.sendMessage(msg, protaddresses);
        transport.close();
          goto _L1
        SendFailedException sex;
        sex;
        sendFailed = true;
        if(chainedEx == null)
            chainedEx = sex;
        else
            chainedEx.setNextException(sex);
        Address a[] = sex.getInvalidAddresses();
        if(a != null)
        {
            for(int j = 0; j < a.length; j++)
                invalid.addElement(a[j]);

        }
        a = sex.getValidSentAddresses();
        if(a != null)
        {
            for(int k = 0; k < a.length; k++)
                validSent.addElement(a[k]);

        }
        Address c[] = sex.getValidUnsentAddresses();
        if(c != null)
        {
            for(int l = 0; l < c.length; l++)
                validUnsent.addElement(c[l]);

        }
        transport.close();
          goto _L1
        MessagingException mex;
        mex;
        sendFailed = true;
        if(chainedEx == null)
            chainedEx = mex;
        else
            chainedEx.setNextException(mex);
        transport.close();
          goto _L1
        Exception exception1;
        exception1;
        transport.close();
        throw exception1;
        if(sendFailed || invalid.size() != 0 || validUnsent.size() != 0)
        {
            Address a[] = null;
            Address b[] = null;
            Address c[] = null;
            if(validSent.size() > 0)
            {
                a = new Address[validSent.size()];
                validSent.copyInto(a);
            }
            if(validUnsent.size() > 0)
            {
                b = new Address[validUnsent.size()];
                validUnsent.copyInto(b);
            }
            if(invalid.size() > 0)
            {
                c = new Address[invalid.size()];
                invalid.copyInto(c);
            }
            throw new SendFailedException("Sending failed", chainedEx, a, b, c);
        } else
        {
            return;
        }
    }

    public abstract void sendMessage(Message message, Address aaddress[])
        throws MessagingException;

    public synchronized void addTransportListener(TransportListener l)
    {
        if(transportListeners == null)
            transportListeners = new Vector();
        transportListeners.addElement(l);
    }

    public synchronized void removeTransportListener(TransportListener l)
    {
        if(transportListeners != null)
            transportListeners.removeElement(l);
    }

    protected void notifyTransportListeners(int type, Address validSent[], Address validUnsent[], Address invalid[], Message msg)
    {
        if(transportListeners == null)
        {
            return;
        } else
        {
            TransportEvent e = new TransportEvent(this, type, validSent, validUnsent, invalid, msg);
            queueEvent(e, transportListeners);
            return;
        }
    }

    private volatile Vector transportListeners;
}
