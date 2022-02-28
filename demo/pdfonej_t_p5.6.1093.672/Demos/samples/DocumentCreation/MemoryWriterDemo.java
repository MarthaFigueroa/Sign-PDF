import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class MemoryWriterDemo
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
//          create a new PdfFile
            File f = new File(args[0]);        

            //fileoutput stream object of new file
            FileOutputStream faos =  new FileOutputStream(f);

            // new ByteArrayOutputStream object to which data should be
            // written
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
            
            //create a new pdf page
            PdfPage p1 = new PdfPage();

            //set the page's font object
            p1.setFont(PdfFont.create("TimesNewRoman", PdfFont.ITALIC
                | PdfFont.BOLD, 20, PdfEncodings.CP1252));
            
            //write text to the document
            p1.writeText("This text is written to demonstrate creation of " +
                    "Pdf document Using memory stream", 200, 100, true);

            //add page to the document
            d.add(p1);
            // set the open after save parameter to true to open the
            // document after save
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(baos);

            // write the pdfdocument byte array stream to fileoutput
            // stream
            baos.writeTo(faos);
            //flush the data
            faos.flush();
            //close the file stream
            faos.close();
            
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (Exception e)
        {
            
            System.out
            .println("Usage : java DocumentWithBookMarksDemo "
                + "<input file path1> ");
        }
        
    }

}
