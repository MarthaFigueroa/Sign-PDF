import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class PlaceholderPredefined
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java PlaceholderPredefined <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ////////////////////////////////////////////////////////////
        // Writng text with Predefined PlaceHolders on a document //
        ////////////////////////////////////////////////////////////
        
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            PdfPage p;
    
            /* Create a font */
            PdfFont font = PdfFont.create("Arial", PdfFont.BOLD
                | PdfFont.UNDERLINE | PdfFont.ITALIC
                | PdfFont.STROKE_AND_FILL, 25, PdfEncodings.CP1252);
            font.setColor(Color.GREEN);
            
            for (int i = 1; i <= 5; i++)
            {
                /* Create a new page */
    
                p = new PdfPage(800, 900, 100, 100, 50, 50, 50, 50,
                    PdfMeasurement.MU_POINTS);
                
                /* writeText function containing Predefined PlaceHolders */
                p.writeText("This is page <% pageNo %> of <%pageCount%>" +
                        " and today's date is <%date 'dd/mm/yy'%>",
                        font, 10, 10);
                
                p.writeText("For writing custom and predefined tags inside" +
                        " writeText use ~ operator before the tags, like " +
                        "~<% pageno %>, ~<%pagecount%>, ~<%date 'dd/mm/yy'%>",
                    font, 10, 100);
                
                /* Add page to the document */
                d.add(p);
            }
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java PlaceholderPredefined" +
                    " <output file path>");
        }
    }
}
