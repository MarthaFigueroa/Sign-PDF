package Services;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aspose.pdf.PKCS7;
import com.aspose.pdf.facades.PdfFileSignature;
import com.spire.pdf.PdfDocument;

public class FileService implements IFileService{
	
	private final static Logger logger = LoggerFactory.getLogger(FileService.class);
//	public static String downloadOriginalURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/originalDocuments%2F%s?alt=media";
	public static String downloadURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/signedDocuments%2F%s?alt=media";
	
//	public static String[] getAbsolutePath(String filename) throws IOException {
//		Path path = Paths.get(filename).toRealPath();
//		String inputFilePath = path+"";
//		String outPath = path.getParent()+"";
//		String[] paths = {inputFilePath, outPath};
//		return paths;
//	}
	
	public String[] getAbsolutePath(String directory, String filename) throws IOException {
		String inputFilePath = Path.of(directory+"/"+filename).toAbsolutePath().toString();
		String outPath = Path.of(directory).toAbsolutePath().toString();
		String[] paths = {inputFilePath, outPath};
		return paths;
	}
	
	@SuppressWarnings("resource")
	public HashMap<String, Object> addSignature(String filename, String certName, HashMap<String, Object> certMetadata, 
			HashMap<String, Object> docMetadata, HashMap<String, Object> responseCert) throws IOException  {
		SignatureService signatureService = new SignatureService();
		HashMap<String, Object> signedResponse = signatureService.IsPdfSigned("originalDocs",filename);
		boolean signed = (boolean) signedResponse.get("Signed");
		PdfDocument doc = new PdfDocument();
		File file = new File("originalDocs/"+filename);
		String inputFilePath = getAbsolutePath("originalDocs", filename)[0];
		String outDirPath []= getAbsolutePath("signedDocs", "/Signed_"+filename);
		String outputFilePath =  outDirPath[0];
		System.out.println(outputFilePath);
		String certificate = getAbsolutePath("certificates", certName)[0]; 
		String certPass = (String) certMetadata.get("certPass");
		
		PdfFileSignature pdfSign = new PdfFileSignature();
		
		pdfSign.bindPdf(inputFilePath);
		try {
			if(!signed) {
				doc.loadFromFile(inputFilePath);
				SignersInfo signersInfoData = new SignersInfo();
				String signersInfo = (String) signersInfoData.signerInfo(certificate, certPass).get("signersInfo");
				int x = (int) ((doc.getPages().get(0).getActualSize().getWidth()));
				int y = (int) ((doc.getPages().get(0).getActualSize().getHeight()));
				
				int coordY = y-(y+135);
				Rectangle rect = new Rectangle(10, coordY, x, 200);
				PKCS7 pkcs = new PKCS7(certificate, certPass);
				pkcs = signatureService.configSignature(pkcs);
				pdfSign.setCertificate(certificate, certPass);
				GenerateCSV generateCSV = new GenerateCSV();
				String[] csv = generateCSV.getCSV("CSV", 2018l, file);
				pdfSign.sign(1, signersInfo, signatureService.getHostName(), csv[1], true, rect, pkcs);
				logger.info("File is signed {}", outputFilePath);	
				pdfSign.save(outputFilePath);
				pdfSign.close();
				File signedFile = new File(outputFilePath);
				String signedFiledHash = generateCSV.generateHash("CSV", 2018l, signedFile);
				System.out.println(signedFiledHash);
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("signedFiledHash", signedFiledHash);
				data.put("csv", csv[1]);
				data.put("signersInfo", signersInfo);
				data.put("filename", filename);
				data.put("file_hash", csv[0]);
				return signatureService.setResponse(data, docMetadata, responseCert);				
			}else {
				HashMap<String, Object> Signed = new HashMap<String, Object>();
				logger.info("This document is already signed");
				Signed.put("preSigned", true);
				Signed.put("SignedRes", "This document is already signed");
				pdfSign.close();
				return Signed;
			}
		} catch (Exception e) {
			
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			pdfSign.close();
			return responseObject;
		}		
	}
	
	public void removeFile(String filename) {
		File f = new File(filename); //file to be delete  
		System.out.println(filename);
		if (f.exists()) {
			if(f.delete()){                      //returns Boolean value  
				System.out.println(f.getName() + " deleted");   //getting and printing the file name  
			} else  {
				System.out.println("failed");  
			}  
		}
	}

}