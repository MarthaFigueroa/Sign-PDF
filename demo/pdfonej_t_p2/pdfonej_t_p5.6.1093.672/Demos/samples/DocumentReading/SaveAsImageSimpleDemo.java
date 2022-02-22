import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

/*
 * sample program which demonstrates - how to save PdfDocument pages
 * as images by passing imageName and fileLocation as strings.
 */
public class SaveAsImageSimpleDemo 
{
    static {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
                "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    
    /*
     * demonstrates the usage of one of the overloaded methods
     * saveAsImage(format, pageRange, imageName, fileLocationToBeSaved)
     * by passing the imageName and fileLocation as string
     * 
     */
    public static void main(String[] args) throws PdfException,
        IOException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();

            /* Load the PDF document into the document object */
            d.load(args[0]);
            
            /*
             * save page as image using one of the overloaded
             * saveAsImage(format, pageRange, imageName,
             * fileLocationToBeSaved) of PdfDocument object
             */
            d.saveAsImage(
                "jpeg",//format of the image ex:png, bmp, gif, jpeg.
                "1,2", //desired page ranges to be saved as image
                "imageName", //name of the image with which it is to be saved 
                //where name of the image will be saved with pageNum appended to it
                //ex:imageName_1
                // if this argument is passed as empty then an image
                // name will be constructed based on the document name
                // ex: docName_page_<pageNum>
                "c:\\"//location to which image should be saved.
                //if this argument is passed as empty then an image
                // will be saved in the current working directory.
                );
            
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage : java SaveAsImageSimpleDemo " +
            "<input file path> ");
        }
    }
}
