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
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import Repositories.CertificatesRepository;
import Repositories.DocumentsRepository;
import Repositories.FirebaseStorageStrategy;
import Repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import Services.CertificateConfig;
import Services.FileService;
import Services.SignatureService;
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class Controller {
    private final static Logger logger = LoggerFactory.getLogger(Controller.class);
    
    @GetMapping("/certificates") 
    public static List<Map<String, Object>> getAllCertificates() throws Exception {
    	logger.info("Getting Certificates");
    	CertificatesRepository certificatesRepository = new CertificatesRepository();
    	return certificatesRepository.getAllCertificates();
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/sign")
    public ResponseEntity<?> signFile(@RequestParam("file") MultipartFile file, @RequestPart("data") @Validated JSONObject data) throws Exception {
    	data = (JSONObject) data;
    	System.out.println(data);
    	SignatureService signatureService = new SignatureService();
    	HashMap<String, Object> responseObject = signatureService.save(file, data);
    	return ResponseEntity.ok(responseObject);  	
    }           
    
    @PostMapping("/verifyDoc")
    public ResponseEntity<?> verifySignedFile(@RequestParam("file") MultipartFile file) throws Exception {
    	String filename = StringUtils.cleanPath(file.getOriginalFilename());
    	Path path = Paths.get("signedDocs/"+filename);
    	HashMap<String, Object> responseObject = new HashMap<String, Object>();
    	try {
    		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);	
    		logger.info("Verifying Signed File: {}", filename);
    		SignatureService signatureService = new SignatureService();
    		responseObject = signatureService.IsPdfSigned("signedDocs",filename);
    		FileService fileService = new FileService();
    		fileService.removeFile(fileService.getAbsolutePath("signedDocs",filename)[0]);
    		return ResponseEntity.ok(responseObject);
    	}catch(Exception e) {
    		responseObject.put("Error", e);
    		return ResponseEntity.ok(responseObject);
    	}        
    }
    
    @PostMapping("/deleteCertificate/{id}") 
    public static List<Map<String, Object>> deleteCertificate(@PathVariable String id) throws Exception {
    	logger.info("Getting Documents");
    	CertificatesRepository certificatesRepository = new CertificatesRepository();
    	return certificatesRepository.deleteCertificate(id);
    }
    
    @PostMapping("/deleteDocument/{id}") 
    public static List<Map<String, Object>> deleteDocument(@PathVariable String id) throws Exception {
    	logger.info("Deleting Document");
    	DocumentsRepository documentsRepository = new DocumentsRepository();
    	return documentsRepository.deleteDocument(id);
    }
    
    @GetMapping("/documents")
    public static List<Map<String, Object>> getAllFiles() throws Exception {
	  	logger.info("Getting Documents");
	  	DocumentsRepository documentsRepository = new DocumentsRepository();
	  	return documentsRepository.getAllDocuments();
    }
      
    @SuppressWarnings("unchecked")
	@PostMapping("/createDocument")
    public static List<Map<String, Object>> createDocument(@RequestBody JSONObject data) throws Exception {
	  	logger.info("Uploading Documents");
	  	DocumentsRepository documentsRepository = new DocumentsRepository();
//	  	System.out.println(documentsRepository.createDocument(data));
	  	return documentsRepository.createDocument(data);
    }

    @SuppressWarnings("unchecked")
	@PostMapping("/createCertificate")
    public static List<Map<String, Object>> createCertificate(@RequestBody JSONObject data) throws Exception {
	  	logger.info("Uploading Certificate");
	  	CertificatesRepository certificatesRepository = new CertificatesRepository();
	  	return certificatesRepository.createCertificate(data);
    }
	
	@PostMapping("/uploadSigned") 
    public static ResponseEntity<?> upload(@RequestParam("file") MultipartFile signedFile, @RequestPart("data") @Validated JSONObject data) throws IOException {
    	String filename = (String) data.get("filename");
    	logger.info("Uploading Signed File named: {}", filename);
        return FirebaseStorageStrategy.upload(signedFile, filename, "signedDocuments");
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
			CertificateConfig certificateConfig = new CertificateConfig();
			HashMap<String, Object> responseObject = certificateConfig.setCertData(certMetadata, certName);
			return ResponseEntity.ok(responseObject);
		} catch (Exception e) {
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			responseObject.put("Error", e);
			return ResponseEntity.ok(responseObject);  
		}
    }
	
	@GetMapping("/users") 
    public static List<Map<String, Object>> getAllUsers() throws Exception {
    	logger.info("Getting Users");
    	UsersRepository usersRepository = new UsersRepository();
    	return usersRepository.getAllUsers();
    }
    
    @PostMapping("/deleteUser/{id}/{uid}") 
    public static List<Map<String, Object>> deleteUser(@PathVariable String id, @PathVariable String uid) throws Exception {
    	logger.info("Deleting Users");
    	UsersRepository usersRepository = new UsersRepository();
    	return usersRepository.deleteUser(id, uid);
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("/register")
    public static List<Map<String, Object>> register(@RequestBody JSONObject data) throws Exception {
	  	logger.info("Registering User");
	  	UsersRepository usersRepository = new UsersRepository();
	  	return usersRepository.registerUser(data);
    } 
    
    @SuppressWarnings("unchecked")
	@PostMapping("/editUser/{id}")
    public static List<Map<String, Object>> editUser(@PathVariable String id, @RequestBody JSONObject data) throws Exception {
	  	logger.info("Modifying User Profile");
	  	UsersRepository usersRepository = new UsersRepository();
	  	return usersRepository.editUser(id, data);
    } 
    
	@PostMapping("/disableUser/{id}/{uid}")
    public static List<Map<String, Object>> disableUser(@PathVariable String id, @PathVariable String uid) throws Exception {
	  	logger.info("Modifying User Profile");
	  	UsersRepository usersRepository = new UsersRepository();
	  	return usersRepository.disableUser(id, uid);
    } 
    
	@PostMapping("/enableUser/{id}")
    public static List<Map<String, Object>> enableUser(@PathVariable String id) throws Exception {
	  	logger.info("Modifying User Profile");
	  	UsersRepository usersRepository = new UsersRepository();
	  	return usersRepository.enableUser(id);
    } 
}