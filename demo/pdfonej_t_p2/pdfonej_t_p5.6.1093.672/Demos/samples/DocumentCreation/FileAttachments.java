import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPageMode;

public class FileAttachments
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /*
     * Usage : java FileAttachments <FileToAttach> 
     * <FileToAttach> .... <output file path> 
     */
    public static void main(String[] args) throws IOException, PdfException
    {
        ///////////////////////////////////////////////////////////
        // Attaching or Embedding External files to PDF document //
        ///////////////////////////////////////////////////////////
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
            
            // Attach the files to the document
            for (int i = 0, limit = (args.length - 1); i < limit; i++ )
            {
                d.attachDocument(args[i]);
            }
            
            // Set the PageMode so as to show the Attachments Tab
            d.setPageMode(PdfPageMode.USEATTACHMENTS);
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[args.length-1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage : java FileAttachments " +
                    "<FileToAttach> <FileToAttach> " +
                    ".... <output file path>");
        }
    }
}
