package edu.au.cc.gallery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class DB {

    private static final String dbUrl = "jdbc:postgresql://demo-database-1.cbimem44ngio.us-east-1.rds.amazonaws.com/image_gallery";

    private Connection connection;

    private String getPassword() {
	try(BufferedReader br = new BufferedReader(new FileReader("/home/ec2-user/.sql-passwd"))) {
		String result = br.readLine();
		return result;
	} catch (IOException ex) {
		System.err.println("Error opening password file. Make sure .sql-passwd exists and contains your sql password.");
		System.exit(1);
    		}
		return null;
	}

    //Connects DB
    public void connect() throws SQLException {
	try {
	    Class.forName("org.postgresql.Driver");
	    connection = DriverManager.getConnection(dbUrl, "image_gallery", getPassword());
	} catch (ClassNotFoundException ex) {
	    ex.printStackTrace();
	    System.exit(1);
	}
    }

    //Reads Users
    public void listUsers() throws SQLException {
	PreparedStatement stmt = connection.prepareStatement("select username, password, full_name from users");
	ResultSet rs = stmt.executeQuery();
 	System.out.printf("%-20s%-20s%-30s\n","username","password","full name");
	System.out.println("------------------------------------------------------");
	while(rs.next()) {
	    System.out.printf("%-20s%-20s%-30s\n",rs.getString(1),rs.getString(2),rs.getString(3));
	}
	rs.close();
    }

    //Checks if user exists
    public boolean existingUser (String username) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select * from users where username = ?");
	stmt.setString(1, username);
	ResultSet rs = stmt.executeQuery();
	if(rs.next() == true){
	    return true;
	} else {
	    return false;
	}
    }

    //Adds User
    public void addUser(String username, String password, String fullname) throws SQLException {
	if(existingUser(username) == true){
	   System.out.println("Error: user with username "+username+" already exists");
	   return;
	} else {
	PreparedStatement stmt = connection.prepareStatement("insert into users (username, password, full_name) values (?,?,?)");
	stmt.setString(1, username);
	stmt.setString(2, password);
	stmt.setString(3, fullname);
	stmt.execute();
	System.out.println("User "+username+" created successfully");
	return;
	}
    }

    //Updates User
    public void updateUser(String username, String password, String fullname) throws SQLException {
	   System.out.println();
	if(existingUser(username) == false){
           System.out.println("No such user.");
           return;
        } else {
	if(password.equals("") && fullname.equals("")){
	   System.out.println("No updates needed.");
	   return;
	   }
	if(password.equals("") && !fullname.equals("")){
	   PreparedStatement stmt = connection.prepareStatement("update users set full_name =? where username=?");
           stmt.setString(1, fullname);
           stmt.setString(2, username);
           stmt.execute();
           System.out.println("User "+username+" updated successfully.");
           return;
	   }
	if(!password.equals("") && fullname.equals("")){
	   PreparedStatement stmt = connection.prepareStatement("update users set password =? where username=?");
           stmt.setString(1, password);
           stmt.setString(2, username);
           stmt.execute();
           System.out.println("User "+username+" updated successfully.");
           return;
           }
	if(!password.equals("") && !fullname.equals("")){
	   PreparedStatement stmt = connection.prepareStatement("update users set full_name =?, password =? where username=?");
           stmt.setString(1, fullname);
           stmt.setString(2, password);
	   stmt.setString(3, username);
           stmt.execute();
           System.out.println("User "+username+" updated successfully.");
           return;
	   }
	}
    }

	   public void deleteUser(String username) throws SQLException {
	      System.out.println();
              if(existingUser(username) == false){
                 System.out.println("No such user.");
              return;
              } else {
	      PreparedStatement stmt = connection.prepareStatement("delete from users where username=?");
	      stmt.setString(1, username);
	      stmt.execute();
	      System.out.println("Deleted");
	      return;
	      }
	   }
    //Closes Connection
    public void close() throws SQLException {
	connection.close();
    }

    public static void demo() throws Exception {
	DB db = new DB();
	db.connect();
//	db.updateUser("update users set password=? where username=?",
//		   new String[] {"monkey", "fred"});
//	db.listUsers();//"select username,password,full_name from users");
//	while(rs.next()) System.out.println(rs.getString(1)+","+rs.getString(2)+","+rs.getString(3));
//	rs.close();
	db.close();
    }
}
