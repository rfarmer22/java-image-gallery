package edu.au.cc.gallery;
///
import java.util.*;
import java.io.*;
import java.sql.*;

public class UserAdmin {

	private static Scanner s = new Scanner(System.in);

	private static DB db = new DB();

	private static int promptUser() {
	System.out.println(
	     "1) List users\n"
	   + "2) Add user\n"
	   + "3) Edit user\n"
	   + "4) Delete user\n"
	   + "5) Quit");
	   System.out.print("Enter command> ");
	   int command = Integer.parseInt(s.nextLine());
	   System.out.print("\n");
	   return command;
	}

	private static void performCommand(int command) throws SQLException {
	   switch(command){
	   case 1: ResultSet rs = db.getUsers();
		   System.out.printf("%-20s\n","username"); //,"full name"); //"password","full name");
	           System.out.println("-----------------------------------------");
                   while(rs.next()) {
                   System.out.printf("%-20s\n",rs.getString(1)); //,rs.getString(2)); //,rs.getString(3));
                	}
	           rs.close();
		   break;
	   case 2: System.out.print("Username> ");
		   String username = s.nextLine();
                   System.out.print("\nPassword > ");
                   String password = s.nextLine();
                   System.out.print("\nFull name > ");
                   String fullname = s.nextLine();
	           db.addUser(username, password, fullname);
		   break;
	   case 3: System.out.print("Username to edit> ");
		   String updateUsername = s.nextLine();
                   System.out.print("\nNew Password (press enter to keep current)> ");
                   String updatePassword = s.nextLine();
                   System.out.print("\nNew Full name (press enter to keep current)> ");
                   String updateFullname = s.nextLine();
		   db.updateUser(updateUsername, updatePassword, updateFullname);
		   break;
	   case 4: System.out.print("Enter username to delete> ");
		   String deleteUsername = s.nextLine();
		   System.out.print("Are you sure that you want to delete "+deleteUsername+"? [Yes/No]");
		   String confirmation = s.next().toLowerCase();
		   if(confirmation.equals("yes")){
			db.deleteUser(deleteUsername);
		   } else if (confirmation.equals("no")) {
			System.out.println("Delete command cancelled.");
		   } else {
			System.out.print("Are you sure that you want to delete "+deleteUsername+"? [Yes/No]");
                   	confirmation = s.next().strip().toLowerCase();
		        }
		   break;
   	   default:System.out.println("Invalid command. Please enter command from 1-5.");
		   break;
	           }
	   }


        public static void main (String [] args) throws SQLException {
        try {
  	   db.connect();
	   int userCommand = 0;
           while(userCommand == 0) {
	      userCommand = promptUser();
	      if(userCommand == 5) {
	         db.close();
	         System.out.println("Bye.");
		 break;
		 }
	      performCommand(userCommand);
	      System.out.println();
	      userCommand = 0;
	      }
     	}
	catch (Exception e) {
	   System.out.println(e); 
	   }
        }
   }

