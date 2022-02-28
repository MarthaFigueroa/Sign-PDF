import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfCustomPlaceholderHandler;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfSaveAsImageAdapter;

/*
 * sample program which demonstrates - how to save PdfDocument pages
 * as images with a compression level by modifying imageName, 
 * and fileLocation using PdfSaveAsImageHandler and PdfSaveAsAdapter.
 * 
 */
public class SaveAsImageWithCompression extends
    PdfSaveAsImageAdapter implements PdfCustomPlaceholderHandler
{
    static {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
                "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    
    /*
     * demonstrates the usage of one of the overloaded methods
     * saveAsImage(format, pageRange, imageName, imageWidth,
     * imageHeight, cmopressionQuality, fileLocationToBeSaved) of
     * PdfDocument Object, by passing the imageName, fileLocation as
     * strings and compressionQuality as float ranging
     * 0.0-1.0 which can later be modified by extending the Adapter class 
     * PdfSaveAsImageAdapter and overriding the method onSaveAsImage()
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

            SaveAsImageWithCompression demo = new SaveAsImageWithCompression();
            
            // set the SaveAsImageHandler object by implementing it
            d.setSaveAsImageHandler(demo);
            
            /*
             * save page as image using one of the overloaded
             * saveAsImage(format, pageRange, imageName, imageWidth,
             * imageHeight, compressionQualityLevel, fileLocationToBeSaved)
             */
            d.saveAsImage(
                "jpeg",//format of the image ex:png, bmp, gif, jpeg.
                
                "1", //pageRange desired pages ranges to be saved as
                
                "imageName1",// temporary image name
                // which can be used to set imageName later when
                // onSaveAsImage event
                // is triggered and handled during execution in the
                // implementation class.
                
                "<% mywidth %>",// custom image Width using
                // customplaceholder and saveAsImageHandler
                
                "<% myheight %>",// custom image height using
                // customplaceholder and saveAsImageHandler
                
                0.5f,//compression quality level(should be between 0.0 and 1.0)
                
                "imageLocation1"//location to which image should be saved.
                
                );
            
            /*
             * save page as image using one of the overloaded
             * saveAsImage(format, pageRange, imageName, imageWidth,
             * imageHeight, compressionQualityLevel, fileLocationToBeSaved)
             */
            d.saveAsImage(
                "png",//format of the image ex:png, bmp, gif, jpeg.
                
                "2", //pageRange desired pages ranges to be saved as
                
                "imageName2",// temporary image name
                // which can be used to set imageName later when
                // onSaveAsImage event
                // is triggered and handled during execution in the
                // implementation class.
                
                "<% pageWidth %>",// default image Width using
                                    // predefined placeholder
                
                "<% pageHeight %>",// default image Height using
                // predefined placeholder
                
                0.8f,//compression quality level(should be between 0.0 and 1.0)
                
                "imageLocation2"//location to which image should be saved.
                
                );
            
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage : java SaveAsImageWithCompression " +
            "<input file path> ");
        }
    }

    public String onCustomPlaceHolder(String variable, PdfDocument d, int pagenumber)
    {
        if(variable.equalsIgnoreCase("mywidth"))
        {
            return "600";//modified width
        }
        else if(variable.equalsIgnoreCase("myheight"))
        {
            return "500";//modified height
        }
        return null;
    }
    
    /*
     * overriding this method will allow to modify the attributes of
     * images which are earlier set
     */
    public void onSaveAsImage(PdfDocument pdfDocument, int pageNum,
        int[] imageWidth, int[] imageHeight,
        float[] compressionQuality, StringBuffer imageName,
        StringBuffer outputFilepath)
    {
        if(pageNum == 1)
        {
            imageWidth[0] = 400;
            
            imageHeight[0] = 600;
            
            compressionQuality[0] = 0.5f;
        }
        else if(pageNum == 2)
        {
            imageWidth[0] = 500;
            
            imageHeight[0] = 500;
            
            compressionQuality[0] = 1.0f;
        }
        
        if (imageName.toString().equalsIgnoreCase(
            "imageName1"))
        {
            imageName.delete(0, imageName.length());
            // delete the string already present
            
            imageName.append("myimage1" + pageNum + "of"
                + pdfDocument.getPageCount());
            // append the name image with which image should be saved
        }
        else  if (imageName.toString().equalsIgnoreCase(
            "imageName2"))
        {
            imageName.delete(0, imageName.length());
            // delete the string already present
            
            imageName.append("myimage2" + pageNum + "of"
                + pdfDocument.getPageCount());
            // append the name image with which image should be saved
        }
        if (outputFilepath.toString().trim().equals("imageLocation1"))
        {
             outputFilepath.delete(0, outputFilepath.capacity());
             // delete the string already present
             
             outputFilepath.append("c:\\");
             // append the path to which imageshould be saved
        }
        else if (outputFilepath.toString().trim().equals("imageLocation2"))
        {
            outputFilepath.delete(0, outputFilepath.capacity());
            // delete the string already present
            
            outputFilepath.append("d:\\");
            // append the path to which imageshould be saved
       }
    }

}
