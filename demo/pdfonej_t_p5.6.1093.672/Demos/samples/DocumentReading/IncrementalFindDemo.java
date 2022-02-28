import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfRenderErrorHandler;
import com.gnostice.pdfone.PdfSearchElement;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;

public class IncrementalFindDemo implements PdfRenderErrorHandler
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
    	try
    	{
	        PdfDocument d = new PdfDocument();
	        
	        d.load(args[0]);
	        
	        d.setRenderErrorHandler(new IncrementalFindDemo());
	
	        // supply searchString, searchMode, searchOptions, startPageNum, specify whether search forward/backward for the first occurrence
	        PdfSearchElement element = d.findFirst("simple", PdfSearchMode.LITERAL, PdfSearchOptions.NONE, 3, false);
	
	        if (element == null)
	        {
	            System.out.println("0 matche(s) found");
	        }
	        else
	        {
	            while(element != null)
	            {
	                System.out.println(element);
	                
	                element = d.findNext(element);
	            }
	        }
	        
	        d.close();
    	}
    	catch(ArrayIndexOutOfBoundsException ex)
    	{
    		System.out.println("Usage : java IncrementalFindDemo "
                    + "<input file path>");
    	}
    }

    public void onRenderError(Object sender, Throwable throwable)
    {
        System.out.println(throwable.getMessage());
        throwable.printStackTrace();
    }
}
