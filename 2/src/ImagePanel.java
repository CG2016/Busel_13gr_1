import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by asd on 29.02.2016.
 */
public class ImagePanel extends JPanel{
    private BufferedImage image;
    private boolean resized = false;


    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(File file) throws IOException {
        this.image = ImageIO.read(file);
    }

    public void setImage(BufferedImage image){
        this.image = image;
    }

    public void paint(Graphics g) {
        super.paint(g);

        AffineTransform tx = new AffineTransform();
        if(!resized) {
            tx.scale(0.2, 0.2);
        } else {
            tx.scale(1.0, 1.0);
        }
        AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_BILINEAR);
        image = op.filter(image, null);
        g.drawImage(image, 0, 0, this);
        g.dispose();
    }
}
