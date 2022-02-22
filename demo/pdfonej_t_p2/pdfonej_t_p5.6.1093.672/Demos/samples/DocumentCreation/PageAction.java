import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfAction;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageMode;
import com.gnostice.pdfone.PdfPageSize;

public class PageAction
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java PageAction <ApplicationToLaunch> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        /////////////////////////////////////////
        // Creating document with page Actions //
        /////////////////////////////////////////
        
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Set page mode for the document */
            d.setPageMode(PdfPageMode.USEOUTLINES);
    
            for (int i = 1; i < 6; i++)
            {
                /* Create and add a page to the document */
                PdfPage p;
                p = new PdfPage(PdfPageSize.A3);
                p.setMeasurementUnit(PdfMeasurement.MU_INCHES);
                if (i == 1)
                {
                    /* Adds an URI action to the page */
                    p.addAction(PdfAction.PdfEvent.ON_PAGE_CLOSE,
                        PdfAction.URI, "http://www.gnostice.com");
                }
                if (i == 2)
                {
                    /* Adds a launch action to the page */
                    p.addAction(PdfAction.PdfEvent.ON_PAGE_CLOSE,
                            PdfAction.LAUNCH, args[0],
                            false);
                }
                if (i == 4)
                {
                    /* Adds a named action to the page */
                    p.addAction(PdfAction.PdfEvent.ON_PAGE_OPEN,
                        PdfAction.NAMED_LASTPAGE);
                }
                /* Adds page to the document */
                d.add(p);
            }
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java PageAction " +
                    "<ApplicationToLaunch> <output file path>");
        }
    }
}
