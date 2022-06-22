package edu.au.cc.gallery.data;

import java.sql.SQLException;

public class GenerateUserImageDAO {

	public static UserImageDAO getUserImageDAO() throws SQLException { return new UserImageDAO(); }

}
