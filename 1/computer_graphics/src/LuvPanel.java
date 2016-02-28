import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

/**
 * Created by asd on 22.02.2016.
 */
public class LuvPanel extends AbstractColorChooserPanel {

    private JLabel llabel = new JLabel("L*");
    private JLabel ulabel = new JLabel("u*");
    private JLabel vlabel = new JLabel("v");
    private JSlider lslider = new JSlider(JSlider.HORIZONTAL,0,100,0);
    private JSlider uslider = new JSlider(JSlider.HORIZONTAL,-100,100,0);
    private JSlider vslider = new JSlider(JSlider.HORIZONTAL,-100,100,0);
    private JSpinner lfield = new JSpinner();
    private JSpinner ufield = new JSpinner();
    private JSpinner vfield = new JSpinner();

    private double R;
    private double G;
    private double B;

    private double X;
    private double Y;
    private double Z;

    private double L;
    private double U;
    private double V;

    public LuvPanel() {
        super();
    }

    @Override
    public void updateChooser() {

    }

    @Override
    protected void buildChooser() {
        Color color = getColorSelectionModel().getSelectedColor();
        R = color.getRed();
        G = color.getGreen();
        B = color.getBlue();
        convertRGBtoXYZ();
        convertXYZtoLuv();
        lslider.setValue((int) L);
        SpinnerModel lmodel = new SpinnerNumberModel(L, 0, 100, 1);
        SpinnerModel umodel = new SpinnerNumberModel(U, -100, 100, 1);
        SpinnerModel vmodel = new SpinnerNumberModel(V, -100, 100, 1);
        lfield.setModel(lmodel);
        ufield.setModel(umodel);
        vfield.setModel(vmodel);
        /*lfield.setText(Double.toString(L));
        lfield.setEditable(false);
        ufield.setText(Double.toString(U));
        ufield.setEditable(false);
        vfield.setText(Double.toString(V));
        vfield.setEditable(false);*/
        Box container = Box.createVerticalBox();
        container.setSize(750,450);
        Box lbox = Box.createHorizontalBox();
        Box ubox = Box.createHorizontalBox();
        Box vbox = Box.createHorizontalBox();
        lbox.add(llabel);
        lbox.add(lslider);
        lbox.add(lfield);
        ubox.add(ulabel);
        ubox.add(uslider);
        ubox.add(ufield);
        vbox.add(vlabel);
        vbox.add(vslider);
        vbox.add(vfield);
        container.add(lbox);
        container.add(ubox);
        container.add(vbox);
        this.add(container);
        //lslider.addChangeListener(lListener);
    }

    /*private final ChangeListener lListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent event) {
            JSlider source = (JSlider) event.getSource();
            L = source.getValue();
            convertLuvtoXYZ();
            convertXYZtoRGB();
            getColorSelectionModel().setSelectedColor(new Color((int)R,(int)G,(int)B));
        }
    };*/

