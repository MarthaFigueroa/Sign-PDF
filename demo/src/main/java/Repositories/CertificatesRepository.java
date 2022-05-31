package Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class CertificatesRepository implements ICertificatesRepository{

	Firestore db= FirestoreClient.getFirestore();
	
	public List<Map<String, Object>> getAllCertificates() throws Exception {
    	ApiFuture<QuerySnapshot> future = db.collection("certificates").get();
    	List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
    	Map<String, Object> data = new HashMap<String, Object>();
    	List<QueryDocumentSnapshot> certificates = future.get().getDocuments();
    	for (QueryDocumentSnapshot certificate : certificates) {
    		data = certificate.getData();
    		data.put("id", certificate.getId());
    		response.add(data);
    	}
    	return response;
    }
	
	public List<Map<String, Object>> deleteCertificate(String id) throws Exception {
    	db.collection("certificates").document(id).delete();
    	return getAllCertificates();
    }
	
	public List<Map<String, Object>> createCertificate(HashMap<String, Object> cert) throws Exception {
    	db.collection("certificates").document().set(cert);
    	return getAllCertificates();
    }
}