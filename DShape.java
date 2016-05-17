import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Chris on 5/16/2016.
 */
public class DShape {
    DShapeModel dsm;

    DShape() {
        dsm = new DShapeModel();
    }

    public int getX() {
        return dsm.getX();
    }

    public int getY() {
        return dsm.getY();
    }

    public int getWidth() {
        return dsm.getWidth();
    }

    public int getHeight() {
        return dsm.getHeight();
    }

    public String getType(){
        return dsm.getType();
    }

    public Color getColor() {
        return dsm.getColor();
    }

    public String getShape() {
        return dsm.getType();
    }

    public void addModel(DShapeModel d) {
        dsm = d;
    }

    public void draw(Graphics2D g2) {
    }
}
