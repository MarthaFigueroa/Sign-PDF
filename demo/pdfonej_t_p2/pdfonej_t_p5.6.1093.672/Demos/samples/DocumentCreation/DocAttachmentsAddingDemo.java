import java.io.FileInputStream;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfFileAttachment;
import com.gnostice.pdfone.PdfPageMode;

public class DocAttachmentsAddingDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    public static void main(String[] args) 
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument pdfDoc = new PdfDocument();
            
            // attach document using pdfDocument.attachDocument(String
            // documentAttachmentPath)
            pdfDoc.attachDocument(args[1]);
            
            // attach document using
            // pdfDocument.attachDocument(PdfFileAttachment
            // pdfFileAttachment)
            
            PdfFileAttachment fileAttachment = new PdfFileAttachment(
                args[2]);
            pdfDoc.attachDocument(fileAttachment);
            
            // attach document using
            // pdfDocument.attachDocument(String attachmentName, byte[]
            // bs)
            FileInputStream fileInputStream = new FileInputStream(args[3]);
            
            byte[] bs = new byte[fileInputStream.available()];
            fileInputStream.read(bs, 0, bs.length);
            fileInputStream.close();
            pdfDoc.attachDocument(args[4], bs);
            
            pdfDoc.setPageMode(PdfPageMode.USEATTACHMENTS);
            
            pdfDoc.setOpenAfterSave(true);
            
            /* Save the document object */ 
            pdfDoc.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            pdfDoc.close();
        }
        catch (Exception e)
        {
            System.out
                .println("Usage : java DocAttachmentsAddingDemo "
                    + "<Output file path> <attachemnt file path1>"
                    + "<attachemnt file path2> "
                    +"<attachemnt file path3> "
                    +"<name of the attachemnt file path3>");
        }
    }
}
