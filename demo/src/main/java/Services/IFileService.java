package Services;

import java.io.IOException;
import java.util.HashMap;

public interface IFileService {

	public String[] getAbsolutePath(String dir, String filename) throws IOException;
	public HashMap<String, Object> addSignature(String filename, String certName, HashMap<String, Object> certMetadata, HashMap<String, Object> docMetadata, HashMap<String, Object> responseCert) throws IOException;
	public void removeFile(String filename);	
}
