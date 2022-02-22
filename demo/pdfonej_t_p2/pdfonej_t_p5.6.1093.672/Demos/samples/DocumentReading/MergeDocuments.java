import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class MergeDocuments
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java MergeDocuments <inputfile1> <inputfile2> .. <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        //////////////////////////////
        // Merging of PDF Documents //
        //////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);
    
            List lis = new ArrayList();
            for (int i = 1; i < args.length - 1 ; i++)
            {
              lis.add(args[i]);
            }
            
            /* Merge documents in the list */
            d.merge(lis);
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[args.length - 1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java MergeDocuments " +
                    "<inputfile1> <inputfile2> .. <output file path>");
        }
    }
}
