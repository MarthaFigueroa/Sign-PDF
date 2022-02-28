package Services;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.json.simple.JSONObject;

import com.aspose.pdf.DocMDPAccessPermissions;
import com.aspose.pdf.DocMDPSignature;
import com.aspose.pdf.Document;
import com.aspose.pdf.PKCS7;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextFragmentAbsorber;
import com.aspose.pdf.TextFragmentCollection;
import com.aspose.pdf.facades.PdfFileSignature;
import com.gnostice.pdfone.*;
import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.DigitalSignatureField;
import com.pdftron.pdf.Field;
import com.pdftron.pdf.PDFDoc;
import com.pdftron.pdf.PDFNet;
import com.pdftron.pdf.annots.SignatureWidget;
import com.pdftron.sdf.SDFDoc;

public class FileService {

  public static JSONObject addSignature(String filename, String certName, String certPass) throws Exception {

    // Load an existing PDF document
    PdfDocument doc = new PdfDocument();
//    String path = new File(".").getCanonicalPath();
	Path path = Paths.get(filename).toRealPath();
	Path certPath = Paths.get(certName).toRealPath();
//    String filePath = filename.split(".")[0];
    File file = new File(filename);
//    String path = file.getAbsolutePath();
//    System.out.println(path);
    String inputFilePath = path+""; //+"/"+filename;
    Path outPath = path.getParent();
    String outputFilePath = outPath +"/Signed_"+filename;
    doc.load(inputFilePath);
    String certificate = certPath +""; 
    PDDocument document = null;
    
//    certPass = decryptText(certPass);
    
    String[] csv = GenerateCSV.getCSV("CSV", 2018l, file);
    double[] dimensions = GetPDFPageSize(inputFilePath);
    double width = dimensions[0];
    double height = dimensions[1];
    String signersInfo = Signers_Info.signerInfo();
    
    for (int i = 1; i <= doc.getPageCount(); i++) {
	    // Add signature to the  document
	    doc.addSignature(certificate,  					// pathname of PFX 
	    		certPass,                               // password for PFX
	            csv[1],                  					// reason
	            getHostName(),                          // location
	            signersInfo,                            // contact info
	            i,                                      // page number
	            "Signature1",                           // field name
	            new PdfRect(10,height-70,width,60)      // Rect
		);
//	    ReplaceTextOnAllPages(outputFilePath, "This document was created using Gnostice PDFOne Java Trial", "");
//	    ReplaceTextOnAllPages(outputFilePath, "www.gnostice.com", "");
	     
    }
    // Save the document to file
    doc.save(outputFilePath);
    doc.load(outputFilePath);
    document = PDDocument.load(new File(outputFilePath));
//    replaceText(document, "This document was created using Gnostice PDFOne Java Trial", "");
//    replaceText(document, "www.gnostice.com", "");
//    replaceText(document, "lenguaje", "kk");
    
    
    document.save(outputFilePath);
    SignService.processPDF(outputFilePath, "This document was created using Gnostice PDFOne Java Trial", "h");
    // Close IO resources
    doc.close();
    document.close();
    
//    PDFNet.initialize(PDFTronLicense.Key());
    removeFile(inputFilePath);
    return jsonConverter(csv, signersInfo);
    // verifySignature(outputFilePath	);
  }
  
  @SuppressWarnings("unchecked")
public static JSONObject jsonConverter(String[] csv, String signersInfo) throws Exception {
  	JSONObject responseObject = new JSONObject();
  	responseObject.put("id", 1);
  	responseObject.put("Hex", csv[0]);
  	responseObject.put("CSV", csv[1]);
  	responseObject.put("Signers", signersInfo);
	
//	String id = (String) sampleObject.get("id");
	String hex = (String) responseObject.get("Hex");
	String CSV = (String) responseObject.get("CSV");
	
//	System.out.println("id: "+id);  
	System.out.println("Hex: "+hex);  
	System.out.println("CSV: "+CSV);  
	
//	JSONArray messages = new JSONArray();
//	messages.add("Hey!");
//	messages.add("What's up?!");
//	
//	sampleObject.put("messages", messages);
	
	System.out.println("gg: "+ responseObject);
	return responseObject;
  }
  
  public static void removeFile(String filename) {
	  File f= new File(filename);           //file to be delete  
	  if(f.delete()){                      //returns Boolean value  
		  System.out.println(f.getName() + " deleted");   //getting and printing the file name  
	  } else  {
		  System.out.println("failed");  
	  }  
  }
  
