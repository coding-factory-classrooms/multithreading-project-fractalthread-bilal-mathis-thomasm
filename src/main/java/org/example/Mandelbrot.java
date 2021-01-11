package org.example;

import org.w3c.dom.ls.LSOutput;

import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.imageio.ImageIO;

public class Mandelbrot implements Callable<BufferedImage> {
    int width, height, max;
    double xPos, yPos, zoom;

    public Mandelbrot(int width, int height, int max, double xPos, double yPos, double zoom) {
        this.width = width;
        this.height = height;
        this.max = max;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zoom = zoom;
    }

    int nbThread = Runtime.getRuntime().availableProcessors();

    @Override
    public BufferedImage call() {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0;
        int[] colors = new int[max];
        for (int i = 0; i < max; i++) {
            colors[i] = Color.HSBtoRGB(i / 256f, 1, i / (i + 8f));
        }

        List<RowTask> tasks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        int i = 0;
        long meanTime = 0;
        long start = System.currentTimeMillis();
        int chunkSize = height / nbThread;
        int startX = 0, startY = 0;

        ExecutorService executorService = Executors.newFixedThreadPool(nbThread);

        for (int chunkX = 0; chunkX < height; chunkX += chunkSize) {
            for (int chunkY = 0; chunkY < width; chunkY += chunkSize) {
                RowTask rowTask = new RowTask(width, height, max, xPos, yPos, zoom, image, colors, chunkSize, chunkX, chunkY);
                executorService.execute(rowTask);
            }
        }

        executorService.shutdown();

        try{
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            ImageIO.write(image, "png", new File("mandelbrot.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Generation time =" + elapsed + " ms");
        return image;
    }
}
