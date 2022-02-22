import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfEnumPageElementsHandler;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPageElement;
import com.gnostice.pdfone.PdfRenderErrorHandler;

public class PdfEnumPageElementsHandlerDemo implements PdfRenderErrorHandler,
		PdfEnumPageElementsHandler
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
    	try
        {
            PdfDocument d = new PdfDocument();
        
	        d.load(args[0]);
	        
			PdfEnumPageElementsHandlerDemo enumPageElementsHander = new PdfEnumPageElementsHandlerDemo();
			d.setRenderErrorHandler(enumPageElementsHander);
	
//	        PdfPage p = d.getPage(1);
//	        p.enumPageElements(PdfPageElement.ELEMENT_TYPE_ALL,
//	            enumPageElementsHander);
	        
	        d.enumPageElements(1, PdfPageElement.ELEMENT_TYPE_ALL,
	            enumPageElementsHander);
	        
	        d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java EnumPageElementsDemo "
                + "<input file path>");
        }
    }

    public void onRenderError(Object sender, Throwable throwable)
    {
        throwable.printStackTrace();
    }

    public void onEnumPageElements(int pageNumber, int elementType,
        PdfPageElement pageElement, boolean[] continueExtraction)
    {
        System.out.println(pageElement);
        
        // set continueExtraction[0] to false to stop extraction
//        continueExtraction[0] = false;
    }
    
}