  public static void ReplaceTextOnAllPages(String filename, String searchString, String replacement) {
	  System.out.println(filename);
      try (Document pdfDocument = new Document(filename)) {
		// Create TextAbsorber object to find all instances of the input search phrase
		  TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber(searchString);
		  
		  // Accept the absorber for first page of document
		  pdfDocument.getPages().accept(textFragmentAbsorber);
			  // Get the extracted text fragments into collection
			  TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();
			  
			  // Loop through the fragments
			  for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentCollection) {
				  // Update text and other properties
				  textFragment.setText(replacement);
			  }
		  // Save the updated PDF file
		  pdfDocument.save("signed.pdf");	   
	}
  }
  
  public static PDDocument replaceText(PDDocument document, String searchString, String replacement) throws IOException {
//      if (StringUtils.isEmpty(searchString) || StringUtils.isEmpty(replacement)) {
//          return document;
//      }

      for (PDPage page : document.getPages()) {
//    	  This will parse a PDF byte stream and extract operands
          PDFStreamParser parser = new PDFStreamParser(page);
          parser.parse();
//        This will get the tokens that were parsed from the stream.
          List<?> tokens = parser.getTokens();

          for (int j = 0; j < tokens.size(); j++) {
              Object next = tokens.get(j);
              if (next instanceof Operator) {
                  Operator op = (Operator) next;

                  String pstring = "";
                  int prej = 0;

                  if (op.getName().equals("Tj")) {
                      COSString previous = (COSString) tokens.get(j - 1);
                      String string = previous.getString();
                      string = string.replaceFirst(searchString, replacement);
                      System.out.println(string);
//                      string = new String(string.getBytes(), "ISO-8859-2");
                      previous.setValue(string.getBytes("ISO-8859-2")); //ISO-8859-2
                  } else if (op.getName().equals("TJ")) {
                      COSArray previous = (COSArray) tokens.get(j - 1);
                      for (int k = 0; k < previous.size(); k++) {
                          Object arrElement = previous.getObject(k);
                          if (arrElement instanceof COSString) {
                              COSString cosString = (COSString) arrElement;
                              String string = cosString.getString();
                              System.out.println(string);
                              if (j == prej) {
                                  pstring += string;
                              } else {
                                  prej = j;
                                  pstring = string;
                              }
                          }
                      }

                      if (searchString.equals(pstring.trim())) {
                          COSString cosString2 = (COSString) previous.getObject(0);
                          cosString2.setValue(replacement.getBytes("ISO-8859-2"));//ISO-8859-2

                          int total = previous.size() - 1;
                          for (int k = total; k > 0; k--) {
                              previous.remove(k);
                          }
                      }
                  }
              }
          }
          PDStream updatedStream = new PDStream(document);
          OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
//          BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out1, true), "UTF-8"));
          ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
          tokenWriter.writeTokens(tokens);
          page.setContents(updatedStream);
          out.close();
      }

      return document;
  }

  public static double[] GetPDFPageSize(String filename) {
      
      // Open first document
      Document pdfDocument = new Document(filename);
              
      // Adds a blank page to pdf document
      Page page = pdfDocument.getPages().size() > 0 ? pdfDocument.getPages().get_Item(1): pdfDocument.getPages().add();
      
      double width = page.getPageRect(true).getWidth();
      double height = page.getPageRect(true).getHeight();
      double[] dimensions = {width, height};
      pdfDocument.close();
      
      return dimensions;
  }

  public static String getHostName(){
    String hostname = "Unknown";

    try
    {
        InetAddress addr;
        addr = InetAddress.getLocalHost();
        hostname = addr.getHostName();
    }
    catch (UnknownHostException ex)
    {
        System.out.println("Hostname can not be resolved");
    }
    return hostname;
  }

//  public static void sign(String filename, String certName, String certPass) {
	  public static void signPDF(String input_path ,
				String in_approval_field_name,
				String certName,
				String certPass,
				String output_path ) throws PDFNetException
			{
				System.out.println("================================================================================");
				System.out.println("Signing PDF document");

				// Open an existing PDF
				PDFDoc doc = new PDFDoc(input_path );

				int pgnum = doc.getPageCount();
				// Retrieve the unsigned approval signature field.
				Field found_approval_field = doc.getField(in_approval_field_name);
				DigitalSignatureField found_approval_signature_digsig_field = new DigitalSignatureField(found_approval_field);
				
				// (OPTIONAL) Add an appearance to the signature field.
//				Image img = Image.create(doc, in_appearance_img_path);
				SignatureWidget found_approval_signature_widget = new SignatureWidget(found_approval_field.getSDFObj());
//				found_approval_signature_widget.createSignatureAppearance(img);

				// Prepare the signature and signature handler for signing.
				found_approval_signature_digsig_field.signOnNextSave(certName, certPass);

				// The actual approval signing will be done during the following incremental save operation.
				doc.save(output_path, SDFDoc.SaveMode.INCREMENTAL, null);

				System.out.println("================================================================================");
			}
//   public static void verifySignature(String filename){
//     PdfFileSignature pdfSign = new PdfFileSignature();
//     // Bind PDF
//     pdfSign.bindPdf(filename);
//     // Verify signature using signature name
//     if (pdfSign.verifySigned("Signature1"))
//     {
//         if (pdfSign.isCertified()) // Certified?
//         {
//             if (pdfSign.getAccessPermissions() == DocMDPAccessPermissions.FillingInForms) // Get access permission
//             {
//                 System.out.println("Verified");
//             }
//         } 
//     }
//     pdfSign.close();
//   }
}