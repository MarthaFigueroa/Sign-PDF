package Services;

import java.util.HashMap;
import com.spire.pdf.security.PdfCertificate;

public class SignersInfo implements ISignersInfo{
    public HashMap<String, Object> signerInfo(String certificate, String certPass){       

    	try {
    		PdfCertificate cert = new PdfCertificate(certificate, certPass);
    		String user = cert.getIssuer();
    		int start = user.indexOf("O=")+2;
    		int end = user.indexOf(", C=ES");
    		System.out.println(user);
    		HashMap<String, Object> responseObject = new HashMap<String, Object>();
    		responseObject.put("signersInfo", user.substring(start, end));
    		return responseObject;    		
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			return responseObject;  	
    	}
//        String path = new File(".").getCanonicalPath();
//        String certificate = path + "/"+certName;
  }
}
