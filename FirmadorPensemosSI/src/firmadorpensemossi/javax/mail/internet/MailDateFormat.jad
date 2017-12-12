// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailDateFormat.java

package javax.mail.internet;

import com.sun.mail.util.MailLogger;
import java.text.*;
import java.util.*;
import java.util.logging.Level;

// Referenced classes of package javax.mail.internet:
//            MailDateParser

public class MailDateFormat extends SimpleDateFormat
{

    public MailDateFormat()
    {
        super("EEE, d MMM yyyy HH:mm:ss 'XXXXX' (z)", Locale.US);
    }

    public StringBuffer format(Date date, StringBuffer dateStrBuf, FieldPosition fieldPosition)
    {
        int start = dateStrBuf.length();
        super.format(date, dateStrBuf, fieldPosition);
        int pos = 0;
        for(pos = start + 25; dateStrBuf.charAt(pos) != 'X'; pos++);
        calendar.clear();
        calendar.setTime(date);
        int offset = calendar.get(15) + calendar.get(16);
        if(offset < 0)
        {
            dateStrBuf.setCharAt(pos++, '-');
            offset = -offset;
        } else
        {
            dateStrBuf.setCharAt(pos++, '+');
        }
        int rawOffsetInMins = offset / 60 / 1000;
        int offsetInHrs = rawOffsetInMins / 60;
        int offsetInMins = rawOffsetInMins % 60;
        dateStrBuf.setCharAt(pos++, Character.forDigit(offsetInHrs / 10, 10));
        dateStrBuf.setCharAt(pos++, Character.forDigit(offsetInHrs % 10, 10));
        dateStrBuf.setCharAt(pos++, Character.forDigit(offsetInMins / 10, 10));
        dateStrBuf.setCharAt(pos++, Character.forDigit(offsetInMins % 10, 10));
        return dateStrBuf;
    }

    public Date parse(String text, ParsePosition pos)
    {
        return parseDate(text.toCharArray(), pos, isLenient());
    }

    private static Date parseDate(char orig[], ParsePosition pos, boolean lenient)
    {
        try
        {
            int day = -1;
            int month = -1;
            int year = -1;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            int offset = 0;
            MailDateParser p = new MailDateParser(orig, pos.getIndex());
            p.skipUntilNumber();
            day = p.parseNumber();
            if(!p.skipIfChar('-'))
                p.skipWhiteSpace();
            month = p.parseMonth();
            if(!p.skipIfChar('-'))
                p.skipWhiteSpace();
            year = p.parseNumber();
            if(year < 50)
                year += 2000;
            else
            if(year < 100)
                year += 1900;
            p.skipWhiteSpace();
            hours = p.parseNumber();
            p.skipChar(':');
            minutes = p.parseNumber();
            if(p.skipIfChar(':'))
                seconds = p.parseNumber();
            try
            {
                p.skipWhiteSpace();
                offset = p.parseTimeZone();
            }
            catch(ParseException pe)
            {
                if(logger.isLoggable(Level.FINE))
                    logger.log(Level.FINE, (new StringBuilder()).append("No timezone? : '").append(new String(orig)).append("'").toString(), pe);
            }
            pos.setIndex(p.getIndex());
            Date d = ourUTC(year, month, day, hours, minutes, seconds, offset, lenient);
            return d;
        }
        catch(Exception e)
        {
            if(logger.isLoggable(Level.FINE))
                logger.log(Level.FINE, (new StringBuilder()).append("Bad date: '").append(new String(orig)).append("'").toString(), e);
        }
        pos.setIndex(1);
        return null;
    }

    private static synchronized Date ourUTC(int year, int mon, int mday, int hour, int min, int sec, int tzoffset, boolean lenient)
    {
        cal.clear();
        cal.setLenient(lenient);
        cal.set(1, year);
        cal.set(2, mon);
        cal.set(5, mday);
        cal.set(11, hour);
        cal.set(12, min);
        cal.add(12, tzoffset);
        cal.set(13, sec);
        return cal.getTime();
    }

    public void setCalendar(Calendar newCalendar)
    {
        throw new RuntimeException("Method setCalendar() shouldn't be called");
    }

    public void setNumberFormat(NumberFormat newNumberFormat)
    {
        throw new RuntimeException("Method setNumberFormat() shouldn't be called");
    }

    private static final long serialVersionUID = 0x8eebae2a0a637d55L;
    static boolean debug;
    private static MailLogger logger;
    private static final Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

    static 
    {
        debug = false;
        logger = new MailLogger(javax/mail/internet/MailDateFormat, "DEBUG", debug, System.out);
    }
}
