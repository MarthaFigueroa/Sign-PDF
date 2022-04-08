package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

@Service
public class UploadService {
	private FirebaseAuth firebaseAuth;
	@Autowired
	
	public void findUser(String mobile) throws FirebaseAuthException {
		firebaseAuth.getUserByPhoneNumber(mobile);
	}
	
}