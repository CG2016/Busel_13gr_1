import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by asd on 29.02.2016.
 */
public class CMYColorPanel extends JPanel implements ChangeListener {
    private boolean hasChanged;
    private CMYColorSpace colorSpace = CMYColorSpace.getInstance();
    private Color color;
    private SliderWithSpinner CsliderSpinner;
    private SliderWithSpinner MsliderSpinner;
    private SliderWithSpinner YsliderSpinner;

    public Color getColor() {
        return color;
    }

    public boolean isHasChanged() {
        return hasChanged;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public CMYColorPanel() {
        Box container = Box.createVerticalBox();
        Box firstLine = Box.createHorizontalBox();
        CsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(0, 0, 255), 0, true);
        firstLine.add(new Label("C"));
        firstLine.add(CsliderSpinner);
        CsliderSpinner.addChangeListener(changeListener);
        container.add(firstLine);
        Box secondLine = Box.createHorizontalBox();
        MsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(0, 0, 255), 0, true);
        secondLine.add(new Label("M"));
        secondLine.add(MsliderSpinner);
        MsliderSpinner.addChangeListener(changeListener);
        container.add(secondLine);
        Box thirdLine = Box.createHorizontalBox();
        YsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(0, 0, 255), 0, true);
        thirdLine.add(new Label("Y"));
        thirdLine.add(YsliderSpinner);
        YsliderSpinner.addChangeListener(changeListener);
        container.add(thirdLine);
        this.add(container);
        color = Color.WHITE;
    }

    private final ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent event) {
            JComponent sliderWithSpinner = (JComponent) event.getSource();
            CMYColorPanel panel = (CMYColorPanel) sliderWithSpinner.getParent().getParent().getParent().getParent();
            ChangeEvent eventForPanel = new ChangeEvent(sliderWithSpinner.getParent());
            panel.stateChanged(eventForPanel);
        }
    };

    public void stateChanged(ChangeEvent event) {
        SliderWithSpinner source = (SliderWithSpinner)event.getSource();
        if (this.CsliderSpinner == source || this.YsliderSpinner == source || this.MsliderSpinner == source) {
            if(!this.hasChanged) {
                float[] rgb = this.colorSpace.toRGB(new float[]{this.CsliderSpinner.getValue(), this.MsliderSpinner.getValue(), this.YsliderSpinner.getValue()});
                try {
                    this.color = new Color(rgb[0] / 255, rgb[1] / 255, rgb[2] / 255);
                    CMYColorPanel panel = (CMYColorPanel) source.getParent().getParent().getParent();
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

    public void changeColor(Color color) {
        this.color = color;
        this.hasChanged = true;
        float[] cmy = this.colorSpace.fromRGB(new float[]{color.getRed(), color.getGreen(), color.getBlue()});
        this.CsliderSpinner.setValue((int) cmy[0]);
        this.MsliderSpinner.setValue((int) cmy[1]);
        this.YsliderSpinner.setValue((int) cmy[2]);
        this.hasChanged = false;
    }
}

