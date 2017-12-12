// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 12/12/2017 12:21:44 p. m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   JsonNumber.java

package firmadorpensemossi.firmaralmacenwinpdf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonNumber extends Number
{

    public JsonNumber(String s)
    {
        if(s == null)
            throw new NullPointerException();
        if(!SYNTAX.matcher(s).matches())
        {
            throw new IllegalArgumentException("Invalid number syntax");
        } else
        {
            rawValue = s;
            return;
        }
    }

    public byte byteValue()
    {
        return Byte.parseByte(rawValue);
    }

    public short shortValue()
    {
        return Short.parseShort(rawValue);
    }

    public int intValue()
    {
        return Integer.parseInt(rawValue);
    }

    public long longValue()
    {
        return Long.parseLong(rawValue);
    }

    public float floatValue()
    {
        return Float.parseFloat(rawValue);
    }

    public double doubleValue()
    {
        return Double.parseDouble(rawValue);
    }

    public BigInteger bigIntegerValue()
    {
        return new BigInteger(rawValue);
    }

    public BigDecimal bigDecimalValue()
    {
        String parts[] = rawValue.split("[eE]");
        BigDecimal result = new BigDecimal(parts[0]);
        if(parts.length == 2)
            result = result.movePointRight(Integer.parseInt(parts[1]));
        return result;
    }

    public boolean equals(Object obj)
    {
        if(!(obj instanceof JsonNumber))
            return false;
        else
            return rawValue.equals(((JsonNumber)obj).rawValue);
    }

    public int hashCode()
    {
        return rawValue.hashCode();
    }

    public String toString()
    {
        return rawValue;
    }

    private final String rawValue;
    static Pattern SYNTAX = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");

}