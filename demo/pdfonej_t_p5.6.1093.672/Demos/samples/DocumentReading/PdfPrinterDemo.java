import java.awt.print.PrinterException;
import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPrinter;

public class PdfPrinterDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }    
    
    public static void main(String[] args) throws IOException, PdfException, PrinterException
    {
        /* Create a new PdfDocument instance */
        PdfDocument d = new PdfDocument();

        /* Load the PDF document into the document object */
        d.load(args[0]);
        
        PdfPrinter printer = new PdfPrinter();
        printer.setDocument(d);

		/* Specify Printer name, page range, number of copies  */
		//   printer.print("Gnostice Print2eDoc","1", 2);
        
		/* Print through default print by passing page range and number of copies */
		//  printer.print("1", 1);
		
		/* Print all pages through default print by passing page range as "-" and number of copies */
		printer.print("-", 1);
		
		// Opens the custom print dialog box where Printer, page range and number of copies can be specified.
		//  printer.showPrintDialog();
        
        /* Dispose the I/O files associated with this document object */
        d.close();
    }
}
