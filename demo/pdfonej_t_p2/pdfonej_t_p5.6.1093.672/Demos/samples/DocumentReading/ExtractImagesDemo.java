import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPageElement;
import com.gnostice.pdfone.PdfPageImageElement;
import com.gnostice.pdfone.PdfRenderErrorHandler;

public class ExtractImagesDemo implements PdfRenderErrorHandler
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
    	try
    	{
	    	String dirPath = "D:\\Temp\\";
	    	
	        PdfDocument d = new PdfDocument();
	
	        d.load(args[0]);
	        
	        d.setRenderErrorHandler(new ExtractImagesDemo());
	
	        // include Image and Inline Image as the required element types to be extracted
	        int elementTypes = PdfPageElement.ELEMENT_TYPE_IMAGE | PdfPageElement.ELEMENT_TYPE_INLINE_IMAGE;
	        
	        // get the specified element types from all the pages
	        List elementsList = d.getPageElements("-", elementTypes);
	        
	        if (!elementsList.isEmpty())
	        {
	            File outputDir = new File(dirPath + d.getInputFileName().replace(".pdf", ""));
	            boolean isOutputDirExist = outputDir.mkdirs();
	            
	            PdfPageImageElement imageElement = null;
	            BufferedImage img = null;
	            for(int i = 0; i < elementsList.size(); i++)
	            {
	                imageElement = (PdfPageImageElement) elementsList.get(i);
	                System.out.println(imageElement);
	                
	                // Get BufferedImage object from the image element
	                img = imageElement.getImage();
	                
	                if (isOutputDirExist)
	                {
	                    ImageIO.write(img, "png", new File(outputDir,
	                        "Image" + String.valueOf(i + 1) + ".png"));
	                }
	                else
	                {
	                	// Save the image in the current directory
	                    ImageIO.write(img, "png", new File(
	                        "Image" + String.valueOf(i + 1)
	                            + ".png"));
	                }
	            }
	        }
	        else
	        {
	            System.out.println("No elements found");
	        }
	        
	        d.close();
    	}
    	catch(ArrayIndexOutOfBoundsException ex)
    	{
    		System.out.println("Usage : java ExtractImagesDemo "
                    + "<input file path>");
    	}
    }

    public void onRenderError(Object sender, Throwable throwable)
    {
        throwable.printStackTrace();
    }
}
