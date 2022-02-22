import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;
import com.gnostice.pdfone.fonts.PdfFont;

public class RedactTextDemo 
{
    public static void main(String[] args) throws IOException, PdfException
    {
    	try
    	{
	        PdfDocument d = new PdfDocument();
	        d.load(args[0]);
	        
	        // Specify the pageNum, text to search for, searchMode, searchOptions
	        d.redactText(1, "Text", PdfSearchMode.LITERAL, PdfSearchOptions.NONE);
	        
	        // regex expression search for the phone number pattern (+91)9243259214
//	        d.redactText(1, "\\(\\+\\d{2}\\)\\d{10}", PdfSearchMode.REGEX, PdfSearchOptions.NONE);
	        
	        // regex expression search for the pattern +91-9243259214
//	        d.redactText(1, "\\+\\d{2}\\-\\d{10}", PdfSearchMode.REGEX, PdfSearchOptions.NONE);
	        
	        // regex expression search for the pattern anything like (+91)9243259214 or +91-9243259214 or +919243259214
//	        d.redactText(1, "\\(?\\+\\d{2}[\\)|\\-]?\\d{10}", PdfSearchMode.REGEX, PdfSearchOptions.NONE);
	        
	        // regex expression search and redact email id
//	        d.redactText(1, "^[\\w\\.\\-]+\\@[\\w\\.\\-]+\\.[\\w\\.\\-]+$", PdfSearchMode.REGEX, PdfSearchOptions.NONE);

            /*
             * overloaded method specify whether the associated
             * annotations should be removed after redacting the text
             */
//			String searchString = "Text";
//			String replaceString = null;
//			PdfFont font = null;
//			boolean fontSizeAuto = true;
//			boolean removeAssociatedAnnotations = true;
//			d.redactText(1, searchString, PdfSearchMode.LITERAL,
//				PdfSearchOptions.NONE, replaceString, font, fontSizeAuto,
//				removeAssociatedAnnotations);
	        
	        d.setOpenAfterSave(true);
	        d.save(args[1]);
	        d.close();
    	}
    	catch(ArrayIndexOutOfBoundsException ex)
    	{
    		System.out.println("Usage : java RedactTextDemo "
                    + "<input file path> <output file path>");
    	}
    }
}
