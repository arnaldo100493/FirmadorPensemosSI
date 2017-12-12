// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MailDateFormat.java

package javax.mail.internet;

import java.text.ParseException;

class MailDateParser
{

    public MailDateParser(char orig[], int index)
    {
        this.index = 0;
        this.orig = null;
        this.orig = orig;
        this.index = index;
    }

    public void skipUntilNumber()
        throws ParseException
    {
_L2:
        switch(orig[index])
        {
        case 48: // '0'
        case 49: // '1'
        case 50: // '2'
        case 51: // '3'
        case 52: // '4'
        case 53: // '5'
        case 54: // '6'
        case 55: // '7'
        case 56: // '8'
        case 57: // '9'
            return;
        }
        try
        {
            index++;
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            throw new ParseException("No Number Found", index);
        }
        while(false) ;
        if(true) goto _L2; else goto _L1
_L1:
    }

    public void skipWhiteSpace()
    {
        int len = orig.length;
        do
        {
            if(index >= len)
                break;
            switch(orig[index])
            {
            case 9: // '\t'
            case 10: // '\n'
            case 13: // '\r'
            case 32: // ' '
                index++;
                break;

            default:
                return;
            }
        } while(true);
    }

    public int peekChar()
        throws ParseException
    {
        if(index < orig.length)
            return orig[index];
        else
            throw new ParseException("No more characters", index);
    }

    public void skipChar(char c)
        throws ParseException
    {
        if(index < orig.length)
        {
            if(orig[index] == c)
                index++;
            else
                throw new ParseException("Wrong char", index);
        } else
        {
            throw new ParseException("No more characters", index);
        }
    }

    public boolean skipIfChar(char c)
        throws ParseException
    {
        if(index < orig.length)
        {
            if(orig[index] == c)
            {
                index++;
                return true;
            } else
            {
                return false;
            }
        } else
        {
            throw new ParseException("No more characters", index);
        }
    }

    public int parseNumber()
        throws ParseException
    {
        int length = orig.length;
        boolean gotNum = false;
        int result = 0;
        for(; index < length; index++)
            switch(orig[index])
            {
            case 48: // '0'
                result *= 10;
                gotNum = true;
                break;

            case 49: // '1'
                result = result * 10 + 1;
                gotNum = true;
                break;

            case 50: // '2'
                result = result * 10 + 2;
                gotNum = true;
                break;

            case 51: // '3'
                result = result * 10 + 3;
                gotNum = true;
                break;

            case 52: // '4'
                result = result * 10 + 4;
                gotNum = true;
                break;

            case 53: // '5'
                result = result * 10 + 5;
                gotNum = true;
                break;

            case 54: // '6'
                result = result * 10 + 6;
                gotNum = true;
                break;

            case 55: // '7'
                result = result * 10 + 7;
                gotNum = true;
                break;

            case 56: // '8'
                result = result * 10 + 8;
                gotNum = true;
                break;

            case 57: // '9'
                result = result * 10 + 9;
                gotNum = true;
                break;

            default:
                if(gotNum)
                    return result;
                else
                    throw new ParseException("No Number found", index);
            }

        if(gotNum)
            return result;
        else
            throw new ParseException("No Number found", index);
    }

