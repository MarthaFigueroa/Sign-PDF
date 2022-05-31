package Services;

import java.util.HashMap;

public interface ICertificateConfig {
	
	public HashMap<String, Object> setCertRes(HashMap<String, Object> responseCert, HashMap<String, Object> certMetadata);
	public HashMap<String, Object> setCertData(HashMap<String, Object> certMetadata, String certName);

}
