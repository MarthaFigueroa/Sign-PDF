import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PdfAnnot;
import com.gnostice.pdfone.PdfAppearanceStream;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfRect;
import com.gnostice.pdfone.PdfTextFormatter;
import com.gnostice.pdfone.PdfWatermarkAnnot;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;
import com.gnostice.pdfone.graphics.PdfPen;

public class WatermarkAnnotCreationDemo
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
            
            // Create a PdfFont object and set the color
            PdfFont f = PdfFont.create("Helvetica", 16,
                PdfEncodings.WINANSI);
            f.setColor(Color.MAGENTA);
           
            // Create a page
            PdfPage p = new PdfPage();
            
            //Create the appearance for the Watermark Annotation
            PdfAppearanceStream ap1 = new PdfAppearanceStream(
                new PdfRect(0, 0, 200, 30));
            PdfPen pdfPen1 = new PdfPen();
            pdfPen1.strokeColor = Color.BLACK;
            ap1.drawRect(new PdfRect(0, 0, 200, 30), pdfPen1, null);
            ap1.writeText("WatermarkAnnotation Text", f, new PdfRect(0,
                0, 200, 30), new PdfTextFormatter());
            
            PdfWatermarkAnnot waterAnnot1 = new PdfWatermarkAnnot();
            
            // Set the rectangle to specify x, y location of the watermark
            // annotation placed on the page and width and height of the
            // annotation
            waterAnnot1.setRect(new PdfRect(100, 100, 200, 30));
            
            // The appearance of the watermark annotation
            waterAnnot1.setNormalAppearance(ap1);
            
            // Set Print flag to true so that annotation will be printed
            waterAnnot1.setFlags(PdfAnnot.FLAG_PRINT);
            
            // Specify if the annotation should be considered for
            // FixedPrint option.
            // Note: A fixed print option specifies how this annotation
            // should be drawn relative to the dimensions of the target
            // media using the horizontal and vertical translation values.
            waterAnnot1.setFixedPrint(false);
            
            // This value is useful only if isFixedPrint() is true
            // The amount to translate the associated content horizontally while printing 
    //        waterAnnot1.setHorizontalTranslation(0.5);
            
            // This value is useful only if isFixedPrint() is true
            // The amount to translate the associated content vertically while printing 
    //        waterAnnot1.setVerticalTranslation(0.5);
            
            // Add the watermark annotation to the page
            p.addAnnotation(waterAnnot1);
            
            // Write a text on the page
            p.writeText("Text written on the page", 100, 100);
    //        p.drawRect(100, 100, 202, 32);
            
            // Add the page to the document
            d.add(p);
            
            d.setOpenAfterSave(true);
            d.save(args[0]);
            
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage : java WatermarkAnnotCreationDemo" +
                " <output file path>");
        }
    }

}
