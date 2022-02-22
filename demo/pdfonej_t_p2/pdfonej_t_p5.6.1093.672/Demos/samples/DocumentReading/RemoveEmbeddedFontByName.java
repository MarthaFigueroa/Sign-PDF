import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class RemoveEmbeddedFontByName
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
            PdfDocument pdfDoc = new PdfDocument();

            /* Load the PDF document into the document object */
            pdfDoc.load(args[0]);
            
            //create a font with which all the embedded fonts to be replaced with
            PdfFont replaceWithFont = PdfFont.create(args[2], 15,
                PdfEncodings.WINANSI);
            
            //call PdfDocument.replaceEmbeddedFontsByName(PdfFont replaceWithFont)
            pdfDoc.replaceEmbeddedFont(args[3], replaceWithFont);

            
            pdfDoc.setOpenAfterSave(true);
            
            /* Save the document object */ 
            pdfDoc.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            pdfDoc.close();
            
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out
                .println("Usage : java DocAttachmentsRemovingDemo "
                    + "<input file path> <0utput file path> <replace_font_name_or_file> <EmbeddedFontBaseFontname>");
        }
    }

}
