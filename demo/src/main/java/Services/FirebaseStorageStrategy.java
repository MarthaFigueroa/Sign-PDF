package Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.UUID;

import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//import java.io.InputStream;
//import java.nio.channels.Channels;
//import java.nio.file.Path;
//import java.util.Date;
//import java.util.Objects;
//import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.core.env.Environment;
//import org.apache.pdfbox.io.IOUtils;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.core.ApiFuture;
//import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
//import com.google.cloud.ReadChannel;
//import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
//import com.google.firebase.cloud.StorageClient;
import com.google.firebase.cloud.FirestoreClient;

import Controllers.FileController;
import Entities.Links;

@Service
public class FirebaseStorageStrategy {
	private final static Logger logger = LoggerFactory.getLogger(FileController.class);
//    private static final String DOWNLOAD_URL = "https://storage.cloud.google.com/validacion-de-documentos.appspot.com/";//"https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com";
	private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/validacion-de-documentos.appspot.com/o/"; //%s?alt=media
    private static String bucketName;
    @PostConstruct
    public static FirebaseApp initializeFirebase() throws Exception {
        bucketName = "validacion-de-documentos.appspot.com";
        logger.info("Firebase config initialized");
        FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
        		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//        		.setProjectId(projectId)
//        		.setStorageBucket(bucketName)
//        		.setDatabaseUrl("https://validacion-de-documentos.firebaseio.com/")
        		.build();

        FirebaseApp app;
        if (FirebaseApp.getApps().isEmpty()) {
            app = FirebaseApp.initializeApp(options, "my-app");
        } else {
            app = FirebaseApp.getApps().get(0);
        }
        
        return app;
    }
    
	public static List<Links> getDocs() throws Exception {
    	FirebaseApp app = initializeFirebase();
    	Firestore dbFirestore = FirestoreClient.getFirestore(app);
    	Iterable<DocumentReference> documents = dbFirestore.collection("documents").listDocuments();
    	Iterator<DocumentReference> iterator = documents.iterator();

    	ArrayList<Links> docsList=new ArrayList<Links>();
    	
    	System.out.println(documents.toString());
    	
    	while(iterator.hasNext()) {
    		DocumentReference doc = iterator.next();
    		ApiFuture<DocumentSnapshot> future = doc.get();
    		DocumentSnapshot document = future.get();
    		docsList.add(document.toObject(Links.class));
    	}
    	
    	return docsList;
    }
	
	public static void saveFile(HashMap<String, Object> reqObject) throws Exception{
		FirebaseApp app = initializeFirebase();
    	Firestore dbFirestore = FirestoreClient.getFirestore(app);
		ApiFuture<DocumentReference> addedDocRef = dbFirestore.collection("documents").add(reqObject);

		System.out.println("Added document with ID: " + addedDocRef.get().getId());
	}
    
    public static ArrayList<Links> getLinks() throws Exception{ //HashMap<String, Links>
    	FirebaseApp app = initializeFirebase();
    	Firestore dbFirestore = FirestoreClient.getFirestore(app);
    	ApiFuture<QuerySnapshot> future = dbFirestore.collection("links").get();
    	ArrayList<Links> docsList=new ArrayList<Links>();
    	List<QueryDocumentSnapshot> documents = future.get().getDocuments();
    	for (QueryDocumentSnapshot document : documents) {
    		Links data = document.toObject(Links.class);
    		data.setId(document.getId());
    		docsList.add(data);    		
    	}
		return docsList;
    }
    
    public static ResponseEntity<?> upload2(MultipartFile multipartFile, String fileName, String imageName) {

        try {
        	initializeFirebase();
//            fileName = UUID.randomUUID().toString().concat(getExtension(fileName));  // to generated random string values for file name. 
            File file = convertToFile2(multipartFile, fileName);                      // to convert multipartFile to File
            Object TEMP_URL = uploadFile(file, fileName, multipartFile, imageName);                                   // to get uploaded file link
            file.delete();                                                                // to delete the copy of uploaded file stored in the project folder
            HashMap<String, Object> responseObject = new HashMap<String, Object>();
            responseObject.put("DownloadUurl", TEMP_URL);
            return ResponseEntity.ok(responseObject);                     // Your customized response
        } catch (Exception e) {
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
    	logger.info("Uploading Signed File named: {}", fileName);
        BlobId blobId = BlobId.of(bucketName, imageName+"/"+fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
        		.setContentType("application/pdf")
        		.build();
        File fileKey = new File("serviceAccountKey2.json");
        FileInputStream serviceAccount = new FileInputStream(fileKey);
		Storage storage = StorageOptions.newBuilder()
        		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
        		.build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
//        String url = DOWNLOAD_URL+imageName+"/"+fileName+"?authuser=0";
        String url = DOWNLOAD_URL+imageName+"%2F"+fileName+"?alt=media";
//        String url = String.format(DOWNLOAD_URL+"%2F"+fileName+"?alt=media", URLEncoder.encode(fileName, StandardCharsets.UTF_8));
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