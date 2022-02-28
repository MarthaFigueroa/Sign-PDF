import java.awt.Color;
import java.io.IOException;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfSearchMode;
import com.gnostice.pdfone.PdfSearchOptions;
import com.gnostice.pdfone.encodings.PdfEncodings;
import com.gnostice.pdfone.fonts.PdfFont;

public class RedactAndReplaceTextDemo 
{
    public static void main(String[] args) throws IOException, PdfException
    {
        PdfDocument d = new PdfDocument();
        d.load(args[0]);
        
        PdfFont font = PdfFont.create("Helvetica", 12, PdfEncodings.WINANSI);
        font.setColor(Color.RED);
        
        //"Gnostice is the text present in the PDF"
        //"COMPANY NAME" is the new text which replaces the old text
        d.redactText("-", // page range
			"Gnostice", // search string
			PdfSearchMode.LITERAL, // search mode 
			PdfSearchOptions.NONE, // search option
			"COMPANY NAME", // replace string
			font, // font to write replace string
			true); // autofit replace string
        
        d.setOpenAfterSave(true);
        d.save(args[1]);
        
        d.close();
    }
}
