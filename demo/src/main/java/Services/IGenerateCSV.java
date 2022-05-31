package Services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public interface IGenerateCSV {
	
	public String toHex(byte[] bytes) throws IOException;
	public String toHex(byte[] bytes, HashMap<String, Object> docMetadata) throws IOException;
	public String generateHash(String prefix, Long uuid, File file) throws IOException, Exception;
	public String[] getCSV(String prefix, Long uuid, File file, HashMap<String, Object> docMetadata) throws Exception;
	public void checkParams(String prefix, Long uuid) throws Exception;
	public String getCSVInternal(String prefix, Long uuid, String hashBase16);
	public int hashCSV(String csv);

}
