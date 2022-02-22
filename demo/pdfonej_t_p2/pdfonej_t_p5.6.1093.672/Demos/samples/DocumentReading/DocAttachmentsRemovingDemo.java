import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class DocAttachmentsRemovingDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    public static void main(String[] args) throws IOException, PdfException 
    {
        try
        {
            removeAllAttachments(args[0]);   
            
            removeAttachmentsByName(args[1], args[2]);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out
            .println("Usage : java DocAttachmentsRemovingDemo "
                + "<input file path1> <input file path2> <attachmentName>");
        }
    }
    
    public static void removeAllAttachments(String pdfFileName)
        throws IOException, PdfException
    {
        /* Create a new PdfDocument instance */
        PdfDocument pdfDoc = new PdfDocument();

        /* Load the PDF document into the document object */
        pdfDoc.load(pdfFileName);
        
        //remove all the file attachments added to the document
        //using PdfDocument.removeAllAttachments()
        pdfDoc.removeAllAttachments();
        
        pdfDoc.close();       
    }
    
    public static void removeAttachmentsByName(String pdfFileName, String fileAttachmentName)
        throws IOException, PdfException
    {
        /* Create a new PdfDocument instance */
        PdfDocument pdfDoc = new PdfDocument();

        /* Load the PDF document into the document object */
        pdfDoc.load(pdfFileName);

        //remove all the file attachments added to the document
        //using PdfDocument.removeAllAttachments()
        pdfDoc.removeAllAttachments(fileAttachmentName);

        pdfDoc.close();   
    }

}
