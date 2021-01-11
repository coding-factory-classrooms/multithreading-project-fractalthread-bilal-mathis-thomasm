package org.example;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Multithreading{
    private static final int THREADS = 8;

    public static void main(String[] args) {

    }
     public static File generateMandelbrot(int width, int height, double xPos, double yPos, double zoom) throws IOException {

         ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
         Mandelbrot task = new Mandelbrot(width, height, 1000, xPos, yPos, zoom);
         Future<BufferedImage> future = executorService.submit(task);
         BufferedImage bufferedImage = null;
         try {
             bufferedImage = future.get();
         } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         }

         System.out.println(bufferedImage);
         File outputfile = new File("mandelbrot.png");
         ImageIO.write(bufferedImage, "png", outputfile);
         return outputfile;
     }


}
