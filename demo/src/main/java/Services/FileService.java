package Services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.gnostice.pdfone.*;

public class FileService {

  public static JSONObject addSignature(String filename, String certName, String certPass) throws Exception {

    // Load an existing PDF document
    PdfDocument doc = new PdfDocument();
//    String path = new File(".").getCanonicalPath();
	Path path = Paths.get(filename).toRealPath();
//    String filePath = filename.split(".")[0];
    System.out.println(path);
    File file = new File(filename);
<<<<<<< HEAD
    String inputFilePath = path+"";
    Path outPath = Paths.get(inputFilePath).getParent();
    System.out.println(outPath);
    String outputFilePath = outPath+"/Signed_"+filename;
    doc.load(inputFilePath);
    String certificate = outPath + "/certificate369258.pfx";
=======
//    String path = file.getAbsolutePath();
//    System.out.println(path);
    String inputFilePath = path+"/"+filename;
    String outputFilePath = path +"/Signed_"+filename;
    doc.load(inputFilePath);
    String certificate = path +"/"+certName; 
>>>>>>> 514ebf65b270ab9a692d7746a87432662f505c38
    PDDocument document = null;
    
    certPass = decryptText(certPass);
    
    String[] csv = GenerateCSV.getCSV("CSV", 2018l, file);
    double[] dimensions = GetPDFPageSize(inputFilePath);
    double width = dimensions[0];
    double height = dimensions[1];
    String signersInfo = Signers_Info.signerInfo();
	System.out.println("Width: "+width);   
	System.out.println("height: "+height);  
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
	   }
	   
    // Save the document to file
    doc.save(outputFilePath);
    document = PDDocument.load(new File(outputFilePath));
    replaceText(document, "This document was created using Gnostice PDFOne Java Trial", "");
    replaceText(document, "www.gnostice.com", "");
    document.save(outputFilePath);
    // Close IO resources
    doc.close();
    document.close();
    return jsonConverter(csv, signersInfo);
    
    // verifySignature(outputFilePath	);
  }
  
  public static File getFile(File file) {
	  System.out.println(file);
	  return file;
  }
  
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
  
  public static String decryptText(String cipherText) throws Exception {
      // AES defaults to AES/ECB/PKCS5Padding in Java 7
          Cipher aesCipher = Cipher.getInstance("AES");
          String password = "validacionesKey";
          byte[] saltBytes = {0, 1, 2, 3, 4, 5, 6};
          int pswdIterations = 65536;
          int keySize = 128;
          SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
          PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, pswdIterations, keySize);
          SecretKey secretKey = factory.generateSecret(spec);
          SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
          aesCipher.init(Cipher.DECRYPT_MODE, secret);
          byte[] byteCipherText = cipherText.getBytes();
          byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
          return new String(bytePlainText);
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
                      previous.setValue(string.getBytes());
                  } else if (op.getName().equals("TJ")) {
                      COSArray previous = (COSArray) tokens.get(j - 1);
                      for (int k = 0; k < previous.size(); k++) {
                          Object arrElement = previous.getObject(k);
                          if (arrElement instanceof COSString) {
                              COSString cosString = (COSString) arrElement;
                              String string = cosString.getString();

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
                          cosString2.setValue(replacement.getBytes());

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
          ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
          tokenWriter.writeTokens(tokens);
          out.close();
          page.setContents(updatedStream);
      }

      return document;
  }

  public static double[] GetPDFPageSize(String filename) {
      
      // Open first document
      Document pdfDocument = new Document(filename);
              
      // Adds a blank page to pdf document
      Page page = pdfDocument.getPages().size() > 0 ? pdfDocument.getPages().get_Item(1): pdfDocument.getPages().add();
                                                            
      // Get page height and width information
      System.out.println(page.getPageRect(true).getWidth() + ":" + page.getPageRect(true).getHeight());
      
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