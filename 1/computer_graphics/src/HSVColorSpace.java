import java.awt.color.ColorSpace;

/**
 * Created by asd on 14.03.2016.
 */
public class HSVColorSpace extends ColorSpace {

    public static HSVColorSpace getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public float[] toRGB(float[] colorvalue) {

        float H = colorvalue[0];
        float S = colorvalue[1];
        float V = colorvalue[2];

        float R=0,G=0,B=0;

        int Hi = (int) Math.floor(H/60)%6;
        float Vmin=(100-S)*V/100;
        float a = (V-Vmin)*(H%60)/60;
        float Vinc = Vmin + a;
        float Vdec = V - a;

        switch (Hi){
            case 0:
                R = V;
                G = Vinc;
                B = Vmin;
                break;
            case 1:
                R = Vdec;
                G = V;
                B = Vmin;
                break;
            case 2:
                R = Vmin;
                G = V;
                B = Vinc;
                break;
            case 3:
                R = Vmin;
                G = Vdec;
                B = V;
                break;
            case 4:
                R = Vinc;
                G = Vmin;
                B = V;
                break;
            case 5:
                R = V;
                G = Vmin;
                B = Vdec;
                break;
        }

        return new float[]{R*2.55f,G*2.55f,B*2.55f};
    }

    @Override
    public float[] fromRGB(float[] rgbvalue) {
        float R = ( rgbvalue[0] / 255 );                     //RGB from 0 to 255
        float G = ( rgbvalue[1] / 255 );
        float B = ( rgbvalue[2] / 255 );

        float H=0,S=0,V;

        float min = Float.min(Float.min( R, G), B );    //Min. value of RGB
        float max = Float.max(Float.max(R, G), B);    //Max. value of RGB
        float delta = max - min;             //Delta RGB value

        V = max;

        if ( delta == 0 ) {
            H = 0;
        } else if (max == R && G >= B){
            H = 60*(G-B)/delta;
        } else if (max == R && G < B){
            H = 60*(G-B)/delta + 360;
        } else if (max == G){
            H = 60*(B-R)/delta + 120;
        } else if (max == B){
            H = 60*(R-G)/delta + 240;
        }

        if (max == 0){
            S = 0;
        } else {
            S = 1 - (min/max);
        }

        return new float[]{H,S*100,V*100};
    }

    @Override
    public float[] toCIEXYZ(float[] colorvalue) {
        return new float[0];
    }

    @Override
    public float[] fromCIEXYZ(float[] colorvalue) {
        return new float[0];
    }

    HSVColorSpace() {
        super(ColorSpace.TYPE_HSV, 3);
    }

    private Object readResolve() {
        return getInstance();
    }

    private static class Holder {
        static final HSVColorSpace INSTANCE = new HSVColorSpace();
    }

    private static final long serialVersionUID = 5027741380892134287L;
}
