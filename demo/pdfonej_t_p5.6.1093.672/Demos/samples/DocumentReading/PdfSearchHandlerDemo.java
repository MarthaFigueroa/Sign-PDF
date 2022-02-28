import java.io.IOException;
import java.util.List;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfRenderErrorHandler;
import com.gnostice.pdfone.PdfSearchHandler;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;

public class PdfSearchHandlerDemo implements PdfRenderErrorHandler,
		PdfSearchHandler
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
    	try
    	{
	        PdfDocument d = new PdfDocument();
	        
	        d.load(args[0]);
	        
	        PdfSearchHandlerDemo pdfSearchHandlerDemo = new PdfSearchHandlerDemo();
	        d.setRenderErrorHandler(pdfSearchHandlerDemo);
	
	        // supply searchString, searchMode, searchOptions, PdfSearchHandler object, startPageNum
	        d.search("Text", PdfSearchMode.LITERAL, PdfSearchOptions.NONE, pdfSearchHandlerDemo, 1);
	        
	        d.close();
    	}
    	catch(ArrayIndexOutOfBoundsException ex)
    	{
    		System.out.println("Usage : java PdfSearchHandlerDemo "
                    + "<input file path>");
    	}
    }

    public void onRenderError(Object sender, Throwable throwable)
    {
        System.out.println(throwable.getMessage());
        throwable.printStackTrace();
    }

	// called by pdfDocument.search method that passes the pageNum and the list
	// of searchElements found in that pageNum.
    // 
    public void onSearchElement(int pageNumber,
        boolean[] continueSearch, List searchElements)
    {
        for(int i =0; i < searchElements.size(); i++)
        {
            System.out.println(searchElements.get(i));
        }
        
        // set continueSearch[0] to false to stop the search
//        continueSearch[0] = false;
    }
}
