package org.example;
import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Callable;
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

    @Override
    public BufferedImage call() {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0;
        int[] colors = new int[max];
        for (int i = 0; i<max; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }

        int i = 0;
        long meanTime =0;
            long start = System.currentTimeMillis();
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    double c_re = ((col - width/2)*zoom/width) + xPos;
                    double c_im = ((row - height/2)*zoom/width) + yPos;
                    double x = 0, y = 0;
                    int iteration = 0;
                    while (x*x+y*y < 4 && iteration < max) {
                        double x_new = x*x-y*y+c_re;
                        y = 2*x*y+c_im;
                        x = x_new;
                        iteration++;
                    }
                    if (iteration < max) image.setRGB(col, row, colors[iteration]);
                    else image.setRGB(col, row, black);
                }
            }
            try {
                ImageIO.write(image, "png", new File("mandelbrot.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }

            long elapsed = System.currentTimeMillis() - start;

        System.out.println("Generation time ="+ elapsed + " ms");
        return image;
    }
}
