import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.List;

import com.gnostice.pdfone.PdfBoundingRect;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfRenderErrorHandler;
import com.gnostice.pdfone.PdfSearchElement;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;

public class SearchDemo implements PdfRenderErrorHandler
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
    	try
    	{
	        PdfDocument d = new PdfDocument();
	        
	        d.load(args[0]);
	        
	        d.setRenderErrorHandler(new SearchDemo());
	
	        // Literal string search with case sensitive and whole word only options set
//	        List searchResults = d.search("Text", 1, PdfSearchMode.LITERAL, PdfSearchOptions.WHOLE_WORD | PdfSearchOptions.CASE_SENSITIVE);
	        
	        // Literal/Normal search 
	        List searchResults = d.search("Text", 1, PdfSearchMode.LITERAL, PdfSearchOptions.NONE);
	        
	        // Regex expression for phone number search
	        // Ex: searches for the phone numbers in the format for instanceof (+91)9302304240
//	        List searchResults = d.search("\\(\\+\\d{2}\\)\\d{10}", 1, PdfSearchMode.REGEX, PdfSearchOptions.NONE);
	        
	        // Regex expression for email search in the format email@email.com
//	        List searchResults = d.search(1, "^[\\w\\.\\-]+\\@[\\w\\.\\-]+\\.[\\w\\.\\-]+$", PdfSearchMode.REGEX, PdfSearchOptions.NONE);
	        
	        if (searchResults.isEmpty())
	        {
	            System.out.println("0 matche(s) found");
	        }
	        else
	        {
	            System.out.println(searchResults.size() + " matche(s) found");
	            
	            PdfSearchElement searchElement = null;
	            // Print the details of each search occurrence.
	            for(int i = 0; i < searchResults.size(); i++)
	            {
	                searchElement = (PdfSearchElement) searchResults.get(i);
	                
	                // Prints the basic details of the current match.
	                System.out.println(searchElement);
	                
                    // Get the actual match string. This is useful in
                    // identifying the actual string that was matched for
                    // a regex (Regular expression) search.
	                String actualMatchString = searchElement.getMatchString();
	                
	                // The text of the line that contains this current match.
                    String lineContainingMatchString = searchElement
                        .getLineContainingMatchString();
                    
                    // The index of the match string in the line that
                    // contains this match.
                    int indexInLine = searchElement.getIndexOfMatchStringInLine();
                    
                    // Each search element contains list of
                    // PdfBoundingRect objects to hold the bounding
                    // rectangles of the text that span across
                    // lines/pages. If the search element is a single
                    // word then the list will contain only one
                    // PdfBoundingRect object.
	                List boudingRects = searchElement.getBoundingRects();
	                
	                PdfBoundingRect pdfBoundingRect = null;
	                for (int j = 0; j < boudingRects.size(); j++)
	                {
	                    pdfBoundingRect = (PdfBoundingRect)boudingRects.get(j);
	                    
                        // Page number of the current bounding
                        // rectangle of the search occurrence.
                        int pageNum = pdfBoundingRect.getPageNum();
                        
                        // Bounding rectangle
                        Rectangle2D boundingRect = pdfBoundingRect.getBoundingRect();
                        
                        // Bounding rectangles of each character of the current text
                        List charBoundingRects = pdfBoundingRect.getCharBoundingRects();
	                }
	            }
	        }
	        
	        d.close();
    	}
    	catch(ArrayIndexOutOfBoundsException ex)
    	{
    		System.out.println("Usage : java SearchDemo "
                    + "<input file path>");
    	}
    }

    public void onRenderError(Object sender, Throwable throwable)
    {
        throwable.printStackTrace();
    }
}
