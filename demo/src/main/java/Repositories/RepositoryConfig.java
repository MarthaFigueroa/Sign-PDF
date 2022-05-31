package Repositories;

import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class RepositoryConfig {
	
	private final static Logger logger = LoggerFactory.getLogger(RepositoryConfig.class);

    public static void initializeFirebase() throws Exception {
        
        logger.info("Firebase config initialized");
        FileInputStream serviceAccount = new FileInputStream("serviceAccountKey2.json");

        FirebaseOptions options = FirebaseOptions.builder()
        		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
        		.setDatabaseUrl("https://validacion-de-documentos.firebaseio.com")
        		.build();
        System.out.println(FirebaseApp.getApps());
        FirebaseApp.initializeApp(options);
    }
	
	    
//	FirebaseOptions options = FirebaseOptions.builder()
//		    .setCredentials(GoogleCredentials.getApplicationDefault())
//		    .setDatabaseUrl("https://validacion-de-documentos.firebaseio.com/")
//		    .build();

//	FirebaseApp.initializeApp(options);

	
//	String bucketName = environment.getRequiredProperty("FIREBASE_BUCKET_NAME");
//    String projectId = environment.getRequiredProperty("FIREBASE_PROJECT_ID");
//    StorageOptions.newBuilder()
//            .setProjectId(projectId)
//            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//            .build();
}
