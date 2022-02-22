import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class DocInfoChanged
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java DocInfoChanged <input file path> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        // //////////////////////////////////////////////
        // Change a document's information Properties  //
        // //////////////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);

            /*
             * Set various entries in the document information
             * dictionary of the document
             */
            d.setTitle("PDFOne Java document properties Changed");
            d.setAuthor("Danny Developer Changed");
            d.setSubject("PDFOne Java document properties" +
                    " setting demo Changed");
            d.setKeywords("These doc info properties can be in "
                    + "unicode charsets supported by Adobe for" +
                            " this window  Changed.");

            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java DocInfoChanged" +
                    " <input file path> <output file path>");
        }
    }
}
