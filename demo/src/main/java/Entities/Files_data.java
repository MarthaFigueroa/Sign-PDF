package Entities;

public class Files_data {

	public int id; //Document's id
	public int id_user; //User's id that login
	public int id_type; //Type's id, for example application/PDF
	public String Filename; //Document's Name
	public String Certificate; //Certificate with which the document was signed
	public String Description; //Signed Document's Hash
	public String File; //URL to download the document from the storage 
	public String Signed_file; //URL to download the signed document from the storage

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public int getId_type() {
		return id_type;
	}
	public void setId_type(int id_type) {
		this.id_type = id_type;
	}
	public String getFilename() {
		return Filename;
	}
	public void setFilename(String filename) {
		Filename = filename;
	}
	public String getCertificate() {
		return Certificate;
	}
	public void setCertificate(String certificate) {
		Certificate = certificate;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getFile() {
		return File;
	}
	public void setFile(String file) {
		File = file;
	}
	public String getSigned_file() {
		return Signed_file;
	}
	public void setSigned_file(String signed_file) {
		Signed_file = signed_file;
	}
}
