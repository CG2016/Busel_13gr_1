import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by asd on 29.02.2016.
 */
public class RGBColorPanel extends JPanel implements ChangeListener {
    private boolean hasChanged;
    private Color color;
    private SliderWithSpinner RsliderSpinner;
    private SliderWithSpinner GsliderSpinner;
    private SliderWithSpinner BsliderSpinner;

    public boolean isHasChanged() {
        return hasChanged;
    }

    public void setHasChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    public Color getColor() {
        return color;
    }

    public RGBColorPanel() {
        Box container = Box.createVerticalBox();
        Box firstLine = Box.createHorizontalBox();
        RsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(255, 0, 255), 0, true);
        firstLine.add(new Label("R"));
        firstLine.add(RsliderSpinner);
        RsliderSpinner.addChangeListener(changeListener);
        container.add(firstLine);
        Box secondLine = Box.createHorizontalBox();
        GsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(255, 0, 255), 0, true);
        secondLine.add(new Label("G"));
        secondLine.add(GsliderSpinner);
        GsliderSpinner.addChangeListener(changeListener);
        container.add(secondLine);
        Box thirdLine = Box.createHorizontalBox();
        BsliderSpinner = new SliderWithSpinner(new SliderWithSpinnerModel(255, 0, 255), 0, true);
        thirdLine.add(new Label("B"));
        thirdLine.add(BsliderSpinner);
        BsliderSpinner.addChangeListener(changeListener);
        container.add(thirdLine);
        this.add(container);
        color = Color.WHITE;
    }

    private final ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent event) {
            JComponent sliderWithSpinner = (JComponent) event.getSource();
            RGBColorPanel panel = (RGBColorPanel) sliderWithSpinner.getParent().getParent().getParent().getParent();
            ChangeEvent eventForPanel = new ChangeEvent(sliderWithSpinner.getParent());
            panel.stateChanged(eventForPanel);
        }
    };

    public void stateChanged(ChangeEvent event) {
        SliderWithSpinner source = (SliderWithSpinner) event.getSource();
        if (this.RsliderSpinner == source || this.GsliderSpinner == source || this.BsliderSpinner == source) {
            if (!this.hasChanged) {
                try {
                    this.color = new Color(RsliderSpinner.getValue(), GsliderSpinner.getValue(), BsliderSpinner.getValue());
                    RGBColorPanel panel = (RGBColorPanel) source.getParent().getParent().getParent();
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
        this.RsliderSpinner.setValue(color.getRed());
        this.GsliderSpinner.setValue(color.getGreen());
        this.BsliderSpinner.setValue(color.getBlue());
        this.hasChanged = false;
    }
}
