package Services;

//import java.io.ByteArrayOutputStream;
//import java.util.Iterator;
//import java.util.ArrayList;
//import org.json.simple.JSONObject;
//import java.security.NoSuchAlgorithmException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.Random;

/**
 * Generate CSV for document or hash according to rules described at:
 * 
 * https://administracionelectronica.gob.es/ctt/resources/Soluciones/829/descargas/Analisis%20funcional%20de%20CSV.pdf?idIniciativa=829&idElemento=1966
 * 
 * Result is a 32 characters string composed by:
 * 
 *  -  3 characters for prefix: Raw (P)
 *  -  21 characters for hash: Base36 (H)
 *  -  7 characters for ID: Base36 (I)
 *  -  1 character  to identify random positions selected: Base36 (R)
 * 
 * Samples 
 * 
 * PPPIIIIIIIHHHHHHHHHHHHHHHHHHHHHR for R=0 and uuidCharPos=(0,1,2,3,4,5,6,7)
 * PPPIHIHIHIHIHIHIHHHHHHHHHHHHHHHR for R=28 and uuidCharPos=(0,2,4,6,8,10,12,14)
 * 
 * Use following methods to get 32-chars CSV from file or hash:
 * 
 * String getCSV(String prefix, Long uuid, File file)
 * String getCSV(String prefix, Long uuid, byte[] hash)
 * 
 * String getHexModifiedPDF(String fileName)
 *
 */

public class GenerateCSV {
	// Hash algorithm
	private static final String SHA_512 = "SHA-512";
	// Max UUID for document, expressed as zzzzzzz (7 characters) in Base36
	private static Long MAX_UUID = 78364164095l;
	// Random generator
//	private static Random generator = new Random();
	
