import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;
//change split to splitRegion
public class MondrianArt {
    private int canvasHeight;
    private int canvasWidth;
    private BufferedImage bufferedImage;
    private Graphics2D g2d;
    private File file;
    Random random;
    MondrianArt(int width, int height){
        this.canvasWidth = width;
        this.canvasHeight = height;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = bufferedImage.createGraphics();
        random = new Random();

        //sets the color of the whole canvas to white
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0,canvasWidth, canvasHeight);

        //set upper boundary and left boundary black
        g2d.setColor(Color.black);
        g2d.drawLine(0, 0, width,0);
        g2d.drawLine(0, 1, 0,height);

        split(0, 0, canvasWidth, canvasHeight);
        printCanvas("canvasArt.png");
        g2d.dispose();

    }
    private void split(int regionX, int regionY, int regionWidth, int regionHeight){
        if(regionWidth > canvasWidth/2 && regionHeight > canvasHeight/2){

//            // draws black horizontal line on region
//            int ranY = random.nextInt(regionHeight-1)+regionY+1;   // ranY: [1, regionHeight-1]
//            g2d.drawLine(regionX, ranY, regionX+regionWidth-1, ranY);
//
//            // draws black vertical line on region
//            int ranX = random.nextInt(regionWidth-1)+regionX+1;  // ranX: [1, regionWidth-1]
//            g2d.drawLine(ranX, regionY, ranX, regionY+regionHeight-1);
            int ranY = drawHorizontalLine(regionX, regionY, regionWidth, regionHeight);
            int ranX = drawVerticalLine(regionX, regionY, regionWidth, regionHeight);

            //up-left region
            split(regionX, regionY, ranX-regionX, ranY-regionY);
            //up-right region
            split(ranX, regionY,regionX+regionWidth-ranX, ranY-regionY);
            //down-left region
            split(regionX, ranY, ranX-regionX, regionY+regionHeight-ranY);
            //down-right region
            split(ranX, ranY, regionX+regionWidth-ranX, regionY+regionHeight-ranY);

        }
        else if(regionWidth>canvasWidth/2){
//            // draws black vertical line on region at possible random location
//            int ranX = random.nextInt(regionHeight-1)+1;  // ranX: [1, regionHeight-1]
//            g2d.drawLine(ranX, regionY, ranX, regionHeight);
            int ranX = drawVerticalLine(regionX, regionY, regionWidth, regionHeight);

            split(regionX, regionY, ranX-regionX, regionHeight);
            split(ranX, regionY, regionX+regionWidth-ranX,regionHeight);

        }
        else if(regionHeight > canvasHeight/2){
//            // draws black horizontal line on region at possible random location
//            int ranY = random.nextInt(regionWidth-1)+1;   // ranY: [1, regionWidth-1]
//            g2d.drawLine(regionX, ranY, regionWidth-1, ranY);
            int ranY = drawHorizontalLine(regionX, regionY, regionWidth, regionHeight);

            split(regionX, regionY, regionWidth, ranY-regionY);
            split(regionX, ranY, regionWidth, regionY+regionHeight-ranY);
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
    // draws a vertical line on the region at a possible location
    // returns the x-axis of the vertical line
    public int drawVerticalLine(int x, int y, int width, int height){
        // draws black vertical line on region
        g2d.setColor(Color.black);
        int ranX = random.nextInt(width-1)+x+1;  // ranX: [1, regionWidth-1]
        g2d.drawLine(ranX, y, ranX, y+height-1);

        return ranX;
    }
    // draws a horizontal line on the region at a possible location
    // returns the y-axis of the drawn horizontal line
    public int drawHorizontalLine(int x, int y, int width, int height){
        // draws black horizontal line on region
        g2d.setColor(Color.black);
        int ranY = random.nextInt(height-1)+y+1;   // ranY: [1, regionHeight-1]
        g2d.drawLine(x, ranY, x+width-1, ranY);

        return ranY;

    }
    public void printCanvas(String fileName){
        file = new File(fileName);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) {
        new MondrianArt(700,700);

    }

}