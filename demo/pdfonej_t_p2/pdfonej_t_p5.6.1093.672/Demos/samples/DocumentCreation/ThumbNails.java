import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageMode;
import com.gnostice.pdfone.PdfPageSize;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class ThumbNails
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java ThumbNails <image1> <image2> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        /////////////////////////////////////
        // Setting PageMode for a document //
        /////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Set page mode for the document */
            d.setPageMode(PdfPageMode.USETHUMBS);
    
            /* Create a font and set its color */
            PdfFont font = PdfFont.create("Helvetica", 20, PdfEncodings.WINANSI);
            font.setColor(Color.BLUE);
    
            /* Create a PdfPage instance */
            PdfPage pageOne = new PdfPage(PdfPageSize.A4);
    
            /* Set measurement unit for the page 1 */
            pageOne.setMeasurementUnit(PdfMeasurement.MU_INCHES);
    
            /* Add thumbnail to page 1 with pathname of the first image */
            pageOne.addThumbnailImage(args[0]);
            pageOne.writeText("Adding Thumbnail Demo.", font, 2.5, 4);
    
            PdfPage pageTwo = new PdfPage(PdfPageSize.A4);
            /*  Set measurement unit for page 2 */
            pageTwo.setMeasurementUnit(PdfMeasurement.MU_INCHES);
    
            /* Add thumb nail to page 2 with the pathname of the second image */
            pageTwo.addThumbnailImage(args[1]);
            pageTwo.writeText("Adding Thumbnail Demo.", font, 2.5, 4);
    
            /* Add the pages to the document. */
            d.add(pageOne);
            d.add(pageTwo);
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[2]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ThumbNails" +
                    " <image1> <image2> <output file path>");
        }
    }
}
