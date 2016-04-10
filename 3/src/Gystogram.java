import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by asd on 03.04.2016.
 */
public class Gystogram {
    BufferedImage image;
    Vector<Integer> RGB = new Vector<Integer>();
    int[] R = new int[256];
    int[] G = new int[256];
    int[] B = new int[256];
    int[] H = new int[256];

    public Gystogram(File imageFile) throws IOException {
        image = ImageIO.read(imageFile);
        for (int i = 0; i < 256; i++) {
            R[i] = 0;
            G[i] = 0;
            B[i] = 0;
            H[i] = 0;
        }
        getRGB();
        makeGystogram();
    }

    public void getRGB(){
        for(int i = 0;  i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){
                Color pixelColor = new Color(image.getRGB(i,j));
                RGB.add(pixelColor.getRed());
                RGB.add(pixelColor.getGreen());
                RGB.add(pixelColor.getBlue());
            }
        }
    }

    public void makeGystogram(){
        int byteIndex = 0;
        while (byteIndex < RGB.size()){
            int r = RGB.get(byteIndex);
            int g = RGB.get(byteIndex+1);
            int b = RGB.get(byteIndex+2);
            H[r] = H[r] + 1;
            H[g] = H[g] + 1;
            H[b] = H[b] + 1;
            R[r] = R[r] + 1;
            G[g] = G[g] + 1;
            B[b] = B[b] + 1;
            byteIndex+=3;
        }
        for(int i = 0; i < H.length; i++){
            H[i] /= 3;
        }
    }
}
