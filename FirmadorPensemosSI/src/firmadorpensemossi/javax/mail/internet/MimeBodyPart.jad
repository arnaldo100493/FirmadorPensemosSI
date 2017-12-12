// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MimeBodyPart.java

package javax.mail.internet;

import com.sun.mail.util.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.activation.*;
import javax.mail.*;

// Referenced classes of package javax.mail.internet:
//            InternetHeaders, SharedInputStream, MimePartDataSource, MimeMultipart, 
//            ContentType, ParseException, ContentDisposition, HeaderTokenizer, 
//            MimeMessage, MimePart, MimeUtility

public class MimeBodyPart extends BodyPart
    implements MimePart
{
    static class MimePartDataHandler extends DataHandler
    {

        public MimePartDataHandler(DataSource ds)
        {
            super(ds);
        }
    }

    private static class EncodedFileDataSource extends FileDataSource
        implements EncodingAware
    {

        public String getContentType()
        {
            return contentType == null ? super.getContentType() : contentType;
        }

        public String getEncoding()
        {
            return encoding;
        }

        private String contentType;
        private String encoding;

        public EncodedFileDataSource(File file, String contentType, String encoding)
        {
            super(file);
            this.contentType = contentType;
            this.encoding = encoding;
        }
    }


    public MimeBodyPart()
    {
        headers = new InternetHeaders();
    }

    public MimeBodyPart(InputStream is)
        throws MessagingException
    {
        if(!(is instanceof ByteArrayInputStream) && !(is instanceof BufferedInputStream) && !(is instanceof SharedInputStream))
            is = new BufferedInputStream(is);
        headers = new InternetHeaders(is);
        if(is instanceof SharedInputStream)
        {
            SharedInputStream sis = (SharedInputStream)is;
            contentStream = sis.newStream(sis.getPosition(), -1L);
        } else
        {
            try
            {
                content = ASCIIUtility.getBytes(is);
            }
            catch(IOException ioex)
            {
                throw new MessagingException("Error reading input stream", ioex);
            }
        }
    }

    public MimeBodyPart(InternetHeaders headers, byte content[])
        throws MessagingException
    {
        this.headers = headers;
        this.content = content;
    }

    public int getSize()
        throws MessagingException
    {
        if(content != null)
            return content.length;
        if(contentStream != null)
            try
            {
                int size = contentStream.available();
                if(size > 0)
                    return size;
            }
            catch(IOException ex) { }
        return -1;
    }

    public int getLineCount()
        throws MessagingException
    {
        return -1;
    }

    public String getContentType()
        throws MessagingException
    {
        String s = getHeader("Content-Type", null);
        s = MimeUtil.cleanContentType(this, s);
        if(s == null)
            s = "text/plain";
        return s;
    }

    public boolean isMimeType(String mimeType)
        throws MessagingException
    {
        return isMimeType(((MimePart) (this)), mimeType);
    }

    public String getDisposition()
        throws MessagingException
    {
        return getDisposition(((MimePart) (this)));
    }

    public void setDisposition(String disposition)
        throws MessagingException
    {
        setDisposition(((MimePart) (this)), disposition);
    }

    public String getEncoding()
        throws MessagingException
    {
        return getEncoding(((MimePart) (this)));
    }

    public String getContentID()
        throws MessagingException
    {
        return getHeader("Content-Id", null);
    }

    public void setContentID(String cid)
        throws MessagingException
    {
        if(cid == null)
            removeHeader("Content-ID");
        else
            setHeader("Content-ID", cid);
    }

    public String getContentMD5()
        throws MessagingException
    {
        return getHeader("Content-MD5", null);
    }

    public void setContentMD5(String md5)
        throws MessagingException
    {
        setHeader("Content-MD5", md5);
    }

    public String[] getContentLanguage()
        throws MessagingException
    {
        return getContentLanguage(((MimePart) (this)));
    }

    public void setContentLanguage(String languages[])
        throws MessagingException
    {
        setContentLanguage(((MimePart) (this)), languages);
    }

    public String getDescription()
        throws MessagingException
    {
        return getDescription(((MimePart) (this)));
    }

    public void setDescription(String description)
        throws MessagingException
    {
        setDescription(description, null);
    }

    public void setDescription(String description, String charset)
        throws MessagingException
    {
        setDescription(((MimePart) (this)), description, charset);
    }

    public String getFileName()
        throws MessagingException
    {
        return getFileName(((MimePart) (this)));
    }

    public void setFileName(String filename)
        throws MessagingException
    {
        setFileName(((MimePart) (this)), filename);
    }

    public InputStream getInputStream()
        throws IOException, MessagingException
    {
        return getDataHandler().getInputStream();
    }

    protected InputStream getContentStream()
        throws MessagingException
    {
        if(contentStream != null)
            return ((SharedInputStream)contentStream).newStream(0L, -1L);
        if(content != null)
            return new ByteArrayInputStream(content);
        else
            throw new MessagingException("No MimeBodyPart content");
    }

    public InputStream getRawInputStream()
        throws MessagingException
    {
        return getContentStream();
    }

    public DataHandler getDataHandler()
        throws MessagingException
    {
        if(dh == null)
            dh = new MimePartDataHandler(new MimePartDataSource(this));
        return dh;
    }

    public Object getContent()
        throws IOException, MessagingException
    {
        if(cachedContent != null)
            return cachedContent;
        Object c;
        try
        {
            c = getDataHandler().getContent();
        }
        catch(FolderClosedIOException fex)
        {
            throw new FolderClosedException(fex.getFolder(), fex.getMessage());
        }
        catch(MessageRemovedIOException mex)
        {
            throw new MessageRemovedException(mex.getMessage());
        }
        if(cacheMultipart && ((c instanceof Multipart) || (c instanceof Message)) && (content != null || contentStream != null))
        {
            cachedContent = c;
            if(c instanceof MimeMultipart)
                ((MimeMultipart)c).parse();
        }
        return c;
    }

    public void setDataHandler(DataHandler dh)
        throws MessagingException
    {
        this.dh = dh;
        cachedContent = null;
        invalidateContentHeaders(this);
    }

    public void setContent(Object o, String type)
        throws MessagingException
    {
        if(o instanceof Multipart)
            setContent((Multipart)o);
        else
            setDataHandler(new DataHandler(o, type));
    }

    public void setText(String text)
        throws MessagingException
    {
        setText(text, null);
    }

    public void setText(String text, String charset)
        throws MessagingException
    {
        setText(((MimePart) (this)), text, charset, "plain");
    }

    public void setText(String text, String charset, String subtype)
        throws MessagingException
    {
        setText(((MimePart) (this)), text, charset, subtype);
    }

    public void setContent(Multipart mp)
        throws MessagingException
    {
        setDataHandler(new DataHandler(mp, mp.getContentType()));
        mp.setParent(this);
    }

    public void attachFile(File file)
        throws IOException, MessagingException
    {
        FileDataSource fds = new FileDataSource(file);
        setDataHandler(new DataHandler(fds));
        setFileName(fds.getName());
        setDisposition("attachment");
    }

    public void attachFile(String file)
        throws IOException, MessagingException
    {
        File f = new File(file);
        attachFile(f);
    }

    public void attachFile(File file, String contentType, String encoding)
        throws IOException, MessagingException
    {
        DataSource fds = new EncodedFileDataSource(file, contentType, encoding);
        setDataHandler(new DataHandler(fds));
        setFileName(fds.getName());
        setDisposition("attachment");
    }

    public void attachFile(String file, String contentType, String encoding)
        throws IOException, MessagingException
    {
        attachFile(new File(file), contentType, encoding);
    }

    public void saveFile(File file)
        throws IOException, MessagingException
    {
        OutputStream out;
        InputStream in;
        out = null;
        in = null;
        out = new BufferedOutputStream(new FileOutputStream(file));
        in = getInputStream();
        byte buf[] = new byte[8192];
        int len;
        while((len = in.read(buf)) > 0) 
            out.write(buf, 0, len);
        try
        {
            if(in != null)
                in.close();
        }
        catch(IOException ex) { }
        try
        {
            if(out != null)
                out.close();
        }
        catch(IOException ex) { }
        break MISSING_BLOCK_LABEL_116;
        Exception exception;
        exception;
        try
        {
            if(in != null)
                in.close();
        }
        catch(IOException ex) { }
        try
        {
            if(out != null)
                out.close();
        }
        catch(IOException ex) { }
        throw exception;
    }

    public void saveFile(String file)
        throws IOException, MessagingException
    {
        File f = new File(file);
        saveFile(f);
    }

    public void writeTo(OutputStream os)
        throws IOException, MessagingException
    {
        writeTo(((MimePart) (this)), os, null);
    }

    public String[] getHeader(String name)
        throws MessagingException
    {
        return headers.getHeader(name);
    }

    public String getHeader(String name, String delimiter)
        throws MessagingException
    {
        return headers.getHeader(name, delimiter);
    }

    public void setHeader(String name, String value)
        throws MessagingException
    {
        headers.setHeader(name, value);
    }

    public void addHeader(String name, String value)
        throws MessagingException
    {
        headers.addHeader(name, value);
    }

    public void removeHeader(String name)
        throws MessagingException
    {
        headers.removeHeader(name);
    }

    public Enumeration getAllHeaders()
        throws MessagingException
    {
        return headers.getAllHeaders();
    }

    public Enumeration getMatchingHeaders(String names[])
        throws MessagingException
    {
        return headers.getMatchingHeaders(names);
    }

    public Enumeration getNonMatchingHeaders(String names[])
        throws MessagingException
    {
        return headers.getNonMatchingHeaders(names);
    }

    public void addHeaderLine(String line)
        throws MessagingException
    {
        headers.addHeaderLine(line);
    }

    public Enumeration getAllHeaderLines()
        throws MessagingException
    {
        return headers.getAllHeaderLines();
    }

    public Enumeration getMatchingHeaderLines(String names[])
        throws MessagingException
    {
        return headers.getMatchingHeaderLines(names);
    }

    public Enumeration getNonMatchingHeaderLines(String names[])
        throws MessagingException
    {
        return headers.getNonMatchingHeaderLines(names);
    }

    protected void updateHeaders()
        throws MessagingException
    {
        updateHeaders(((MimePart) (this)));
        if(cachedContent != null)
        {
            dh = new DataHandler(cachedContent, getContentType());
            cachedContent = null;
            content = null;
            if(contentStream != null)
                try
                {
                    contentStream.close();
                }
                catch(IOException ioex) { }
            contentStream = null;
        }
    }

    static boolean isMimeType(MimePart part, String mimeType)
        throws MessagingException
    {
        try
        {
            ContentType ct = new ContentType(part.getContentType());
            return ct.match(mimeType);
        }
        catch(ParseException ex)
        {
            return part.getContentType().equalsIgnoreCase(mimeType);
        }
    }

    static void setText(MimePart part, String text, String charset, String subtype)
        throws MessagingException
    {
        if(charset == null)
            if(MimeUtility.checkAscii(text) != 1)
                charset = MimeUtility.getDefaultMIMECharset();
            else
                charset = "us-ascii";
        part.setContent(text, (new StringBuilder()).append("text/").append(subtype).append("; charset=").append(MimeUtility.quote(charset, "()<>@,;:\\\"\t []/?=")).toString());
    }

    static String getDisposition(MimePart part)
        throws MessagingException
    {
        String s = part.getHeader("Content-Disposition", null);
        if(s == null)
        {
            return null;
        } else
        {
            ContentDisposition cd = new ContentDisposition(s);
            return cd.getDisposition();
        }
    }

    static void setDisposition(MimePart part, String disposition)
        throws MessagingException
    {
        if(disposition == null)
        {
            part.removeHeader("Content-Disposition");
        } else
        {
            String s = part.getHeader("Content-Disposition", null);
            if(s != null)
            {
                ContentDisposition cd = new ContentDisposition(s);
                cd.setDisposition(disposition);
                disposition = cd.toString();
            }
            part.setHeader("Content-Disposition", disposition);
        }
    }

    static String getDescription(MimePart part)
        throws MessagingException
    {
        String rawvalue = part.getHeader("Content-Description", null);
        if(rawvalue == null)
            return null;
        try
        {
            return MimeUtility.decodeText(MimeUtility.unfold(rawvalue));
        }
        catch(UnsupportedEncodingException ex)
        {
            return rawvalue;
        }
    }

    static void setDescription(MimePart part, String description, String charset)
        throws MessagingException
    {
        if(description == null)
        {
            part.removeHeader("Content-Description");
            return;
        }
        try
        {
            part.setHeader("Content-Description", MimeUtility.fold(21, MimeUtility.encodeText(description, charset, null)));
        }
        catch(UnsupportedEncodingException uex)
        {
            throw new MessagingException("Encoding error", uex);
        }
    }

    static String getFileName(MimePart part)
        throws MessagingException
    {
        String filename = null;
        String s = part.getHeader("Content-Disposition", null);
        if(s != null)
        {
            ContentDisposition cd = new ContentDisposition(s);
            filename = cd.getParameter("filename");
        }
        if(filename == null)
        {
            s = part.getHeader("Content-Type", null);
            s = MimeUtil.cleanContentType(part, s);
            if(s != null)
                try
                {
                    ContentType ct = new ContentType(s);
                    filename = ct.getParameter("name");
                }
                catch(ParseException pex) { }
        }
        if(decodeFileName && filename != null)
            try
            {
                filename = MimeUtility.decodeText(filename);
            }
            catch(UnsupportedEncodingException ex)
            {
                throw new MessagingException("Can't decode filename", ex);
            }
        return filename;
    }

    static void setFileName(MimePart part, String name)
        throws MessagingException
    {
        if(encodeFileName && name != null)
            try
            {
                name = MimeUtility.encodeText(name);
            }
            catch(UnsupportedEncodingException ex)
            {
                throw new MessagingException("Can't encode filename", ex);
            }
        String s = part.getHeader("Content-Disposition", null);
        ContentDisposition cd = new ContentDisposition(s != null ? s : "attachment");
        cd.setParameter("filename", name);
        part.setHeader("Content-Disposition", cd.toString());
        if(setContentTypeFileName)
        {
            s = part.getHeader("Content-Type", null);
            s = MimeUtil.cleanContentType(part, s);
            if(s != null)
                try
                {
                    ContentType cType = new ContentType(s);
                    cType.setParameter("name", name);
                    part.setHeader("Content-Type", cType.toString());
                }
                catch(ParseException pex) { }
        }
    }

    static String[] getContentLanguage(MimePart part)
        throws MessagingException
    {
        String s = part.getHeader("Content-Language", null);
        if(s == null)
            return null;
        HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
        Vector v = new Vector();
        do
        {
            HeaderTokenizer.Token tk = h.next();
            int tkType = tk.getType();
            if(tkType == -4)
                break;
            if(tkType == -1)
                v.addElement(tk.getValue());
        } while(true);
        if(v.size() == 0)
        {
            return null;
        } else
        {
            String language[] = new String[v.size()];
            v.copyInto(language);
            return language;
        }
    }

    static void setContentLanguage(MimePart part, String languages[])
        throws MessagingException
    {
        StringBuffer sb = new StringBuffer(languages[0]);
        int len = "Content-Language".length() + 2 + languages[0].length();
        for(int i = 1; i < languages.length; i++)
        {
            sb.append(',');
            if(++len > 76)
            {
                sb.append("\r\n\t");
                len = 8;
            }
            sb.append(languages[i]);
            len += languages[i].length();
        }

        part.setHeader("Content-Language", sb.toString());
    }

    static String getEncoding(MimePart part)
        throws MessagingException
    {
        String s = part.getHeader("Content-Transfer-Encoding", null);
        if(s == null)
            return null;
        s = s.trim();
        if(s.equalsIgnoreCase("7bit") || s.equalsIgnoreCase("8bit") || s.equalsIgnoreCase("quoted-printable") || s.equalsIgnoreCase("binary") || s.equalsIgnoreCase("base64"))
            return s;
        HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
        do
        {
            HeaderTokenizer.Token tk = h.next();
            int tkType = tk.getType();
            if(tkType != -4)
            {
                if(tkType == -1)
                    return tk.getValue();
            } else
            {
                return s;
            }
        } while(true);
    }

    static void setEncoding(MimePart part, String encoding)
        throws MessagingException
    {
        part.setHeader("Content-Transfer-Encoding", encoding);
    }

    static String restrictEncoding(MimePart part, String encoding)
        throws MessagingException
    {
        String type;
        if(!ignoreMultipartEncoding || encoding == null)
            return encoding;
        if(encoding.equalsIgnoreCase("7bit") || encoding.equalsIgnoreCase("8bit") || encoding.equalsIgnoreCase("binary"))
            return encoding;
        type = part.getContentType();
        if(type == null)
            return encoding;
        ContentType cType;
        cType = new ContentType(type);
        if(cType.match("multipart/*"))
            return null;
        try
        {
            if(cType.match("message/*") && !PropUtil.getBooleanSystemProperty("mail.mime.allowencodedmessages", false))
                return null;
        }
        catch(ParseException pex) { }
        return encoding;
    }

    static void updateHeaders(MimePart part)
        throws MessagingException
    {
        DataHandler dh = part.getDataHandler();
        if(dh == null)
            return;
        String type;
        boolean composite;
        boolean needCTHeader;
        ContentType cType;
        String charset;
        String s;
        String enc;
        ContentDisposition cd;
        String filename;
        try
        {
            type = dh.getContentType();
            composite = false;
            needCTHeader = part.getHeader("Content-Type") == null;
            cType = new ContentType(type);
            if(cType.match("multipart/*"))
            {
                composite = true;
                Object o;
                if(part instanceof MimeBodyPart)
                {
                    MimeBodyPart mbp = (MimeBodyPart)part;
                    o = mbp.cachedContent == null ? dh.getContent() : mbp.cachedContent;
                } else
                if(part instanceof MimeMessage)
                {
                    MimeMessage msg = (MimeMessage)part;
                    o = msg.cachedContent == null ? dh.getContent() : msg.cachedContent;
                } else
                {
                    o = dh.getContent();
                }
                if(o instanceof MimeMultipart)
                    ((MimeMultipart)o).updateHeaders();
                else
                    throw new MessagingException((new StringBuilder()).append("MIME part of type \"").append(type).append("\" contains object of type ").append(o.getClass().getName()).append(" instead of MimeMultipart").toString());
            } else
            if(cType.match("message/rfc822"))
                composite = true;
            if(dh instanceof MimePartDataHandler)
                return;
        }
        catch(IOException ex)
        {
            throw new MessagingException("IOException updating headers", ex);
        }
        if(!composite)
        {
            if(part.getHeader("Content-Transfer-Encoding") == null)
                setEncoding(part, MimeUtility.getEncoding(dh));
            if(needCTHeader && setDefaultTextCharset && cType.match("text/*") && cType.getParameter("charset") == null)
            {
                enc = part.getEncoding();
                if(enc != null && enc.equalsIgnoreCase("7bit"))
                    charset = "us-ascii";
                else
                    charset = MimeUtility.getDefaultMIMECharset();
                cType.setParameter("charset", charset);
                type = cType.toString();
            }
        }
        if(needCTHeader)
        {
            s = part.getHeader("Content-Disposition", null);
            if(s != null)
            {
                cd = new ContentDisposition(s);
                filename = cd.getParameter("filename");
                if(filename != null)
                {
                    cType.setParameter("name", filename);
                    type = cType.toString();
                }
            }
            part.setHeader("Content-Type", type);
        }
    }

    static void invalidateContentHeaders(MimePart part)
        throws MessagingException
    {
        part.removeHeader("Content-Type");
        part.removeHeader("Content-Transfer-Encoding");
    }

    static void writeTo(MimePart part, OutputStream os, String ignoreList[])
        throws IOException, MessagingException
    {
        InputStream is;
        LineOutputStream los = null;
        if(os instanceof LineOutputStream)
            los = (LineOutputStream)os;
        else
            los = new LineOutputStream(os);
        for(Enumeration hdrLines = part.getNonMatchingHeaderLines(ignoreList); hdrLines.hasMoreElements(); los.writeln((String)hdrLines.nextElement()));
        los.writeln();
        is = null;
        byte buf[] = null;
        DataHandler dh = part.getDataHandler();
        if(dh instanceof MimePartDataHandler)
            if(part instanceof MimeBodyPart)
            {
                MimeBodyPart mbp = (MimeBodyPart)part;
                is = mbp.getContentStream();
            } else
            if(part instanceof MimeMessage)
            {
                MimeMessage msg = (MimeMessage)part;
                is = msg.getContentStream();
            }
        if(is != null)
        {
            byte buf[] = new byte[8192];
            int len;
            while((len = is.read(buf)) > 0) 
                os.write(buf, 0, len);
        } else
        {
            os = MimeUtility.encode(os, restrictEncoding(part, part.getEncoding()));
            part.getDataHandler().writeTo(os);
        }
        if(is != null)
            is.close();
        byte buf[] = null;
        break MISSING_BLOCK_LABEL_230;
        Exception exception;
        exception;
        if(is != null)
            is.close();
        byte buf[] = null;
        throw exception;
        os.flush();
        return;
    }

    private static final boolean setDefaultTextCharset = PropUtil.getBooleanSystemProperty("mail.mime.setdefaulttextcharset", true);
    private static final boolean setContentTypeFileName = PropUtil.getBooleanSystemProperty("mail.mime.setcontenttypefilename", true);
    private static final boolean encodeFileName = PropUtil.getBooleanSystemProperty("mail.mime.encodefilename", false);
    private static final boolean decodeFileName = PropUtil.getBooleanSystemProperty("mail.mime.decodefilename", false);
    private static final boolean ignoreMultipartEncoding = PropUtil.getBooleanSystemProperty("mail.mime.ignoremultipartencoding", true);
    static final boolean cacheMultipart = PropUtil.getBooleanSystemProperty("mail.mime.cachemultipart", true);
    protected DataHandler dh;
    protected byte content[];
    protected InputStream contentStream;
    protected InternetHeaders headers;
    protected Object cachedContent;

}
