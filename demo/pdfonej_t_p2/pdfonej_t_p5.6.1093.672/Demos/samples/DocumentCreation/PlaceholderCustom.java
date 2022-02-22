import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfCustomPlaceholderHandler;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class PlaceholderCustom implements PdfCustomPlaceholderHandler
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    public String onCustomPlaceHolder(String variable, PdfDocument d,
        int pagenumber)
    {
        if(variable.equals("myCustVar1") && pagenumber == 1)
        {
            return "One";
        }
        else if(variable.equals("myCustVar1") && pagenumber == 2)
        {
            return "Two";
        }
        else if(variable.equals("myCustVar1") && pagenumber == 3)
        {
            return "Three";
        }
        else if(variable.equals("myCustVar2"))
        {
            return  d.getVersion();
        }
        return null;
    }

    /* Usage : java PlaceholderCustom <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ////////////////////////////////////////////////////////
        // Writng text with Custom PlaceHolders on a document //
        ////////////////////////////////////////////////////////

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

            for (int i = 1; i <= 3; i++)
            {
                /* Create a new page */
                p = new PdfPage(800, 900, 100, 100, 50, 50, 50, 50,
                    PdfMeasurement.MU_POINTS);
                
                /*
                 * set the CustomPlaceHoler Variable with this class
                 * Object
                 */
                p.setCph(new PlaceholderCustom());
                
                /*
                 * writeText function containing Custom
                 * PlaceHolders
                 */
                p.writeText("This is page <% myCustVar1 %> and PDFVersion" +
                        " is version <% myCustVar2 %>", font, 10, 10);

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
            System.out.println("Usage : java PlaceholderCustom"
                + " <output file path>");
        }
    }
}
