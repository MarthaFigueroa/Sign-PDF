import java.awt.Color;
import java.awt.Graphics;

import javax.print.attribute.standard.MediaTray;
import javax.print.attribute.standard.Sides;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPage;
import com.gnostice.pdfone.PdfPageSize;
import com.gnostice.pdfone.PdfPrintPageHandler;
import com.gnostice.pdfone.PdfPrinter;
import com.gnostice.pdfone.PdfReader;

public class PdfCustomPagesizePrintingDemo implements
    PdfPrintPageHandler
{

    /* Usage : java PdfCustomPagesizePrintingDemo <InputPdfDocFilepath>*/
    public static void main(String[] args)
    {
        PdfCustomPagesizePrintingDemo printDemo = new PdfCustomPagesizePrintingDemo();
        
        printDemo.printTest(args);
    }
    
    void printTest(String[] args)
    {
        PdfReader pdfReader = null;
        try
        {
            //PdfReader object to read the pdf document 
            pdfReader = PdfReader.fileReader(args[0]);//input Pdf document file path

            //PdfDocument of the read PdfDocument file
            PdfDocument document = new PdfDocument(pdfReader);

            //new default PdfPrinter object
            PdfPrinter pdfPrinter = new PdfPrinter(document);
            
            // page range to be printed
            pdfPrinter.setPageRange("-");//represents all
            //property auto rotate and center
            pdfPrinter.autoRotateandCenter(true);

            //set the page size as custom
            pdfPrinter.setPageSize(PdfPageSize.CUSTOM);
                       
            // set the dimentsion of the page custom page by default
            // it will be 8.5 X 11.0 inches
            pdfPrinter.setCustomPageDimension(300 , 400, PdfPrinter.MU_POINTS);
                       
            //scaling with which printing should happen
            pdfPrinter
                .setPageScale(PdfPrinter.SCALE_FIT_TO_PRINTER_MARGINS);
           
            //set the copies
            pdfPrinter.setCopies(1);
            
            //set the media tray from which paper should be selected 
            pdfPrinter.setPrintMediaTray(MediaTray.TOP);
            
            //set the sides to be printed on
            pdfPrinter.setPrintSides(Sides.DUPLEX);
            
            //register the printing page handler event to the PdfPrinter
            pdfPrinter.setPrintPageHandler(this);
            
            //print PDF using custom PrintDialog 
            pdfPrinter.showPrintDialog();
               
        }
        catch(PdfException pdfException) 
        {
            pdfException.printStackTrace();
        }
        catch (Exception exception)
        {   
            System.out.println("Usage : java PdfCustomPagesizePrintingDemo " +
            "<InputPDFFilePath> ");
        }
        finally
        {
            try
            {
                pdfReader.dispose();                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public void beforePrintingOnGraphics(Graphics g, int pageNum, PdfPage pdfPage)
    {
        g.setColor(Color.CYAN);
        g.drawString(
            "This is printing using Gnostice PDFOne Java Printer.",
            80, 80);
        
    }

    public void printingOnGraphics(int printingPageNum, int pageCount)
    {
        System.out.println("Printing Page" + printingPageNum + " of "
            + pageCount);
        
    }

    public void afterPrintingOnGraphics(Graphics g, int pageNum, PdfPage pdfPage)
    {
        g.setColor(Color.MAGENTA);
        g.drawString(
            "This is printing using Gnostice PDFOne Java Printer.",
            80, 160);
        
    }

    public boolean cancelPrinting(int numOfPrintedPages, int totalPagesIssued)
    {
        return false;
    }

}
