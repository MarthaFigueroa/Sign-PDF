package Services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;

import com.spire.pdf.security.PdfCertificate;

public class Signers_Info {
    public static HashMap<String, Object> signerInfo(String certName, String certPass){       

    	try {
    		String certificate = certName;
    		System.out.println(certificate);
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
//    		String msg = "Invalid MAC - file may have been tampered!";
//    		System.out.println(msg);
//			if(e.getMessage().equals(msg)) {
//				System.out.println("HHHHHHH");
//			}
    		HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
//			logger.error("Error: {}", e);
			return responseObject;  	
    	}
//        String path = new File(".").getCanonicalPath();
//        String certificate = path + "/"+certName;
  }
}
