// Aniss Fadel
// CISC 4900
// Task: to write a program that uses recursion to generate pseudo-random art in a Mondrian style.
// MondrianArt.class

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class MondrianArt {
    private int canvasHeight;
    private int canvasWidth;
    private BufferedImage bufferedImage;
    private Graphics2D g2d;
    private File file;
    Random random;
    MondrianArt(int width, int height, String name){

        this.canvasWidth = width;
        this.canvasHeight = height;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = bufferedImage.createGraphics();
        random = new Random();


        splitRegion(0, 0, canvasWidth, canvasHeight);
        printCanvas(name);
        g2d.dispose();

    }
    // Generates psuedo-random art in a Mondrian style.
    // Splits region depending on regionWidth && regionHeight
    // recursively calls the splitted regions
    // eventually colors the regions (white, blue, red, yellow)
    private void splitRegion(int regionX, int regionY, int regionWidth, int regionHeight){
        if(regionWidth > canvasWidth/2 && regionHeight > canvasHeight/2){

            int ranY = drawHorizontalLine(regionX, regionY, regionWidth, regionHeight);
            int ranX = drawVerticalLine(regionX, regionY, regionWidth, regionHeight);

            //up-left region
            splitRegion(regionX, regionY, ranX-regionX, ranY-regionY);
            //up-right region
            splitRegion(ranX, regionY,regionX+regionWidth-ranX, ranY-regionY);
            //down-left region
            splitRegion(regionX, ranY, ranX-regionX, regionY+regionHeight-ranY);
            //down-right region
            splitRegion(ranX, ranY, regionX+regionWidth-ranX, regionY+regionHeight-ranY);

        }
        else if(regionWidth>canvasWidth/2){

            int ranX = drawVerticalLine(regionX, regionY, regionWidth, regionHeight);

            splitRegion(regionX, regionY, ranX-regionX, regionHeight);
            splitRegion(ranX, regionY, regionX+regionWidth-ranX,regionHeight);

        }
        else if(regionHeight > canvasHeight/2){
            int ranY = drawHorizontalLine(regionX, regionY, regionWidth, regionHeight);

            splitRegion(regionX, regionY, regionWidth, ranY-regionY);
            splitRegion(regionX, ranY, regionWidth, regionY+regionHeight-ranY);
        }
        else if(regionHeight > 50 && regionWidth > 50){
            int ranY = drawHorizontalLine(regionX, regionY, regionWidth, regionHeight);
            int ranX = drawVerticalLine(regionX, regionY, regionWidth, regionHeight);

            //up-left region
            splitRegion(regionX, regionY, ranX-regionX, ranY-regionY);
            //up-right region
            splitRegion(ranX, regionY,regionX+regionWidth-ranX, ranY-regionY);
            //down-left region
            splitRegion(regionX, ranY, ranX-regionX, regionY+regionHeight-ranY);
            //down-right region
            splitRegion(ranX, ranY, regionX+regionWidth-ranX, regionY+regionHeight-ranY);
        }
        else if(regionWidth>50){

            int ranX = drawVerticalLine(regionX, regionY, regionWidth, regionHeight);

            splitRegion(regionX, regionY, ranX-regionX, regionHeight);
            splitRegion(ranX, regionY, regionX+regionWidth-ranX,regionHeight);

        }
        else if(regionHeight > 50){
            int ranY = drawHorizontalLine(regionX, regionY, regionWidth, regionHeight);

            splitRegion(regionX, regionY, regionWidth, ranY-regionY);
            splitRegion(regionX, ranY, regionWidth, regionY+regionHeight-ranY);
        }
        else{
            //chooses randomly white or colored (0 = white, 1 = colored)
            int choice = random.nextInt(2);
            Color color;
            if(choice == 0){
                color = Color.white;
            }else {
                // 0 = red, 1 = blue, 2 = yellow
                choice = random.nextInt(3);
                if(choice == 0)
                    color = Color.red;
                else if(choice == 1)
                    color = Color.blue;
                else
                    color = Color.yellow;
            }
            g2d.setColor(color);
            g2d.fillRect(regionX+1,regionY+1,regionWidth-1,regionHeight-1);
        }

    }
    // Draws a vertical line on the region at a possible location.
    // Returns the x-axis of the vertical line
    public int drawVerticalLine(int x, int y, int width, int height){
        // draws black vertical line on region
        g2d.setColor(Color.black);
        int ranX = random.nextInt(width-1)+x+1;  // ranX: [1, regionWidth-1]
        g2d.drawLine(ranX, y, ranX, y+height-1);

        return ranX;
    }
    // Draws a horizontal line on the region at a possible location.
    // Returns the y-axis of the drawn horizontal line
    public int drawHorizontalLine(int x, int y, int width, int height){
        // draws black horizontal line on region
        g2d.setColor(Color.black);
        int ranY = random.nextInt(height-1)+y+1;   // ranY: [1, regionHeight-1]
        g2d.drawLine(x, ranY, x+width-1, ranY);

        return ranY;

    }
    // Prints the canvas into a png file
    public void printCanvas(String fileName){
        //sets black boarder on canvas
        g2d.setColor(Color.black);
        g2d.drawLine(0, 0, canvasWidth,0);
        g2d.drawLine(0, canvasHeight-1, canvasWidth,canvasHeight-1);
        g2d.drawLine(0, 1, 0,canvasHeight);
        g2d.drawLine(canvasWidth-1, 1, canvasWidth-1,canvasHeight);


        file = new File(fileName);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) {
        new MondrianArt(700,700, "canvasArt1.png");
        new MondrianArt(400,700, "canvasArt2.png");
        new MondrianArt(900,700, "canvasArt3.png");


    }

}