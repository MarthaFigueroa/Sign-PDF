package Repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

public class DocumentsRepository implements IDocumentsRepository{

	Firestore db= FirestoreClient.getFirestore();
	
	public List<Map<String, Object>> getAllDocuments() throws Exception {
    	ApiFuture<QuerySnapshot> future = db.collection("documents").get();
    	List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
    	Map<String, Object> data = new HashMap<String, Object>();
    	List<QueryDocumentSnapshot> documents = future.get().getDocuments();
    	for (QueryDocumentSnapshot document : documents) {
    		data = document.getData();
    		data.put("id", document.getId());
    		response.add(data);
    	}
    	return response;
    }
	
	public List<Map<String, Object>> deleteDocument(String id) throws Exception {
    	db.collection("documents").document(id).delete();
    	return getAllDocuments();
    }
	
	public List<Map<String, Object>> createDocument(HashMap<String, Object> doc) throws Exception {
    	db.collection("documents").document().set(doc);
    	return getAllDocuments();
    }
}