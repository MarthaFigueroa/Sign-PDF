import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfCustomPlaceholderHandler;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfSaveAsImageHandler;

public class SaveAsImageDemo implements PdfCustomPlaceholderHandler,
		PdfSaveAsImageHandler 
{
	static 
	{
		PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
				"9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
	}

	public static void main(String[] args) throws IOException,
        PdfException
    {
		// Create an object of this class
		SaveAsImageDemo demo1 = new SaveAsImageDemo();
		// Execute example method from the above instance
		demo1.loadAndSaveAsImages(args);
	}
	
	public void loadAndSaveAsImages(String[] args)
        throws IOException, PdfException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);
    
    		// Specify instance that implements
    		// PdfCustomPlaceholderHandler
    		d.setCph(this);
            // Specify instance that implements
    		// PdfSaveAsImageHandler
    		d.setSaveAsImageHandler(this);
    		
    		// Save pages 1 and 2 as images
    		d.saveAsImage(
                // format              
    			"png", 
    			// pages
    			"1,2", 
    			// file name of image
    			// - contains a custom placeholder
    			"<% myvar1 %>", 
    			// paath of image 
    			// - contains a pre-defined placeholder
    			"<% InputFilePath %>");
    		
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            System.out.println("Usage : java SaveAsImageDemo "
                    + "<input PDF file>");
        }
	}

	public void onSaveAsImage(PdfDocument pdfDocument, int pageNum,
			StringBuffer imageName, StringBuffer outputFilepath) 
	{
		if (imageName != null && !imageName.toString().trim().equals("")) 
		{
			imageName.append("page_" + pageNum);
		}
	}

	public String onCustomPlaceHolder(String variable, PdfDocument d,
			int pagenumber) 
	{
		if (variable.equalsIgnoreCase("myVar1")) 
		{
			return "img" + pagenumber + "_" + d.getPageCount();
		}
		return null;
	}
}
