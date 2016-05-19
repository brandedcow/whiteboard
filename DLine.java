import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chris on 5/16/2016.
 */
public class DLine extends DShape {
    private boolean flip = false;

    DLine(){

    }

    DLine(DLineModel model){
        dsm = model;
        dsm.addListener(this);
    }

    public ArrayList<Point> getKnobs(){
        ArrayList<Point> knobPoints = new ArrayList<>(4);
        Point point0 = new Point(getX(),getY());
        Point point1 = new Point(getX() + getWidth(), getY());
        Point point2 = new Point(getX() + getWidth(), getY() + getHeight());
        Point point3 = new Point(getX(), getY() + getHeight());
        knobPoints.add(point0);
        knobPoints.add(point1);
        knobPoints.add(point2);
        knobPoints.add(point3);

        if (knobs == null) {
            knobs = new ArrayList<>(4);
            Rectangle rectangle = null;
            rectangle =  new Rectangle((int)dsm.getP1().getX()-4, (int)dsm.getP1().getY()-4, KNOB_SIZE, KNOB_SIZE);
            knobs.add(rectangle);

            rectangle =  new Rectangle((int)dsm.getP2().getX()-4, (int)dsm.getP2().getY()-4, KNOB_SIZE, KNOB_SIZE);
            knobs.add(rectangle);

            rectangle = new Rectangle(0,0,0,0);
            knobs.add(rectangle);
            knobs.add(rectangle);

        }
        return knobPoints;
    }
    public boolean getFlip(){
        return flip;
    }
    public Point getP1(){
        return dsm.getP1();
    }
    public Point getP2(){
        return dsm.getP2();
    }
    public void setFlip(boolean b){
        flip = b;
    }
    public void setP1(int x, int y){
        dsm.setP1(x,y);
    }
    public void setP2(int x, int y){
        dsm.setP2(x, y);
    }

    // draws a line from p1 to p2
    public void draw(Graphics2D g2){
        if (selected){
            g2.setColor(dsm.getColor().brighter());
        } else {
            g2.setColor(dsm.getColor());
        }
        g2.drawLine(
                (int)dsm.getP1().getX(),
                (int)dsm.getP1().getY(),
                (int)dsm.getP2().getX(),
                (int)dsm.getP2().getY()
                );
    }


    public void draw(Graphics2D g2, Rectangle bounds){
        if (!flip){
        setBounds(bounds);
        setPoints(flip);
        updateKnobs();
        }
        draw(g2);
    }

    public void draw(Graphics2D g2, ArrayList<Point> knobs){
        Color save = g2.getColor(); //save color
        g2.setColor(Color.BLACK); // knobs are black
        g2.fillRect((int)dsm.getP1().getX()-4,(int)dsm.getP1().getY()-4,KNOB_SIZE,KNOB_SIZE); //draw p1 knob
        g2.fillRect((int)dsm.getP2().getX()-4,(int)dsm.getP2().getY()-4,KNOB_SIZE,KNOB_SIZE); //draw p2 knob
        g2.setColor(save);
    }

    public void setPoints(boolean flip){
        Rectangle rectangle = dsm.getBounds();
        if (!flip) {
            dsm.setP1((int) rectangle.getX(), (int) rectangle.getY());
            dsm.setP2((int) (rectangle.getX() + rectangle.getWidth()),
                    (int) (rectangle.getY() + rectangle.getHeight())
            );

        } else { // flip
            dsm.setP1((int) (rectangle.getX()), (int) (rectangle.getY() - rectangle.getHeight()) );
            dsm.setP2((int) (rectangle.getX()+rectangle.getWidth()), (int) (rectangle.getY()) );
            flip = false;
        }
    }

}
