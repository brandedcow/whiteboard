import java.awt.*;

/**
 * Created by Chris on 5/16/2016.
 */
public class DShapeModel {
    Rectangle bounds;
    Point point;
    Color color;
    String type;
    DShape dShape;

    DShapeModel(){
        bounds = new Rectangle(0,0,0,0);
        point = new Point(0,0);
        color = Color.gray;
        type = "";
        dShape = null;
    }

    public int getX() {
        return (int)bounds.getX();
    }

    public void setX(int x) {
        bounds.setBounds(x,(int)bounds.getY(),(int)bounds.getWidth(),(int)bounds.getHeight());
    }

    public int getY() {
        return (int)bounds.getY();
    }

    public void setY(int y) {
        bounds.setBounds((int)bounds.getX(),y,(int)bounds.getWidth(),(int)bounds.getHeight());
    }

    public int getWidth() {
        return (int)bounds.getWidth();
    }

    public void setWidth(int width) {
        bounds.setBounds((int)bounds.getX(),(int)bounds.getY(),width,(int)bounds.getHeight());
    }

    public int getHeight() {
        return (int)bounds.getHeight();
    }

    public void setHeight(int height) {
        bounds.setBounds((int)bounds.getX(),(int)bounds.getY(),(int)bounds.getWidth(),height);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DShape getShape() {
        return dShape;
    }

    public void setShape(DShape shape){
        this.dShape = shape;
    }
}
