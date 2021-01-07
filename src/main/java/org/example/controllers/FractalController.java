package org.example.controllers;

import org.example.core.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class FractalController {

    public String getFractal(Request request, Response response){
        return "Fractal";
    }
}