	// Randomness Map 
	// @SuppressWarnings("serial")
	private static final Map<Integer, Integer[]> uuidCharsPos = Collections.unmodifiableMap(
		    new HashMap<Integer, Integer[]>() {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
		        put( 0, new Integer[] { 0,  1,  2,  3,  4,  5,  6});
		        put( 1, new Integer[] { 2,  3,  5,  7,  9,  11, 13});
		        put( 2, new Integer[] { 4,  5,  7,  9,  11, 13, 15});
		        put( 3, new Integer[] { 6,  7,  9,  11, 13, 15, 17});
		        put( 4, new Integer[] { 8,  9,  11, 13, 15, 17, 19});
		        put( 5, new Integer[] { 10, 11, 13, 15, 17, 19, 21});
		        put( 6, new Integer[] { 12, 13, 15, 17, 19, 21, 23});
		        put( 7, new Integer[] { 14, 15, 17, 19, 21, 23, 25});
		        put( 8, new Integer[] { 16, 17, 19, 21, 23, 25, 27});
		        put( 9, new Integer[] { 18, 19, 21, 23, 25, 27, 0});
		        put(10, new Integer[] { 20, 21, 23, 25, 27, 0,  2});
		        put(11, new Integer[] { 22, 23, 25, 27, 0,  2,  4});
		        put(12, new Integer[] { 24, 25, 27, 0,  2,  4,  6});
		        put(13, new Integer[] { 26, 27, 0,  2,  4,  6,  8});
		        put(14, new Integer[] { 27, 0,  2,  4,  6,  8,  10});
		        put(15, new Integer[] { 1,  2,  4,  6,  8,  10, 12});
		        put(16, new Integer[] { 3,  4,  6,  8,  10, 12, 14});
		        put(17, new Integer[] { 5,  6,  8,  10, 12, 14, 16});
		        put(18, new Integer[] { 7,  8,  10, 12, 14, 16, 18});
		        put(19, new Integer[] { 9,  10, 12, 14, 16, 18, 20});
		        put(20, new Integer[] { 11, 12, 14, 16, 18, 20, 22});
		        put(21, new Integer[] { 13, 14, 16, 18, 20, 22, 24});
		        put(22, new Integer[] { 15, 16, 18, 20, 22, 24, 26});
		        put(23, new Integer[] { 17, 18, 20, 22, 24, 26, 1});
		        put(24, new Integer[] { 19, 20, 22, 24, 26, 1,  3});
		        put(25, new Integer[] { 21, 22, 24, 26, 1,  3,  5});
		        put(26, new Integer[] { 23, 24, 26, 1,  3,  5,  7});
		        put(27, new Integer[] { 25, 26, 1,  3,  5,  7,  9});
		        put(28, new Integer[] { 27, 1,  3,  5,  7,  9,  11});
		        put(29, new Integer[] { 0,  3,  5,  7,  9,  11, 13});
		        put(30, new Integer[] { 2,  5,  7,  9,  11, 13, 15});
		        put(31, new Integer[] { 4,  7,  9,  11, 13, 15, 17});
		        put(32, new Integer[] { 6,  9,  11, 13, 15, 17, 19});
		        put(33, new Integer[] { 8,  11, 13, 15, 17, 19, 21});
		        put(34, new Integer[] { 10, 13, 15, 17, 19, 21, 23});
		        put(35, new Integer[] { 12, 15, 17, 19, 21, 23, 25});
		    }});
	
    //Calculate the checksum for the content of a file
    private static byte[] checksum(File input) throws Exception {
        try (InputStream in = new FileInputStream(input)) {
            MessageDigest digest = MessageDigest.getInstance(SHA_512);
            byte[] block = new byte[4096];
            int length;
            while ((length = in.read(block)) > 0) {
                digest.update(block, 0, length);
            }
            return digest.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        
    //Convert byte[] to String expressed in hexadecimal
//    private String toHex(byte[] bytes, HashMap<String, Object> docMetadata) throws IOException {
////    	String name = (String) metadata.get("name");
////    	String size = (String) metadata.get("size");
////    	String lastModified = (String) metadata.get("lastModified");
////    	String type = (String) metadata.get("type");
////    	System.out.println(name);
////    	String metadataArr = String.join("***", name, size, lastModified, type);
////    	System.out.println(metadataArr);
////    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
////    	byte[] metadataByte = metadata.getBytes();
////    	outputStream.write( metadataByte );    	
////    	outputStream.write( bytes );
////    	byte totalBytes[] = outputStream.toByteArray();
////    	System.out.println(totalBytes);
//    	String hex = new BigInteger(1, bytes).toString(16);
//    	return hex;
//    }
    
    private String toHex(byte[] bytes) throws IOException {
    	String hex = new BigInteger(1, bytes).toString(16);
    	return hex;
    }
    
	/**
	 * Return a 32 chars CSV
	 * 
	 * @param prefix
	 * @param uuid
	 * @param hashBase16
	 * @return
	 */
	private String getCSVInternal(String prefix, Long uuid, String hashBase16) {
		
		String hashBase36 = new BigInteger(hashBase16, 16).toString(36).toUpperCase().substring(0, 21);
		String uuidBase36 = String.format("%1$7s", Long.toString(uuid, 36)).replace(' ', '0');

		StringBuilder csvConcat = new StringBuilder();
		int randomSelected = 22;
		List<Integer> uuidPosArray = Arrays.asList(uuidCharsPos.get(randomSelected));
		
		int hashPos = 0;
		int uuidPos = 0;
		for (Integer i = 0; i < 28; i++) {
			if (uuidPosArray.contains(i)) {
				csvConcat.append(uuidBase36.charAt(uuidPos++));
			} else {
				csvConcat.append(hashBase36.charAt(hashPos++));
			}
		}
		
		return csvConcat + Integer.toString(randomSelected, 36);
	}
	
	/**
	 * Parameter validations
	 * 
	 * @param prefix is a 3 chars String
	 * @param uuid is a number able to be expressed in 7 chars in base36
	 * @throws Exception
	 */
	private void checkParams(String prefix, Long uuid) throws Exception {
		if (prefix.length() != 3) {
			throw new Exception("Prefix must be a 3 characters String!");
		}
		if (uuid > MAX_UUID) {
			throw new Exception("UUID must be lower or equals than " + MAX_UUID);
		}
	}

	/**
	 * Return a 32 chars CSV
	 * 
	 * @param prefix
	 * @param uuid
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String[] getCSV(String prefix, Long uuid, File file) throws Exception {
		checkParams(prefix, uuid);
		String hashBase16 = toHex(checksum(file));
		String csv = getCSVInternal(prefix, uuid, hashBase16);
		String[] responseCSV = {hashBase16, csv};
		return responseCSV;
	}
	
	public String generateHash(String prefix, Long uuid, File file) throws IOException, Exception {
		checkParams(prefix, uuid);
		String hashBase16 = toHex(checksum(file));
		return hashBase16;
	}
	
	/**
	 * Return a 32 chars CSV
	 * 
	 * @param prefix
	 * @param uuid
	 * @param hash
	 * @return
	 * @throws Exception
	 */
	/*
	public static String getCSV(String prefix, Long uuid, byte[] hash, String metadata) throws Exception {
		
		checkParams(prefix, uuid);
		String hashBase16 = toHex(hash, metadata);
		return getCSVInternal(prefix, uuid, hashBase16);
	}
	*/	
}
