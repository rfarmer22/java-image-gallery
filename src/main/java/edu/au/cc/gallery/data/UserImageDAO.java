package edu.au.cc.gallery.data;

import java.util.*;
import edu.au.cc.gallery.ui.*;
import edu.au.cc.gallery.aws.*;

import java.sql.ResultSet;
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
		List<Image> result = new ArrayList<>();
		ResultSet rs = connection.executeQuery("select file_name from images where username = ?", new String[]{u.getUsername()});
		while (rs.next()) {
			result.add(new Image(u, rs.getString(1)));
		}
		rs.close();
		return result;
	}

	public void addImageDB(User u, Image image) throws Exception {
		connection.execute("insert into images(username,file_name) values (?,?)", new String[]{u.getUsername(),image.getUuid()});
	}

	public void addImageS3(User u, Image image, byte[] imageData, String contentType) throws Exception {
		s3.connect();
		s3.putObject(bucketName, image.getUuid(), imageData, contentType);
	}

	public void deleteImageDB(User u, String uuid) throws Exception {
		connection.execute("delete from images where username = ? and file_name = ?", new String[]{u.getUsername(), uuid});
	}

	public void deleteImageS3(String uuid) throws Exception {
		s3.connect();
		s3.deleteObject(bucketName, uuid);
	}

//	@Override
//	public String toString() {
//		return "User with username: " + username + " password: " + password +" full name: " +fullName;
//	}
}
