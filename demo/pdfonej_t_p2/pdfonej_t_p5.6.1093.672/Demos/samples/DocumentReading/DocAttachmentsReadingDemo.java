import java.io.IOException;
import java.util.ArrayList;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFileAttachment;

public class DocAttachmentsReadingDemo
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
            /* Create a new PdfDocument instance */
            PdfDocument pdfDoc = new PdfDocument();

            /* Load the PDF document into the document object */
            pdfDoc.load(args[0]);
            
            //get attachments list of the document
            // using PdfDocument.getAttachments();
            ArrayList docAttachmentsList = pdfDoc.getAttachments();
            
            //iterate through attachments arraylist
            //to get the attributes
            for(int i=0 ; i< docAttachmentsList.size(); i++)
            {
                //get the PdfFileattachment 
                PdfFileAttachment fileAttachment = (PdfFileAttachment) docAttachmentsList
                    .get(i);
                
                // get the name of the file attachment with which it is
                // saved
                String attachmentName = fileAttachment
                    .getFileAttachmentName();
                System.out
                    .println("FileAttachmentName:" + attachmentName);

                //get the byte[] stream of the file attachment
                byte[] bs = fileAttachment.getByteStream();

                System.out.println("File Stream length:" + bs.length);            
            }
            //dispose the document
            pdfDoc.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out
            .println("Usage : java DocAttachmentsReadingDemo "
                + "<input file path>");
        }
        
    }
}
