package org.example.controllers;

import org.example.core.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class HomeController {

    public String getHome(Request request, Response response){
        return Template.render("home.html", new HashMap<>());
    }
}
