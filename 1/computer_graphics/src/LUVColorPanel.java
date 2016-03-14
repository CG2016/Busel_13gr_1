import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by asd on 29.02.2016.
 */
public class LUVColorPanel extends JPanel implements ChangeListener{
    private boolean hasChanged;
    private LUVColorSpace colorSpace = LUVColorSpace.getInstance();
    private Color color;
    private SliderWithSpinner LsliderSpinner;
    private SliderWithSpinner UsliderSpinner;
    private SliderWithSpinner VsliderSpinner;

    public boolean isHasChanged() {
        return hasChanged;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public Color getColor() {
        return color;
    }

    public LUVColorPanel() {
        Box container = Box.createVerticalBox();
        Box firstLine = Box.createHorizontalBox();
        LsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(0, 0, 100), 0, true);
        firstLine.add(new Label("L*"));
        firstLine.add(LsliderSpinner);
        LsliderSpinner.addChangeListener(changeListener);
        container.add(firstLine);
        Box secondLine = Box.createHorizontalBox();
        UsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(0, -100, 100), 0, true);
        secondLine.add(new Label("u*"));
        secondLine.add(UsliderSpinner);
        UsliderSpinner.addChangeListener(changeListener);
        container.add(secondLine);
        Box thirdLine = Box.createHorizontalBox();
        VsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(0, -100, 100), 0, true);
        thirdLine.add(new Label("v"));
        thirdLine.add(VsliderSpinner);
        VsliderSpinner.addChangeListener(changeListener);
        container.add(thirdLine);
        this.add(container);
        color = Color.WHITE;
    }

    private final ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent event) {
                JComponent sliderWithSpinner = (JComponent) event.getSource();
                LUVColorPanel panel = (LUVColorPanel) sliderWithSpinner.getParent().getParent().getParent().getParent();
                ChangeEvent eventForPanel = new ChangeEvent(sliderWithSpinner.getParent());
                panel.stateChanged(eventForPanel);
        }
    };

    public void stateChanged(ChangeEvent event) {
        SliderWithSpinner source = (SliderWithSpinner)event.getSource();
        if (this.LsliderSpinner == source || this.VsliderSpinner == source || this.UsliderSpinner == source) {
            if(!this.hasChanged) {
                float[] rgb = this.colorSpace.toRGB(new float[]{this.LsliderSpinner.getValue(), this.UsliderSpinner.getValue(), this.VsliderSpinner.getValue()});
                try {
                    this.color = new Color(rgb[0] / 255, rgb[1] / 255, rgb[2] / 255);
                    LUVColorPanel panel = (LUVColorPanel) source.getParent().getParent().getParent();
                    MainColorPanel main = (MainColorPanel) panel.getParent().getParent().getParent().getParent().getParent().getParent();
                    ChangeEvent eventForFrame = new ChangeEvent(panel);
                    main.stateChanged(eventForFrame);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(new JFrame(), e.getLocalizedMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void changeColor(Color color){
        this.color = color;
        this.hasChanged = true;
        float[] luv = this.colorSpace.fromRGB(new float[] {color.getRed(),color.getGreen(),color.getBlue()});
        this.LsliderSpinner.setValue((int) luv[0]);
        this.UsliderSpinner.setValue((int) luv[1]);
        this.VsliderSpinner.setValue((int) luv[2]);
        this.hasChanged = false;
    }
}
