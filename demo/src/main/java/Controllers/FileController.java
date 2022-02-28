package Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import Services.FileService;
import Services.SignService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class FileController {
    
    @GetMapping(path = "files")
    public void signFile() throws Exception {
        // log.info("UsersController:  list users");
        System.out.println("File Controller: signFile GET");
        
    }
    
    @GetMapping("users")
    public String getAll() {
//        return ResponseEntity.ok("Juan");
    	return "Juan";
    }
    
    @PostMapping(path = "files")
    public ResponseEntity<?> signFile2() throws Exception {
        System.out.println("File Controller: signFile");
        return ResponseEntity.ok("File Signed");
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("/file")
    public ResponseEntity<?> signFile(@RequestBody JSONObject requestObject) throws Exception {
        System.out.println("File Controller: signFile gg");
        String filename = (String) requestObject.get("filename");
        String certName = (String) requestObject.get("certName");
        String certPass = (String) requestObject.get("certPass");
        
        JSONObject responseObject = FileService.addSignature(filename, certName, certPass);
        responseObject.put("message", "Signed File");
        return ResponseEntity.ok(responseObject);
    }
    
    @SuppressWarnings("unchecked")
	@PostMapping("/upload")
    public ResponseEntity<?> uploadToLocalFileSystem(@RequestBody MultipartFile file, @RequestBody MultipartFile cert) throws Exception {
    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    	String certName = StringUtils.cleanPath(cert.getOriginalFilename());
    	Path path = Paths.get(fileName);
    	Path certPath = Paths.get(certName);
    	try {
    		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    		Files.copy(cert.getInputStream(), certPath, StandardCopyOption.REPLACE_EXISTING);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
    			.path("/files/download/")
    			.path(fileName)
    			.toUriString();
    	String certUri = ServletUriComponentsBuilder.fromCurrentContextPath()
    			.path("/files/download/")
    			.path(certName)
    			.toUriString();
    	JSONObject responseObject = FileService.addSignature(fileName, certName, "369258");
    	responseObject.put("file",fileDownloadUri);
    	responseObject.put("cert",certUri);
    	return ResponseEntity.ok(responseObject);
    }
    
//    @SuppressWarnings({ "unchecked", "null" })
//	@PostMapping("/multi-upload")
//    public ResponseEntity<?> multiUpload(@RequestBody MultipartFile files) throws Exception {//JSONObject requestObject
////        MultipartFile[] file = (MultipartFile[]) requestObject.get("formData");
//    	System.out.println(files);
//    	List<Object> fileDownloadUrls = new ArrayList<>();
//    	Arrays.asList(files)
//    			.stream()
//    			.forEach(file -> fileDownloadUrls.add(uploadToLocalFileSystem(file).getBody()));
////        String certPass = (String) requestObject.get("certPass");
////        String fileBasePath = new File(".").getCanonicalPath();
//                
//    	System.out.println(Arrays.asList(files).get(0));
//    	
//        JSONObject responseObject = null;//FileService.addSignature(fileName, certName, "369258");
//        responseObject.put("message", "Signed File");
//        responseObject.put("fileDownloadUri", fileDownloadUrls);
//        return ResponseEntity.ok(responseObject);
//    }
}