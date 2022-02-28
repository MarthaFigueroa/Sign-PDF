import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class DocInfoCreation
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java DocInfoCreation <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ////////////////////////////////////////////////////////
        // Setting entries in document information dictionary //
        ////////////////////////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Set various document information properties */
            d.setTitle("PDFOne Java document properties");
            d.setAuthor("Danny Developer");
            d.setSubject("PDFOne Java document properties setting demo");
            d.setKeywords("These doc info properties can be in " +
                    "unicode charsets supported by Adobe for this window.");
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java DocInfoCreation " +
                    "<output file path> ");
        }
    }
}
