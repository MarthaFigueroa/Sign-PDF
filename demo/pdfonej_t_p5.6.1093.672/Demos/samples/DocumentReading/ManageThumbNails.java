import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPageMode;

public class ManageThumbNails
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java ManageThumbNails <input file path> <output file path> */
    /* Supply PDF file with ThumbNails as "input file path" */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ///////////////////////////////////////////
        // Open a documnet and delete thumbnails //
        // from its document outline             //
        ///////////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Load the PDF document into the document object */
            d.load(args[0]);
    
            /* Set the page mode for the document */
            d.setPageMode(PdfPageMode.USETHUMBS);
    
            /* Remove all thumbnail images from the document outline */
            d.removeThumbnailImage("1-"
                + Integer.toString(d.getPageCount()));
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ManageThumbNails" +
                    " <input file path> <output file path>");
        }
    }
}
