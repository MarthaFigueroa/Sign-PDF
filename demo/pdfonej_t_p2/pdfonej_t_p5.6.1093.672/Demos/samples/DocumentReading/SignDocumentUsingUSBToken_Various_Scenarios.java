import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFormSignatureField;
import com.gnostice.pdfone.PdfRect;
import com.gnostice.pdfone.PdfSignature;

/*
 * Demonstrates various scenarios for signing PDF documents using USB
 * token.
 */
public class SignDocumentUsingUSBToken_Various_Scenarios
{
    public static void main(String[] args)
        throws IOException, PdfException
    {
        /*
         * Include validation result appearance along with signature
         * appearance.
         */
        signAndIncludeValidationResultAppearance();

        /*
         * Use password CallbackHandler to supply PIN/password to
         * access the USB token's KeyStore.
         */
//        supplyTokenDevicePasswordUsingCallbackHandler();
        
        /*
         * Connect to the USB token and retrieve the certificate at
         * the user end and sign using PDFOne API.
         */
//        retrieveCertificateAtUserEndAndSign();
        
        /*
         * Signing using a specified certificate serial number when
         * there are multiple certificates installed on a USB token.
         * Also it demonstrates how to handle when mutiple USB tokens
         * are connected.
         */
//        signUsingSpecifiedCertificateWhenMultipleTokensConnected();
    }

    private static void signAndIncludeValidationResultAppearance()
        throws IOException, PdfException
    {
        String inputFileName = "InputDocument.pdf";
        String outputFileName = "OutputDocument.pdf";

        // Load an existing PDF document
        PdfDocument pdfDoc = new PdfDocument();
        pdfDoc.load(inputFileName);

        /*
         * Supply the parameters required to access the USB token, and
         * other details such as Reason, Location, and ContactInfo.
         */
        PdfSignature pdfSignature = new PdfSignature(
            "libraryPath", // path to PKCS11 implementation library
            "password", // password to access KeyStore
            null, // issuer Common name
            null, // certificate Serial number
            null, // callbackHandler to get password
            -1, // maxSlotNumber
            "Signer name", // signer's name
            "Signing this document", // reason
            "Bangalore, India", // location
            "test@example.com", // contact info
            1); // page number

        PdfFormSignatureField signatureField = new PdfFormSignatureField(
            new PdfRect(10, 10, 100, 50));
        signatureField.setName("sigfld");
        signatureField.fill(pdfSignature);

        /*
         * specify whether validation result appearance should be
         * included so that the viewer that renders this signature
         * will show a validation result mark and text upon signature
         * validation. Note: since including the validation result
         * appearance is a violation of PAdES standard it is false by
         * default.
         */
        signatureField.setIncludeValidationResultAppearance(true);

        /*
         * specify if validation result appearance text should not be
         * shown when IncludeValidationResultAppearance is true.
         */
//         signatureField.setIncludeValidationResultAppearanceText(false);

        pdfDoc.addFormField(signatureField, 1);

        pdfDoc.setOpenAfterSave(true);
        pdfDoc.save(outputFileName);
        pdfDoc.close();

    }
    
    private static void supplyTokenDevicePasswordUsingCallbackHandler()
        throws IOException, PdfException
    {
        String inputFileName = "InputDocument.pdf";
        String outputFileName = "OutputDocument.pdf";

        // Load an existing PDF document
        PdfDocument pdfDoc = new PdfDocument();
        pdfDoc.load(inputFileName);

        /*
         * specify callback handler if you do not want to supply the
         * password directly to Gnostice API.
         */
        CallbackHandler callbackHandlerToGetPassword = new CallbackHandler()
        {
            public void handle(Callback[] callbacks)
                throws IOException, UnsupportedCallbackException
            {
                for (int i = 0; i < callbacks.length; i++)
                {
                    Callback callback = (Callback) callbacks[i];
                    if (callback instanceof PasswordCallback)
                    {
                        PasswordCallback passwordCallback = (PasswordCallback) callback;

                        // security provider shows a password prompt to get a password
//                         passwordCallback.getPrompt();

                        // directly supply the password
                        passwordCallback.setPassword("password".toCharArray());
                    }
                }
            }
        };

        /*
         * Supply the parameters required to access the USB token, and
         * other details such as Reason, Location, and ContactInfo.
         */
        pdfDoc.addSignature(
            "librayPath", // path to PKCS11 implementation library
            null, // password to access KeyStore
            callbackHandlerToGetPassword, -1, // maxSlotNumber
            "Signer name", // signer's name
            "Signing this document", // reason
            "Bangalore, India", // location
            "test@example.com", // contact info
            1, // page number
            "sigField1", // field name
            new PdfRect(10, 20, 100, 50)); // location on page

        pdfDoc.setOpenAfterSave(true);
        pdfDoc.save(outputFileName);
        pdfDoc.close();

    }
    
