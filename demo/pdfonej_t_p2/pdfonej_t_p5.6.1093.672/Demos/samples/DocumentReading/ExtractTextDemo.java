import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class ExtractTextDemo
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
    	try
    	{
	        PdfDocument d = new PdfDocument();
	
	        d.load(args[0]);
	        
	        // Get all the lines of the specified page as list
	        List lines = d.extractText(1);
	        
	        // Get all the lines of all the pages as list
	        // supply pageRange and specify whether to include pageBreak character 0x0c
//	        List lines = d.extractText("-", true);
	        
	        for (Iterator iterator = lines.iterator(); iterator.hasNext();)
	        {
	            String line = (String) iterator.next();
	            
	            System.out.println(line);
	        }
	        
	        d.close();
    	}
    	catch(ArrayIndexOutOfBoundsException ex)
    	{
    		System.out.println("Usage : java ExtractTextDemo "
                    + "<input file path>");
    	}
    }

}
