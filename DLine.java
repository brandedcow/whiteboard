import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chris on 5/16/2016.
 */
public class DLine extends DShape {
    private boolean flip = false;

    DLine() {

    }

    DLine(DLineModel model) {
        dsm = model;
        dsm.addListener(this);
    }

    public ArrayList<Point> getKnobs() {
        ArrayList<Point> knobPoints = new ArrayList<>(2);
        Point point0 = new Point((int) dsm.getP1().getX(), (int) dsm.getP1().getY());
        Point point1 = new Point((int) dsm.getP2().getX(), (int) dsm.getP2().getY());
        knobPoints.add(point0);
        knobPoints.add(point1);

        knobs = null;
        knobs = new ArrayList<>(2);
        Rectangle rectangle = null;

        rectangle = new Rectangle((int) point0.getX() - 4, (int) point0.getY() - 4, KNOB_SIZE, KNOB_SIZE);
        knobs.add(rectangle);

        rectangle = new Rectangle((int) point1.getX() - 4, (int) point1.getY() - 4, KNOB_SIZE, KNOB_SIZE);
        knobs.add(rectangle);

        return knobPoints;
    }

    public boolean getFlip() {
        return flip;
    }

    public Point getP1() {
        return dsm.getP1();
    }

    public Point getP2() {
        return dsm.getP2();
    }

    public void setFlip(boolean b) {
        flip = b;
    }

    public void setP1(int x, int y) {
        dsm.setP1(x, y);
    }

    public void setP2(int x, int y) {
        dsm.setP2(x, y);
    }

    // draws a line from p1 to p2
    public void draw(Graphics2D g2) {
        if (selected) {
            g2.setColor(dsm.getColor().brighter());
        } else {
            g2.setColor(dsm.getColor());
        }
        g2.drawLine(
                (int) dsm.getP1().getX(),
                (int) dsm.getP1().getY(),
                (int) dsm.getP2().getX(),
                (int) dsm.getP2().getY()
        );
    }


    public void draw(Graphics2D g2, Rectangle bounds) {
        setBounds(bounds);
        setPoints();
        System.out.println(bounds);
        updateKnobs();
        draw(g2);
    }

    public void draw(Graphics2D g2, ArrayList<Point> knobs) {
        Color save = g2.getColor(); //save color
        g2.setColor(Color.BLACK); // knobs are black
        g2.fillRect((int) dsm.getP1().getX() - 4, (int) dsm.getP1().getY() - 4, KNOB_SIZE, KNOB_SIZE); //draw p1 knob
        g2.fillRect((int) dsm.getP2().getX() - 4, (int) dsm.getP2().getY() - 4, KNOB_SIZE, KNOB_SIZE); //draw p2 knob
        g2.setColor(save);
    }

    public void setPoints() {
        Rectangle rectangle = dsm.getBounds();
        if (!flip) {
            dsm.setP1((int) rectangle.getX(), (int) rectangle.getY());
            dsm.setP2((int) (rectangle.getX() + rectangle.getWidth()), (int) (rectangle.getY() + rectangle.getHeight()));
        } else { // flip
            dsm.setP1((int) (rectangle.getX()), (int) (rectangle.getY() + rectangle.getHeight()));
            dsm.setP2((int) (rectangle.getX() + rectangle.getWidth()), (int) (rectangle.getY()));
        }
    }

}
