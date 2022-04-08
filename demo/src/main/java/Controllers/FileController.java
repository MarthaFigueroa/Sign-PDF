package Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.commons.io.IOUtils;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.pdf.PKCS7;

import Entities.Links;
import Services.FileService;
import Services.FirebaseStorageStrategy;
import Services.SignatureConfig;
import Services.Signers_Info;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class FileController {
    
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);
    public static String downloadOriginalURL = "https://storage.cloud.google.com/validacion-de-documentos.appspot.com/originalDocuments";
    
    @GetMapping(path = "files")
    public void signFile() throws Exception {
        // log.info("UsersController:  list users");
        System.out.println("File Controller: signFile GET");
        
    }
    
	@PostMapping("/uploadOriginalFile")
    public ResponseEntity<?> uploadOriginalFile(@RequestBody MultipartFile file, @RequestPart("data") @Validated JSONObject data) throws Exception { //, @RequestPart("data") @Validated JSONObject data
    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	String metadataCont = (String) data.get("metadata");
    	HashMap<String, Object> responseObject = new HashMap<String, Object>();
		logger.info("Uploaded Unsigned File named: {}", fileName);
		JSONParser parser = new JSONParser();  
    	JSONObject json = (JSONObject) parser.parse(metadataCont); 
		responseObject.put("metadata", json);
		responseObject.put("signed", false);
		responseObject.put("file",downloadOriginalURL);
		return ResponseEntity.ok(responseObject);
    }
    
    @PostMapping("/uploadSigned")
//    @RequestParam("file") MultipartFile multipartFile, @RequestParam("filename") String filename
    public static Object upload(@RequestParam("file") MultipartFile signedFile, @RequestPart("data") @Validated JSONObject data) throws IOException {
    	String filename = (String) data.get("filename");
    	logger.info("Uploading Signed File named: {}", filename);
        return FirebaseStorageStrategy.upload2(signedFile, filename, "signedDocuments");
    }
        
    @GetMapping("/documents")
//  @RequestParam("file") MultipartFile multipartFile, @RequestParam("filename") String filename
  public static ArrayList<Links> getAllFiles() throws Exception {
  	logger.info("Getting Documents");
  	System.out.println(FirebaseStorageStrategy.getLinks());
      return FirebaseStorageStrategy.getLinks();
  }

//    @PostMapping("/profile/pic/{fileName}")
//    public Object download(@PathVariable String fileName) throws Exception {
//        System.out.println("HIT -/download | File Name : "+ fileName);
//        return FirebaseStorageStrategy.download(fileName);
//    }
//    
	@PostMapping("/verify")
    public ResponseEntity<?> verifySignedFile(@RequestBody JSONObject requestObject) throws Exception {
        System.out.println("File Controller: signFile gg");
        String filename = (String) requestObject.get("filename");
        logger.info("Verifying Signed File: {}", filename);
        HashMap<String, Object> responseObject = SignatureConfig.IsPdfSigned(filename);
        return ResponseEntity.ok(responseObject);
    }
	@SuppressWarnings("unchecked")
	@PostMapping("/sign")
    public ResponseEntity<?> signFile(@RequestBody MultipartFile file, @RequestBody MultipartFile cert, @RequestPart("data") @Validated JSONObject data) throws Exception {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		HashMap<String, Object>  certMetadata = (HashMap<String, Object> ) data.get("certMetadata");
		HashMap<String, Object> docMetadata = (HashMap<String, Object>) data.get("docMetadata");
		String certName = (String) certMetadata.get("name");
		Path path = Paths.get(fileName);
		Path certPath = Paths.get(certName);
//    	String certName = StringUtils.cleanPath(cert.getOriginalFilename());
//		String certName = (String) data.get("certName");
//    	String certPassName = (String) certPass;
//    	String certPassName = (String) data.get("certPass");
//    	String metadataCont = (String) data.get("metadata");
    	
//    	HashMap<String, Object> certData = new HashMap<String, Object>(); 
//    	certData.put("name", (String) certName);
//    	certData.put("lastModified", (String) certMetadata.get("lastModified"));
//    	certData.put("size", certMetadata.get("size"));
//    	certData.put("type", (String) certMetadata.get("type"));
//    	certData.put("certPass", (String) certMetadata.get("certPass"));
    	
    	System.out.println(path);
    	System.out.println(certName);
    	System.out.println(docMetadata);
    	System.out.println(certMetadata);
    	
    	//    	JSONParser parser = new JSONParser();  
//    	JSONObject json = (JSONObject) parser.parse(certDataCont); 
        FirebaseStorageStrategy.upload2(file, fileName, "originalDocuments");
    	logger.info("Starting Signing Process with File named: {}", fileName);    	    	
    	try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			Files.copy(cert.getInputStream(), certPath, StandardCopyOption.REPLACE_EXISTING);    			
		
		
			HashMap<String, Object> responseObject = FileService.addSignature(fileName, certName, certMetadata, docMetadata);
	//    		String signFilename = (String) responseObject.get("Signed_file");
			
			if(responseObject.get("Error") == null) {
				if(!(boolean) responseObject.get("preSigned")) {
					System.out.println(responseObject.get("FileData"));
					HashMap<String, Object> responseFile = (HashMap<String, Object>) responseObject.get("FileData");
					logger.info("Signed File named: {}", responseFile.get("Signed_filename"));    			
				}    			
			}
			return ResponseEntity.ok(responseObject);
    	}catch(IOException e) {
			System.out.println(e.getMessage());
    		String msg = fileName+": The process cannot access the file because it is being used by another process";
    		System.out.println(msg);
			if(e.getMessage().equals(msg)) {
				System.out.println("HHHHHHH");
				File fileToClose = new File(fileName);
		        if (fileToClose.exists()) {
		            FileService.removeFile(fileName);
//		            fileToClose.
		        }
			}
    		HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
//			logger.error("Error: {}", e);
			return ResponseEntity.ok(responseObject);  		
		}
    }       
	
	@GetMapping("/file/{filename}")
    public ResponseEntity<?> getFile(@PathVariable String filename) throws IOException{
    	File file = new File(filename);
    	InputStreamResource resource = new InputStreamResource(new FileInputStream(file));   
    	return ResponseEntity.ok()
//    			.header("Content-Disposition", "attachment; filename=" + filename)
    			.lastModified(file.lastModified())
    			.contentLength(file.length())
			    .body(resource);  
    }
	
	@SuppressWarnings("unchecked")
	@PostMapping("/certs")
    public ResponseEntity<?> setCertificates(@RequestBody MultipartFile cert, @RequestPart("data") @Validated JSONObject data) throws IOException {
		HashMap<String, Object>  certMetadata = (HashMap<String, Object> ) data.get("certMetadata");
		String certName = (String) certMetadata.get("name");
		Path certPath = Paths.get(certName);
		System.out.println(certPath);
		
		try {
			Files.copy(cert.getInputStream(), certPath, StandardCopyOption.REPLACE_EXISTING);
			
			HashMap<String, Object> responseObject = SignatureConfig.setCertMetadata(certMetadata, certName);
			return ResponseEntity.ok(responseObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			return ResponseEntity.ok(responseObject);  
		}
    }
//    @GetMapping("/file/{filename}")
//    public ResponseEntity<?> getFile(@PathVariable String filename) throws IOException{
//    	File file = new File(filename);
//    	InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//    	return ResponseEntity.ok()
////    			.header("Content-Disposition", "attachment; filename=" + filename)
//    			.lastModified(file.lastModified())
//    			.contentLength(file.length())
//			    .contentType(MediaType.parseMediaType("application/pdf"))
//			    .body(resource);  
//    }
    
}