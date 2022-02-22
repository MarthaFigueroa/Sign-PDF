
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfAction;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageMode;
import com.gnostice.pdfone.PdfPageSize;

public class DocActions
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java DocActions <FileToLaunch> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ////////////////////////////////////
        // Setting document-level actions //
        ////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /*
             Sets page mode to hide Bookmarks, Thumbnails,
             Attachments and other tabs
            */
            d.setPageMode(PdfPageMode.USENONE);
            d.add(new PdfPage(PdfPageSize.A3));
    
            /* 
             Add action to document so that it launches the file
             specified by the command-line argument
            */
            d.addAction(PdfAction.LAUNCH, args[1], false, null);
    
            /* 
             Add action to document so that it opens the 
             Gnostice website URL
            */
            d.addAction(PdfAction.URI, "http://www.gnostice.com");
    
            /*
             Only JAVASCRIPT actions can be added in document
             event triggers. Examples of document event triggers are 
             DOCUMENT_CLOSE and DOCUMENT_BEFORE_PRINT.
            */
            d.addAction(PdfAction.PdfEvent.ON_DOCUMENT_CLOSE,
                PdfAction.JAVASCRIPT,
                "app.alert('Gnostice PDFOne... (Doc Close Action)')");
    
            d.addAction(PdfAction.PdfEvent.ON_BEFORE_DOCUMENT_PRINT,
                PdfAction.JAVASCRIPT,
                "app.alert('Gnostice PDFOne...(Before Print)')");
            
            PdfPage p = null;
            for(int i=1; i<5; i++)
            {
                p = new PdfPage();
                d.add(p);
            }
            
            /* add action GO_TO so that specified page will be opened */
            d.addAction(PdfAction.GOTO, 5);
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java DocActions " +
                    "<FileToLaunch> <output file path> ");
        }
    }
}
