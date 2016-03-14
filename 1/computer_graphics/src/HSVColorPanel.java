import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by asd on 29.02.2016.
 */
public class HSVColorPanel extends JPanel implements ChangeListener {
    private boolean hasChanged;
    private HSVColorSpace colorSpace = HSVColorSpace.getInstance();
    private Color color;
    private SliderWithSpinner HsliderSpinner;
    private SliderWithSpinner SsliderSpinner;
    private SliderWithSpinner VsliderSpinner;

    public Color getColor() {
        return color;
    }

    public boolean isHasChanged() {
        return hasChanged;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public HSVColorPanel() {
        Box container = Box.createVerticalBox();
        Box firstLine = Box.createHorizontalBox();
        HsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(0, 0, 360), 0, true);
        firstLine.add(new Label("H"));
        firstLine.add(HsliderSpinner);
        HsliderSpinner.addChangeListener(changeListener);
        container.add(firstLine);
        Box secondLine = Box.createHorizontalBox();
        SsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(0, 0, 100), 0, true);
        secondLine.add(new Label("S"));
        secondLine.add(SsliderSpinner);
        SsliderSpinner.addChangeListener(changeListener);
        container.add(secondLine);
        Box thirdLine = Box.createHorizontalBox();
        VsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(100, 0, 100), 0, true);
        thirdLine.add(new Label("V"));
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
            HSVColorPanel panel = (HSVColorPanel) sliderWithSpinner.getParent().getParent().getParent().getParent();
            ChangeEvent eventForPanel = new ChangeEvent(sliderWithSpinner.getParent());
            panel.stateChanged(eventForPanel);
        }
    };

    public void stateChanged(ChangeEvent event) {
        SliderWithSpinner source = (SliderWithSpinner)event.getSource();
        if (this.HsliderSpinner == source || this.VsliderSpinner == source || this.SsliderSpinner == source) {
            if(!this.hasChanged) {
                float[] rgb = this.colorSpace.toRGB(new float[]{this.HsliderSpinner.getValue(), this.SsliderSpinner.getValue(), this.VsliderSpinner.getValue()});
                try {
                    this.color = new Color(rgb[0] / 255, rgb[1] / 255, rgb[2] / 255);
                    HSVColorPanel panel = (HSVColorPanel) source.getParent().getParent().getParent();
                    MainColorPanel main = (MainColorPanel) panel.getParent().getParent().getParent().getParent().getParent().getParent();
                    ChangeEvent eventForFrame = new ChangeEvent(panel);
                    main.stateChanged(eventForFrame);
                }
                catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(new JFrame(), e.getLocalizedMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void changeColor(Color color) {
        this.color = color;
        this.hasChanged = true;
        float[] hsv = this.colorSpace.fromRGB(new float[]{color.getRed(), color.getGreen(), color.getBlue()});
        this.HsliderSpinner.setValue((int) hsv[0]);
        this.SsliderSpinner.setValue((int) hsv[1]);
        this.VsliderSpinner.setValue((int) hsv[2]);
        this.hasChanged = false;
    }
}
