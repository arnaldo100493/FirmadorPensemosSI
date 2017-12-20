package pensemos.firmador;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import sun.misc.BASE64Decoder;

public class FirmadorPensemosSI {

    private static KeyManagerFactory kmf;
    private static TrustManagerFactory tmf;
    public static String keyPass = "localPasswd";
    public static String dataSign = "";
    public static String errorMsg = "";
    public static JTextArea textLog = null;

    public FirmadorPensemosSI() {
        try {
            String message = "Puerto habilitado: 8448";
            textLog = new JTextArea(message, 5, 10);
            loadCertificateKey();
            HttpServer httpserver = HttpServer.create(new InetSocketAddress(8448), 0);
            httpserver.setExecutor(null);

            httpserver.createContext("/test", new TestHandler());
            HttpContext context = httpserver.createContext("/pki", new DoSign());
            context.getFilters().add(new ParameterFilter());
            httpserver.start();
        } catch (NoSuchAlgorithmException | IOException ex) {
            String message = textLog.getText();
            message = message + "\nPkiAutenticacion " + ex.getMessage();
            textLog.setText(message);
            Logger.getLogger(FirmadorPensemosSI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        FirmadorPensemosSI pki = new FirmadorPensemosSI();
        pki.createInterface();
        System.out.println("Servidor iniciado");
    }

    public void loadCertificateKey()
            throws NoSuchAlgorithmException {
        String stringcert = Resource.getCert();
        String key = Resource.getPrivKey();
        BASE64Decoder b64 = new BASE64Decoder();
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(b64.decodeBuffer(stringcert)));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            KeySpec privateKeySpec = new PKCS8EncodedKeySpec(b64.decodeBuffer(key));
            PrivateKey privKey = kf.generatePrivate(privateKeySpec);
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(null, null);
            X509Certificate[] chain = new X509Certificate[1];
            chain[0] = ((X509Certificate) certificate);
            keystore.setKeyEntry("localServer", privKey, keyPass.toCharArray(), chain);
            kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keystore, keyPass.toCharArray());
            tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(keystore);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | CertificateException | KeyStoreException | UnrecoverableKeyException ex) {
            String message = textLog.getText();
            message = message + "\nLoadCertificateKey " + ex.getMessage();
            textLog.setText(message);
            Logger.getLogger(FirmadorPensemosSI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createInterface() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            JFrame frame = new JFrame("Firmador Pensemos Soluciones de Industria");
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.ICONIFIED);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(new Dimension(400, 300));
            frame.setIconImage(new ImageIcon(getClass().getResource("/pensemos/images/Logo.png")).getImage());
            ImageIcon icon = new ImageIcon(getClass().getResource("/pensemos/images/PensemosSI.png"));
            //String message = "<html><body>Agente Iniciado Version: 1.0.0 <br/>Recibiendo solicitudes en el puerto 8448 </body></html>";
            JLabel label = new JLabel(icon, 0);
            label.setVerticalTextPosition(3);
            label.setHorizontalTextPosition(0);
            Container content = frame.getContentPane();
            content.setBackground(new Color(206, 227, 246));
            content.add(label, "North");
            //textLog = new JTextArea(5, 10);
            JScrollPane scrollPane = new JScrollPane(textLog);
            frame.add(scrollPane, BorderLayout.CENTER);
            textLog.setEditable(false);
            content.add(textLog, "Center");
            //JButton button = new JButton("Detener ");
            //button.setBounds(10, 10, 20, 30);
            ActionListener listener = new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    System.exit(0);
                }
            };
            //button.addActionListener(listener);
            //content.add(button, "South");
            frame.show();
        } catch (Exception ex) {
            errorMsg = textLog.getText();
            errorMsg = errorMsg + "\nOcurrió un error: PkiAutenticacion " + ex.getMessage();
            textLog.setText(errorMsg);
            Logger.getLogger(FirmadorPensemosSI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ocurrió un error: " + ex.getMessage());
        }
    }

    public static void setHeaders(Headers headers) {
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
    }

}
