import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageLayout;
import com.gnostice.pdfone.PdfPageSize;

public class PageLayout
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java PageLayout <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        // ////////////////////////////////
        // Setting document page layout //
        // ////////////////////////////////
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Create and add some pages to the document */
            PdfPage p = new PdfPage(PdfPageSize.A3);
            d.add(p);
            p = new PdfPage(PdfPageSize.A3);
            d.add(p);
            p = new PdfPage(PdfPageSize.A3);
            d.add(p);
    
            /* Set page layout for the document */
            d.setPageLayout(PdfPageLayout.TWO_COLUMN_LEFT);
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java PageLayout" +
                    " <output file path>");
        }
    }
}
