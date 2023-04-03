package Repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
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

@Service
public class FirebaseStorageStrategy {
	private final static Logger logger = LoggerFactory.getLogger(FirebaseStorageStrategy.class);
	private static final String downloadURL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/"; //%s?alt=media
    private static String bucketName = "validacion-de-documentos.appspot.com";
    public static Storage storage = null;

    public static HashMap<String, Object> downloadFile(String fileName, Path destFilePath, String imageName){
    	try {
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
			logger.info("This document doesn't exist!", e.getMessage());
			responseObject.put("Exists", false);
			return responseObject;
		}
    }

    public static ResponseEntity<?> upload(MultipartFile multipartFile, String fileName, String imageName) {

        try {
        	File file = convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            Object TEMP_URL = uploadFile(file, fileName, multipartFile, imageName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            HashMap<String, Object> responseObject = new HashMap<String, Object>();
            responseObject.put("DownloadUurl", TEMP_URL);
            return ResponseEntity.ok(responseObject);                     // Your customized response
        } catch (StorageException e) {
            e.printStackTrace();
            return ResponseEntity.ok("Unsuccessfully Uploaded! "+e.getMessage());
        } catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok("Unsuccessfully Uploaded! "+e);
		}

    }

    private static File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }
    
    private static String uploadFile(File file, String fileName, MultipartFile multipartFile, String imageName) throws IOException {
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
        String url = downloadURL+imageName+"%2F"+fileName+"?alt=media";
        logger.info("Uploaded Signed File in: {}", url);
        return url;
    }
}