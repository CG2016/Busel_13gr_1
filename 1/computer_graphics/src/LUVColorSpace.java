import java.awt.color.ColorSpace;

/**
 * Created by asd on 29.02.2016.
 */
public class LUVColorSpace extends ColorSpace {

    public static LUVColorSpace getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public float[] fromCIEXYZ(float[] colorvalue) {
        float u = (4 * colorvalue[0]) / (colorvalue[0] + (15 * colorvalue[1]) + (3 * colorvalue[2]));
        float v = (9 * colorvalue[1]) / (colorvalue[0] + (15 * colorvalue[1]) + (3 * colorvalue[2]));

        float y = colorvalue[1] / 100;
        if ( y > 0.008856 ){
            y = (float)Math.pow(y,1f/3f);
        } else {
            y = (7.787f * y) + (16f / 116f);
        }

        float ref_X =  95.047f;
        float ref_Y = 100f;
        float ref_Z = 108.883f;

        float ref_U = (4 * ref_X) / (ref_X + (15 * ref_Y) + (3 * ref_Z));
        float ref_V = (9 * ref_Y) / (ref_X + (15 * ref_Y) + (3 * ref_Z));

        float L = ((116 * y) - 16);
        float U = (13 * L * (u - ref_U));
        float V = (13 * L * (v - ref_V));

        return new float[] {L, U, V};
    }

    @Override
    public float[] fromRGB(float[] rgbvalue) {
        float R = rgbvalue[0] / 255;        //R from 0 to 255
        float G = rgbvalue[1] / 255;        //G from 0 to 255
        float B = rgbvalue[1] / 255;        //B from 0 to 255

        if (R > 0.04045){
            R = (float) Math.pow((R + 0.055f) / 1.055f ,2.4);
        } else {
            R = R / 12.92f;
        }
        if ( G > 0.04045 ) {
            G = (float) Math.pow((G + 0.055f) / 1.055f , 2.4);
        } else {
            G = G / 12.92f;
        }
        if ( B > 0.04045 ){
            B = (float) Math.pow((B + 0.055f) / 1.055f, 2.4);
        } else{
            B = B / 12.92f;
        }

        R *= 100;
        G *= 100;
        B *= 100;

//Observer. = 2°, Illuminant = D65
        float X = R * 0.4124f + G * 0.3576f + B * 0.1805f;
        float Y = R * 0.2126f + G * 0.7152f + B * 0.0722f;
        float Z = R * 0.0193f + G * 0.1192f + B * 0.9505f;

        return fromCIEXYZ(new float[]{X,Y,Z});
    }

    @Override
    public float getMaxValue(int component) {
        return 128f;
    }

    @Override
    public float getMinValue(int component) {
        return (component == 0)? 0f: -128f;
    }

    @Override
    public String getName(int idx) {
        return String.valueOf("Luv".charAt(idx));
    }

    @Override
    public float[] toCIEXYZ(float[] colorvalue) {
        float y = (colorvalue[0] + 16) / 116;
        if (Math.pow(y,3) > 0.008856){
            y = (float)Math.pow(y,3);
        } else {
            y = (y - 16 / 116) / 7.787f;
        }

        float ref_X =  95.047f;
        float ref_Y = 100.000f;
        float ref_Z = 108.883f;

        float ref_U = (4 * ref_X) / (ref_X + (15 * ref_Y) + (3 * ref_Z));
        float ref_V = (9 * ref_Y) / (ref_X + (15 * ref_Y) + (3 * ref_Z));

        float var_U = colorvalue[1] / (13 * colorvalue[0]) + ref_U;
        float var_V = colorvalue[2] / (13 * colorvalue[0]) + ref_V;

        float Y = y * 100;
        float X =  -(9 * Y * var_U) / ((var_U - 4) * var_V  - var_U * var_V);
        float Z = (9 * Y - (15 * var_V * Y) - (var_V * X)) / (3 * var_V);
        return new float[] {X, Y, Z};
    }

    @Override
    public float[] toRGB(float[] colorvalue) {
        float[] xyz = toCIEXYZ(colorvalue);

        float X = xyz[0] / 100;        //X from 0 to  95.047      (Observer = 2°, Illuminant = D65)
        float Y = xyz[1] / 100;        //Y from 0 to 100.000
        float Z = xyz[2] / 100;        //Z from 0 to 108.883

        float R = X *  3.2406f + Y * -1.5372f + Z * -0.4986f;
        float G = X * -0.9689f + Y *  1.8758f + Z *  0.0415f;
        float B = X *  0.0557f + Y * -0.2040f + Z *  1.0570f;

        if (R > 0.0031308){
            R = 1.055f * (float) Math.pow(R,(1/2.4)) - 0.055f;
        } else {
            R = 12.92f * R;
        }
        if ( G > 0.0031308 ){
            G = 1.055f * (float) Math.pow(G,(1/2.4)) - 0.055f;
        } else {
            G = 12.92f * G;
        }
        if ( B > 0.0031308 ){
            B = 1.055f * (float)Math.pow(B,(1/2.4)) - 0.055f;
        }
        else {
            B = 12.92f * B;
        }

        return new float[]{R*255,G*255,B*255};
    }

    LUVColorSpace() {
        super(ColorSpace.TYPE_Luv, 3);
    }

    private Object readResolve() {
        return getInstance();
    }

    private static class Holder {
        static final LUVColorSpace INSTANCE = new LUVColorSpace();
    }

    private static final long serialVersionUID = 5027741380892134289L;

    private static final ColorSpace CIEXYZ =
            ColorSpace.getInstance(ColorSpace.CS_CIEXYZ);

}
