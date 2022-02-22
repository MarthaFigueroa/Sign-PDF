import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.gnostice.pdfone.PdfAnnot;
import com.gnostice.pdfone.PdfAppearanceStream;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfRect;
import com.gnostice.pdfone.PdfTextFormatter;
import com.gnostice.pdfone.PdfWatermarkAnnot;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;
import com.gnostice.pdfone.graphics.PdfPen;

public class WatermarkAnnotReadingDemo
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
            d.load(args[0]);
            
            // Create a PdfFont object and set the color
            PdfFont f = PdfFont.create("Times New Roman", 13,
                PdfEncodings.WINANSI);
            f.setColor(Color.BLUE);
            
            // Create a new appearance for the Watermark Annotation
            PdfAppearanceStream ap1 = new PdfAppearanceStream(
                new PdfRect(0, 0, 200, 30));
            PdfPen pdfPen1 = new PdfPen();
            pdfPen1.strokeColor = Color.BLACK;
            ap1.drawRect(new PdfRect(0, 0, 200, 30), pdfPen1, null);
            ap1.writeText("Modified WatermarkAnnotation Text", f,
                new PdfRect(0, 0, 200, 30), new PdfTextFormatter());
            
            // Get All the annotations of type Watermark Annotation
            List annotsList = d.getAllAnnotationsOnPage(1,
                PdfAnnot.ANNOT_TYPE_WATERMARK);
            
            for (Iterator iter = annotsList.iterator(); iter.hasNext();)
            {
                PdfWatermarkAnnot watermarkAnnot = (PdfWatermarkAnnot) iter.next();
                
                // Identify the location of the watermark annotation added to the page
                if (watermarkAnnot.getRect().getX() == 100 
                    && watermarkAnnot.getRect().getY() == 100)
                {
                    // Replace the existing appearance with the new appearance
                    watermarkAnnot.setNormalAppearance(ap1);
                }
            }
            
            d.setOpenAfterSave(true);
            d.save(args[1]);
            
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage : java WatermarkAnnotReadingDemo" +
                "<input file path> <output file path>");
        }
    }

}
