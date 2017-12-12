// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MimeBodyPart.java

package javax.mail.internet;

import java.io.File;
import javax.activation.FileDataSource;
import javax.mail.EncodingAware;

// Referenced classes of package javax.mail.internet:
//            MimeBodyPart

private static class MimeBodyPart$EncodedFileDataSource extends FileDataSource
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

    public MimeBodyPart$EncodedFileDataSource(File file, String contentType, String encoding)
    {
        super(file);
        this.contentType = contentType;
        this.encoding = encoding;
    }
}
