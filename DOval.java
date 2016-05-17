import java.awt.*;

/**
 * Created by Chris on 5/16/2016.
 */
public class DOval extends DShape {
    DOval(){

    }
    DOval(DOvalModel model){
        dsm = model;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(dsm.getColor());
        g2.fillOval(dsm.getX(), dsm.getY(), dsm.getWidth(), dsm.getHeight());
    }
}
