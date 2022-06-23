package edu.au.cc.gallery.data;

import java.util.*;

public interface ImageDAO {

	List<Image> getAllUserImages(User u) throws Exception;

	Image getImage(User u, Image image) throws Exception;

	void addImageDB(User u, Image image) throws Exception;

	void addImageS3(User u, Image image, byte[] imageData, String contentType) throws Exception;

	void deleteImageDB(User u, Image image) throws Exception;

	void deleteImageS3(User u, Image image) throws Exception;

//	@Override
//	public String toString() {
//		return "User with username: " + username + " password: " + password +" full name: " +fullName;
//	}
}
