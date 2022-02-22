import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPreferences;

public class ViewerPreference
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java ViewerPreference <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        /////////////////////////////////////////
        // Setting document viewer preferences //
        /////////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Set various viewer preferences for the document */
            int prefences = PdfPreferences.HIDE_TOOLBAR
                | PdfPreferences.HIDE_MENUBAR
                | PdfPreferences.HIDE_WINDOWUI
                | PdfPreferences.CENTER_WINDOW
                | PdfPreferences.DISPLAY_DOC_TITLE
                | PdfPreferences.NonFullScreenPageMode.OUTLINES;
            d.setViewerPreferences(prefences);
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ViewerPreference" +
                    " <output file path>");
        }
    }
}
