/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.au.cc.gallery;

import java.util.Map;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

import static spark.Spark.*;

import spark.ModelAndView;

import spark.template.handlebars.HandlebarsTemplateEngine;

public class App {
    public String getGreeting() {
        return "Hello David.";
    }

    public static void main(String[] args) throws Exception {
//	port(5000);

	String portString = System.getenv("JETTY_PORT");
           if (portString == null || portString.equals("")) {
             port(5000);
           }  else {
           port(Integer.parseInt(portString));
	   }

	Routes.connectToDatabase();
	new Routes().addRoutes();

//	String portString = System.getenv("JETTY_PORT");
//	if (portString == null || portString.equals(""))
//	    port(5000);
//	else
//	    port(Integer.parseInt(portString));
//	get("/hello", (req, res) -> "<!DOCTYPE html><html><head><title>Hello</title><meta charset=\"utf-8\" /></head><body><h1>Hello World</h1></body></html>");
//
//	get("/goodbye", (req, res) -> "Goodbye");
//
//	get("/greet/:name", (req, res) -> "Nice to meet you "+ req.params(":name"));
//
//	post("/add", (req, res) -> "The sum is " + (Integer.parseInt(req.queryParams("x")) + Integer.parseInt(req.queryParams("y"))));
//
//	DB db = new DB();
//	new Calculator().addRoutes();
//	port(5000);
//        get("/hello", (req, res) -> "Hello World");
//	UserAdmin.main(null);
//	DB.demo();
    }
}
