package Services;

import java.io.File;
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
	public static HashMap<String, Object> save(MultipartFile file, MultipartFile cert, HashMap<String, Object> data) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		HashMap<String, Object>  certMetadata = (HashMap<String, Object>) data.get("certMetadata");
		HashMap<String, Object> docMetadata = (HashMap<String, Object>) data.get("docMetadata");
		String certName = (String) certMetadata.get("name");
		Path path = Paths.get(fileName);
		Path certPath = Paths.get(certName);
		
		FirebaseStorageStrategy.upload2(file, fileName, "originalDocuments");
    	logger.info("Starting Signing Process with File named: {}", fileName); 
    	String originalUrl = downloadOriginalURL+file.getOriginalFilename();
    	File originalF = new File(originalUrl);
    	System.out.println(originalF);

    	try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			Files.copy(cert.getInputStream(), certPath, StandardCopyOption.REPLACE_EXISTING);    			
			HashMap<String, Object> responseObject = FileService.addSignature(fileName, certName, certMetadata, docMetadata);
//    		String signFilename = (String) responseObject.get("Signed_file");
			
			if(responseObject.get("Error") == null) {
				if(!(boolean) responseObject.get("preSigned")) {
					System.out.println(responseObject.get("FileData"));
					HashMap<String, Object> responseFile = (HashMap<String, Object>) responseObject.get("FileData");
					logger.info("Signed File named: {}", responseFile.get("Signed_filename"));    			
				}    			
			}
			return responseObject;
		} catch (IOException e) {
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			return responseObject;  	
		}	
	}

    public static HashMap<String, Object> IsPdfSigned(String filename) throws IOException {
		PdfFileSignature pdfSign = new PdfFileSignature();
		//	      JSONObject responseObject = new JSONObject();
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		filename = FileService.getAbsolutePath(filename)[0];
		pdfSign.bindPdf(filename);
		if (pdfSign.containsSignature()) {
			responseObject.put("Signed", true);
			responseObject.put("Message", "Document Signed");  	  
		}else {
			responseObject.put("Signed", false);
			responseObject.put("Message", "Document not Signed");
		}

		pdfSign.close();
		  
		return responseObject;
	}
    
}
