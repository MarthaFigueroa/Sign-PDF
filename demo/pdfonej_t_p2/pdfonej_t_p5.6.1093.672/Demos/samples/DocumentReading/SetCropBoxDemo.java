import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;

public class SetCropBoxDemo
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
            d.load(args[0]);
            
            PdfPage p = d.getPage(1);
            
            p.writeText("Sample Text", 100, 100);
            p.drawRect(100, 100, p.getWidth() - 200, p.getHeight() - 200);
            
            // Specify cropLeft, cropTop, cropRight, cropBottom 
            p.setCropBox(100, 100, 100, 100);
            
            // Crop values can be specified as a Rectangle as well
            // Specify cropLeft, cropTop and cropWidth and cropHeight
//            p.setCropBox(new PdfRect(100, 100, p.getWidth() - 100, p
//                .getHeight() - 100));
            
            // CropBox can be set for more than one page by specifying the
            // pagerange using the setCropBox method of PdfDocument object
//            d.setCropBox(new PdfRect(100, 100, p.getWidth() - 100, p
//                .getHeight() - 100), "-");
            
            d.setOpenAfterSave(true);
            d.save(args[1]);
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java SetCropBoxDemo " +
                    "<input file path> <output file path>");
        }
    }
}
