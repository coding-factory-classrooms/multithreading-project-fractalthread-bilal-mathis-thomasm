package org.example;

import org.example.controllers.FractalController;
import org.example.controllers.HomeController;
import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import spark.Spark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        initialize();

        Multithreading multithreading = new Multithreading();
        HomeController homeController = new HomeController();
        FractalController fractalController = new FractalController();

        Spark.get("/", homeController::getHome);
        Spark.get("/fractal", fractalController::getFractal);

        Spark.get("/image", (req, res) -> {
            return Template.render("image.html", new HashMap<>());
        });

        Spark.get("/images/:width/:height/:x/:y/:zoom", (req, res) -> {
            double width = Double.parseDouble(req.params(":width"));
            double height = Double.parseDouble(req.params(":height"));
            double x = Double.parseDouble(req.params(":x"));
            double y = Double.parseDouble(req.params(":y"));
            double zoom = Double.parseDouble(req.params(":zoom"));

            int intWidth = (int)width;
            int intHeight = (int)height;

            File file = Multithreading.generateMandelbrot(intWidth,intHeight, x, y, zoom);
            res.raw().setContentType("image/jpeg");

            try (OutputStream out = res.raw().getOutputStream()) {
                ImageIO.write(ImageIO.read(file), "png", out);
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
