package edu.au.cc.gallery.data;

import java.util.*;
import edu.au.cc.gallery.ui.*;
import edu.au.cc.gallery.aws.*;

import java.sql.SQLException;

public class UserImageDAO implements ImageDAO {

	private DB connection;

	public UserImageDAO() throws SQLException {
		connection = new DB();
		connection.connect();
	}

	private S3 s3 = new S3();
	private String bucketName = "edu.au.cc.image-gallery";

	public List<Image> getAllUserImages(User u)  throws Exception {
		return null;
	}

	public Image getImage(User u, Image image) throws Exception {
		return null;
	}

	public void addImageDB(User u, Image image) throws Exception {
		connection.execute("insert into images(username,file_name) values (?,?)", new String[]{u.getUsername(),image.getUuid()});
	}

	public void addImageS3(User u, Image image) throws Exception {
		s3.connect();
		s3.putObject(bucketName, image.getUuid(), image.getFileName());
	}

	public void deleteImage(User u, Image image) throws Exception {

	}

//	@Override
//	public String toString() {
//		return "User with username: " + username + " password: " + password +" full name: " +fullName;
//	}
}