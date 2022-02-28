import java.io.IOException;

import javax.security.auth.callback.CallbackHandler;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfRect;

/*
 * Demonstrates how to sign a document using an USB token.
 */
public class SignDocumentUsingUSBToken
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        String inputFileName = "InputDocument.pdf";
        String outputFileName = "OutputDocument.pdf";
        
        // Load an existing PDF document
        PdfDocument pdfDoc = new PdfDocument();
        pdfDoc.load(inputFileName);
        
        // path of the library that has the PKCS11 implementation of the USB token.
        String pathToPKCS11ImplLibOfUSBToken = "libFileName";
        // eg. For SafeNet token on Windows
//        String pathToPKCS11ImplLibOfUSBToken = "C:\\WINDOWS\\system32\\eTPKCS11.dll";
        // eg. for SafeNet token on Linux
//        String pathToPKCS11ImplLibOfUSBToken = "/lib/libeToken.so.9";
        
        // PIN/password to access the certificates in USB token.
        String passwordToAccessKeyStore = "password";
        
        /*
         * specify callback handler if you do not want to supply the
         * password directly to Gnostice API.
         */
        CallbackHandler callbackHandlerToGetPassword = null;
        
        /*
         * when the token is not available in the default slot then
         * attempts are made from slot number '0' to this
         * maxSlotNumber to access the token. If the value specified
         * for maxSlotNumber is '-1', then only the default slot will
         * be attempted with, to access the token.
         */
        int maxSlotNumber = -1;
        
        /*
         * Supply the parameters required to access the USB token, and
         * other details such as Reason, Location, and ContactInfo.
         */
        pdfDoc.addSignature(pathToPKCS11ImplLibOfUSBToken,
            passwordToAccessKeyStore,
            callbackHandlerToGetPassword,
            maxSlotNumber,
            "Signer name",  // signer's name
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
}
