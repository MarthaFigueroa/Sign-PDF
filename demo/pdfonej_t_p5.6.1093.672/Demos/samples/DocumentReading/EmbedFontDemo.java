import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class EmbedFontDemo
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    public static void main(String[] args) throws IOException,
        PdfException
    {
        EmbedFontDemo embedFontDemo = new  EmbedFontDemo();
        embedFontDemo.embedFontToExistingPDF(args);
    }

    private void embedFontToExistingPDF(String[] args)
        throws IOException, PdfException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /*
             * Load the PDF document which has text of unembed font into
             * the document object
             */
            d.load(args[0]);
    
            /*
             * create a font which object passing the location of the
             * font.
             */
            PdfFont embedFont = PdfFont.create(
                "daisy.ttf", 25,
                PdfEncodings.CP1252, PdfFont.EMBED_FULL);
    
            /* embed the font using created font.*/
            d.embedFont(embedFont);
    
            /* setopenAfterSave property to true to view file after save. */
            d.setOpenAfterSave(true);
    
            /* Save the document object */ 
            d.save(args[1]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java EmbedFontDemo "
                + "<input file path> <output file path>");
        }
    }
}