    public int parseMonth()
        throws ParseException
    {
        orig[index++];
        JVM INSTR tableswitch 65 115: default 858
    //                   65 510
    //                   66 858
    //                   67 858
    //                   68 797
    //                   69 858
    //                   70 376
    //                   71 858
    //                   72 858
    //                   73 858
    //                   74 236
    //                   75 858
    //                   76 858
    //                   77 436
    //                   78 736
    //                   79 675
    //                   80 858
    //                   81 858
    //                   82 858
    //                   83 614
    //                   84 858
    //                   85 858
    //                   86 858
    //                   87 858
    //                   88 858
    //                   89 858
    //                   90 858
    //                   91 858
    //                   92 858
    //                   93 858
    //                   94 858
    //                   95 858
    //                   96 858
    //                   97 510
    //                   98 858
    //                   99 858
    //                   100 797
    //                   101 858
    //                   102 376
    //                   103 858
    //                   104 858
    //                   105 858
    //                   106 236
    //                   107 858
    //                   108 858
    //                   109 436
    //                   110 736
    //                   111 675
    //                   112 858
    //                   113 858
    //                   114 858
    //                   115 614;
           goto _L1 _L2 _L1 _L1 _L3 _L1 _L4 _L1 _L1 _L1 _L5 _L1 _L1 _L6 _L7 _L8 _L1 _L1 _L1 _L9 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L2 _L1 _L1 _L3 _L1 _L4 _L1 _L1 _L1 _L5 _L1 _L1 _L6 _L7 _L8 _L1 _L1 _L1 _L9
_L1:
        break; /* Loop/switch isn't completed */
_L5:
        orig[index++];
        JVM INSTR lookupswitch 4: default 373
    //                   65: 296
    //                   85: 327
    //                   97: 296
    //                   117: 327;
           goto _L10 _L11 _L12 _L11 _L12
_L10:
        break; /* Loop/switch isn't completed */
_L11:
        char curr;
        curr = orig[index++];
        if(curr == 'N' || curr == 'n')
            return 0;
        break; /* Loop/switch isn't completed */
_L12:
        curr = orig[index++];
        if(curr == 'N' || curr == 'n')
            return 5;
        try
        {
            if(curr == 'L' || curr == 'l')
                return 6;
        }
        catch(ArrayIndexOutOfBoundsException e) { }
        break; /* Loop/switch isn't completed */
_L4:
        curr = orig[index++];
        if(curr == 'E' || curr == 'e')
        {
            curr = orig[index++];
            if(curr == 'B' || curr == 'b')
                return 1;
        }
        break; /* Loop/switch isn't completed */
_L6:
        curr = orig[index++];
        if(curr != 'A' && curr != 'a')
            break; /* Loop/switch isn't completed */
        curr = orig[index++];
        if(curr == 'R' || curr == 'r')
            return 2;
        if(curr == 'Y' || curr == 'y')
            return 4;
        break; /* Loop/switch isn't completed */
_L2:
        curr = orig[index++];
        if(curr == 'P' || curr == 'p')
        {
            curr = orig[index++];
            if(curr == 'R' || curr == 'r')
                return 3;
            break; /* Loop/switch isn't completed */
        }
        if(curr != 'U' && curr != 'u')
            break; /* Loop/switch isn't completed */
        curr = orig[index++];
        if(curr == 'G' || curr == 'g')
            return 7;
        break; /* Loop/switch isn't completed */
_L9:
        curr = orig[index++];
        if(curr != 'E' && curr != 'e')
            break; /* Loop/switch isn't completed */
        curr = orig[index++];
        if(curr == 'P' || curr == 'p')
            return 8;
        break; /* Loop/switch isn't completed */
_L8:
        curr = orig[index++];
        if(curr != 'C' && curr != 'c')
            break; /* Loop/switch isn't completed */
        curr = orig[index++];
        if(curr == 'T' || curr == 't')
            return 9;
        break; /* Loop/switch isn't completed */
_L7:
        curr = orig[index++];
        if(curr != 'O' && curr != 'o')
            break; /* Loop/switch isn't completed */
        curr = orig[index++];
        if(curr == 'V' || curr == 'v')
            return 10;
        break; /* Loop/switch isn't completed */
_L3:
        curr = orig[index++];
        if(curr != 'E' && curr != 'e')
            break; /* Loop/switch isn't completed */
        curr = orig[index++];
        if(curr == 'C' || curr == 'c')
            return 11;
        throw new ParseException("Bad Month", index);
    }

    public int parseTimeZone()
        throws ParseException
    {
        if(index >= orig.length)
            throw new ParseException("No more characters", index);
        char test = orig[index];
        if(test == '+' || test == '-')
            return parseNumericTimeZone();
        else
            return parseAlphaTimeZone();
    }

    public int parseNumericTimeZone()
        throws ParseException
    {
        boolean switchSign = false;
        char first = orig[index++];
        if(first == '+')
            switchSign = true;
        else
        if(first != '-')
            throw new ParseException("Bad Numeric TimeZone", index);
        int oindex = index;
        int tz = parseNumber();
        if(tz >= 2400)
            throw new ParseException("Numeric TimeZone out of range", oindex);
        int offset = (tz / 100) * 60 + tz % 100;
        if(switchSign)
            return -offset;
        else
            return offset;
    }

    public int parseAlphaTimeZone()
        throws ParseException
    {
        int result = 0;
        boolean foundCommon = false;
        try
        {
            switch(orig[index++])
            {
            case 85: // 'U'
            case 117: // 'u'
            {
                char curr = orig[index++];
                if(curr == 'T' || curr == 't')
                    result = 0;
                else
                    throw new ParseException("Bad Alpha TimeZone", index);
                break;
            }

            case 71: // 'G'
            case 103: // 'g'
            {
                char curr = orig[index++];
                if(curr == 'M' || curr == 'm')
                {
                    curr = orig[index++];
                    if(curr == 'T' || curr == 't')
                    {
                        result = 0;
                        break;
                    }
                }
                throw new ParseException("Bad Alpha TimeZone", index);
            }

            case 69: // 'E'
            case 101: // 'e'
            {
                result = 300;
                foundCommon = true;
                break;
            }

            case 67: // 'C'
            case 99: // 'c'
            {
                result = 360;
                foundCommon = true;
                break;
            }

            case 77: // 'M'
            case 109: // 'm'
            {
                result = 420;
                foundCommon = true;
                break;
            }

            case 80: // 'P'
            case 112: // 'p'
            {
                result = 480;
                foundCommon = true;
                break;
            }

            default:
            {
                throw new ParseException("Bad Alpha TimeZone", index);
            }
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            throw new ParseException("Bad Alpha TimeZone", index);
        }
        if(foundCommon)
        {
            char curr = orig[index++];
            if(curr == 'S' || curr == 's')
            {
                curr = orig[index++];
                if(curr != 'T' && curr != 't')
                    throw new ParseException("Bad Alpha TimeZone", index);
            } else
            if(curr == 'D' || curr == 'd')
            {
                curr = orig[index++];
                if(curr == 'T' || curr != 't')
                    result -= 60;
                else
                    throw new ParseException("Bad Alpha TimeZone", index);
            }
        }
        return result;
    }

    int getIndex()
    {
        return index;
    }

    int index;
    char orig[];
}
