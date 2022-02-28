import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;

/*
 * PDFOne (for Java) automatically takes care of writing the
 * Creation/Modified date to the output document. You can also
 * customize the setting of Creation Date and Modified Date that needs
 * to be written to the PDF document.
 */

/*
Details of the letters that need to be used in the format string while creating 
the object of SimpleDateFormat object below.
========================================
Letter  Date / Time Component   Examples 
========================================
G       Era designator          AD 
y       Year                    1996; 96 
M       Month in year           July; Jul; 07 
w       Week in year            27 
W       Week in month           2 
D       Day in year             189 
d       Day in month            10 
F       Day of week in month    2 
E       Day in week             Tuesday; Tue 
a       Am/pm marker            PM 
H       Hour in day (0-23)      0 
k       Hour in day (1-24)      24 
K       Hour in am/pm (0-11)    0 
h       Hour in am/pm (1-12)    12 
m       Minute in hour          30 
s       Second in minute        55 
S       Millisecond             978 
z       Time zone               Pacific Standard Time; PST; GMT-08:00 
Z       Time zone               -0800 
*/
public class DocInfoDatesDemo
{
    public static void main(String[] args) throws IOException,
        PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
    
            // While creating a new document if you don't want to set
            // the CreationDate and ModDate then you can set them as null
            // d.setCreationDate(null);
            // d.setModifiedDate(null);
            
            // During document saving, PDFOne for Java automatically writes
            // the creation date and modified date as the current date and
            // time.
            // Note: If you want to set a different date for
            // creation/modified date you can use the methods
            // setCreationDate(Date date) and setModifiedDate(Date date).
            
            // Create a Date object with the desired date
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy H:m:s z");
            Date date = null;
            
            try
            {
                // Set the date information according to the format
                // string specified while constructing the object of
                // SimpleDateFormat
                date = dateFormat.parse("20/7/2005 22:34:20 IST");
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            
            d.setCreationDate(date);
            // you can also set null values if you don't want to set any
            // of the creation/modified date
            d.setModifiedDate(null);
    
            d.setOpenAfterSave(true);
            d.save(args[0]);
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Usage: java DocInfoDatesDemo <output file path>");
        }
    }
}
