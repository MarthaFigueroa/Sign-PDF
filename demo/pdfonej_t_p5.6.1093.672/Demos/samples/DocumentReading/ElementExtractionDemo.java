import java.io.IOException;
import java.util.List;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPageElement;
import com.gnostice.pdfone.PdfPageTextElement;
import com.gnostice.pdfone.PdfRenderErrorHandler;

public class ElementExtractionDemo implements PdfRenderErrorHandler
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
            d.load(args[0]);
            
            d.setRenderErrorHandler(new ElementExtractionDemo());
    
            // Get elements in a particular page
            List elementsList = d.getPageElements(1, PdfPageElement.ELEMENT_TYPE_ALL);
            
            // Get elements in all the specified page range
    //        List elementsList = d.getPageElements("-", PdfPageElement.ELEMENT_TYPE_ALL);
            
            PdfPageElement pageElement = null;
            for(int i = 0; i < elementsList.size(); i++)
            {
                pageElement = (PdfPageElement) elementsList.get(i);
                
                switch(pageElement.getElementType())
                {
                    case PdfPageElement.ELEMENT_TYPE_TEXT:
                        PdfPageTextElement textElement = (PdfPageTextElement) pageElement;
                        System.out.println("Element Type: Text");
                        System.out.println("Page Number: " + pageElement.getPageNum());
                        System.out.println("Text: " + textElement.getText());
                        System.out.println("Bounding Rectangle: " + pageElement.getBoundingRect());
                        break;
                        
                    case PdfPageElement.ELEMENT_TYPE_IMAGE:
                        // print the pageElement object
                        System.out.println(pageElement);
                        break;
                        
                    case PdfPageElement.ELEMENT_TYPE_PATH:
                        System.out.println(pageElement);
                        break;
                }
            }
            
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java ElementExtractionDemo "
                + "<input file path>");
        }
    }

    public void onRenderError(Object sender, Throwable throwable)
    {
        throwable.printStackTrace();
    }
}
