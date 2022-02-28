import java.awt.Color;
import java.awt.Graphics;
import java.awt.print.PrinterException;
import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageSize;
import com.gnostice.pdfone.PdfPrintPageHandler;
import com.gnostice.pdfone.PdfPrinter;

public class PdfPrintingWithCustomPageSize implements
    PdfPrintPageHandler
{

    /*
     * Usage : java PdfCustomPagesizePrintingDemo
     * <InputPdfDocFilepath>
     */
    public static void main(String[] args) throws PdfException,
        IOException, PrinterException
    {
        PdfPrintingWithCustomPageSize printDemo = new PdfPrintingWithCustomPageSize();
        printDemo.printTest(args);
    }

    void printTest(String[] args) throws PdfException, IOException,
        PrinterException
    {
        try
        {
            // Create a PdfDocument object
            PdfDocument document = new PdfDocument();
            // Load the document
            document.load(args[0]);

            // new default PdfPrinter object
            PdfPrinter pdfPrinter = new PdfPrinter();
            // Set the document object for printing
            pdfPrinter.setDocument(document);
            
            // page range to be printed
            pdfPrinter.setPageRange("-");// represents all
            // property auto rotate and center
            pdfPrinter.autoRotateandCenter(true);

            // set the page size as custom
            pdfPrinter.setPageSize(PdfPageSize.CUSTOM);

            // set the dimentsion of the page custom page by default
            // it will be 8.5 X 11.0 inches
            pdfPrinter.setCustomPageDimension(7.5, 11.5,
                PdfPrinter.MU_INCHES);

            // set the margins of the print page
            pdfPrinter.setPageMargins(new double[] { 1, 1, 1, 1 },
                PdfPrinter.MU_INCHES);

            // scaling with which printing should happen
            pdfPrinter
                .setPageScale(PdfPrinter.SCALE_FIT_TO_PRINTER_MARGINS);

            pdfPrinter.getPageDimension();
            pdfPrinter.setPageMargins(new double[] { 1, 1, 1, 1 },
                PdfPrinter.MU_INCHES);
            // register the printing page handler event to the
            // PdfPrinter
            pdfPrinter.setPrintPageHandler(this);

            // print the document
            pdfPrinter.print(pdfPrinter.getDefaultPrinterName(),
                pdfPrinter.getPageRange(), pdfPrinter.getCopies());

            
            document.close();
        }
        catch (ArrayIndexOutOfBoundsException exception)
        {
            System.out
                .println("Usage : java PdfPrintingWithCustomPageSize "
                    + "<input PDF file>");
        }

    }

    public void beforePrintingOnGraphics(Graphics g, int pageNum,
        PdfPage pdfPage)
    {
        g.setColor(Color.CYAN);
        g.drawString(
            "This text was written before printing the page contents.",
            80, 80);

    }

    public void printingOnGraphics(int printingPageNum, int pageCount)
    {
        System.out.println("Printing Page" + printingPageNum + " of "
            + pageCount);

    }

    public void afterPrintingOnGraphics(Graphics g, int pageNum,
        PdfPage pdfPage)
    {
        g.setColor(Color.MAGENTA);
        g.drawString(
            "This text was written after printing the page contents.",
            80, 160);

    }

    public boolean cancelPrinting(int numOfPrintedPages, int totalPagesIssued)
    {
        return false;
    }

}
