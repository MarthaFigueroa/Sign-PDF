package Services;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.aspose.pdf.Color;
import com.aspose.pdf.PKCS7;
import com.aspose.pdf.SignatureCustomAppearance;

public class SignatureConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(SignatureConfig.class);
//	public static String downloadURL = "https://storage.cloud.google.com/validacion-de-documentos.appspot.com/signedDocuments"; 
//	public static String downloadOriginalURL = "https://storage.cloud.google.com/validacion-de-documentos.appspot.com/originalDocuments";
	public static String downloadURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/";
//	public static String downloadOriginalURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/originalDocuments";
//	public static String downloadURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/signedDocuments";

	public static HashMap<String, Object> jsonConverter(String[] csv, String signersInfo, String originalFileName, String parent, HashMap<String, Object> responseCert, HashMap<String, Object> docMetadata) throws Exception {
		String signedFileName = "Signed_"+originalFileName;
		String signedPath =parent+"/"+signedFileName;
		  
		File signed = new File(signedPath);
		
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		  
		String signedFiledHash = GenerateCSV.generateHash("CSV", 2018l, signed);
		HashMap<String, Object> response= new HashMap<String, Object>();  
//		HashMap<String, Object> responseCert = new HashMap<String, Object>();
//        //		  responseCert.put("id", "1");
//		setResponseCert(responseCert, signersInfo, certMetadata);
//        responseCert.put("Signers", signersInfo);
//        responseCert.put("File", downloadURL+"certificates%2F"+certMetadata.get("name")+"?alt=media");
//        responseCert.put("Filename", certMetadata.get("name"));
//        responseCert.put("Created_at", timeStampMillis);
//        responseCert.put("LastModified", certMetadata.get("lastModified"));
//        responseCert.put("Size", certMetadata.get("size"));
//        responseCert.put("type", certMetadata.get("type"));
                
		HashMap<String, Object> responseFile = new HashMap<String, Object>();
        //		  responseFile.put("id", "1");
		responseFile.put("Filename", originalFileName);
        responseFile.put("File_hash", csv[0]);
        responseFile.put("File", downloadURL+"originalDocuments%2F"+originalFileName+"?alt=media");
        responseFile.put("LastModified_originalFile", docMetadata.get("lastModified"));
        responseFile.put("Created_at", timeStampMillis);
        responseFile.put("Size_originalFile", docMetadata.get("size"));
        responseFile.put("Type_originalFile", docMetadata.get("type"));
        
        responseFile.put("Signed_filename", signedFileName);
        responseFile.put("Signed_file_hash", signedFiledHash);
        responseFile.put("Signed_file", downloadURL+"signedDocuments%2F"+signedFileName+"?alt=media");
        responseFile.put("CSV", csv[1]);
        responseFile.put("Signers", signersInfo);
        responseFile.put("LastModified_signedFile", timeStampMillis);
        responseFile.put("Size_signedFile", docMetadata.get("size"));
        responseFile.put("Type_signedFile", docMetadata.get("type"));
        
        response.put("FileData", responseFile);
        response.put("CertData", responseCert);
        response.put("preSigned", false);
        
//        logger.info("Response Data: {}", response);
		return response;
	}
	
	public static PKCS7 configSignature(PKCS7 pkcs) {
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
	  
	public static String getHostName(){
		String hostname = "Unknown";
		
		hostname = System.getenv("username");
		System.out.println(hostname);
		return hostname;
	}

	public static HashMap<String, Object> setResponseCert(HashMap<String, Object> responseCert, String signer, HashMap<String, Object> certMetadata) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		responseCert.put("Signers", signer);
		responseCert.put("File", downloadURL+"certificates%2F"+certMetadata.get("name")+"?alt=media");
		responseCert.put("Filename", certMetadata.get("name"));
		responseCert.put("Created_at", timeStampMillis);
		responseCert.put("LastModified", certMetadata.get("lastModified"));
		responseCert.put("Size", certMetadata.get("size"));
		responseCert.put("type", certMetadata.get("type"));
		
		return responseCert;
	}
	
	public static HashMap<String, Object> setCertMetadata(HashMap<String, Object> certMetadata, String certName) {
		// TODO Auto-generated method stub
		String certificate;
		try {
			certificate = FileService.getAbsolutePath("certificates", certName)[0];
			String certPass = (String) certMetadata.get("certPass");
			HashMap<String, Object> signersInfo = Signers_Info.signerInfo(certificate, certPass);
			String signer = (String) signersInfo.get("signersInfo");
			System.out.println(signer);
			HashMap<String, Object> responseCert = new HashMap<String, Object>();
			//		  responseCert.put("id", "1");
			if(signer != null) {
				setResponseCert(responseCert, signer, certMetadata);
//				responseCert.put("Signers", signer);
//				responseCert.put("File", downloadURL+"certificates%2F"+certMetadata.get("name")+"?alt=media");
//				responseCert.put("Filename", certMetadata.get("name"));
//				responseCert.put("LastModified", certMetadata.get("lastModified"));
//				responseCert.put("Size", certMetadata.get("size"));
//				responseCert.put("type", certMetadata.get("type"));
			}else {
				responseCert.put("Signers", null);
			}
			return responseCert;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			return responseObject;
		} 
	}
}
