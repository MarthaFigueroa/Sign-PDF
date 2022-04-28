package Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseStorageStrategy {
	private final static Logger logger = LoggerFactory.getLogger(FirebaseStorageStrategy.class);
//    private static final String DOWNLOAD_URL = "https://storage.cloud.google.com/validacion-de-documentos.appspot.com/";//"https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com";
	private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/"; //%s?alt=media
    private static String bucketName = "validacion-de-documentos.appspot.com";
    public static Storage storage = null;
    
    @PostConstruct
    public static FirebaseApp initializeFirebase() throws Exception {
        
        logger.info("Firebase config initialized");
        FileInputStream serviceAccount = new FileInputStream("serviceAccountKey2.json");

        FirebaseOptions options = FirebaseOptions.builder()
        		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
        		.build();
        FirebaseApp app;
        if (FirebaseApp.getApps().isEmpty()) {
            app = FirebaseApp.initializeApp(options, "my-app");
        } else {
            app = FirebaseApp.getApps().get(0);
        }
        return app;
    }
    
     public static HashMap<String, Object> downloadFile(String fileName, Path destFilePath, String imageName){
    	try {
//			initializeFirebase();
			FileInputStream serviceAccount = new FileInputStream("serviceAccountKey2.json");
			storage = StorageOptions.newBuilder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build().getService();
			System.out.println(fileName);
			String  file = imageName+"/"+fileName;//+fileName;
			BlobId blobId = BlobId.of(bucketName, file);
			System.out.println(bucketName+"/"+file);
			System.out.println(blobId);
			System.out.println(destFilePath);
			storage.get(blobId).downloadTo(destFilePath);
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			logger.info("Document downloaded successfully.!");
			responseObject.put("Exists", true);
			responseObject.put("Downloaded", true);
			return responseObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HashMap<String, Object> responseObject = new HashMap<String, Object>();
			logger.info("This document doesn't exist!");
			responseObject.put("Exists", false);
			return responseObject;
		}
    }
//    		docsList.add(data);    		
//    	}
//		return docsList;
//    }
//    
    public static ResponseEntity<?> upload2(MultipartFile multipartFile, String fileName, String imageName) {

        try {
        	
//            fileName = UUID.randomUUID().toString().concat(getExtension(fileName));  // to generated random string values for file name. 
            File file = convertToFile2(multipartFile, fileName);                      // to convert multipartFile to File
            Object TEMP_URL = uploadFile(file, fileName, multipartFile, imageName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            HashMap<String, Object> responseObject = new HashMap<String, Object>();
            responseObject.put("DownloadUurl", TEMP_URL);
            return ResponseEntity.ok(responseObject);                     // Your customized response
        } catch (StorageException e) {
            e.printStackTrace();
            return ResponseEntity.ok("Unsuccessfully Uploaded! "+e.getMessage());
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.ok("Unsuccessfully Uploaded! "+e);
		}

    }

    private static File convertToFile2(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }
    
    private static String uploadFile(File file, String fileName, MultipartFile multipartFile, String imageName) throws IOException {
//    	logger.info("Uploading Signed File named: {}", fileName);
        BlobId blobId = BlobId.of(bucketName, imageName+"/"+fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
        		.setContentType("application/pdf")
        		.build();
        File fileKey = new File("serviceAccountKey2.json");
        FileInputStream serviceAccount = new FileInputStream(fileKey);
		storage = StorageOptions.newBuilder()
        		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
        		.build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        String url = DOWNLOAD_URL+imageName+"%2F"+fileName+"?alt=media";
        logger.info("Uploaded Signed File in: {}", url);
        return url;
    }

//    private static String getExtension(String fileName) {
//        return fileName.substring(fileName.lastIndexOf("."));
//    }

//    public static String[] upload(MultipartFile multipartFile) throws IOException {
//        File file = convertMultiPartToFile(multipartFile);
//        Path filePath = file.toPath();
//        String objectName = generateFileName(multipartFile);
//
//        Storage storage = storageOptions.getService();
//
//        BlobId blobId = BlobId.of(bucketName, objectName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//        @SuppressWarnings("unused")
//		Blob blob = storage.create(blobInfo, Files.readAllBytes(filePath));
//
//        System.out.println("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
//        return new String[]{"fileUrl", objectName};
//    }


//    public static ResponseEntity<Object> download(String fileName) throws Exception {
//        Storage storage = storageOptions.getService();
//
//        Blob blob = storage.get(BlobId.of(bucketName, fileName));
//        ReadChannel reader = blob.reader();
//        InputStream inputStream = Channels.newInputStream(reader);
//
//        byte[] content = null;
//        System.out.println("File downloaded successfully.");
//
//        content = IOUtils.toByteArray(inputStream);
//
//        final ByteArrayResource byteArrayResource = new ByteArrayResource(content);
//
//        return ResponseEntity
//                .ok()
//                .contentLength(content.length)
//                .header("Content-type", "application/octet-stream")
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                .body(byteArrayResource);
//
//    }

//    private static File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//        FileOutputStream fos = new FileOutputStream(convertedFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convertedFile;
//    }
//
//    private static String generateFileName(MultipartFile multiPart) {
//        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
//    }

}