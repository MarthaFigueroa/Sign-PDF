import java.io.File;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class ImportFromFdf
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /*
     * Usage : java ImportFromFdf <input file path> <output file path>
     * <input Fdf file path>
     */
    public static void main(String[] args) throws IOException,
    PdfException
    {
        /////////////////////////////////////
        //  ImportFromFdf to PDF document  //
        /////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);
            
            /*
             * For importing the name and value of the formfields present
             * in the Fdf file to the document
             */
            d.importFromFDF(new File(args[2]));    
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ImportFromFdf " +
                    "<input file path> <output file path>  " +
                    "<input Fdf file path>");
        }
    }
}
