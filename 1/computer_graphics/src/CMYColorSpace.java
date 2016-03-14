import java.awt.color.ColorSpace;

/**
 * Created by asd on 14.03.2016.
 */
public class CMYColorSpace extends ColorSpace {

    public static CMYColorSpace getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public float[] toRGB(float[] colorvalue) {
        float R,G,B;

        R = 255 - colorvalue[0];
        G = 255 - colorvalue[1];
        B = 255 - colorvalue[2];

        return new float[]{R,G,B};
    }

    @Override
    public float[] fromRGB(float[] rgbvalue) {
        float C,M,Y;

        C = 255 - rgbvalue[0];
        M = 255 - rgbvalue[1];
        Y = 255 - rgbvalue[2];

        return new float[]{C,M,Y};
    }

    @Override
    public float[] toCIEXYZ(float[] colorvalue) {
        return new float[0];
    }

    @Override
    public float[] fromCIEXYZ(float[] colorvalue) {
        return new float[0];
    }

    CMYColorSpace() {
        super(ColorSpace.TYPE_CMY, 3);
    }

    private Object readResolve() {
        return getInstance();
    }

    private static class Holder {
        static final CMYColorSpace INSTANCE = new CMYColorSpace();
    }

    private static final long serialVersionUID = 5027741380892134286L;
}
