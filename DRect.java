import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Chris on 5/16/2016.
 */
public class DRect extends DShape {
    DRect(){

    }
    DRect(DRectModel model){
        dsm = model;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(dsm.getColor());
        g2.fillRect(dsm.getX(),dsm.getY(),dsm.getWidth(),dsm.getHeight());
    }
}
