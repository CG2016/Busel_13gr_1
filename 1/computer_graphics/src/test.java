import java.awt.*;

/**
 * Created by asd on 14.03.2016.
 */
public class test {
    public static void main(String []args){
        Color color = Color.WHITE;
        float [] hsv = new LUVColorSpace().fromRGB(new float[]{color.getRed(),color.getGreen(),color.getBlue()});
        System.out.println(hsv[0] +","+ hsv[1] +","+ hsv[2]);
    }
}
