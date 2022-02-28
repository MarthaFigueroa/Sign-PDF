import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class SubstitueFontDemo
{
    public static void main(String[] args) throws PdfException,
        IOException
    {
        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument pdfDoc = new PdfDocument();

            /* Load the PDF document into the document object */
            pdfDoc.load(args[0]);

            // create a font with which all the embedded fonts to be
            // replaced with
            PdfFont replaceWithFont = PdfFont.create(args[2], 15,
                PdfEncodings.WINANSI);

            pdfDoc.substituteFont(args[3], replaceWithFont);

            pdfDoc.setOpenAfterSave(true);

            /* Save the document object */ 
            pdfDoc.save(args[1]);

            /* Dispose the I/O files associated with this document object */
            pdfDoc.close();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out
                .println("Usage : java SubstitueExistingFontDemo "
                    + "<input file path>" + " <output file path>"
                    + " <font filepath or Type1 font name to substitute with> "
                    + "<baseName of the font being substituted>");
        }

    }

}
