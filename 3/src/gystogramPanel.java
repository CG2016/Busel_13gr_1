import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.IOException;

/**
 * Created by asd on 28.03.2016.
 */
public class gystogramPanel extends JPanel{
    Color color;
    int [] array;

    public gystogramPanel(Color color, int[] array) throws IOException {
        this.color = color;
        this.array = array;
        this.setPreferredSize(new Dimension(300, 300));
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setBackground(Color.WHITE);
    }


    /*class PanelForPaint extends JPanel{

        PanelForPaint(){
            this.setPreferredSize(new Dimension(300, 300));
        }*/

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            for(int i = 0; i < 256; i++){
                g.setColor(color);
                g.drawRect(i,0, 5, array[i]);
            }
        }
    //}
}
