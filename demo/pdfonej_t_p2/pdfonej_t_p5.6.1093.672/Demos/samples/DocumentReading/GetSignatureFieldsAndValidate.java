

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.List;

import com.gnostice.pdfone.PDFOne;
import com.gnostice.pdfone.PdfDocument;
import com.gnostice.pdfone.PdfException;
import com.gnostice.pdfone.PdfFormField;
import com.gnostice.pdfone.PdfFormSignatureField;
import com.gnostice.pdfone.PdfSignatureInfo;
import com.gnostice.pdfone.PdfSignedDocumentAlteredStatus;
import com.gnostice.pdfone.PdfSignerStatus;

public class GetSignatureFieldsAndValidate 
{
    static
    {
        PDFOne.activate("T95VZE:W8HBPVA:74VQ8QV:LO4V8",
            "9B1HRZAP:X5853ERNE:5EREMEGRQ:TX1R10");
    }
    
    public static void main(String[] args) throws IOException, PdfException
    {
        String inputFileName = null;
        try
        {
            inputFileName = args[0];
        }
        catch (ArrayIndexOutOfBoundsException n)
        {
            System.out.println("Usage : java GetSignatureFieldsAndValidate "
                + "<input file path>");
            return;
        }
        
        PdfDocument d = new PdfDocument();
        d.load(inputFileName);
        
        List al = d.getAllFormFields(PdfFormField.TYPE_SIGNATURE);
        
        System.out.println("Number of signature fields found: " + al.size());
        
        for (int i = 0; i < al.size(); i++)
        {
            PdfFormField fld = (PdfFormField) al.get(i);
            
            System.out.println("Field Name: " + fld.getName());
            
            switch(fld.getType())
            {
                case PdfFormField.TYPE_SIGNATURE:
                    PdfFormSignatureField sigFld = (PdfFormSignatureField) fld;
                    PdfSignatureInfo signatureInfo = sigFld.validate();
                    
                    System.out.println("IsSigned: " + signatureInfo.isSigned());
                    System.out.println("IsDocumentSignatureValid: " + signatureInfo.isDocumentSignatureValid());
                    
                    // Getting the following information are restricted in the trial version of the product.
                    switch(signatureInfo.getDocumentAlteredState())
                    {
                        case PdfSignedDocumentAlteredStatus.MODIFIED_AFTER_SIGN:
                            System.out.println("DocumentAlteredState: MODIFIED_AFTER_SIGN");
                            break;
                            
                        case PdfSignedDocumentAlteredStatus.NOT_MODIFIED:
                            System.out.println("DocumentAlteredState: NOT_MODIFIED");
                            break;
                    }
                    
                    switch(signatureInfo.getSignerState())
                    {
                        case PdfSignerStatus.CERTIFICATE_INVALID:
                            System.out.println("SignerState: CERTIFICATE_INVALID");
                            break;
                            
                        case PdfSignerStatus.VALID:
                            System.out.println("SignerState: VALID");
                            break;
                    }
                    
                    System.out.println("CertificateIssuerDN: " + signatureInfo.getCertificateIssuerDN());
                    System.out.println("CertificateSerialNumber: " + signatureInfo.getCertificateSerialNumber());
                    System.out.println("CertificateVersion: " + signatureInfo.getCertificateVersion());
                    System.out.println("SignedOnDate: " + signatureInfo.getSignedOnDate());
                    System.out.println("ValidFromDate: " + signatureInfo.getValidFromDate());
                    System.out.println("ValidToDate: " + signatureInfo.getValidToDate());
                    
                    // Get the certificate object of the signer
                    System.out.println();
                    X509Certificate issuerCertificate = signatureInfo.getIssuerCertificate();
                    System.out.println("Certificate details: ");
                    System.out.println(issuerCertificate);
                    
                    
                    break;
            }
            
            System.out.println("--------------------------");
        }
        
        d.close();
    }
}
