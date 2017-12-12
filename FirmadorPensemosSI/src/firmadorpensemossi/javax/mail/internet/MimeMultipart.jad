// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MimeMultipart.java

package javax.mail.internet;

import com.sun.mail.util.*;
import java.io.*;
import java.util.Vector;
import javax.activation.DataSource;
import javax.mail.*;

// Referenced classes of package javax.mail.internet:
//            ContentType, MimeBodyPart, SharedInputStream, InternetHeaders, 
//            UniqueValue

public class MimeMultipart extends Multipart
{

    public MimeMultipart()
    {
        this("mixed");
    }

    public MimeMultipart(String subtype)
    {
        ds = null;
        parsed = true;
        complete = true;
        preamble = null;
        ignoreMissingEndBoundary = true;
        ignoreMissingBoundaryParameter = true;
        ignoreExistingBoundaryParameter = false;
        allowEmpty = false;
        String boundary = UniqueValue.getUniqueBoundaryValue();
        ContentType cType = new ContentType("multipart", subtype, null);
        cType.setParameter("boundary", boundary);
        contentType = cType.toString();
        initializeProperties();
    }

    public transient MimeMultipart(BodyPart parts[])
        throws MessagingException
    {
        this();
        BodyPart arr$[] = parts;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            BodyPart bp = arr$[i$];
            super.addBodyPart(bp);
        }

    }

    public transient MimeMultipart(String subtype, BodyPart parts[])
        throws MessagingException
    {
        this(subtype);
        BodyPart arr$[] = parts;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            BodyPart bp = arr$[i$];
            super.addBodyPart(bp);
        }

    }

    public MimeMultipart(DataSource ds)
        throws MessagingException
    {
        this.ds = null;
        parsed = true;
        complete = true;
        preamble = null;
        ignoreMissingEndBoundary = true;
        ignoreMissingBoundaryParameter = true;
        ignoreExistingBoundaryParameter = false;
        allowEmpty = false;
        if(ds instanceof MessageAware)
        {
            MessageContext mc = ((MessageAware)ds).getMessageContext();
            setParent(mc.getPart());
        }
        if(ds instanceof MultipartDataSource)
        {
            setMultipartDataSource((MultipartDataSource)ds);
            return;
        } else
        {
            parsed = false;
            this.ds = ds;
            contentType = ds.getContentType();
            return;
        }
    }

    protected void initializeProperties()
    {
        ignoreMissingEndBoundary = PropUtil.getBooleanSystemProperty("mail.mime.multipart.ignoremissingendboundary", true);
        ignoreMissingBoundaryParameter = PropUtil.getBooleanSystemProperty("mail.mime.multipart.ignoremissingboundaryparameter", true);
        ignoreExistingBoundaryParameter = PropUtil.getBooleanSystemProperty("mail.mime.multipart.ignoreexistingboundaryparameter", false);
        allowEmpty = PropUtil.getBooleanSystemProperty("mail.mime.multipart.allowempty", false);
    }

    public synchronized void setSubType(String subtype)
        throws MessagingException
    {
        ContentType cType = new ContentType(contentType);
        cType.setSubType(subtype);
        contentType = cType.toString();
    }

    public synchronized int getCount()
        throws MessagingException
    {
        parse();
        return super.getCount();
    }

    public synchronized BodyPart getBodyPart(int index)
        throws MessagingException
    {
        parse();
        return super.getBodyPart(index);
    }

    public synchronized BodyPart getBodyPart(String CID)
        throws MessagingException
    {
        parse();
        int count = getCount();
        for(int i = 0; i < count; i++)
        {
            MimeBodyPart part = (MimeBodyPart)getBodyPart(i);
            String s = part.getContentID();
            if(s != null && s.equals(CID))
                return part;
        }

        return null;
    }

    public boolean removeBodyPart(BodyPart part)
        throws MessagingException
    {
        parse();
        return super.removeBodyPart(part);
    }

    public void removeBodyPart(int index)
        throws MessagingException
    {
        parse();
        super.removeBodyPart(index);
    }

    public synchronized void addBodyPart(BodyPart part)
        throws MessagingException
    {
        parse();
        super.addBodyPart(part);
    }

    public synchronized void addBodyPart(BodyPart part, int index)
        throws MessagingException
    {
        parse();
        super.addBodyPart(part, index);
    }

    public synchronized boolean isComplete()
        throws MessagingException
    {
        parse();
        return complete;
    }

    public synchronized String getPreamble()
        throws MessagingException
    {
        parse();
        return preamble;
    }

    public synchronized void setPreamble(String preamble)
        throws MessagingException
    {
        this.preamble = preamble;
    }

    protected synchronized void updateHeaders()
        throws MessagingException
    {
        parse();
        for(int i = 0; i < parts.size(); i++)
            ((MimeBodyPart)parts.elementAt(i)).updateHeaders();

    }

    public synchronized void writeTo(OutputStream os)
        throws IOException, MessagingException
    {
        parse();
        String boundary = (new StringBuilder()).append("--").append((new ContentType(contentType)).getParameter("boundary")).toString();
        LineOutputStream los = new LineOutputStream(os);
        if(preamble != null)
        {
            byte pb[] = ASCIIUtility.getBytes(preamble);
            los.write(pb);
            if(pb.length > 0 && pb[pb.length - 1] != 13 && pb[pb.length - 1] != 10)
                los.writeln();
        }
        if(parts.size() == 0)
        {
            if(allowEmpty)
            {
                los.writeln(boundary);
                los.writeln();
            } else
            {
                throw new MessagingException((new StringBuilder()).append("Empty multipart: ").append(contentType).toString());
            }
        } else
        {
            for(int i = 0; i < parts.size(); i++)
            {
                los.writeln(boundary);
                ((MimeBodyPart)parts.elementAt(i)).writeTo(os);
                los.writeln();
            }

        }
        los.writeln((new StringBuilder()).append(boundary).append("--").toString());
    }

    protected synchronized void parse()
        throws MessagingException
    {
        InputStream in;
        SharedInputStream sin;
        long start;
        long end;
        String boundary;
        if(parsed)
            return;
        initializeProperties();
        in = null;
        sin = null;
        start = 0L;
        end = 0L;
        try
        {
            in = ds.getInputStream();
            if(!(in instanceof ByteArrayInputStream) && !(in instanceof BufferedInputStream) && !(in instanceof SharedInputStream))
                in = new BufferedInputStream(in);
        }
        catch(Exception ex)
        {
            throw new MessagingException("No inputstream from datasource", ex);
        }
        if(in instanceof SharedInputStream)
            sin = (SharedInputStream)in;
        ContentType cType = new ContentType(contentType);
        boundary = null;
        if(!ignoreExistingBoundaryParameter)
        {
            String bp = cType.getParameter("boundary");
            if(bp != null)
                boundary = (new StringBuilder()).append("--").append(bp).toString();
        }
        if(boundary == null && !ignoreMissingBoundaryParameter && !ignoreExistingBoundaryParameter)
            throw new MessagingException("Missing boundary parameter");
        LineInputStream lin;
        lin = new LineInputStream(in);
        StringBuffer preamblesb = null;
        String lineSeparator = null;
        String line;
        do
        {
            if((line = lin.readLine()) == null)
                break;
            int i = line.length() - 1;
            do
            {
                if(i < 0)
                    break;
                char c = line.charAt(i);
                if(c != ' ' && c != '\t')
                    break;
                i--;
            } while(true);
            line = line.substring(0, i + 1);
            if(boundary != null)
            {
                if(line.equals(boundary))
                    break;
                if(line.length() == boundary.length() + 2 && line.startsWith(boundary) && line.endsWith("--"))
                {
                    line = null;
                    break;
                }
            } else
            if(line.length() > 2 && line.startsWith("--") && (line.length() <= 4 || !allDashes(line)))
            {
                boundary = line;
                break;
            }
            if(line.length() > 0)
            {
                if(lineSeparator == null)
                    try
                    {
                        lineSeparator = System.getProperty("line.separator", "\n");
                    }
                    catch(SecurityException ex)
                    {
                        lineSeparator = "\n";
                    }
                if(preamblesb == null)
                    preamblesb = new StringBuffer(line.length() + 2);
                preamblesb.append(line).append(lineSeparator);
            }
        } while(true);
        if(preamblesb != null)
            preamble = preamblesb.toString();
        if(line != null)
            break MISSING_BLOCK_LABEL_482;
        if(allowEmpty)
        {
            try
            {
                in.close();
            }
            catch(IOException cex) { }
            return;
        }
        throw new MessagingException("Missing start boundary");
        byte bndbytes[] = ASCIIUtility.getBytes(boundary);
        int bl = bndbytes.length;
        int bcs[] = new int[256];
        for(int i = 0; i < bl; i++)
            bcs[bndbytes[i] & 0xff] = i + 1;

        int gss[] = new int[bl];
label0:
        for(int i = bl; i > 0; i--)
        {
            int j;
            for(j = bl - 1; j >= i; j--)
            {
                if(bndbytes[j] != bndbytes[j - i])
                    continue label0;
                gss[j - 1] = i;
            }

            while(j > 0) 
                gss[--j] = i;
        }

        gss[bl - 1] = 1;
        MimeBodyPart part;
        for(boolean done = false; !done; super.addBodyPart(part))
        {
            InternetHeaders headers = null;
            if(sin != null)
            {
                start = sin.getPosition();
                String line;
                while((line = lin.readLine()) != null && line.length() > 0) ;
                if(line == null)
                {
                    if(!ignoreMissingEndBoundary)
                        throw new MessagingException("missing multipart end boundary");
                    complete = false;
                    break;
                }
            } else
            {
                headers = createInternetHeaders(in);
            }
            if(!in.markSupported())
                throw new MessagingException("Stream doesn't support mark");
            ByteArrayOutputStream buf = null;
            if(sin == null)
                buf = new ByteArrayOutputStream();
            else
                end = sin.getPosition();
            byte inbuf[] = new byte[bl];
            byte previnbuf[] = new byte[bl];
            int inSize = 0;
            int prevSize = 0;
            boolean first = true;
            int eolLen;
            do
            {
                in.mark(bl + 4 + 1000);
                eolLen = 0;
                inSize = readFully(in, inbuf, 0, bl);
                if(inSize < bl)
                {
                    if(!ignoreMissingEndBoundary)
                        throw new MessagingException("missing multipart end boundary");
                    if(sin != null)
                        end = sin.getPosition();
                    complete = false;
                    done = true;
                    break;
                }
                int i;
                for(i = bl - 1; i >= 0 && inbuf[i] == bndbytes[i]; i--);
                if(i < 0)
                {
                    eolLen = 0;
                    if(!first)
                    {
                        int b = previnbuf[prevSize - 1];
                        if(b == 13 || b == 10)
                        {
                            eolLen = 1;
                            if(b == 10 && prevSize >= 2)
                            {
                                b = previnbuf[prevSize - 2];
                                if(b == 13)
                                    eolLen = 2;
                            }
                        }
                    }
                    if(first || eolLen > 0)
                    {
                        if(sin != null)
                            end = sin.getPosition() - (long)bl - (long)eolLen;
                        int b2 = in.read();
                        if(b2 == 45 && in.read() == 45)
                        {
                            complete = true;
                            done = true;
                            break;
                        }
                        for(; b2 == 32 || b2 == 9; b2 = in.read());
                        if(b2 == 10)
                            break;
                        if(b2 == 13)
                        {
                            in.mark(1);
                            if(in.read() != 10)
                                in.reset();
                            break;
                        }
                    }
                    i = 0;
                }
                int skip = Math.max((i + 1) - bcs[inbuf[i] & 0x7f], gss[i]);
                if(skip < 2)
                {
                    if(sin == null && prevSize > 1)
                        buf.write(previnbuf, 0, prevSize - 1);
                    in.reset();
                    skipFully(in, 1L);
                    if(prevSize >= 1)
                    {
                        previnbuf[0] = previnbuf[prevSize - 1];
                        previnbuf[1] = inbuf[0];
                        prevSize = 2;
                    } else
                    {
                        previnbuf[0] = inbuf[0];
                        prevSize = 1;
                    }
                } else
                {
                    if(prevSize > 0 && sin == null)
                        buf.write(previnbuf, 0, prevSize);
                    prevSize = skip;
                    in.reset();
                    skipFully(in, prevSize);
                    byte tmp[] = inbuf;
                    inbuf = previnbuf;
                    previnbuf = tmp;
                }
                first = false;
            } while(true);
            if(sin != null)
            {
                part = createMimeBodyPartIs(sin.newStream(start, end));
                continue;
            }
            if(prevSize - eolLen > 0)
                buf.write(previnbuf, 0, prevSize - eolLen);
            if(!complete && inSize > 0)
                buf.write(inbuf, 0, inSize);
            part = createMimeBodyPart(headers, buf.toByteArray());
        }

        try
        {
            in.close();
        }
        catch(IOException cex) { }
        break MISSING_BLOCK_LABEL_1363;
        IOException ioex;
        ioex;
        throw new MessagingException("IO Error", ioex);
        Exception exception;
        exception;
        try
        {
            in.close();
        }
        catch(IOException cex) { }
        throw exception;
        parsed = true;
        return;
    }

    private static boolean allDashes(String s)
    {
        for(int i = 0; i < s.length(); i++)
            if(s.charAt(i) != '-')
                return false;

        return true;
    }

    private static int readFully(InputStream in, byte buf[], int off, int len)
        throws IOException
    {
        if(len == 0)
            return 0;
        int total = 0;
        do
        {
            if(len <= 0)
                break;
            int bsize = in.read(buf, off, len);
            if(bsize <= 0)
                break;
            off += bsize;
            total += bsize;
            len -= bsize;
        } while(true);
        return total <= 0 ? -1 : total;
    }

    private void skipFully(InputStream in, long offset)
        throws IOException
    {
        long cur;
        for(; offset > 0L; offset -= cur)
        {
            cur = in.skip(offset);
            if(cur <= 0L)
                throw new EOFException("can't skip");
        }

    }

    protected InternetHeaders createInternetHeaders(InputStream is)
        throws MessagingException
    {
        return new InternetHeaders(is);
    }

    protected MimeBodyPart createMimeBodyPart(InternetHeaders headers, byte content[])
        throws MessagingException
    {
        return new MimeBodyPart(headers, content);
    }

    protected MimeBodyPart createMimeBodyPart(InputStream is)
        throws MessagingException
    {
        return new MimeBodyPart(is);
    }

    private MimeBodyPart createMimeBodyPartIs(InputStream is)
        throws MessagingException
    {
        MimeBodyPart mimebodypart = createMimeBodyPart(is);
        try
        {
            is.close();
        }
        catch(IOException ex) { }
        return mimebodypart;
        Exception exception;
        exception;
        try
        {
            is.close();
        }
        catch(IOException ex) { }
        throw exception;
    }

    protected DataSource ds;
    protected boolean parsed;
    protected boolean complete;
    protected String preamble;
    protected boolean ignoreMissingEndBoundary;
    protected boolean ignoreMissingBoundaryParameter;
    protected boolean ignoreExistingBoundaryParameter;
    protected boolean allowEmpty;
}
