import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPasswordHandler;

public final class EncryptedDocumentReading implements PdfPasswordHandler
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* This is the password event handler */
    public String onPassword(PdfDocument d, boolean[] flags)
    {
        return "owner";
    }

    /* Usage : java EncryptedDocumentReading <input file path> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        /////////////////////////////////
        //Reading an encrypted document//
        /////////////////////////////////
        
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
            
            /* Set password event handler for PdfDocument instance */
            d.setOnPasswordHandler(new EncryptedDocumentReading());

            /* Load the PDF document into the document object */
            d.load(args[0]);
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java EncryptedDocumentReading " +
                    "<input file path> <output file path>");
        }
    }
}
