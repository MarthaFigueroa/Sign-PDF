package Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.commons.io.IOUtils;
//import org.json.simple.parser.JSONParser;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import org.springframework.util.StringUtils;
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

import Services.FileService;
//import Entities.Links;
import Services.FirebaseStorageStrategy;
import Services.SignatureConfig;
import Services.SignatureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class FileController {
    
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);
//    public static String downloadOriginalURL = "https://storage.cloud.google.com/validacion-de-documentos.appspot.com/originalDocuments";

    @GetMapping(path = "files")
    public void signFile() throws Exception {
        // log.info("UsersController:  list users");
        System.out.println("File Controller: signFile GET");
    }
    
//	@PostMapping("/uploadOriginalFile")
//    public ResponseEntity<?> uploadOriginalFile(@RequestBody MultipartFile file, @RequestPart("data") @Validated JSONObject data) throws Exception { //, @RequestPart("data") @Validated JSONObject data
//    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//    	String metadataCont = (String) data.get("metadata");
//    	HashMap<String, Object> responseObject = new HashMap<String, Object>();
//		logger.info("Uploaded Unsigned File named: {}", fileName);
//		JSONParser parser = new JSONParser();  
//    	JSONObject json = (JSONObject) parser.parse(metadataCont); 
//		responseObject.put("metadata", json);
//		responseObject.put("signed", false);
//		responseObject.put("file",downloadOriginalURL);
//		return ResponseEntity.ok(responseObject);
//    }
    
    @PostMapping("/uploadSigned")
//    @RequestParam("file") MultipartFile multipartFile, @RequestParam("filename") String filename
    public static Object upload(@RequestParam("file") MultipartFile signedFile, @RequestPart("data") @Validated JSONObject data) throws IOException {
    	String filename = (String) data.get("filename");
    	logger.info("Uploading Signed File named: {}", filename);
        return FirebaseStorageStrategy.upload2(signedFile, filename, "signedDocuments");
    }
        
//    @GetMapping("/documents")
////  @RequestParam("file") MultipartFile multipartFile, @RequestParam("filename") String filename
//    public static ArrayList<Links> getAllFiles() throws Exception {
//	  	logger.info("Getting Documents");
//	  	System.out.println(FirebaseStorageStrategy.getLinks());
//	  	return FirebaseStorageStrategy.getLinks();
//    }

//    @PostMapping("/profile/pic/{fileName}")
//    public Object download(@PathVariable String fileName) throws Exception {
//        System.out.println("HIT -/download | File Name : "+ fileName);
//        return FirebaseStorageStrategy.download(fileName);
//    }
//    
//	@PostMapping("/verifyDoc")
//    public ResponseEntity<?> verifySignedFile(@RequestBody String filename) throws Exception {
//		System.out.println(filename);
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		filename = filename.substring(1,filename.length() - 1);
//		String destFilePath = FileService.getAbsolutePath("./signedDocs")[1]+"/signedDocs/"+filename; 
//		HashMap<String, Object> downloaded = FirebaseStorageStrategy.downloadFile(filename, destFilePath, "signedDocuments");
//		HashMap<String, Object> responseObject = new HashMap<String, Object>();
//		if((boolean) downloaded.get("Exists")) {
//			logger.info("Verifying Signed File: {}", filename);
//			responseObject = SignatureService.IsPdfSigned("signedDocs/"+filename);
//		}else {
//			responseObject = downloaded;
//		}
////		InputStream in = new URL(url).openStream();
////		System.out.println("URL: "+url);
////		Files.copy(in, Paths.get(url), StandardCopyOption.REPLACE_EXISTING);
////		String filename = (String) requestObject.get("filename");
//        return ResponseEntity.ok(responseObject);
//    }
    
    @PostMapping("/verifyDoc")
    public ResponseEntity<?> verifySignedFile(@RequestBody MultipartFile file) throws Exception {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
//		filename = filename.substring(1,filename.length() - 1);
		System.out.println(filename);
//		String destFilePath = FileService.getAbsolutePath("./signedDocs")[1]+"/signedDocs/"+filename; 
		Path path = Paths.get("signedDocs/"+filename);
		HashMap<String, Object> responseObject = new HashMap<String, Object>();
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);	
			logger.info("Verifying Signed File: {}", filename);
			responseObject = SignatureService.IsPdfSigned("signedDocs",filename);
			FileService.removeFile(FileService.getAbsolutePath("signedDocs",filename)[0]);
			return ResponseEntity.ok(responseObject);
		}catch(Exception e) {
			responseObject.put("Error", e);
			return ResponseEntity.ok(responseObject);
		}        
    }
	
	@SuppressWarnings("unchecked")
	@PostMapping("/sign")
    public ResponseEntity<?> signFile(@RequestBody MultipartFile file, @RequestPart("data") @Validated JSONObject data) throws Exception {
		data = (JSONObject) data;
		HashMap<String, Object> responseObject = SignatureService.save(file, data);
		return ResponseEntity.ok(responseObject);  	
    }       
	
	@GetMapping("/file/{filename}")
    public ResponseEntity<?> getFile(@PathVariable String filename) throws IOException{
//		String destFilePath = FileService.getAbsolutePath("./originalDocs")[1]+"/originalDocs/"+filename; 
//		HashMap<String, Object> downloaded = FirebaseStorageStrategy.downloadFile(filename, destFilePath, "signedDocuments");
//		return ResponseEntity.ok(downloaded);
    	File file = new File("signedDocs/"+filename);
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
//		String certFilePath = FileService.getAbsolutePath("certificates/"+certName)[0]; 
//		Path certPath = Path.of("certificates/"+certName).toAbsolutePath();
		Path certPath = Paths.get("certificates/"+certName);
		System.out.println(certPath);
		
		try {
			Files.copy(cert.getInputStream(), certPath, StandardCopyOption.REPLACE_EXISTING);
			
			HashMap<String, Object> responseObject = SignatureConfig.setCertMetadata(certMetadata, certName);
			return ResponseEntity.ok(responseObject);
		} catch (Exception e) {
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