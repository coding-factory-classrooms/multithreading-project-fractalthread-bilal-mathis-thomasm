package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Color.black;

public class RowTask implements Runnable {
    int width, height, max, chunkSize, startX, startY;
    int[] colors;
    double xPos, yPos, zoom;
    int nbThread = Runtime.getRuntime().availableProcessors();
    String typeFactal;

    BufferedImage image;

    @Override
    public void run() {
        createRow();
    }

    public RowTask(int width, int height, int max, double xPos, double yPos, double zoom, BufferedImage image, int[] colors, int chunkSize, int startX, int startY, String typeFactal) {
        this.width = width;
        this.height = height;
        this.max = max;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zoom = zoom;
        this.image = image;
        this.colors = colors;
        this.chunkSize = chunkSize;
        this.startX = startX;
        this.startY = startY;
        this.typeFactal = typeFactal;
    }

    public void createRow() {

        switch (typeFactal){

            case "mandelbrot":
                int maxX = startX+chunkSize;
                int maxY = startY+chunkSize;
                for (int row = startX; row < maxX && row < height; row++) {
                    for (int col = startY; col < maxY && col < width; col++) {
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
                        else image.setRGB(col, row, 0);
                    }
                }
                break;

            case "julia":
                //je suis vraiment désolé mais mon I9 meurt avec 5000 itérations
                max = 300;
                zoom = zoom-1;
                double CX = -0.7, CY = 0.27015;
                for (int row = startX; row < width; row++) {
                    for (int col = startY; col < height; col++) {
                        double zx = 1.5 * (row - width / 2) / (0.5 * zoom * width) + xPos;
                        double zy = (col - height / 2) / (0.5 * zoom * height) + yPos;
                        float i = max;
                        while (zx * zx + zy * zy < 4 && i > 0) {
                            double tmp = zx * zx - zy * zy + CX;
                            zy = 2.0 * zx * zy + CY;
                            zx = tmp;
                            i--;
                        }
                        int c = Color.HSBtoRGB((max / i) % 1, 1, i > 0 ? 1 : 0);
                        image.setRGB(row, col, c);
                    }
                }
                break;

        }
    }
}
