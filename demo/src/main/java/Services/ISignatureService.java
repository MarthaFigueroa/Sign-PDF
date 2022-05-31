package Services;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import com.aspose.pdf.PKCS7;

public interface ISignatureService {

	public HashMap<String, Object> IsPdfSigned(String dir, String filename) throws IOException;
	public HashMap<String, Object> save(MultipartFile file, HashMap<String, Object> data) throws Exception;
	public HashMap<String, Object> setResponse(HashMap<String, Object> data, HashMap<String, Object> docMetadata, HashMap<String, Object> responseCert);
	public PKCS7 configSignature(PKCS7 pkcs);
	
}
