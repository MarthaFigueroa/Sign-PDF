import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfMeasurement;
import com.gnostice.pdfone.PdfPageReadHandler;

public class OnPageRead implements PdfPageReadHandler
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    public double[] onPageRead(PdfDocument d, int pageRead,
        double width, double height)
    {
        System.out.println("Pageno: " + pageRead);
        double[] margin = new double[7];
        /* Set page header height */
        margin[0] = 50;
        /* Set page footer height */
        margin[1] = 50;
        /* Set page left margin */
        margin[2] = 100;
        /* Set page top margin */
        margin[3] = 100;
        /* Set page right margin */
        margin[4] = 100;
        /* Set page bottom margin */
        margin[5] = 100;
        /* Set Measurement unit */
        margin[6] = PdfMeasurement.MU_POINTS;
        
        return margin;
        // TODO Auto-generated method stub
    }

    /* Usage : java OnPageRead <input file path> <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ///////////////////////////////////////////////////
        // Open a document and set page margins through  //
        // OnPageRead event                              //
        ///////////////////////////////////////////////////
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Set the OnPageRead event handler for the PdfReader instance */
            d.setOnPageReadHandler(new OnPageRead());
            
            /* Load the PDF document into the document object */
            d.load(args[0]);
            
            d.setMeasurementUnit(PdfMeasurement.MU_POINTS);
    
            /* Write text to the document */
            d.writeText("Text written at 0, 0", 0, 0, "1");
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java OnPageRead " +
                    "<input file path> <output file path>");
        }
    }

}
