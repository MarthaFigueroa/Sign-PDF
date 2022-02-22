import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class SaveDocAsTIFFWithCompression
{
    public static void main(String[] args) throws PdfException,
        IOException
    {
        try
        {           
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);
            
            /*
             * saves the input PDF as multi page tiff image with the
             * specified image name and scale the image to the resolution
             */
            d.saveDocAsTiffImage(args[1], "-",
                PdfDocument.TIFF_Compression_Deflate, 1.0f, 100);
            
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("java SaveDocAsTIFFWithCompression <Input_PDF_Filepath> <Tiff File Name>"+e.getMessage());
        }
    }
}
