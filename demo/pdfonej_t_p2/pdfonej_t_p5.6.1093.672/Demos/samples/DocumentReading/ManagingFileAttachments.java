import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPageMode;

public class ManagingFileAttachments
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /*
     * Usage : java FileAttachments <input file path> <output file path> 
     * <FileToAttach> <FileToAttach> ... 
     */
    public static void main(String[] args) 
        throws IOException, PdfException
    {
        ///////////////////////////////
        // Managing File Attachments //
        ///////////////////////////////
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);
            
            // Prepare a list of file names to be given to the attachment
            // For Demonstration purpose let us assume that there 2 files and
            // the first file is a text file and the second file is a JPEG image
            ArrayList fileNameList = new ArrayList();
            fileNameList.add("file01.txt");
            fileNameList.add("file02.jpg");

            String outputPath = "C:\\";
            
            ArrayList al = d.getAttachments();
            for (int i = 0, limit = al.size(); i < limit; i++)
            {
                // Since the attachments are read as 
                // ByteBuffer let us get each attachment in a
                // ByteBuffer Object
                ByteBuffer bb = (ByteBuffer) al.get(i);
                
                // Create an OutputStream with a file name
                FileOutputStream fos = new FileOutputStream(outputPath + fileNameList.get(i));
                
                for (int j = 0, bufLimit = bb.capacity(); j < bufLimit; j++)
                {
                    fos.write(bb.get(j));
                    fos.flush();
                }
                fos.close();
            }
            
            // Attach additional files to the document
            if (args.length > 2)
            {
                for (int i = 2, limit = args.length; i < limit; i++ )
                {
                    d.attachDocument(args[i]);
                }
            }
            
            // Set the PageMode so as to show the Attachments Tab
            d.setPageMode(PdfPageMode.USEATTACHMENTS);
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage : java ManagingFileAttachments " +
                    "<input file path> <output file path> " +
                    "<FileToAttach> <FileToAttach> ...");
        }
    }
}
