package Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IDocumentsRepository {

	public List<Map<String, Object>> getAllDocuments() throws Exception;
	public List<Map<String, Object>> deleteDocument(String id) throws Exception;
	public List<Map<String, Object>> createDocument(HashMap<String, Object> doc) throws Exception;
	
}
