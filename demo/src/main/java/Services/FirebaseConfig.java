package Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {
	
	    @Bean
	    public FirebaseApp createFireBaseApp() throws IOException {

	        System.out.println("Firebase config initialized");
	        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	        File file = new File(classloader.getResource("serviceAccountKey.json").getFile());
	        
	        FileInputStream serviceAccount =
	        		new FileInputStream(file.getAbsoluteFile());
	        
//			FirebaseOptions options = new FirebaseOptions.Builder()
	        FirebaseOptions options = FirebaseOptions.builder()
	        		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
	        		.setDatabaseUrl("https://validacion-de-documentos.firebaseio.com/")
	        		.build();

	        return FirebaseApp.initializeApp(options);
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
