import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Chris on 5/16/2016.
 */
public class DLine extends DShape {

    DLine(){

    }

    DLine(DLineModel model){
        dsm = model;
        dsm.addListener(this);
    }

    public void setP1(int x, int y){
        dsm.setP1(x,y);
    }
    public void setP2(int x, int y){
        dsm.setP2(x,y);
    }

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
        setBounds(bounds);
        setPoints();
        updateKnobs();
        draw(g2);
    }

    public void draw(Graphics2D g2, ArrayList<Point> knobs){
        Color save = g2.getColor(); //save color
        g2.setColor(Color.BLACK); // knobs are black
        g2.fillRect((int)dsm.getP1().getX()-4,(int)dsm.getP1().getY()-4,KNOB_SIZE,KNOB_SIZE); //draw p1 knob
        g2.fillRect((int)dsm.getP2().getX()-4,(int)dsm.getP2().getY()-4,KNOB_SIZE,KNOB_SIZE); //draw p2 knob
        g2.setColor(save);
    }

    public void setPoints(){
        Rectangle rectangle = dsm.getBounds();
        dsm.setP1((int)rectangle.getX(),(int)rectangle.getY());
        dsm.setP2((int)(rectangle.getX()+rectangle.getWidth()),
                (int)(rectangle.getY()+rectangle.getHeight())
        );
    }

}
