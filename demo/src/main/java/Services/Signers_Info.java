package Services;

import java.io.File;
import java.io.IOException;

import com.spire.pdf.security.PdfCertificate;

public class Signers_Info {
    public static String signerInfo() throws IOException{       
        String path = new File(".").getCanonicalPath();
        String certificate = path + "/certificate369258.pfx";
        PdfCertificate cert = new PdfCertificate(certificate, "369258");
        String user = cert.getIssuer();
        int start = user.indexOf("O=")+2;
        int end = user.indexOf(", C=ES");
        System.out.println(user.substring(start, end));
        return user.substring(start, end);
  }
}
