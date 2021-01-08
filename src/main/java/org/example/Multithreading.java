package org.example;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Multithreading {
    private static final int THREADS = 8;

    public static void main(String[] args) {
        generateMandelbrot();
    }
     public static void generateMandelbrot(){
         ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
         Mandelbrot task = new Mandelbrot(1000, 1000, 1000, 0.38, 0.38, 0.4);
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
     }
}
