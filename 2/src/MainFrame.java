import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by asd on 24.03.2016.
 */
public class MainFrame  extends JFrame{
    File image;
    ImagePanel inputImage = new ImagePanel();
    ImagePanel outputImage = new ImagePanel();
    JSlider distance = new JSlider();
    Color oldColor = null;
    JColorChooser newColor = new JColorChooser();
    Button okButton = new Button("OK");
    Button resetButton = new Button("Reset");

    public MainFrame() {
        inputImage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                oldColor = new Color(inputImage.getImage().getRGB(point.x, point.y));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
                inputImage.setCursor(cursor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
                inputImage.setCursor(cursor);
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (oldColor == null) {
                    JOptionPane.showMessageDialog(new JFrame(), "Please, choose color to change and try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ImageProcess imageProcess = new ImageProcess(distance.getValue(), oldColor, newColor.getColor(), inputImage.getImage());
                try {
                    outputImage.setImage(imageProcess.generateNewImage());
                    outputImage.setResized(true);
                    outputImage.repaint();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(new JFrame(), e1.getLocalizedMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputImage.setResized(false);
                inputImage.setResized(false);
                try {
                    outputImage.setImage(image);
                    inputImage.setImage(image);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                outputImage.repaint();
                inputImage.repaint();
            }
        });

        this.setSize(1500, 700);
        distance.setMinimum(0);
        distance.setMaximum(100);
        distance.setValue(0);
        okButton.setSize(50, 50);

        JPanel panel = new JPanel();
        panel.setSize(50, 50);
        panel.add(okButton);
        panel.add(resetButton);
        panel.add(newColor);

        JPanel main = new JPanel(new GridLayout(3,2));
        main.add(inputImage);
        main.add(outputImage);
        main.add(distance);
        main.add(panel);

        JScrollPane pane = new JScrollPane(main, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setContentPane(pane);
    }

    public static void main(String [] args) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        MainFrame frame = new MainFrame();
        int ret = fileChooser.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            frame.image = fileChooser.getSelectedFile();
        }
        frame.inputImage.setImage(frame.image);
        frame.outputImage.setImage(frame.image);
        frame.inputImage.repaint();
        frame.outputImage.repaint();
        frame.setVisible(true);
    }
}
