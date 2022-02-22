import java.io.IOException;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfEncryption;
import com.gnostice.pdfone.PdfException;

public final class EncryptedDocumentCreation
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }

    /* Usage : java EncryptedDocumentCreation <output file path> */
    public static void main(String[] args) throws IOException,
        PdfException
    {
        ////////////////////////////////////
        // Creating an encrypted document //
        ////////////////////////////////////

        try
        {
            /* Create a new PdfDocument instance */
            PdfDocument d = new PdfDocument();
    
            /* Obtain PdfEncryption object of the PdfDocument */
            PdfEncryption e = d.getEncryptor();
            e.setOwnerPwd("owner");
            e.setUserPwd("user");
            e.setLevel(PdfEncryption.LEVEL_128_BIT);
            e.setPermissions(PdfEncryption.AllowHighResPrint
                | PdfEncryption.AllowAccessibility);
    
            /* Change the PdfEncryption object of the PdfDocument */
            d.setEncryptor(e);
    
            d.setOpenAfterSave(true);
            
            /* Save the document object */ 
            d.save(args[0]);
    
            /* Dispose the I/O files associated with this document object */
            d.close();
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java EncryptedDocumentCreation " +
                    "<output file path> ");
        }
    }
}
