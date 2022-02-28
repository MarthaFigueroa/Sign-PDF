package Services;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
//import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;

//import org.json.simple.JSONObject;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfFont;
import com.spire.pdf.graphics.PdfFontFamily;
import com.spire.pdf.graphics.PdfFontStyle;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.security.GraphicMode;
import com.spire.pdf.security.PdfCertificate;
import com.spire.pdf.security.PdfCertificationFlags;
//import com.pdftron.common.PDFNetException;
//import com.pdftron.pdf.Date;
//import com.pdftron.pdf.DigitalSignatureField;
//import com.pdftron.pdf.DigitalSignatureFieldIterator;
//import com.pdftron.pdf.Field;
//import com.pdftron.pdf.FieldIterator;
//import com.pdftron.pdf.PDFDoc;
//import com.pdftron.pdf.PDFNet;
//import com.pdftron.pdf.Page;
//import com.pdftron.pdf.Rect;
//import com.pdftron.pdf.annots.SignatureWidget;
//import com.pdftron.pdf.annots.TextWidget;
//import com.pdftron.sdf.SDFDoc;
import com.spire.pdf.security.PdfSignature;

public class SignService {
	
	public static void sign(String filename, String certName, String certPass) {

        //Load a pdf document
        PdfDocument doc = new PdfDocument();
        doc.loadFromFile(filename);

        //Load the certificate
        PdfCertificate cert = new PdfCertificate(certName, certPass);

        //Create a PdfSignature object and specify its position and size
        PdfSignature signature = new PdfSignature (doc, doc.getPages().get(0), cert, "MySignature");
        Rectangle2D rect = new Rectangle2D.Float();
        rect.setFrame(new Point.Float((float) doc.getPages().get(0).getActualSize().getWidth() - 380, (float) doc.getPages().get(0).getActualSize().getHeight() - 120), new Dimension(250, 150));
        signature.setBounds(rect);

        //Set the graphics mode
        signature.setGraphicMode(GraphicMode.Sign_Image_And_Sign_Detail);

        //Set the signature content
        signature.setNameLabel("Signer:");
        signature.setName("Jessie");
        signature.setContactInfoLabel("ContactInfo:");
        signature.setContactInfo("xxxxxxxxx");
        signature.setDateLabel("Date:");
        signature.setDate(new java.util.Date());
        signature.setLocationInfoLabel("Location:");
        signature.setLocationInfo("Florida");
        signature.setReasonLabel("Reason: ");
        signature.setReason("The certificate of this document");
        signature.setDistinguishedNameLabel("DN: ");
        signature.setDistinguishedName(signature.getCertificate().get_IssuerName().getName());
        signature.setSignImageSource(PdfImage.fromFile("C:\\Users\\Administrator\\Desktop\\cert.jpg"));

        //Set the signature font
        signature.setSignDetailsFont(new PdfFont(PdfFontFamily.Helvetica, 10f, PdfFontStyle.Bold));

        //Set the document permission
        signature.setDocumentPermissions(PdfCertificationFlags.Forbid_Changes);
        signature.setCertificated(true);

        //Save to file
        doc.saveToFile("AddSignature.pdf");
        doc.close();
    }
	
	public static void processPDF(String src, String search, String replacement) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int pNumbers = reader.getNumberOfPages();
        System.out.println(pNumbers);
        PRStream stream;
        for (int i= 1 ; i <= pNumbers;i++){
            PdfDictionary  dict = reader.getPageN(i);
            PdfObject  object = dict.getDirectObject(PdfName.XOBJECT);
            if (object instanceof PRStream) {
                stream = (PRStream) object;
                byte[] data = PdfReader.getStreamBytes(stream);
                String dd = new String(data);
                dd = dd.replaceAll(search, replacement);
                stream.setData(dd.getBytes());
                System.out.println(stream);
            }
            System.out.println(object);
        }

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("kk.pdf"));
        stamper.close();
        reader.close();
    }
