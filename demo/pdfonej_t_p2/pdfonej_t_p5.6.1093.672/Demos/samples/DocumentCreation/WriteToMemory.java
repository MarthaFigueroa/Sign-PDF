import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class WriteToMemory
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java WriteToMemory <output file path> */
    public static void main(String[] args) throws IOException,
    PdfException
    {
        //////////////////////////////////////////
        // Write a document's content to memory //
        //////////////////////////////////////////
        try
        {
            /* Create a ByteArrayOutputStream object */
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
            /* Create a new PdfDocument instance */
            PdfDocument document = new PdfDocument();
    
            /* Write a line of text to the document */
            document.writeText("A new file was created using PdfWriter "
                               + "with ByteArrayOutputStream.");
    
            document.setOpenAfterSave(true);
    
            /* 
             Saves the PdfDocument object to the ByteArrayOutputStream
             object 
            */
            document.save(baos);
    
            /*
             Sets the output file to be used by the ByteArrayOutputStream 
             object 
            */
            OutputStream os = new FileOutputStream(args[0]);
    
            /* Writes to the output file */
            baos.writeTo(os);
    
            /* Dispose the I/O files associated with this document object */
            document.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java WriteToMemory" +
                    " <output file path>");
        }
    }

}
