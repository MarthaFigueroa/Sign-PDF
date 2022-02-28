import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

/*
 * FlattenFormFieldsSampleDemo demonstrates flattening
 * of the form fields with the existing values of the formfields
 */
public class FlattenFormFieldsDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument document = new PdfDocument();

            /* Load the PDF document into the document object */
            document.load(args[0]);

            /*
             * flattens all the form fields of the document with the
             * existing values
             */
            document.flattenFormFields(false);
            
            /*set the document to open after save*/
            document.setOpenAfterSave(true);
            
            /* Save the document object */ 
            document.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            document.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage : java FlattenFormFieldsDemo " +
            "<input file path> <output file path>");
        }
    }
}