//	public static JSONObject sign(String filename, String certName, String certPass) throws Exception {
//		Path path = Paths.get(filename).toRealPath();
//		Path certPath = Paths.get(certName).toRealPath();
//		String inputFilePath = path+""; //+"/"+filename;
//		Path outPath = path.getParent();
//		String outputFilePath = outPath +"/Signed_"+filename;
//		String certificate = certPath +""; 
//		PDFDoc doc = new PDFDoc(filename);
//		
//		PDFNet.initialize();
//		
//		boolean result = true;
//		
//		try
//		{
//			DigitalSignatureField approval_signature_field = doc.createDigitalSignatureField("PDFTronApprovalSig");
//			SignatureWidget widgetAnnotApproval = SignatureWidget.create(doc, new Rect(300, 287, 376, 306), approval_signature_field);
//			Page page1 = doc.getPage(1);
//			page1.annotPushBack(widgetAnnotApproval);
//			doc.save(outPath+"/signatureApproval.pdf", SDFDoc.SaveMode.REMOVE_UNUSED, null);
//		}
//		catch (Exception e)
//		{
//			System.err.println(e.getMessage());
//			e.printStackTrace(System.err);
//		}
//		
//		try
//		{
//			certifyPDF(inputFilePath,
//				"PDFTronCertificationSig",
//				certificate,
//				certPass,
//				outPath+"/signatureCertified"
//				);
//			printSignaturesInfo(outPath+"/signatureCertified");
//		}
//		catch (Exception e)
//		{
//			System.err.println(e.getMessage());
//			e.printStackTrace(System.err);
//		}
//		
//		try
//		{
//			signPDF(inputFilePath,
//				"PDFTronApprovalSig",
//				certificate,
//				certPass,
//				outputFilePath);
//			printSignaturesInfo(outputFilePath);
//		}catch (Exception e)
//		{
//			System.err.println(e.getMessage());
//			e.printStackTrace(System.err);
//		}
//		
//		File file = new File(filename);
//		String[] csv = GenerateCSV.getCSV("CSV", 2018l, file);
//		String signersInfo = Signers_Info.signerInfo();
//		
//		return FileService.jsonConverter(csv, signersInfo);
//	}
//	
//	public static void printSignaturesInfo(String in_docpath) throws PDFNetException
//	{
//		System.out.println("================================================================================");
//		System.out.println("Reading and printing digital signature information");
//
//		PDFDoc doc = new PDFDoc(in_docpath);
//		if (!doc.hasSignatures())
//		{
//			System.out.println("Doc has no signatures.");
//			System.out.println("================================================================================");
//			return;
//		}
//		else
//		{
//			System.out.println("Doc has signatures.");
//		}
//
//		
//		for (FieldIterator fitr = doc.getFieldIterator(); fitr.hasNext(); )
//		{
//			Field current = fitr.next();
//			if (current.isLockedByDigitalSignature())
//			{
//				System.out.println("==========\nField locked by a digital signature");
//			}
//			else
//			{
//				System.out.println("==========\nField not locked by a digital signature");
//			}
//
//			System.out.println("Field name: " + current.getName());
//			System.out.println("==========");
//		}
//
//		System.out.println("====================\nNow iterating over digital signatures only.\n====================");
//
//		DigitalSignatureFieldIterator digsig_fitr = doc.getDigitalSignatureFieldIterator();
//		for (; digsig_fitr.hasNext(); )
//		{
//			DigitalSignatureField current = digsig_fitr.next();
//			System.out.println("==========");
//			System.out.println("Field name of digital signature: " + new Field(current.getSDFObj()).getName());
//
//			DigitalSignatureField digsigfield = current;
//			if (!digsigfield.hasCryptographicSignature())
//			{
//				System.out.println("Either digital signature field lacks a digital signature dictionary, " +
//					"or digital signature dictionary lacks a cryptographic Contents entry. " +
//					"Digital signature field is not presently considered signed.\n" +
//					"==========");
//				continue;
//			}
//
//			int cert_count = digsigfield.getCertCount();
//			System.out.println("Cert count: " + cert_count);
//			for (int i = 0; i < cert_count; ++i)
//			{
//				byte[] cert = digsigfield.getCert(i);
//				System.out.println("Cert #" + i + " size: " + cert.length);
//			}
//
//			DigitalSignatureField.SubFilterType subfilter = digsigfield.getSubFilter();
//
//			System.out.println("Subfilter type: " + subfilter.ordinal());
//
//			if (subfilter != DigitalSignatureField.SubFilterType.e_ETSI_RFC3161)
//			{
//				System.out.println("Signature's signer: " + digsigfield.getSignatureName());
//
//				Date signing_time = digsigfield.getSigningTime();
//				if (signing_time.isValid())
//				{
//					System.out.println("Signing time is valid.");
//				}
//
//				System.out.println("Location: " + digsigfield.getLocation());
//				System.out.println("Reason: " + digsigfield.getReason());
//				System.out.println("Contact info: " + digsigfield.getContactInfo());
//			}
//			else
//			{
//				System.out.println("SubFilter == e_ETSI_RFC3161 (DocTimeStamp; no signing info)");
//			}
//
//			if (digsigfield.hasVisibleAppearance())
//			{
//				System.out.println("Visible");
//			}
//			else
//			{
//				System.out.println("Not visible");
//			}
//			
//			DigitalSignatureField.DocumentPermissions digsig_doc_perms = digsigfield.getDocumentPermissions();
//			String[] locked_fields = digsigfield.getLockedFields();
//			for (String it : locked_fields)
//			{
//				System.out.println("This digital signature locks a field named: " + it);
//			}
//
//			switch (digsig_doc_perms)
//			{
//			case e_no_changes_allowed:
//				System.out.println("No changes to the document can be made without invalidating this digital signature.");
//				break;
//			case e_formfilling_signing_allowed:
//				System.out.println("Page template instantiation, form filling, and signing digital signatures are allowed without invalidating this digital signature.");
//				break;
//			case e_annotating_formfilling_signing_allowed:
//				System.out.println("Annotating, page template instantiation, form filling, and signing digital signatures are allowed without invalidating this digital signature.");
//				break;
//			case e_unrestricted:
//				System.out.println("Document not restricted by this digital signature.");
//				break;
//			default:
//				System.err.println("Unrecognized digital signature document permission level.");
//				assert(false);
//			}
//			System.out.println("==========");
//		}
//
//		System.out.println("================================================================================");
//	}
//
//	public static void certifyPDF(String in_docpath,
//			String in_cert_field_name,
//			String in_private_key_file_path,
//			String in_keyfile_password,
//			String in_outpath) throws PDFNetException
//		{
//			System.out.println("================================================================================");
//			System.out.println("Certifying PDF document");
//
//			// Open an existing PDF
//			PDFDoc doc = new PDFDoc(in_docpath);
//
//			if (doc.hasSignatures())
//			{
//				System.out.println("PDFDoc has signatures");
//			}
//			else
//			{
//				System.out.println("PDFDoc has no signatures");
//			}
//
//			Page page1 = doc.getPage(1);
//
//			// Create a text field that we can lock using the field permissions feature.
//			TextWidget annot1 = TextWidget.create(doc, new Rect(143, 440, 350, 460), "asdf_test_field");
//			page1.annotPushBack(annot1);
//
//			/* Create a new signature form field in the PDFDoc. The name argument is optional; leaving it empty causes it to be auto-generated. However, you may need the name for later. Acrobat doesn't show digsigfield in side panel if it's without a widget. Using a Rect with 0 width and 0 height, or setting the NoPrint/Invisible flags makes it invisible. */
//			DigitalSignatureField certification_sig_field = doc.createDigitalSignatureField(in_cert_field_name);
//			SignatureWidget widgetAnnot = SignatureWidget.create(doc, new Rect(143, 287, 219, 306), certification_sig_field);
//			page1.annotPushBack(widgetAnnot);
//
//			// Prepare the document locking permission level. It will be applied upon document certification.
//			System.out.println("Adding document permissions.");
//			certification_sig_field.setDocumentPermissions(DigitalSignatureField.DocumentPermissions.e_annotating_formfilling_signing_allowed);
//			
//			// Prepare to lock the text field that we created earlier.
//			System.out.println("Adding field permissions.");
//			String[] fields_to_lock = {"asdf_test_field"};
//			certification_sig_field.setFieldPermissions(DigitalSignatureField.FieldPermissions.e_include, fields_to_lock);
//
//			certification_sig_field.certifyOnNextSave(in_private_key_file_path, in_keyfile_password);
//			
//			// (OPTIONAL) Add more information to the signature dictionary.
//			certification_sig_field.setLocation("Vancouver, BC");
//			certification_sig_field.setReason("Document certification.");
//			certification_sig_field.setContactInfo("www.pdftron.com");
//
//			// Save the PDFDoc. Once the method below is called, PDFNet will also sign the document using the information provided.
//			doc.save(in_outpath, SDFDoc.SaveMode.NO_FLAGS, null);
//
//			System.out.println("================================================================================");
//		}
//
//	public static void signPDF(String in_docpath,
//			String in_approval_field_name,
//			String in_private_key_file_path,
//			String in_keyfile_password,
//			String in_outpath) throws PDFNetException
//		{
//			System.out.println("================================================================================");
//			System.out.println("Signing PDF document");
//
//			// Open an existing PDF
//			PDFDoc doc = new PDFDoc(in_docpath);
//
//			// Retrieve the unsigned approval signature field.
//			Field found_approval_field = doc.getField(in_approval_field_name);
//			DigitalSignatureField found_approval_signature_digsig_field = new DigitalSignatureField(found_approval_field);
//			
//
//			// Prepare the signature and signature handler for signing.
//			found_approval_signature_digsig_field.signOnNextSave(in_private_key_file_path, in_keyfile_password);
//
//			// The actual approval signing will be done during the following incremental save operation.
//			doc.save(in_outpath, SDFDoc.SaveMode.INCREMENTAL, null);
//
//			System.out.println("================================================================================");
//		}
}
