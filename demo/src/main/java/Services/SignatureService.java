package Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.pdf.Color;
import com.aspose.pdf.PKCS7;
import com.aspose.pdf.SignatureCustomAppearance;
import com.aspose.pdf.facades.PdfFileSignature;

import Repositories.FirebaseStorageStrategy;

public class SignatureService implements ISignatureService{
	
	private final static Logger logger = LoggerFactory.getLogger(SignatureService.class);
	public static String downloadURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/";

	public HashMap<String, Object> responseCert = new HashMap<String, Object>();
	
    @SuppressWarnings("unchecked")
	public HashMap<String, Object> save(MultipartFile file, HashMap<String, Object> data) throws Exception {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		HashMap<String, Object>  certMetadata = (HashMap<String, Object>) data.get("certMetadata");
		HashMap<String, Object> docMetadata = (HashMap<String, Object>) data.get("docMetadata");
		String certName = (String) certMetadata.get("name");
		Path certFilePath = Path.of("certificates/"+certName).toAbsolutePath();
		FirebaseStorageStrategy.downloadFile(certName, certFilePath, "certificates");
		Path path = Paths.get("originalDocs/"+fileName);
    	try {
    		HashMap<String, Object> responseObject = new HashMap<String, Object>();
    			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    			CertificateConfig certificateConfig = new CertificateConfig();
    			responseCert = certificateConfig.setCertData(certMetadata, certName);
    			if(responseCert.get("Signers")!= null) {
    				FileService fileService = new FileService();
	    			responseObject  = fileService.addSignature(fileName, certName, certMetadata, docMetadata, responseCert);
	    			if(responseObject.get("Error") == null) {
	    				if(!(boolean) responseObject.get("preSigned")) {
	    					FirebaseStorageStrategy.upload(file, fileName, "originalDocuments");
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
			return responseObject;
		} catch (IOException e) {
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			return responseObject;  	
		}	
	}

    public HashMap<String, Object> IsPdfSigned(String dir, String filename) throws IOException {
		PdfFileSignature pdfSign = new PdfFileSignature();
		//	      JSONObject responseObject = new JSONObject();
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		FileService fileService = new FileService();
		filename = fileService.getAbsolutePath(dir, filename)[0];
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
 
    public HashMap<String, Object> setResponse(HashMap<String, Object> data, HashMap<String, Object> docMetadata, 
    		HashMap<String, Object> responseCert) {
		String signedFileName = "Signed_"+data.get("filename");
//		String signedPath =parent+"/"+signedFileName;		  
//		File signed = new File(signedPath);
//		GenerateCSV generateCSV = new GenerateCSV();
//		String signedFiledHash = generateCSV.generateHash("CSV", 2018l, signed);
		
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		HashMap<String, Object> response= new HashMap<String, Object>();  
                
		HashMap<String, Object> responseFile = new HashMap<String, Object>();
        //		  responseFile.put("id", "1");
		responseFile.put("Filename", data.get("filename"));
        responseFile.put("File_hash", data.get("file_hash"));
        responseFile.put("File", downloadURL+"originalDocuments%2F"+data.get("filename")+"?alt=media");
        responseFile.put("LastModified_originalFile", docMetadata.get("lastModified"));
        responseFile.put("Created_at", timeStampMillis);
        responseFile.put("Size_originalFile", docMetadata.get("size"));
        responseFile.put("Type_originalFile", docMetadata.get("type"));
        
        responseFile.put("Signed_filename", signedFileName);
        responseFile.put("Signed_file_hash", data.get("signedFiledHash"));
        responseFile.put("Signed_file", downloadURL+"signedDocuments%2F"+signedFileName+"?alt=media");
        responseFile.put("CSV", data.get("csv"));
        responseFile.put("Signers", data.get("signersInfo"));
        responseFile.put("Created_at", timeStampMillis);
        responseFile.put("LastModified_signedFile", timeStampMillis);
        responseFile.put("Size_signedFile", docMetadata.get("size"));
        responseFile.put("Type_signedFile", docMetadata.get("type"));
        
        response.put("FileData", responseFile);
        response.put("CertData", responseCert);
        response.put("preSigned", false);
        
//        logger.info("Response Data: {}", response);
		return response;
	}
	
	public PKCS7 configSignature(PKCS7 pkcs) {
		pkcs.setCustomAppearance( new SignatureCustomAppearance());
		pkcs.getCustomAppearance().setForegroundColor(Color.getBlack());
		pkcs.getCustomAppearance().setDigitalSignedLabel("Signed By:");
		pkcs.getCustomAppearance().setReasonLabel("Certificate's Owner");
		pkcs.getCustomAppearance().setLocationLabel("CSV");
		System.out.println("CSV: "+pkcs.getCustomAppearance().getLocationLabel());
		pkcs.getCustomAppearance().setContactInfoLabel("Signer's Hostname");
		pkcs.getCustomAppearance().setFontSize(6);
		return pkcs;	   
	}
	  
	public String getHostName(){
		String hostname = "Unknown";
		
		hostname = System.getenv("username");
		System.out.println(hostname);
		return hostname;
	}

}
