import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;

public class StitchPages
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java StitchPages <input file path> <output file path> */
    public static void main(String[] args) throws IOException,
    PdfException
    {
        //////////////////////////////
        //  Stitching of PDF Pages  //
        //////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Load the PDF document into the document object */
            d.load(args[0]);
            
            /*
             * Stitch one page of the document to an another page of the
             * document. To stitch the document one above another, set X
             * and Y offset to 0.
             */
            d.stitch(2, 1, 0, 0); 
            
            /*
             * To stitch the document one after another or one besides
             * another, set the X and Y offset accordingly.
             */
            PdfPage p = d.getPage(3);
            d.stitch(3, 1, p.getWidth(), 0);        
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java StitchPages " +
                    "<input file path> <output file path>");
        }
    }
}
