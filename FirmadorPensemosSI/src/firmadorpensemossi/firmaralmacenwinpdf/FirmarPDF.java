// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 12/12/2017 12:21:43 p. m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FirmarPDF.java

package firmadorpensemossi.firmaralmacenwinpdf;

import co.com.andesscd.pki.clases.CMS;
import java.io.*;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;

public class FirmarPDF
{

    public FirmarPDF()
    {
    }

    public void firmarPDF(X509Certificate x509, PrivateKey privKey, Provider provider, InputStream entrada, ByteArrayOutputStream salida, String firmaTSA, String datosTSA)
        throws UnknownError, Exception
    {
        CMS cms = new CMS(entrada);
        CMS.setFuenteHorariaLocal();
        System.out.println(firmaTSA);
        if(firmaTSA.equalsIgnoreCase("true"))
        {
            String tsa[] = datosTSA.split("--");
            CMS.setFuenteHorariaTSA(tsa[0], tsa[1], tsa[2]);
        }
        cms.firmar(x509, privKey, provider, salida);
    }
}