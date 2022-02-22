import java.io.FileWriter;
import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

public class SaveAsTextDemo
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
    	try
    	{
	    	String dirPath = "D:\\Temp\\";
	    	
	        PdfDocument d = new PdfDocument();
	
	        d.load(args[0]);
	        
	        FileWriter fw = new FileWriter(dirPath + d.getInputFileName().replace(".pdf", ".txt"));
	        
	        d.saveAsText("-", fw, true);
	        
	        fw.flush();
	        fw.close();
	        
	        d.close();
    	}
    	catch(ArrayIndexOutOfBoundsException ex)
    	{
    		System.out.println("Usage : java SaveAsTextDemo "
                    + "<input file path>");
    	}
    }

}
