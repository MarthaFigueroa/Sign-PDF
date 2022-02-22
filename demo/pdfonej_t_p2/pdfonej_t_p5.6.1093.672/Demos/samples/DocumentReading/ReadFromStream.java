import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class ReadFromStream
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java ReadFromStream <input file path> <output file path> */
    public static void main(String[] args) throws IOException,
    PdfException 
    {
        //////////////////////////////////////////////
        // Create a document from a FileInputStream //
        //////////////////////////////////////////////

        try
        {
            // Creates a FileInputStream object for the specified 
            // in the command-line argument
            FileInputStream fis = new FileInputStream(args[0]);
    
            // Creates an OutputStream object for the output file
            OutputStream os = new FileOutputStream(args[1]);
    
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the FileInputStream of the PDF document into the document object */
            d.load(fis);
    
            /* Writes a line of text for the output file */
            d.writeText("This text goes in to the Output "
                               + " ReadStream.pdf");
    
            d.setOpenAfterSave(true);
    
            /* Save the document object to the OutputStream */ 
            d.save(os);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ReadFromStream " +
                    "<input file path> <output file path>");
        }
    }

}
