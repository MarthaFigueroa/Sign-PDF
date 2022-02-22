import java.io.IOException;
import java.util.List;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFormField;
import com.gnostice.pdfone.PdfFormSignatureField;
import com.gnostice.pdfone.PdfSignature;


/*
 * Demonstrates how to sign a blank signature form field in an
 * existing document using an USB token.
 */
public class FillBlankSignatureFieldUsingUSBToken
{
    public static void main(String[] args) throws IOException,
        PdfException
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
            null, // issuerCommonName
            null, // certificateSerialNumber
            null, // callbackHandler to get password
            -1, // maxSlotNumber
            "Signer name",  // signer's name
            "Signing this document", // reason
            "Bangalore, India", // location
            "test@example.com", // contact info
            1); // page number
        
        // specify whether the signature is detached
        pdfSignature.setDetached(true);
        
        // Retrieve a list of all signature fields
        List fields = pdfDoc.getAllFormFieldsOnPage(0, PdfFormField.TYPE_SIGNATURE);

        PdfFormSignatureField signatureField;
        // Iterate the list and fill the unsigned signature field
        for (int i = 0; i < fields.size(); i++)
        {
            signatureField = (PdfFormSignatureField) fields.get(i);
            if (signatureField.isUnsigned())
            {
                signatureField.fill(pdfSignature);
                break;
            }
        }
        
        pdfDoc.setOpenAfterSave(true);
        pdfDoc.save(outputFileName);
        pdfDoc.close();
    }
}
