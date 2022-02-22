import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class SaveAsImageWithResolution
{
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
             * pdfDocument saveAsImage to save a page with the
             * parameters specified like format , page range,
             * imageName, inputfilepath, DPI resolution
             */
            document.saveAsImage("PNG",// OutputImage format
                "-", // page range
                "myimage1_20", // image name
                /*
                 * <% inputfilepath %> is a predefined
                 * placeholder to get the path of the loaded input PDF
                 * file
                 */
                "<% inputfilepath %>", // directory path where output image should be stored
                96 // resoultion of the image
                );

            /* Dispose the I/O files associated with this document object */
            document.close();
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out
                .println("Usage : java SaveAsImageWithResolution "
                    + "<input file path> ");
        }
    }
}
