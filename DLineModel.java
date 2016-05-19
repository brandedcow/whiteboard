import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Chris on 5/16/2016.
 */
public class DLineModel extends DShapeModel {
    Point p1, p2;

    DLineModel(){
        p1 = new Point(15,15);
        p2 = new Point(50,50);

        bounds = new Rectangle(15,15,35,35);
        color = Color.gray;
        type = "line";
        dShape = new DLine(this);
        modelListeners = new ArrayList<>();
    }

    public Point getP1(){
        return p1;
    }

    public Point getP2(){
        return p2;
    }

    public void setP1(int x, int y){
        p1.setLocation(x,y);
    }

    public void setP2(int x, int y){
        p2.setLocation(x,y);
    }
}
