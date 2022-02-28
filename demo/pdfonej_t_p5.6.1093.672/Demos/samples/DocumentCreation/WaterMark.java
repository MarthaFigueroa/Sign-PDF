import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfImage;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageSize;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class WaterMark
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java WaterMark <image1> <image2> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ////////////////////////////////////
        // Create document with watermark //
        ////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Create a font */
            PdfFont font = PdfFont.create("Helvetica", 20, PdfEncodings.WINANSI);
            font.setColor(Color.BLUE);
    
            /* Create a page and add it to the document */
            PdfPage pageOne = new PdfPage(PdfPageSize.A3);
            d.add(pageOne);
    
            /* Create a PdfImage object from the first command-line argument */
            PdfImage image1 = PdfImage.create(args[0]);
    
            /*
             Add watermark text to the top and middle part of the first
             page of the document
            */
            d.addWatermarkText("PDFOne WaterMarkText", font, PdfPage.HP_MIDDLE
                | PdfPage.VP_TOP, true, 0, true, "1");
    
            /*
             Add watermark image to the top and middle part of the first
             page of the document
            */
            d.addWatermarkImage(image1, PdfPage.HP_MIDDLE
                | PdfPage.VP_TOP, false, 0, true, "1");
    
            /* Create a PdfImage object from the second command-line argument */
            PdfImage image2 = PdfImage.create(args[1]);
    
            /*
             Add watermark text to the middle and center part of the first
             page in the document
            */
            d.addWatermarkText("PDFOne WaterMarkText", font, PdfPage.HP_MIDDLE
                | PdfPage.VP_CENTRE, true, 0, true, "1");
    
            /*
             Add watermark image to the middle and center part of the first 
             page in the document 
            */
            d.addWatermarkImage(image2, PdfPage.HP_MIDDLE
                | PdfPage.VP_CENTRE, false, 0, true, "1");
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[2]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java WaterMark <image1>" +
                    " <image2> <output file path>");
        }
    }
}
