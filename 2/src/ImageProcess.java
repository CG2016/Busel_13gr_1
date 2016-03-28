import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by asd on 24.03.2016.
 */
public class ImageProcess {
    private Color oldColor;
    private Color newColor;
    private int currDistance;
    private BufferedImage image;
    private float []oldColorLab;

    public ImageProcess(int currDistance, Color oldColor, Color newColor, BufferedImage input){
        this.currDistance = currDistance;
        this.oldColor = oldColor;
        this.newColor = newColor;
        this.image = input;
        oldColorLab = convertToLab(oldColor);
    }

    public void setCurrDistance(int currDistance) {
        this.currDistance = currDistance;
    }

    private float[] convertToLab(Color color){
        float R = color.getRed() / 255f;
        float G = color.getGreen() / 255f;
        float B = color.getBlue() / 255f;

        if (R > 0.04045f){
            R = (float) Math.pow((R + 0.055f) / 1.055f ,2.4f);
        } else {
            R = R / 12.92f;
        }
        if ( G > 0.04045f ) {
            G = (float) Math.pow((G + 0.055f) / 1.055f , 2.4f);
        } else {
            G = G / 12.92f;
        }
        if ( B > 0.04045f ){
            B = (float) Math.pow((B + 0.055f) / 1.055f, 2.4f);
        } else{
            B = B / 12.92f;
        }

        R *= 100f;
        G *= 100f;
        B *= 100f;

        float X = R * 0.4124f + G * 0.3576f + B * 0.1805f;
        float Y = R * 0.2126f + G * 0.7152f + B * 0.0722f;
        float Z = R * 0.0193f + G * 0.1192f + B * 0.9505f;

        X = X / 95.047f;         //ref_X =  95.047   Observer= 2°, Illuminant= D65
        Y = Y / 100.000f;          //ref_Y = 100.000
        Z = Z / 108.883f;          //ref_Z = 108.883

        if ( X > 0.008856f ){
            X = (float) Math.pow(X,( 1f/3f ));
        } else {
            X = ( 7.787f * X ) + ( 16f / 116f );
        }
        if ( Y > 0.008856f ) {
            Y = (float) Math.pow(Y, ( 1f/3f ));
        } else {
            Y = ( 7.787f * Y ) + ( 16f / 116f );
        }
        if ( Z > 0.008856f ){
            Z = (float) Math.pow(Z, ( 1f/3f ));
        } else {
            Z = ( 7.787f * Z ) + ( 16f / 116f );
        }

        float L = ( 116f * Y ) - 16f;
        float a = 500f * ( X - Y );
        float b = 200f * ( Y - Z );

        return new float[] {L,a,b};
    }

    private int distance(float[] lab1, float[] lab2){
        int distance = (int) Math.pow((lab1[0]-lab2[0])*(lab1[0]-lab2[0])+(lab1[1]-lab2[1])*(lab1[1]-lab2[1])+(lab1[2]-lab2[2])*(lab1[2]-lab2[2]),0.5);
        return distance;
    }

    public File generateNewImage() throws IOException {
        for(int i = 0; i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){
                int RGBColor = image.getRGB(i,j);
                Color currColor = new Color(RGBColor);
                float[] currColorLab = convertToLab(currColor);
                int distance = distance(oldColorLab, currColorLab);
                if(distance < currDistance){
                    int distanceR = Math.abs(currColor.getRed() - oldColor.getRed());
                    int distanceG = Math.abs(currColor.getGreen() - oldColor.getGreen());
                    int distanceB = Math.abs(currColor.getBlue() - oldColor.getBlue());
                    Color newColorWithDistance = new Color(Math.abs(newColor.getRed()-distanceR), Math.abs(newColor.getGreen()-distanceG), Math.abs(newColor.getBlue() - distanceB));
                    image.setRGB(i,j,newColorWithDistance.hashCode());
                }
            }
        }
        File out = new File("out.jpg");
        ImageIO.write(image, "JPG", out);
        return out;
    }

}
