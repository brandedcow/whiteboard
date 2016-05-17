import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Chris on 5/16/2016.
 */
public class DRect extends DShape {

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        g2.fillRect(dsm.getX(),dsm.getY(),dsm.getWidth(),dsm.getHeight());
    }
}