    private static void retrieveCertificateAtUserEndAndSign()
        throws IOException, PdfException
    {
     // Start of block - User connects to the USB token at his end and reads the certificate
        Certificate[] certificateChain = null;
        PrivateKey privateKey = null;
        
        try
        {
            // Specify the configuration to access the USB token.
            String tokenName = "eToken";
            String pathToPKCS11ImplLibOfUSBToken = "libraryPath";
            String passwordToAccessKeyStore = "password";
            
            boolean useFirstCertificateFromToken = true;
            String issuerCommonName = "MyName";
            String certificateSerialNumber = "-398666017398154847914207";
            
            String pkcs11Config = "name=" + tokenName + System.getProperty("line.separator") 
                + "library=" + pathToPKCS11ImplLibOfUSBToken;
            
            byte[] pkcs11ConfigBytes = pkcs11Config.getBytes();
            InputStream pkcs11ConfigStream = new ByteArrayInputStream(pkcs11ConfigBytes);
            
            // Create a Provider for accessing the USB token by supplying the configuration.
            Provider pkcs11Provider = new sun.security.pkcs11.SunPKCS11(pkcs11ConfigStream);
            Security.addProvider(pkcs11Provider);
            
            // The USB device requires a PIN to access the certficates in the device.
            char[] pin = passwordToAccessKeyStore.toCharArray();
            
            // Create the Keystore for accessing the certificates in the USB device by supplying the PIN.
            KeyStore smartCardKeyStore = KeyStore.getInstance("PKCS11");
            smartCardKeyStore.load(null, pin);
            
            // System.out.println("Keystore size: " + smartCardKeyStore.size());
            
            // Enumerate the certificates in the keystore
            Enumeration aliasesEnum = smartCardKeyStore.aliases();
            
            if (aliasesEnum.hasMoreElements())
            {
                while(aliasesEnum.hasMoreElements())
                {
                    // choose the required certificate using the alias
                    String alias = (String) aliasesEnum.nextElement();
                    
                    X509Certificate cert = (X509Certificate) smartCardKeyStore
                        .getCertificate(alias);
                    
                    // System.out.println(cert);
                    // System.out.println("Serial Number: " + cert.getSerialNumber());
                    // System.out.println("IssuerDN: " + cert.getIssuerDN());
                    
                    // Always read first certificate
                    if (useFirstCertificateFromToken)
                    {
                        certificateChain = smartCardKeyStore.getCertificateChain(alias);
                        privateKey = (PrivateKey) smartCardKeyStore.getKey(alias, null);
                        break;
                    }
                    else
                    {
                        // Look for the matching certificate serial number and issuer common name
                        if (cert.getIssuerDN().getName().indexOf("CN=" + issuerCommonName) != -1
                            && cert.getSerialNumber().toString().equals(certificateSerialNumber))
                        {
                            // Get the certificate chain of the required certificate and get its private key.
                            certificateChain = smartCardKeyStore.getCertificateChain(alias);
                            privateKey = (PrivateKey) smartCardKeyStore.getKey(alias, null);
                            
                            break;
                        }
                    }
                }
            }
            else
            {            
                throw new KeyStoreException("Keystore is empty");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        if (certificateChain == null
            || privateKey == null)
        {
            return;
        }
        
        // End of block - User connects to the USB token at his end and reads the certificate
        
        // Load the document to be signed.
        PdfDocument pdfDoc = new PdfDocument();
        pdfDoc.load("InputDocument.pdf");
        
        /*
         * Supply Certificate[] array and PrivateKey objects retrieved
         * from the KeyStrore of USB token.
         */
        PdfSignature pdfSignature = new PdfSignature(
            certificateChain,
            privateKey,
            "Signer name",  // signer's name
            "Signing this document", // reason
            "Bangalore, India", // location
            "test@example.com", // contact info
            1); // page number
        
        pdfSignature.setFieldName("sigfld");
        pdfSignature.setFieldRect(new PdfRect(10, 10, 100, 50));
        
        // add the signature to the document
        pdfDoc.addSignature(pdfSignature);
        
        pdfDoc.setOpenAfterSave(true);
        pdfDoc.save("OutputDocument.pdf");
        pdfDoc.close();
        
    }
    
    private static void signUsingSpecifiedCertificateWhenMultipleTokensConnected()
        throws IOException, PdfException
    {
        String inputFileName = "InputDocument.pdf";
        String outputFileName = "OutputDocument.pdf";
        
        // Load an existing PDF document
        PdfDocument pdfDoc = new PdfDocument();
        pdfDoc.load(inputFileName);
        
        /*
         * when the token is not available in the default slot then
         * attempts are made from slot number '0' to this
         * maxSlotNumber to access the token. If the value specified
         * for maxSlotNumber is '-1', then only the default slot will
         * be attempted with, to access the token.
         */
        int maxSlotNumber = 10;
        
        /*
         * Supply the parameters required to access the USB token, and
         * other details such as Reason, Location, and ContactInfo.
         */
        PdfSignature pdfSignature = new PdfSignature(
            "libraryPath", // path to PKCS11 implementation library
            "password", // password to access KeyStore 
            "MyName", // issuer Common name
            "-3c6404190057ff9cfa39", // certificate Serial number
            null, // callbackHandler to get password
            maxSlotNumber, // maxSlotNumber
            "Signer name",  // signer's name
            "Signing this document", // reason
            "Bangalore, India", // location
            "test@example.com", // contact info
            1); // page number
        pdfSignature.setFieldName("sigfld");
        pdfSignature.setFieldRect(new PdfRect(10, 10, 100, 50));
        
        pdfDoc.addSignature(pdfSignature);
        
        pdfDoc.setOpenAfterSave(true);
        pdfDoc.save(outputFileName);
        pdfDoc.close();
    }
}
