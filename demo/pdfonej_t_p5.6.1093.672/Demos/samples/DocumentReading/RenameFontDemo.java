import java.io.IOException;

import com.gnostice.pdfone.PdfDict;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfName;
import com.gnostice.pdfone.PdfNull;
import com.gnostice.pdfone.PdfObject;
import com.gnostice.pdfone.PdfObjectTypes;
import com.gnostice.pdfone.PdfObjectWriteListener;

/*
 * This demo shows how to rename the Font names in the PDF using PdfObjectWriteListener. 
 * eg. changes “CenturyGothic” to “Century Gothic” by adding spaces in between.
 */
public class RenameFontDemo implements PdfObjectWriteListener
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
            d.load("InputDocument.pdf");

            // onWritePdfObject method of this subscribed
            // PdfObjectWriteListener will be invoked at the time of
            // writing the specified PdfObject types.
            d.setPdfObjectWriteListener(new RenameFontDemo(), 
                PdfObjectTypes.FONT | PdfObjectTypes.FONTDESCRIPTOR);
            
            d.setOpenAfterSave(true);
            d.save("OutputDocument.pdf");
            d.close();
        }
        catch(PdfException pdfEx)
        {
            System.out.println(pdfEx.getMessage());
        }
        catch(StackOverflowError e)
        {
        }
    }

	/*
	 * You can modify getNewFontName() method according to your needs.
	 */
    public void onWritePdfObject(PdfDocument sourceDoc,
        PdfDict pdfDictObj, int objectType)
    {
        /*
         * If you want to change the name of the font then it should be
         * done at 2 places. 
         * 1. The BaseFont entry in the Font dictionary. 
         * 2. The FontName entry in the FontDescriptor dictionary. 
         */
        
        /* 
         * Important Note: if the name contains a prefix
         * which is a pattern of 6 UpperCase Letters followed by a Plus
         * (+) sign then the remaining part of the font name should
         * only be modified leaving the prefix unmodified as this is the
         * naming convention for a subset-embedded font. i.e., for
         * instance if font name is "ABCDEF+CenturyGothic" then it can
         * be renamed to "ABCDEF+Century Gothic" leaving the prefix
         * unmodified.
         */
        
        if (objectType == PdfObjectTypes.FONT)
        {
            // Get the value of BaseFont entry from the dictionary and
            // modify it. It should return a Non-Null value for the
            // Font dictionary.
            PdfObject value = pdfDictObj.getValue(new PdfName("BaseFont"));
            
            if (!PdfNull.isNull(value) && value instanceof PdfName)
            {
                // Get the String value from the PdfName object
                String baseFontName = ((PdfName)value).getString();
                
                // Modify/Rename the font name
                String newBaseFontName = getNewFontName(baseFontName);
                
                // Put the modified font name as the value back in the dictionary
                pdfDictObj.setValue(new PdfName("BaseFont"), new PdfName(newBaseFontName));
            }
        }
        else if (objectType == PdfObjectTypes.FONTDESCRIPTOR)
        {
            // Get the value of FontName entry from the dictionary and
            // modify it. It should return a Non-Null value for the
            // FontDescriptor dictionary.
            PdfObject value = pdfDictObj.getValue(new PdfName("FontName"));
            
            if (!PdfNull.isNull(value) && value instanceof PdfName)
            {
                // Get the String value from the PdfName object
                String baseFontName = ((PdfName)value).getString();
                
                // Modify/Rename the font name
                String newBaseFontName = getNewFontName(baseFontName);
                
                // Put the modified font name as the value back in the dictionary
                pdfDictObj.setValue(new PdfName("FontName"), new PdfName(newBaseFontName));
            }
        }
    }
    
	// This method actually does the renaming of the font name and returns the new name.
    public String getNewFontName(String currFontName)
    {
        if (currFontName == null || currFontName.trim().equals(""))
        {
            // This will never happen.
            return null;
        }
        
        String currFontNameInLowerCase = currFontName.toLowerCase();
        // if the font is one of the Standard Type1 Fonts then DO NOT RENAME. 
        if (currFontNameInLowerCase.indexOf("helvetica") != -1 
            || currFontNameInLowerCase.indexOf("times") != -1
            || currFontNameInLowerCase.indexOf("courier") != -1
            || currFontNameInLowerCase.indexOf("symbol") != -1
            || currFontNameInLowerCase.indexOf("zapfdingbats") != -1)
        {
            // DO NOT RENAME if it is a Standard Type1 Font.
            return currFontName;
        }
        
        // Note: modify the below logic according to your needs.
        // The below logic just identifies the Uppercase letters and
        // adds a space character before it. It keeps the font prefix
        // unmodified.
        
        String fontNamePrefix = "";
        
        int indexOfPlusSign = currFontName.indexOf('+');
        if (indexOfPlusSign == 6)
        {
            fontNamePrefix = currFontName.substring(0, 7);
        }
        
        String fontNameWithoutPrefix = currFontName
            .substring(fontNamePrefix.length());
        
        StringBuffer newFontName = new StringBuffer();
        newFontName.append(fontNamePrefix);
        
        for (int i = 0; i < fontNameWithoutPrefix.length(); i++)
        {
            newFontName.append(fontNameWithoutPrefix.charAt(i));
            
            if ((i+1) < fontNameWithoutPrefix.length())
            {
                char nextChar = fontNameWithoutPrefix.charAt(i+1);
                if (Character.isUpperCase(nextChar) || nextChar == '-')
                {
                    newFontName.append(" ");
                }
            }
        }
        
        return newFontName.toString();
    }
}
