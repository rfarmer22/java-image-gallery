package edu.au.cc.gallery.ui;

import edu.au.cc.gallery.data.*;

import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.Request;
import spark.Response;

public class Admin {

	private static DB db = new DB();

	public static UserDAO getUserDAO() throws Exception {
                return Postgres.getUserDAO();
        }

	public void addRoutes() {
		before("/", (req, res) -> checkUser(req, res));
                get("/", (req, res) -> mainMenu(req, res));

                get("/admin/users", (req, res) -> listUsers());

                get("/login", (req, res) -> login(req,res));
                before("/admin/*", (request, response) -> checkAdmin(request, response));
                post("/login", (req, res) -> loginPost(req,res));

                get("/admin/addUser", (req, res) -> addUser(req, res));
                post("/admin/addUser", (req, res) -> postAddUser(req.queryParams("username"), req.queryParams("pwd"), req.queryParams("full_name"), res));

                get("/admin/modifyUser/:user", (req, res) -> modifyUser(req.params(":user"), res));
                post("/admin/modifyUser/:user", (req, res) -> postModifyUser(req.params(":user"), req.queryParams("pwd"), req.queryParams("full_name"), res));

                get("/admin/deleteUser/:username", (req, res) -> deleteUser(req, res));
                get("/admin/deleteUserExec/:username", (req, res) -> deleteUserExec(req, res));

                get("/debugSession", (req, res) -> debugSession(req, res));
		before("users/*", (req, res) -> checkUser(req, res));
		get("/users/addimage", (req, res) -> App.addImage(req, res));

		get("/users/:username/images", (req, res) -> mainMenu(req, res));;
		post("/users/:username/images", (req, res) -> App.postAddImage(req, res));

		before("/users/:username/*", (req, res) -> checkUser(req, res));

		get("/users/:username/viewimages", (req, res) -> App.viewImages(req, res));

		get("/user/:username/images/:uuid", (req, res) -> App.viewSingleImage(req, res));

		get("/user/:username/deleteimage/:uuid", (req, res) -> App.deleteImage(req, res));
		get("/user/:username/deleteimageexec/:uuid", (req, res) -> App.deleteImageExec(req, res));

                }


	//Login Methods
	private String login(Request req, Response res) {
                Map<String, Object> model = new HashMap<String, Object>();
                return new HandlebarsTemplateEngine()
                        .render(new ModelAndView(model, "login.hbs"));
        }

	private String loginPost(Request req, Response res) {
        try {
             	String username = req.queryParams("username");
                User u = getUserDAO().getUserByUsername(username);
                if(u == null || !u.getPassword().equals(req.queryParams("password"))) {
			res.redirect("/login");
                }
                req.session().attribute("user", username);
                res.redirect("/");
        } catch (Exception e) {
                return "Error: "+e.getMessage();
                }
          return "";
        }

	//Methods for Admin Verification
	private boolean isAdmin(String username) {
		return username != null && username.equals("Administrator");
	}

	private void checkAdmin(Request req, Response res) {
		if(!isAdmin(req.session().attribute("user"))) {
			res.redirect("/login");
			halt();
		}
	}

	//Methods for User Verification
	private boolean isUser(String currentUsername, String requestedUsername) {
		return currentUsername !=null && currentUsername.equals(requestedUsername);
	}

	private void checkUser(Request req, Response res) throws Exception {
            	String requestedUsername = req.session().attribute("user");
            	User currentUser = getUserDAO().getUserByUsername(requestedUsername);
            	if (currentUser == null || !isUser(req.session().attribute("user"), requestedUsername)) {
                	res.redirect("/login");
                	halt();
            		}
	}

 	public String mainMenu(Request req, Response res) {
		Map<String, Object> model = new HashMap<String, Object>();
		String username = req.session().attribute("user");
		model.put("username", username);
		   return new HandlebarsTemplateEngine()
                       .render(new ModelAndView(model, "mainmenu.hbs"));
        	}


	//Methods to perform Admin actions
	private String listUsers() {
        try {
		Map<String, Object> model = new HashMap<String, Object>();
        	model.put("users", getUserDAO().getUsers());
        	return new HandlebarsTemplateEngine()
	        	.render(new ModelAndView(model, "users.hbs"));
        } catch (Exception e) {
                return "Error: " + e.getMessage();
                }
        }

	//Add User Methods
	public String addUser(Request req, Response res) {
           Map<String, Object> model = new HashMap<String, Object>();
           return new HandlebarsTemplateEngine().render(new ModelAndView(model, "addUser.hbs"));
           }


	public String postAddUser(String username, String password, String fullname, Response res) {
           	Map<String, Object> model = new HashMap<String, Object>();
		try {
                        getUserDAO().addUser(new User(username, password, fullname));
                        res.redirect("/admin/users");
			return "";
                } catch (Exception e) {
                        return "Error: "+e.getMessage();
                }
        }

	//Modify User Methods
	public String modifyUser(String username, Response res) {
                Map<String, Object> model = new HashMap<String, Object>();
        try{
                model.put("user", username);
                return new HandlebarsTemplateEngine().render(new ModelAndView(model, "modifyUser.hbs"));
        } catch (Exception e) {
                System.out.println(e);
                }
           return "";
        }

        public String postModifyUser(String username, String password, String fullname, Response res) {
                Map<String, Object> model = new HashMap<String, Object>();
        try{
		User editUser = getUserDAO().getUserByUsername(username);
                editUser.setUsername(username);
                editUser.setPassword(password);
                editUser.setFullName(fullname);
		getUserDAO().modifyUser(editUser);
               	res.redirect("/admin/users");
		return "";
        } catch (Exception e) {
                System.out.println(e);
        }
        return "";
        }

	//Delete User Methods
	private String deleteUser(Request req, Response res) {
		Map<String, Object> model = new HashMap<>();
		model.put("title", "Delete User");
		model.put("message", "Are you sure that you want to delete this user?");
		model.put("onYes", "/admin/deleteUserExec/"+req.params(":username"));
		model.put("onNo", "/admin/users");
		return new HandlebarsTemplateEngine()
			.render(new ModelAndView(model, "confirm.hbs"));
	}

	private String deleteUserExec(Request req, Response res) {
		try {
			getUserDAO().deleteUser(req.params(":username"));
			res.redirect("/admin/users");
		} catch (Exception e) {
			return "Error: "+e.getMessage();
		}
		return null;
	}

	private String debugSession(Request req, Response res) {
		StringBuffer sb = new StringBuffer();
		for(String key: req.session().attributes()) {
			sb.append(key+"->"+req.session().attribute(key)+"<br />");
		}
		return sb.toString();
	}


	private String sessionDemo(Request req, Response res){
		if (req.session().isNew()) {
			req.session().attribute("value",0);
		} else {
			req.session().attribute("value", (int)req.session().attribute("value")+1);
		}
		return "<h1>"+req.session().attribute("value")+"</h1>";
	}

//	public void addRoutes() {
//		get("/admin/users", (req, res) -> listUsers());
//		get("/admin/deleteUser/:username", (req, res) -> deleteUser(req, res));
//		get("/admin/deleteUserExec/:username", (req, res) -> deleteUserExec(req, res));
//		get("/login", (req, res) -> login(req,res));
//		before("/protected/*", (request, response) -> checkAdmin(request, response));
//		post("/login", (req, res) -> loginPost(req,res));
//		get("/debugSession", (req, res) -> debugSession(req, res));
//	}
}
