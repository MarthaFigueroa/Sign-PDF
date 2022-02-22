import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class GetPageAsBufferedImageDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    
    public void savePageAsBufferedImage(String[] args) throws IOException, PdfException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);
            
            BufferedImage pageAsImage = null;
            
            try
            {
                pageAsImage = d.getPageAsBufferedImage(1);
                ImageIO.write(pageAsImage, "png", new File(args[1]));
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.getMessage());
            }
            
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java GetPageAsBufferedImageDemo "
                + "<input file path> <output image file path>");
        }
    }

    public static void main(String[] args) throws IOException, PdfException
    {
        GetPageAsBufferedImageDemo demo = new GetPageAsBufferedImageDemo();
        demo.savePageAsBufferedImage(args);
    }
}
