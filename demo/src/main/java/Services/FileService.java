package Services;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.pdf.PKCS7;
import com.aspose.pdf.facades.PdfFileSignature;
import com.spire.pdf.PdfDocument;
/*
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Map;
import com.aspose.pdf.Document;
import com.aspose.pdf.SignatureField;
import com.aspose.pdf.facades.Form;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.aspose.pdf.Color;
import com.aspose.pdf.SignatureCustomAppearance;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import com.itextpdf.text.pdf.PdfReader;
import com.gnostice.pdfone.*;
*/

public class FileService {
	
	private final static Logger logger = LoggerFactory.getLogger(FileService.class);
//	private static FileNameMap MIMETYPES = URLConnection.getFileNameMap();
//	public static String downloadURL = "https://storage.cloud.google.com/validacion-de-documentos.appspot.com/signedDocuments"; 
//	public static String downloadOriginalURL = "https://storage.cloud.google.com/validacion-de-documentos.appspot.com/originalDocuments";
	public static String downloadOriginalURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/originalDocuments%2F%s?alt=media";
	public static String downloadURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/signedDocuments%2F%s?alt=media";
	private final Path root = Paths.get("Uploads");
	
	public void init() {
	    try {
	      Files.createDirectory(root);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not initialize folder for upload!");
	    }
	}
	
//	public static String[] getAbsolutePath(String filename) throws IOException {
//		Path path = Paths.get(filename).toRealPath();
//		String inputFilePath = path+"";
//		String outPath = path.getParent()+"";
//		String[] paths = {inputFilePath, outPath};
//		return paths;
//	}
	
	public static String[] getAbsolutePath(String dir, String filename) throws IOException {
		String inputFilePath = Path.of(dir+"/"+filename).toAbsolutePath().toString();
		String outPath = Path.of(dir).toAbsolutePath().toString();
		String[] paths = {inputFilePath, outPath};
		return paths;
	}
	
	@SuppressWarnings("resource")
	public static HashMap<String, Object> addSignature(String filename, String certName, HashMap<String, Object> certMetadata, HashMap<String, Object> docMetadata,HashMap<String, Object> certResponse) throws IOException  {
		HashMap<String, Object> signedResponse = SignatureService.IsPdfSigned("originalDocs",filename);
		boolean signed = (boolean) signedResponse.get("Signed");
		PdfDocument doc = new PdfDocument();
		File file = new File("originalDocs/"+filename);
		String inputFilePath = getAbsolutePath("originalDocs", filename)[0];
		String outDirPath []= getAbsolutePath("signedDocs", "/Signed_"+filename);
		String outputFilePath =  outDirPath[0];
		System.out.println(outputFilePath);
//		String outputFilePath = Path.of("signedDocs/Signed_"+filename).toAbsolutePath().toString();
		String certificate = getAbsolutePath("certificates", certName)[0]; 
		String certPass = (String) certMetadata.get("certPass");
		
		PdfFileSignature pdfSign = new PdfFileSignature();
		
		pdfSign.bindPdf(inputFilePath);
		try {
			if(!signed) {
				doc.loadFromFile(inputFilePath);
				String signersInfo = (String) Signers_Info.signerInfo(certificate, certPass).get("signersInfo");
//				logger.info("This document is up to be signed named: {}", filename);
//				removeFile(inputFilePath);
				int x = (int) ((doc.getPages().get(0).getActualSize().getWidth()));
				int y = (int) ((doc.getPages().get(0).getActualSize().getHeight()));
				
				int coordY = y-(y+135);
				Rectangle rect = new Rectangle(10, coordY, x, 200);
//				pdfSign.setSignatureAppearance ( outDirPath + "/UNEAT.jpg");
				PKCS7 pkcs = new PKCS7(certificate, certPass);
				pkcs = SignatureConfig.configSignature(pkcs);
				pdfSign.setCertificate(certificate, certPass);
//				System.out.println(metadata);
				String[] csv = GenerateCSV.getCSV("CSV", 2018l, file, docMetadata);
//				pdfSign.sign(1, signersInfo, SignatureConfig.getHostName(), csv[1], true, rect, pkcs);
				
				pdfSign.sign(1, signersInfo, SignatureConfig.getHostName(), csv[1], true, rect, pkcs);
//				pdfSign.sign(1, "gggggggg", SignatureConfig.getHostName(), csv[1], true, rect, pkcs);
				logger.info("File is signed {}", outputFilePath);	
				pdfSign.save(outputFilePath);
//				removeFile(outputFilePath);
				pdfSign.close();
				return SignatureConfig.jsonConverter(csv, signersInfo, filename, outDirPath[1], certResponse, docMetadata);				
			}else {
				HashMap<String, Object> Signed = new HashMap<String, Object>();
				logger.info("This document is already signed");
				Signed.put("preSigned", true);
				Signed.put("SignedRes", "This document is already signed");
				return Signed;
			}
		} catch (Exception e) {
//			removeFile(inputFilePath);
			
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			pdfSign.close();
			return responseObject;
		}		
	}
	
	public static void removeFile(String filename) {
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