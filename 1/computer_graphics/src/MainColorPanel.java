import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by asd on 14.03.2016.
 */
public class MainColorPanel extends JFrame implements ChangeListener {

    private LUVColorPanel LuvPanel;
    private HSVColorPanel HSVPanel;
    private CMYColorPanel CMYPanel;
    private RGBColorPanel RGBPanel;
    private JPanel choosedColorPanel;
    private Color selectedColor;
    private boolean hasChanged;

    public MainColorPanel(){

        choosedColorPanel = new JPanel();
        LuvPanel = new LUVColorPanel();
        HSVPanel = new HSVColorPanel();
        CMYPanel = new CMYColorPanel();
        RGBPanel = new RGBColorPanel();

        selectedColor = Color.WHITE;
        // size set for the frame
        this.setSize(1500, 1000);
        choosedColorPanel.setSize(100,100);
        choosedColorPanel.setBackground(selectedColor);

        //adding all components to the frame
        Box container = Box.createVerticalBox();
        Box firstLine = Box.createHorizontalBox();
        firstLine.add(RGBPanel);
        firstLine.add(CMYPanel);
        container.add(firstLine);
        Box middleLine = Box.createHorizontalBox();
        middleLine.add(choosedColorPanel);
        container.add(middleLine);
        Box secondLine = Box.createHorizontalBox();
        secondLine.add(HSVPanel);
        secondLine.add(LuvPanel);
        LuvPanel.setVisible(true);
        container.add(secondLine);

        this.add(container);
    }

    // event listener which reacts on color changing
    public void stateChanged(ChangeEvent event) {
        if(event.getSource() == LuvPanel && !hasChanged){
            Color newColor = LuvPanel.getColor();
                if(!newColor.equals(selectedColor)){
                    hasChanged = true;
                    selectedColor = newColor;
                    HSVPanel.changeColor(newColor);
                    CMYPanel.changeColor(newColor);
                    RGBPanel.changeColor(newColor);
                }
        }
        if(event.getSource() == HSVPanel && !hasChanged){
            Color newColor = HSVPanel.getColor();
            if(!newColor.equals(selectedColor)){
                hasChanged = true;
                selectedColor = newColor;
                LuvPanel.changeColor(newColor);
                CMYPanel.changeColor(newColor);
                RGBPanel.changeColor(newColor);
            }
        }
        if(event.getSource() == CMYPanel && !hasChanged){
            Color newColor = CMYPanel.getColor();
            if(!newColor.equals(selectedColor)){
                hasChanged = true;
                selectedColor = newColor;
                LuvPanel.changeColor(newColor);
                HSVPanel.changeColor(newColor);
                RGBPanel.changeColor(newColor);
            }
        }
        if(event.getSource() == RGBPanel && !hasChanged){
            Color newColor = RGBPanel.getColor();
            if(!newColor.equals(selectedColor)){
                hasChanged = true;
                selectedColor = newColor;
                LuvPanel.changeColor(newColor);
                HSVPanel.changeColor(newColor);
                CMYPanel.changeColor(newColor);
            }
        }
        choosedColorPanel.setBackground(selectedColor);
        hasChanged = false;
    }

    // opens main frame
    public static void main(String []args){
        JFrame mainFrame = new MainColorPanel();
        mainFrame.setVisible(true);
    }
}
