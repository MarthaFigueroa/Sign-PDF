package Repositories;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.google.firebase.cloud.FirestoreClient;

public class UsersRepository implements IUsersRepository{

	Firestore db= FirestoreClient.getFirestore();
	
	public List<Map<String, Object>> getAllUsers() throws Exception {
    	ApiFuture<QuerySnapshot> future = db.collection("users").get();
    	List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
    	Map<String, Object> data = new HashMap<String, Object>();
    	List<QueryDocumentSnapshot> users = future.get().getDocuments();
    	for (QueryDocumentSnapshot user : users) {
    		data = user.getData();
    		data.put("id", user.getId());
    		response.add(data);
    	}
    	return response;
    }
	
	public List<Map<String, Object>> deleteUser(String id, String uid) throws Exception {
    	db.collection("users").document(id).delete();
    	FirebaseAuth.getInstance().deleteUser(uid);
    	System.out.println("Successfully deleted user.");

    	return getAllUsers();
    }
	
	public List<Map<String, Object>> registerUser(HashMap<String, Object> user) throws Exception {
    	db.collection("users").document().set(user);
    	return getAllUsers();
    }

	public List<Map<String, Object>> editUser(String id, HashMap<String, Object> user) throws Exception {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		user.put("Updated_At ", timeStampMillis);
		db.collection("users").document(id).update(user);
		return getAllUsers();
	}
	
	public List<Map<String, Object>> disableUser(String id, String uid) throws Exception {
		db.collection("users").document(id).update("disable", true);
		UpdateRequest request = new UpdateRequest(uid)
			    .setDisabled(true);

			UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
			System.out.println("Successfully updated user: " + userRecord.getUid());

		return getAllUsers();
	}
	
	public List<Map<String, Object>> enableUser(String id, String uid) throws Exception {
		db.collection("users").document(id).update("disable", false);
		UpdateRequest request = new UpdateRequest(uid)
			    .setDisabled(false);

			UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
			System.out.println("Successfully updated user: " + userRecord.getUid());
		return getAllUsers();
	}
}