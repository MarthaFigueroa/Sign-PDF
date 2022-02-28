import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfCustomPlaceholderHandler;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

/*
 * sample program which demonstrates - how to save PdfDocument pages
 * as images with a compression level by passing imageName, 
 * fileLocation as predefined & custom placeholders .
 * 
 */
public class SaveAsImageUsingPlaceHolders implements
    PdfCustomPlaceholderHandler
{
    static {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
                "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    
    /*
     * demonstrates the usage of one of the overloaded methods
     * saveAsImage(format, pageRange, imageName, cmopressionQuality,
     * fileLocationToBeSaved) 
     * of PdfDocument Object,
     * by passing the imageName, fileLocation as customplaceholders and 
     * compressionQuality as float ranging 0.0-1.0
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
             * create an instance of the object which implements
             * PdfCustomPlaceholderHandler interface
             */            
            SaveAsImageUsingPlaceHolders demo = new SaveAsImageUsingPlaceHolders();

            /*
             * set PdfCustomPlaceholderHandler implementation object
             * to pdf document object
             */
            d.setCph(demo);
                   
            /*
             * save page as image using one of the overloaded
             * saveAsImage(format, pageRange, imageName, compressionQualityLevel
             * fileLocationToBeSaved)
             * 
             * by passing imageName, fileLocation as predefined placedholders
             * and compressionQualityLevel as float ranging 0.0f-1.0f.
             * 
             */
            d.saveAsImage(
                "jpeg",//format of the image ex:png, bmp, gif, jpeg.
                
                " 2, 3", //pageRange desired pages ranges to be saved as
                
                "<% myImageName %>", // imageName as customPlaceHolder
                // which can be used to set imageName when an event
                // is triggered and handled during execution in the
                // implementation class. 
                // if invalid custom placeholder is passed then an
                // image name will be constructed as 
                // <LoadedPDFDocumentName>_page_<pagenumber>
                // which will be unique
                
                0.5f,//compression quality level(should be between 0.0 and 1.0)
                
                "<% myImageLocation %>"//location to which image should be saved.
                
                );
            
            /*
             * save page as image using one of the overloaded
             * saveAsImage(format, pageRange, imageName, compressionQualityLevel
             * fileLocationToBeSaved)
             * 
             * by passing imageName, fileLocation as predefined placedholders
             * and compressionQualityLevel as float ranging 0.0f-1.0f.
             * 
             */
            d.saveAsImage(
                "jpeg",//format of the image ex:png, bmp, gif, jpeg.
                
                " 1, 4", //pageRange desired pages ranges to be saved as
                
                
                "<% inputfilename %>", // imageName as predefined placeholder
                // <% inputfilename %> for which inputpdfdocname is
                // considered as image name.
                // if invalid predefined plcaholder is
                // specified an imageName is constructed
                // as <LoadedPDFDocumentName>_page_<pagenumber>
                // which will be unique
                
                0.5f,//compression quality level(should be between 0.0 and 1.0)
                
                "<% inputfilepath %>"//location to which image should be saved.
                
                );
            
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage : java SaveAsImageUsingPlaceHolders " +
            "<input file path> ");
        }
    }

    /*
     * customplaceholder implementation method which returns value of
     * specific custom placeholder
     */
    public String onCustomPlaceHolder(String variable, PdfDocument d, int pagenumber)
    {
        if(variable.equalsIgnoreCase("myImageName"))
        {
            /*
             * return the imageName with which the image should be
             * saved
             */
            return "imageName_" + pagenumber + "of"
                + d.getPageCount() + "_0.5c";
        }
        else if(variable.equalsIgnoreCase("myImageLocation"))
        {
            return "";// return the file path to which image
                        // should be saved.
        }
        return null;
    }
}
