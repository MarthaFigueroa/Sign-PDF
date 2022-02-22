import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;

public class ExtractPages
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java ExtractPages <ExtractToFilepath> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        //////////////////////////////////////////////
        // Extract pages from current document and  //
        // append them to another document          //
        //////////////////////////////////////////////
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Add some pages to the document */
            PdfPage p;
            for (int i = 0; i < 5; i++)
            {
                p = new PdfPage();
                d.add(p);
                p.writeText("This is Page:" + (i+1));
            }
    
            /*
             Extract pages 2 to 4 from current document and 
             them to file specified in command-line argument
            */
            d.extractPagesTo(args[0], "2-4");
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ExtractPages " +
                    "<ExtractToFilepath> <output file path>");
        }
    }
}
