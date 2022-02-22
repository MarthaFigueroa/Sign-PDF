import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFormSignatureField;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfRect;


public class AddBlankSignatureFieldDemo
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
            
            // Add a page to the document
            d.add(new PdfPage());
            
            // Create a blank unsigned Signature Form field on page number 1.
            PdfFormSignatureField signatureField = new PdfFormSignatureField(new PdfRect(10, 10, 100, 20));
            signatureField.setName("SigFld1");
            
            // Add the blank Signature form field to page 1
            d.addFormField(signatureField, 1);
            
            d.setOpenAfterSave(true);
            d.save(args[0]);
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage : java AddBlankSignatureFieldDemo "
                    + "<output file path>");
        }
    }
}
