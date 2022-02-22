import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class HeaderFooter
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java HeaderFooter <image1> <image2> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        //////////////////////////////////////////////
        // Create document with a header and footer //
        //////////////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            PdfPage p ;
    
            for (int i = 0; i < 5; i++)
            {
                /*
                 Create a page specifying page width, page height, page header,
                 footer height, left margin, top margin, right margin, bottom
                 margin expressed in a particular measurement unit.
                 */
                p = new PdfPage(800, 900, 100, 100, 50, 50, 50, 50,
                    PdfMeasurement.MU_POINTS);
    
                /* Add images to header of the page */
                p.addHeaderImage(args[0], PdfPage.HP_LEFT
                    | PdfPage.VP_TOP, true);
                p.addHeaderImage(args[0], PdfPage.HP_RIGHT
                    | PdfPage.VP_BOTTOM, true);
                p.addHeaderImage(args[1], PdfPage.HP_MIDDLE
                    | PdfPage.VP_CENTRE, true);
    
                /* Add images to footer of the page */
                p.addFooterImage(args[0], PdfPage.HP_RIGHT
                    | PdfPage.VP_TOP, true);
                p.addFooterImage(args[0], PdfPage.HP_LEFT
                    | PdfPage.VP_BOTTOM, true);
                p.addFooterImage(args[1], PdfPage.HP_MIDDLE
                    | PdfPage.VP_CENTRE, true);
    
                /* Add text to header of the page */
                p.addHeaderText("Header Center Center", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_MIDDLE
                    | PdfPage.VP_CENTRE, false);
                p.addHeaderText("Header Center Top", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_MIDDLE
                    | PdfPage.VP_TOP, false);
                p.addHeaderText("Header Center Bottom", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_MIDDLE
                    | PdfPage.VP_BOTTOM, false);
                p.addHeaderText("Header Left Center", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_LEFT
                    | PdfPage.VP_CENTRE, false);
                p.addHeaderText("Header Left Top", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_LEFT
                    | PdfPage.VP_TOP, false);
                p.addHeaderText("Header Left Bottom", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_LEFT
                    | PdfPage.VP_BOTTOM, false);
                p.addHeaderText("Header Right Top", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_RIGHT
                    | PdfPage.VP_CENTRE, false);
                p.addHeaderText("Header Left Top", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_RIGHT
                    | PdfPage.VP_TOP, false);
                p.addHeaderText("Header Right Bottom", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_RIGHT
                    | PdfPage.VP_BOTTOM, false);
    
              /* Add text to footer of the page */
                p.addFooterText("Footer Center Center", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_MIDDLE
                    | PdfPage.VP_CENTRE, false);
                p.addFooterText("Footer Center Top", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_MIDDLE
                    | PdfPage.VP_TOP, false);
                p.addFooterText("Footer Center Bottom", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_MIDDLE
                    | PdfPage.VP_BOTTOM, false);
                p.addFooterText("Footer Left Center", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_LEFT
                    | PdfPage.VP_CENTRE, false);
                p.addFooterText("Footer Left Top", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_LEFT
                    | PdfPage.VP_TOP, false);
                p.addFooterText("Footer Left Bottom", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_LEFT
                    | PdfPage.VP_BOTTOM, false);
                p.addFooterText("Footer Right Top", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_RIGHT
                    | PdfPage.VP_CENTRE, false);
                p.addFooterText("Footer Left Top", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_RIGHT
                    | PdfPage.VP_TOP, false);
                p.addFooterText("Footer Right Bottom", PdfFont.create(
                    "Arial", 15, PdfEncodings.CP1252), PdfPage.HP_RIGHT
                    | PdfPage.VP_BOTTOM, false);
    
              /* Add the page to document */
              d.add(p);
            }
            
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[2]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java HeaderFooter " +
                    "<image1> <image2> <output file path>");
        }
    }
}
