import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfRect;


public class AddSignatureDemo
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
            
            // Add a page to the document
            d.add(new PdfPage());
            
            // Add a Signature on page number 1.
            // Supply .pfx file name, password for .pdf file, Reason,
            // Location, ContactInfo, Page number, name of the
            // Signature Form Field, Bounding Rect for Signature Form
            // Field. 
            d.addSignature(
                args[0],
                args[1],
                "I am approving this document",
                "India",
                "email@email.com",
                1,
                "SigFld1",
                new PdfRect(10, 10, 100, 100));
            
            d.setOpenAfterSave(true);
            d.save(args[2]);
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage : java SignDocumentDemo "
                    + "<input .pfx file path> pwd_for_pfx_file <output file path>");
        }
    }
}
