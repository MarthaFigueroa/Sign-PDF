package Services;

import java.util.HashMap;

public interface ISignersInfo {

	public HashMap<String, Object> signerInfo(String certificate, String certPass);
	
}
