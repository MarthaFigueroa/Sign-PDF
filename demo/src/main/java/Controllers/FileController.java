package Controllers;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Services.FileService;
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
    
    @PostMapping("/file")
    public ResponseEntity<?> signFile(@RequestBody JSONObject requestObject) throws Exception {
        System.out.println("File Controller: signFile gg");
        String filename = (String) requestObject.get("filename");
        JSONObject responseObject = FileService.addSignature(filename);
        responseObject.put("message", "Signed File");
        return ResponseEntity.ok(responseObject);
    }
}