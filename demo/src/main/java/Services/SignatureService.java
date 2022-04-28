package Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.pdf.facades.PdfFileSignature;

public class SignatureService {
	
	private final static Logger logger = LoggerFactory.getLogger(SignatureService.class);
	public static String downloadOriginalURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/originalDocuments%2F%";
	public static String downloadURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/signedDocuments%2F%";
	
    @SuppressWarnings("unchecked")
	public static HashMap<String, Object> save(MultipartFile file, HashMap<String, Object> data) throws Exception {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		HashMap<String, Object>  certMetadata = (HashMap<String, Object>) data.get("certMetadata");
		HashMap<String, Object> docMetadata = (HashMap<String, Object>) data.get("docMetadata");
		String certName = (String) certMetadata.get("name");
		System.out.println(certName);
//		String certFilePath = FileService.getAbsolutePath("certificates/"+certName)[0]; 
		Path certFilePath = Path.of("certificates/"+certName).toAbsolutePath();
//		HashMap<String, Object> downloaded = 
				FirebaseStorageStrategy.downloadFile(certName, certFilePath, "certificates");
//		Path path = Paths.get(fileName);
		Path path = Paths.get("originalDocs/"+fileName);
//		Path certPath = Paths.get(certName);
		FirebaseStorageStrategy.upload2(file, fileName, "originalDocuments");
//    	logger.info("Starting Signing Process with File named: {}", fileName); 

    	try {
    		HashMap<String, Object> responseObject = new HashMap<String, Object>();
//    		if((boolean) downloaded.get("Exists")) {
    			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);	
    	        //		  responseCert.put("id", "1");
    			HashMap<String, Object> responseCert = SignatureConfig.setCertMetadata(certMetadata, certName);
    			System.out.println(responseCert);
    			if(responseCert.get("Signers")!= null) {
	    			responseObject  = FileService.addSignature(fileName, certName, certMetadata, docMetadata, responseCert);
	    			if(responseObject.get("Error") == null) {
	    				if(!(boolean) responseObject.get("preSigned")) {
	    					System.out.println(responseObject.get("FileData"));
	    					HashMap<String, Object> responseFile = (HashMap<String, Object>) responseObject.get("FileData");
	    					logger.info("Signed File named: {}", responseFile.get("Signed_filename"));    			
	    				}    			
	    			}      				
    			}else {
    				HashMap<String, Object> message = new HashMap<String, Object>();
    				message.put("message", "The specified network password is not correct.");
    				responseObject.put("Error", message.put("message", "The specified network password is not correct."));
    			}
//    		}else {
//				responseObject = downloaded;
//			}  
			return responseObject;
		} catch (IOException e) {
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			return responseObject;  	
		}	
	}

    public static HashMap<String, Object> IsPdfSigned(String dir, String filename) throws IOException {
		PdfFileSignature pdfSign = new PdfFileSignature();
		//	      JSONObject responseObject = new JSONObject();
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		filename = FileService.getAbsolutePath(dir, filename)[0];
		pdfSign.bindPdf(filename);
		if (pdfSign.containsSignature()) {
			System.out.println("CSV: "+pdfSign.getLocation("Signature1"));
			responseObject.put("Signed", true);
			responseObject.put("CSV", pdfSign.getLocation("Signature1"));
			responseObject.put("Message", "Document Signed");  	  
		}else {
			responseObject.put("Signed", false);
			responseObject.put("Message", "Document not Signed");
		}

		pdfSign.close();
		  
		return responseObject;
	}
    
}
