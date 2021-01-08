package org.example;

import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Mandelbrot {
    public static void main(String[] args) throws Exception {

    }

    public static File getImageFrom(int width, int height, double xPos, double yPos, double zoom) throws IOException {
        int max = 5000;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0;
        int[] colors = new int[max];
        for (int i = 0; i < max; i++) {
            colors[i] = Color.HSBtoRGB(i / 256f, 1, i / (i + 8f));
        }

        // int i = 0;
        long meanTime = 0;
        //while(i < 10){
        long start = System.currentTimeMillis();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double c_re = ((col - width / 2) * zoom / width) + xPos;
                double c_im = ((row - height / 2) * zoom / width) + yPos;
                double x = 0, y = 0;
                int iteration = 0;
                while (x * x + y * y < 4 && iteration < max) {
                    double x_new = x * x - y * y + c_re;
                    y = 2 * x * y + c_im;
                    x = x_new;
                    iteration++;
                }
                if (iteration < max) image.setRGB(col, row, colors[iteration]);
                else image.setRGB(col, row, black);
            }
        }


        ImageIO.write(image, "png", new File("mandelbrot.png"));
        File repositoryFile = new File("mandelbrot.png");
        return repositoryFile;

    }
}
