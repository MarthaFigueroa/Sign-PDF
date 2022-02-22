import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfAutoPageCreationHandler;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfPageSize;

/*
 * This program demonstrates the usage of PdfDocument.addPageBreak()
 * method and PdfAutoPageCreationHandler interface.
 */
public class AutoPaginateWithPageBreak implements
    PdfAutoPageCreationHandler
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* 
             * Set the Measurement Unit for the PdfDocument object.
             * The default Measurement unit is Points.
             */
            // d.setMeasurementUnit(PdfMeasurement.MU_POINTS);

            /*
             * Set the value for autoPaginate propery.
             * By default autoPaginate is true. 
             */ 
            // d.autoPaginate = true;

            /*
             * set the AutoPageCreationHandler Variable with this
             * class Object
             */ 
            d.setAutoPageCreationHandler(new AutoPaginateWithPageBreak());

            // Write some text on page 1
            d.writeText("Gnostice Information Technologies Pvt. Ltd.");

            // Add a page break;
            d.addPageBreak();

            // Write some text on page 2
            d.writeText("Gnostice PDFOne Java");

            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);

            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java DocInfoCreation "
                + "<output file path> ");
        }
    }

    /*
     * This method will be called if a new page needs to be created
     * in autoPaginate mode and when addPageBreak() method is called.
     */
    public double[] onAutoPageCreation(PdfDocument d,
        int creatingPageNum)
    {
        System.out.println("Creating page number " + creatingPageNum);

        // if first page is being created then set its size as A4 Page Size.
        if (creatingPageNum == 1)
        {
            double[] pageSizeElements = new double[10];

            // Set page size to A4 page size constant vlue
            pageSizeElements[0] = PdfPageSize.A4;

            /* Set page header height */
            //pageSizeElements[3] = 20;
            
            /* Set page footer height */
            //pageSizeElements[4] = 20;

            /* Set page left margin */
            pageSizeElements[5] = 20;

            /* Set page top margin */
            pageSizeElements[6] = 20;

            /* Set page right margin */
            pageSizeElements[7] = 20;

            /* Set page bottom margin */
            pageSizeElements[8] = 20;

            /*
             * Set Measurement unit Points is the default Measurement
             * unit.
             */
            // pageSizeElements[9] = PdfMeasurement.MU_POINTS;
            
            return pageSizeElements;
        }
        else
        {
            // return null if the creating page should be created with
            // the size of the previous page
            return null;
        }
    }
}
