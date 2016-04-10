import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by asd on 28.03.2016.
 */
public class MainFrame extends JFrame {
    JPanel greenPanel;
    JPanel redPanel;
    JPanel bluePanel;
    JPanel averagePanel;

    public MainFrame(Gystogram gystogram) throws HeadlessException, IOException {
        greenPanel = new GystogramPanel(Color.GREEN, gystogram.G);
        greenPanel.setBackground(Color.WHITE);
        redPanel = new GystogramPanel(Color.RED, gystogram.R);
        redPanel.setBackground(Color.WHITE);
        bluePanel = new GystogramPanel(Color.BLUE, gystogram.B);
        bluePanel.setBackground(Color.WHITE);
        averagePanel = new GystogramPanel(Color.BLACK, gystogram.H);
        averagePanel.setBackground(Color.WHITE);
        setLayout(new GridLayout(2, 2));
        add(redPanel);
        add(greenPanel);
        add(bluePanel);
        add(averagePanel);
    }

    public static void main(String []args) throws IOException {

        JFileChooser fileChooser = new JFileChooser();
        File image = null;
        int ret = fileChooser.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            image = fileChooser.getSelectedFile();
        }
        Gystogram gystogram = new Gystogram(image);
        MainFrame frame = new MainFrame(gystogram);
        frame.setSize(1500, 700);
        frame.setVisible(true);

    }

    public class GystogramPanel extends JPanel{
        Color color;
        int [] array;

        public GystogramPanel(Color color, int[] array) throws IOException {
            this.color = color;
            this.array = array;
            this.setPreferredSize(new Dimension(300, 300));
            this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(0, 330.0);
            g2d.scale(1.0, -1.0);
            for(int i = 0; i < 256; i++){
                g2d.setColor(color);
                g2d.drawRect(i * 2, 0 , 2, array[i] / 1000);
            }
        }
    }

}
