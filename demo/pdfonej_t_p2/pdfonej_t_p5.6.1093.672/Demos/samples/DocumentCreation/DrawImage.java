import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfImage;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPage;

public class DrawImage
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java DrawImage <image1> <image2> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        /////////////////////////////////
        // Drawing images to a dcument //
        /////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            PdfPage p;
    
            /* 
             Create two PdfImage object from the pathnames specified 
             by the command-line arguments 
            */
            PdfImage jpgImg = PdfImage.create(args[0]);
            PdfImage bmpImg = PdfImage.create(args[1]);
    
            /* Set the width and height of image */
            jpgImg.setHeight(1);
            jpgImg.setWidth(4);
    
            bmpImg.setHeight(1);
            bmpImg.setWidth(4);
    
            for (int i = 1; i <= 2; ++i)
            {
                /* Create a new page */
                p = new PdfPage();
                p.setMeasurementUnit(PdfMeasurement.MU_INCHES);
    
                /* Draw the image on the page */
                p.drawImage(jpgImg, 1, 2);
                p.drawImage(bmpImg, 1, 5);
    
                /* Add the page to the document */
                d.add(p);
            }
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[2]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java DrawImage " +
                    "<image1> <image2> <output file path>");
        }
    }
}