    @Override
    public String getDisplayName() {
        return "L*u*v";
    }

    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }

    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }

    private void convertRGBtoXYZ(){
        double r = ( R / 255.0 );        //R from 0 to 255
        double g = ( G / 255.0 );        //G from 0 to 255
        double b = ( B / 255.0 );        //B from 0 to 255

        if ( r > 0.04045 ) {
            r = Math.pow((r + 0.055) / 1.055, 2.4);
        } else {
            r = r / 12.92;
        }
        if ( g > 0.04045 ){
            g = Math.pow( ( g + 0.055 ) / 1.055 , 2.4);
        } else {
            g = g / 12.92;
        }
        if (b > 0.04045 ){
            b = Math.pow( ( b + 0.055 ) / 1.055 , 2.4);
        } else {
            b = b / 12.92;
        }

        r *= 100.0;
        g *= 100.0;
        b *= 100.0;

        X = r * 0.4124 + g * 0.3576 + b * 0.1805;
        Y = r * 0.2126 + g * 0.7152 + b * 0.0722;
        Z = r * 0.0193 + g * 0.1192 + b * 0.9505;
    }

    private void convertXYZtoRGB() {
        double x = X / 100;        //X from 0 to  95.047
        double y = Y / 100;        //Y from 0 to 100.000
        double z = Z / 100;        //Z from 0 to 108.883

        double r = x *  3.2406 + y * -1.5372 + z * -0.4986;
        double g = x * -0.9689 + y *  1.8758 + z *  0.0415;
        double b = x *  0.0557 + y * -0.2040 + z *  1.0570;

        if ( r > 0.0031308 ){
            r = 1.055 * Math.pow(r,( 1.0 / 2.4 ) ) - 0.055;
        } else {
            r = 12.92 * r;
        }
        if ( g > 0.0031308 ){
            g = 1.055 * Math.pow(g, ( 1.0 / 2.4 ) ) - 0.055;
        } else {
            r = 12.92 * r;
        }
        if ( b > 0.0031308 ){
            b = 1.055 * Math.pow(b, ( 1.0 / 2.4 ) ) - 0.055;
        } else{
            b = 12.92 * b;
        }

        R = (int) (r * 255);
        G = (int) (g * 255);
        B = (int) (b * 255);
    }

    private void convertXYZtoLuv(){
        double u = ( 4.0 * X ) / ( X + ( 15.0 * Y ) + ( 3.0 * Z ) );
        double v = ( 9.0 * Y ) / ( X + ( 15.0 * Y ) + ( 3.0 * Z ) );

        double y = Y / 100.0;
        if ( y > 0.008856 ){
            y = Math.pow(y,1.0/3.0);
        } else {
            y = ( 7.787 * y ) + ( 16.0 / 116.0 );
        }

        double ref_X =  95.047;
        double ref_Y = 100.000;
        double ref_Z = 108.883;

        double ref_U = ( 4.0 * ref_X ) / ( ref_X + ( 15.0 * ref_Y ) + ( 3.0 * ref_Z ) );
        double ref_V = ( 9.0 * ref_Y ) / ( ref_X + ( 15.0 * ref_Y ) + ( 3.0 * ref_Z ) );

        L = (( 116.0 * y ) - 16);
        U = (13.0 * L * ( u - ref_U ));
        V = (13.0 * L * ( v - ref_V ));
    }

    private void convertLuvtoXYZ(){
        double y = ( L + 16 ) / 116;
        if ( Math.pow(y,3) > 0.008856 ){
            y = Math.pow(y,3);
        } else {
            y = ( y - 16 / 116 ) / 7.787;
        }

        double ref_X =  95.047;
        double ref_Y = 100.000;
        double ref_Z = 108.883;

        double ref_U = ( 4 * ref_X ) / ( ref_X + ( 15 * ref_Y ) + ( 3 * ref_Z ) );
        double ref_V = ( 9 * ref_Y ) / ( ref_X + ( 15 * ref_Y ) + ( 3 * ref_Z ) );

        double var_U = U / ( 13 * L ) + ref_U;
        double var_V = V / ( 13 * L ) + ref_V;

        Y = y * 100;
        X =  - ( 9 * Y * var_U ) / ( ( var_U - 4 ) * var_V  - var_U * var_V );
        Z = ( 9 * Y - ( 15 * var_V * Y ) - ( var_V * X ) ) / ( 3 * var_V );
    }

    public void setLUVColor(Color selectedColor){
        this.getColorSelectionModel().setSelectedColor(selectedColor);
        B = selectedColor.getBlue();
        R = selectedColor.getRed();
        G = selectedColor.getGreen();
        convertRGBtoXYZ();
        convertXYZtoLuv();
        if(L < 0){
            lfield.setValue(0);
            lslider.setValue(0);
        } else {
            lfield.setValue(L);
            lslider.setValue((int)L);
        }
        if (Double.isNaN(U)){
            ufield.setValue(0);
            uslider.setValue(0);
        } else {
            ufield.setValue(U);
            uslider.setValue((int)U);
        }
        if(Double.isNaN(V)){
            vfield.setValue(0);
            vslider.setValue(0);
        } else {
            vfield.setValue(V);
            vslider.setValue((int)V);
        }
        /*lfield.setText(Double.toString(L));
        ufield.setText(Double.toString(U));
        vfield.setText(Double.toString(V));
        */
    }
}
