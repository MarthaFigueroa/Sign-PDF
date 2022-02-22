import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageSize;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public final class SinglePageMultiDoc
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java SinglePageMultiDoc <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        //////////////////////////////////////////
        // Adding a single PdfPage instance to  //
        // multiple PdfDocument instances       //
        //////////////////////////////////////////

        try
        {
            /* Create a font and set its color */
            PdfFont arialFont = PdfFont.create("Arial",
                PdfFont.BOLD, 25, PdfEncodings.CP1252);
            arialFont.setColor(Color.GREEN);
    
            /* Create a new PdfDocument instance for the first document */
            PdfDocument firstDoc = new PdfDocument();
    
            /* Create a new PdfDocument instance for the second document*/
            PdfDocument secondDoc = new PdfDocument();
    
            /* 
             Create a PdfPage instance that is to be added to 
             both PdfDocument instances 
            */
            PdfPage masterPage = new PdfPage(PdfPageSize.A4);
    
            /* 
             Create coordinates
            */
            double x = 72 * 1.5; // equals 1.5 inches, as 1 inch = 72 points
            double y = 36;       // 0.5 inches
    
            /* Write text on the page at above coordinates */
            masterPage.writeText("This is first line of text.",
                arialFont, x, y);
            /* Add page to the first PdfDocument instance */
            firstDoc.add(masterPage);
    
            /* Write firstDoc and dispose firstWriter */
            firstDoc.setOpenAfterSave(true);
            /* Save the document object */ 
            firstDoc.save(args[0]);
            /* Dispose the I/O files associated with this document object */
            firstDoc.close();
    
            arialFont.setStyle(PdfFont.STROKE);
            arialFont.setStrokeColor(Color.ORANGE);
            arialFont.setStrokeWidth(1);
            /* Write more text to the page and add it to secondDoc */
            masterPage.writeText("This is another line of text.",
                arialFont, x, y * 2);
            secondDoc.add(masterPage);
    
            /* Write secondDoc and dispose secondWriter */
            secondDoc.setOpenAfterSave(true);
            /* Save the document object */ 
            secondDoc.save(args[1]);
            /* Dispose the I/O files associated with this document object */
            secondDoc.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java SinglePageMultiDoc" +
                    "<output file path 1> <output file path 2>");
        }
    }
}
