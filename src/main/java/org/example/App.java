package org.example;

import org.example.controllers.FractalController;
import org.example.controllers.HomeController;
import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import org.example.utils.LRUCache;
import spark.Spark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class App {
    private static final LRUCache<String, File>cache=new LRUCache<>(500);
    public static void main(String[] args) {
        initialize();

        Mandelbrot mandelbrot = new Mandelbrot();
        HomeController homeController = new HomeController();
        FractalController fractalController = new FractalController();

        Spark.get("/", homeController::getHome);
        Spark.get("/fractal", fractalController::getFractal);

        Spark.get("/image", (req, res) -> {
            return Template.render("image.html", new HashMap<>());
        });

        Spark.get("/images/:x/:y/:zoom", (req, res) -> {
            double x = Double.parseDouble(req.params(":x"));
            double y = Double.parseDouble(req.params(":y"));
            double zoom = Double.parseDouble(req.params(":zoom"));
            String Key= String.format("%f_%f_%f",x,y,zoom);
            if (App.cache.containsKey(Key)){
                try (OutputStream out = res.raw().getOutputStream()) {
                    ImageIO.write(ImageIO.read(App.cache.get(Key)), "png", out);
                }

            }
            else{
                File file = Mandelbrot.getImageFrom(x, y, zoom);
                try (OutputStream out = res.raw().getOutputStream()) {
                    ImageIO.write(ImageIO.read(file), "png", out);
                }
                App.cache.add(Key,file);
            }

            return res;
        });

    }

    static void initialize() {
        Template.initialize();

        // Display exceptions in logs
        Spark.exception(Exception.class, (e, req, res) -> e.printStackTrace());

        // Serve static files (img/css/js)
        Spark.staticFiles.externalLocation(Conf.STATIC_DIR.getPath());

        // Configure server port
        Spark.port(Conf.HTTP_PORT);
        final LoggerMiddleware log = new LoggerMiddleware();
        Spark.before(log::process);
    }
}
