import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Created by asd on 21.02.2016.
 */
public class Main extends JFrame {

    private JColorChooser HSVChooser;
    private JColorChooser RGBChooser;
    private JColorChooser CMYKChooser;
    private JColorChooser LuvChooser;
    private Color selectedColor;

    public Main() throws Exception {
        // size set for the frame
        this.setSize(1500, 1000);

        HSVChooser = new JColorChooser();
        RGBChooser = new JColorChooser();
        CMYKChooser = new JColorChooser();
        LuvChooser = new JColorChooser();

        // remove standard preview panel
        RGBChooser.setPreviewPanel(new JPanel());
        HSVChooser.setPreviewPanel(new JPanel());
        CMYKChooser.setPreviewPanel(new JPanel());
        LuvChooser.setPreviewPanel(new JPanel());

        // choose appropriate color panel
        HSVChooser.setChooserPanels(new AbstractColorChooserPanel[]{HSVChooser.getChooserPanels()[1]});
        RGBChooser.setChooserPanels(new AbstractColorChooserPanel[]{RGBChooser.getChooserPanels()[3]});
        CMYKChooser.setChooserPanels(new AbstractColorChooserPanel[]{CMYKChooser.getChooserPanels()[4]});
        LuvChooser.setChooserPanels(new AbstractColorChooserPanel[]{new LuvPanel()});

        // remove all unnecessary sliders
        removeAlfaSlider(CMYKChooser.getChooserPanels()[0]);
        removeAlfaSliderAndColorCode(RGBChooser.getChooserPanels()[0]);
        removeAlfaSliderAndColorCode(HSVChooser.getChooserPanels()[0]);

        // remove color code component
        RGBChooser.getChooserPanels()[0].remove(RGBChooser.getChooserPanels()[0].getComponent(1));
        RGBChooser.getChooserPanels()[0].remove(RGBChooser.getChooserPanels()[0].getComponent(1));

        //adding all components to the frame
        Box container = Box.createVerticalBox();
        Box firstLine = Box.createHorizontalBox();
        firstLine.add(RGBChooser);
        firstLine.add(CMYKChooser);
        container.add(firstLine);
        Box secondLine = Box.createHorizontalBox();
        secondLine.add(HSVChooser);
        secondLine.add(LuvChooser);
        container.add(secondLine);

        // adding event listeners for all color choosers
        RGBChooser.getChooserPanels()[0].getColorSelectionModel().addChangeListener(changeListener);
        HSVChooser.getChooserPanels()[0].getColorSelectionModel().addChangeListener(changeListener);
        CMYKChooser.getChooserPanels()[0].getColorSelectionModel().addChangeListener(changeListener);
        LuvChooser.getChooserPanels()[0].getColorSelectionModel().addChangeListener(changeListener);
        this.add(container);
    }

    // event listener which reacts on color changing
    private final ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent event) {
            DefaultColorSelectionModel source = (DefaultColorSelectionModel) event.getSource();
            selectedColor = source.getSelectedColor();
            RGBChooser.setColor(selectedColor);
            HSVChooser.setColor(selectedColor);
            CMYKChooser.setColor(selectedColor);
            LuvPanel panel = (LuvPanel)LuvChooser.getChooserPanels()[0];
            panel.setLUVColor(selectedColor);
        }
    };

    // removes alfa slider field from color chooser
    private static void removeAlfaSlider(AbstractColorChooserPanel cp) throws Exception {

            Field panelField = cp.getClass().getDeclaredField("panel");
            panelField.setAccessible(true);

            Object colorPanel = panelField.get(cp);
            Field spinnersField = colorPanel.getClass().getDeclaredField("spinners");
            spinnersField.setAccessible(true);
            Object spinners = spinnersField.get(colorPanel);

            Object alfaSlidSpinner = Array.get(spinners, 4);
            //Object kSlidSpinner = Array.get(spinners, 3);

            Field sliderField = alfaSlidSpinner.getClass().getDeclaredField("slider");
            sliderField.setAccessible(true);
            JSlider slider = (JSlider) sliderField.get(alfaSlidSpinner);
            slider.setEnabled(false);
            slider.setVisible(false);

            /*sliderField = kSlidSpinner.getClass().getDeclaredField("slider");
            sliderField.setAccessible(true);
            slider = (JSlider) sliderField.get(kSlidSpinner);
            slider.setEnabled(false);
            slider.setVisible(false);*/

            Field spinnerField = alfaSlidSpinner.getClass().getDeclaredField("spinner");
            spinnerField.setAccessible(true);
            JSpinner spinner = (JSpinner) spinnerField.get(alfaSlidSpinner);
            spinner.setEnabled(false);
            spinner.setVisible(false);

            /*spinnerField = kSlidSpinner.getClass().getDeclaredField("spinner");
            spinnerField.setAccessible(true);
            spinner = (JSpinner) spinnerField.get(kSlidSpinner);
            spinner.setEnabled(false);
            spinner.setVisible(false);*/

            Field labelField = alfaSlidSpinner.getClass().getDeclaredField("label");
            labelField.setAccessible(true);
            JLabel label = (JLabel) labelField.get(alfaSlidSpinner);
            label.setVisible(false);

            /*labelField = kSlidSpinner.getClass().getDeclaredField("label");
            labelField.setAccessible(true);
            label = (JLabel) labelField.get(kSlidSpinner);
            label.setVisible(false);*/
        }

    // removes alfa and transient sliders from default color chooser
    private static void removeAlfaSliderAndColorCode(AbstractColorChooserPanel cp) throws Exception {

        Field panelField = cp.getClass().getDeclaredField("panel");
        panelField.setAccessible(true);

        Object colorPanel = panelField.get(cp);
        Field spinnersField = colorPanel.getClass().getDeclaredField("spinners");
        spinnersField.setAccessible(true);
        Object spinners = spinnersField.get(colorPanel);

        Object alfaSlidSpinner = Array.get(spinners, 3);

        Field sliderField = alfaSlidSpinner.getClass().getDeclaredField("slider");
        sliderField.setAccessible(true);
        JSlider slider = (JSlider) sliderField.get(alfaSlidSpinner);
        slider.setEnabled(false);
        slider.setVisible(false);

        Field spinnerField = alfaSlidSpinner.getClass().getDeclaredField("spinner");
        spinnerField.setAccessible(true);
        JSpinner spinner = (JSpinner) spinnerField.get(alfaSlidSpinner);
        spinner.setEnabled(false);
        spinner.setVisible(false);

        Field labelField = alfaSlidSpinner.getClass().getDeclaredField("label");
        labelField.setAccessible(true);
        JLabel label = (JLabel) labelField.get(alfaSlidSpinner);
        label.setVisible(false);
    }

    // opens main frame
    public static void main(String []args) throws Exception {
        JFrame mainFrame = new Main();
        mainFrame.setVisible(true);
    }
}
