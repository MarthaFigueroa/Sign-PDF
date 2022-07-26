package Services;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;

public class CertificateConfig implements ICertificateConfig{
	
	public String downloadURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/";

	public HashMap<String, Object> setCertRes(HashMap<String, Object> responseCert, HashMap<String, Object> certMetadata) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		responseCert.put("File", downloadURL+"certificates%2F"+certMetadata.get("name")+"?alt=media");
		responseCert.put("Filename", certMetadata.get("name"));
		responseCert.put("Created_at", timeStampMillis);
		responseCert.put("LastModified", certMetadata.get("lastModified"));
		responseCert.put("Size", certMetadata.get("size"));
		responseCert.put("Type", certMetadata.get("type"));
		return responseCert;
	}
	
	public HashMap<String, Object> setCertData(HashMap<String, Object> certMetadata, String certName) {
		String certificate;
		try {
			FileService fileService = new FileService();
			certificate = fileService.getAbsolutePath("certificates", certName)[0];
			String certPass = (String) certMetadata.get("certPass");
			SignersInfo signersInfoData = new SignersInfo();
			HashMap<String, Object> signersInfo = signersInfoData.signerInfo(certificate, certPass);
			String signer = (String) signersInfo.get("signersInfo");
			System.out.println(signer);
			HashMap<String, Object> responseCert = new HashMap<String, Object>();
			if(signer != null) {
				responseCert.put("Signers", signer);
				setCertRes(responseCert, certMetadata);
			}else {
				responseCert.put("Signers", null);
			}
			return responseCert;
		} catch (IOException e) {
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			return responseObject;
		} 
	}

}
