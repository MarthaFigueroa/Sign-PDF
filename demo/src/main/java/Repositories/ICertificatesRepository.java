package Repositories;

import java.util.List;
import java.util.Map;

public interface ICertificatesRepository {
	public List<Map<String, Object>> getAllCertificates() throws Exception;
}
