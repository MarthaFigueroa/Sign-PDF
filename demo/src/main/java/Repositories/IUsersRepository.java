package Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IUsersRepository {

	public List<Map<String, Object>> getAllUsers() throws Exception;
	public List<Map<String, Object>> deleteUser(String id, String uid) throws Exception;
	public List<Map<String, Object>> registerUser(HashMap<String, Object> user) throws Exception;
	public List<Map<String, Object>> editUser(String id, HashMap<String, Object> user) throws Exception;
	public List<Map<String, Object>> disableUser(String id, String uid) throws Exception;	
	public List<Map<String, Object>> enableUser(String id) throws Exception;
	
}
