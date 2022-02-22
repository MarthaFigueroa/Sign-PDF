import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;

public class AppendPages
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
        
    /* Usage : java AppendPages <FilepathToExtract> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ///////////////////////////////////////////////////////////////////
        // Create a document and append pages extracted from another PDF //
        ///////////////////////////////////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Add new pages to the document */
            PdfPage p;
            for (int i = 0; i < 3; i++)
            {
                p = new PdfPage();
                d.add(p);
            }
    
            /*
              Extract pages 1 to 3 from the file specified in the
              command-line argument and append them to the document
            */
            d.appendPagesFrom(args[0], "1-3");
            
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java AppendPages " +
                    "<FilepathToExtract> <output file path> ");
        }
    }
}
