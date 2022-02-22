import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFormField;
import com.gnostice.pdfone.PdfFormSignatureField;
import com.gnostice.pdfone.PdfSignature;

public class FillBlankSignatureFieldUsingPFXCertificate
{
    public static void main(String[] args) throws IOException, PdfException
    {
        try
        {
            PdfDocument d = new PdfDocument();
            d.load(args[0]);
            
            // Create a PdfSignature object for filling the existing blank signature form field
            PdfSignature pdfSignature = new PdfSignature(
                args[1], // .pfx file path
                args[2], // password for the .pfx file
                "I am approving this document", 
                "India",
                "email@email.com", 
                1);
            
            // specify if the signature should be detached. By default, this is false.
//            pdfSignature.setDetached(true);
            
            List al = d.getAllFormFields(PdfFormField.TYPE_SIGNATURE);
            
            for (Iterator iterator = al.iterator(); iterator.hasNext();)
            {
                PdfFormSignatureField sigFld = (PdfFormSignatureField) iterator.next();
                
                if (sigFld.isUnsigned())
                {
                    // Fill the Signature form field with pdfSignature object as the value
                    sigFld.fill(pdfSignature);
                    
                    // specify whether validation result appearance
                    // should be included so that the viewer that
                    // renders this signature will show a validation
                    // result mark and text upon signature validation.
                    // Note: since including the validation result
                    // appearance is a violation of PAdES standard it
                    // is false by default.
//                    sigFld.setIncludeValidationResultAppearance(true);
                    
                    // specify if validation result appearance text
                    // should not be shown when
                    // IncludeValidationResultAppearance is true.
//                    sigFld.setIncludeValidationResultAppearanceText(false);
                    
                    break;
                }
            }

            d.setOpenAfterSave(true);
            d.save(args[3]);
            d.close();
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage : java FillBlankSignatureField "
                    + "<input file path> <.pfx file path> password_for_pfx_file <output file path>");
        }
    }
}