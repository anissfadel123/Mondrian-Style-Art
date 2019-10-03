import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class MondrianArt {
    private int canvasHeight;
    private int canvasWidth;
    private BufferedImage bufferedImage;
    private Graphics2D g2d;
    private File file;
    MondrianArt(int width, int height){
        this.canvasWidth = width;
        this.canvasHeight = height;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = bufferedImage.createGraphics();

        //sets the color of the whole canvas to white
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0,canvasWidth, canvasHeight);

        split();
        printCanvas("canvasArt.png");
        g2d.dispose();

    }
    public void split(){

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
        new MondrianArt(25,25);

    }

}