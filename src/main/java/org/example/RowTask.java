package org.example;

import java.awt.image.BufferedImage;

import static java.awt.Color.black;

public class RowTask implements Runnable {
    int width, height, max, chunkSize, startX, startY;
    int[] colors;
    double xPos, yPos, zoom;
    int nbThread = Runtime.getRuntime().availableProcessors();

    BufferedImage image;

    @Override
    public void run() {
        createRow();
    }

    public RowTask(int width, int height, int max, double xPos, double yPos, double zoom, BufferedImage image, int[] colors, int chunkSize, int startX, int startY) {
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
    }

    public void createRow() {

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
    }
}
