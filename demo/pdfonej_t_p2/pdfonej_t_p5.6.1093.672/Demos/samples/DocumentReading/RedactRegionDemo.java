import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfRect;
import com.gnostice.pdfone.graphics.PdfBrush;
import com.gnostice.pdfone.graphics.PdfPen;

public class RedactRegionDemo 
{
    public static void main(String[] args) throws IOException, PdfException
    {
    	try
    	{
	        PdfDocument d = new PdfDocument();
	        d.load(args[0]);
	        
	        // Specify the bounds of the region/rectangle 
	        PdfRect rect = new PdfRect(0, 200, 500, 105);
	        
	        PdfPen pen = new PdfPen();
	        pen.strokeColor = Color.black;
	        
	        PdfBrush brush = new PdfBrush();
	        brush.fillColor = Color.white;
	        
	        // Redact or remove text in the specified region/rectangle.
			// supply pageRange, boundingRect, specify whether to
			// includeIntersectingText in that region, clipRegion, pen, brush,
			// isStroke, isFill.
	        d.redactRegion("1", rect, true, true, pen, brush, true, true);
	        
	        d.setOpenAfterSave(true);
	        d.save(args[1]);
	        d.close();
    	}
    	catch(ArrayIndexOutOfBoundsException ex)
    	{
    		System.out.println("Usage : java RedactRegionDemo "
                    + "<input file path> <output file path>");
    	}
    }
}
