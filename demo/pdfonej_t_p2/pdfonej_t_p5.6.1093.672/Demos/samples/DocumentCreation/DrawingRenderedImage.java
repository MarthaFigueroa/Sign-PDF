import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfImage;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageSize;

public class DrawingRenderedImage
{

    /*
     * Usage : java DrawingRenderedImage <OutPutFilepath> <image file
     * to draw on the document>
     */
    public static void main(String[] args) throws PdfException,
        IOException
    {
        try
        {
            // Create a new PdfDocument instance
            PdfDocument pdfDocument = new PdfDocument();

            // RenderedImage object to be drawn on the pdfdocument
            RenderedImage image = ImageIO.read(new File(args[1]));// image filepath

            // create PdfImage of RenderedImage object
            PdfImage pdfImage1 = PdfImage.create(image);

            // x and y position to be drawn on the default page i.e
            // 1st page of the document
            double xPos = 0;
            double yPos = 0;

            // to draw the pdf image on the page at the specified
            // co-ordinates
            pdfDocument.drawImage(pdfImage1, xPos, yPos);

            // create a new PdfPage object
            PdfPage pdfPage2 = new PdfPage(PdfPageSize.A4, 0, 0, 20,
                20, 20, 20, PdfMeasurement.MU_POINTS);

            // scales the image dimensions proportionally so as to fit
            boolean scaleToFit = true;

            // stretch the image dimensions to fit in the available
            // width and height on the page
            boolean stretchToFit = false;

            // paeg level draw image method to draw image on the
            // specified page
            pdfPage2.drawImage(pdfImage1, xPos, yPos, scaleToFit,
                stretchToFit);

            // adds the page the document's page list
            pdfDocument.add(pdfPage2);

            // prompts the Pdfreader to open the pdf document after it
            // is saved
            pdfDocument.setOpenAfterSave(true);

            // completes writing of the contents on to the document
            pdfDocument.save(args[0]);

            // Dispose the I/O files associated with this document object 
            pdfDocument.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out
                .println("Usage : java DrawingRenderedImage "
                    + "<OutPutFilepath> <image file to draw on the document> ");
        }

    }

}
