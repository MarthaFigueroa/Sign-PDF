import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;

public class MemoryReaderDemo
{
    static {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
        "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
        
    }
    public static void main(String[] args) throws PdfException,
        IOException 
    {
        try
        {
            /* create a file input stream object */
            FileInputStream fis = new FileInputStream(new File(
                args[0]));
            /*create a byte array of fis stream size*/
            byte[] byteArray = new byte[fis.available()];
            /*read the bytes of file inputstream to byte array*/
            fis.read(byteArray);
			//close the input stream
            fis.close();
            
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the byteArray of the PDF document into the document object */
            d.load(byteArray);
            
            /* get the first page of the read document and modify it */
            PdfPage p = d.getPage(1);
            
            //write text on the page
            p.writeText("This text is a sample text for the document " +
                    "created through memory stream.");
            //set the property open after save
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (Exception e)
        {
            System.out
            .println("Usage : java MemoryReaderDemo "
                + "<input file path> <output file path>");
        }   
    }
}
