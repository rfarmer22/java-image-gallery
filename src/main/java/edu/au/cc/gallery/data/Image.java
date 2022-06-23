package edu.au.cc.gallery.data;

public class Image {
	private User user;
//	private String fileName;
	private String uuid;

	public Image(User user, String uuid) { //String fileName, String uuid) {
		this.user = user;
//		this.fileName = fileName;
		this.uuid = uuid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		this.user = u;
	}

//	public String getFileName() {
//		return fileName;
//	}

//	public void setFileName(String fn) {
//		this.fileName = fn;
//	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
