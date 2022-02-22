import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class WriteTextUsingStrikeThroughFontStyle
{

    public static void main(String[] args)
        throws IOException, PdfException
    {
        PdfDocument doc = new PdfDocument();

        // create a TTF font object.
        PdfFont font1 = PdfFont.create(
            "arial.ttf", PdfFont.STRIKE_THROUGH,
            15, PdfEncodings.WINANSI, PdfFont.EMBED_SUBSET);
        
        // write text with strike out style using TTF font.
        String text1 = "Strike-through text uses TrueType font."
            + "\nAbcdefghijklmnopqrstuvwxyz.";
        doc.writeText(text1, font1, 100, 100);
        
        // create a built-in Standard Type1 font object.
        PdfFont font2 = PdfFont.create("TimesNewRoman",
            PdfFont.STRIKE_THROUGH, 15, PdfEncodings.WINANSI);
        
        // write text with strike out style using StandardType1 font.
        String text2 = "Strike-through text uses Standard Type1 font."
            + "\nAbcdefghijklmnopqrstuvwxyz.";
        doc.writeText(text2, font2, 100, 200);
        
        doc.setOpenAfterSave(true);
        doc.save("OutputDocument.pdf");
        doc.close();
    }
}
