// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 12/12/2017 12:21:44 p. m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TestHandler.java

package firmadorpensemossi.firmaralmacenwinpdf;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

// Referenced classes of package firmaralmacenwinpdf:
//            FirmarAlmacenWinPDF

public class TestHandler
    implements HttpHandler
{

    public TestHandler()
    {
    }

    public void handle(HttpExchange t)
        throws IOException
    {
        String response = "Test page";
        int responseLong = response.getBytes("UTF-8").length;
        FirmarAlmacenWinPDF.setHeaders(t.getResponseHeaders());
        t.sendResponseHeaders(200, responseLong);
        OutputStream os = t.getResponseBody();
        Throwable localThrowable3 = null;
        try {
            os.write(response.getBytes("UTF-8"));
        } catch (Throwable localThrowable1) {
            localThrowable3 = localThrowable1;
            throw localThrowable1;
        } finally {
            if (os != null) {
                if (localThrowable3 != null) {
                    try {
                        os.close();
                    } catch (Throwable localThrowable2) {
                        localThrowable3.addSuppressed(localThrowable2);
                    }
                } else {
                    os.close();
                }
            }
        }
    }
}