/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.au.cc.gallery;

import java.util.Map;
import java.util.HashMap;

import static spark.Spark.*;

import spark.ModelAndView;

import spark.template.handlebars.HandlebarsTemplateEngine;

public class App {
    public String getGreeting() {
        return "Hello David.";
    }

    public static void main(String[] args) throws Exception {
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
//	new Calculator().addRoutes();

	UserAdmin.main(null);
//	DB.demo();
    }
}
