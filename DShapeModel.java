//import com.sun.javafx.sg.prism.NGShape;
//import sun.font.DelegatingShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Chris on 5/16/2016.
 */
public class DShapeModel {
    Rectangle bounds;
    Point point;
    Color color;
    String type;
    DShape dShape;
    ArrayList<ModelListener> modelListeners;
    ModelListener board;

    DShapeModel(){
        bounds = new Rectangle(0,0,0,0);
        point = new Point(0,0);
        color = Color.gray;
        type = "";
        dShape = null;
        modelListeners = new ArrayList<>();
    }

    DShapeModel(boolean random){
    }
    public void addListener(ModelListener modelListener){
        if (modelListener == null){
            board = modelListener;
        }
        else{
            modelListeners.add(modelListener);
        }
    }
    public void removeListener(ModelListener modelListener){
        modelListeners.remove(modelListener);
    }

    private void notifyChange(){
        modelListeners.trimToSize();
        for(ModelListener m : modelListeners){
            m.modelChanged(this);
        }
       // board.modelChanged(this);
    }

    // getter and setter methods for DLine
    public Point getP1(){return null;}
    public Point getP2(){return null;}
    public void setP1(int x, int y){}
    public void setP2(int x, int y){}

    public Rectangle getBounds(){
        return bounds;
    }

    public int getX() {
        return (int)bounds.getX();
    }
    public void setX(int x) {
        bounds.setBounds(x,(int)bounds.getY(),(int)bounds.getWidth(),(int)bounds.getHeight());
        notifyChange();
    }

    public int getY() {
        return (int)bounds.getY();
    }
    public void setY(int y) {
        bounds.setBounds((int)bounds.getX(),y,(int)bounds.getWidth(),(int)bounds.getHeight());
        notifyChange();
    }

    public int getWidth() {
        return (int)bounds.getWidth();
    }
    public void setWidth(int width) {
        bounds.setBounds((int)bounds.getX(),(int)bounds.getY(),width,(int)bounds.getHeight());
        notifyChange();
    }

    public int getHeight() {
        return (int)bounds.getHeight();
    }
    public void setHeight(int height) {
        bounds.setBounds((int)bounds.getX(),(int)bounds.getY(),(int)bounds.getWidth(),height);
        notifyChange();
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
        notifyChange();
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
        notifyChange();
    }

    public DShape getShape() {
        return dShape;
    }
    public void setShape(DShape shape){
        this.dShape = shape;
        notifyChange();
    }

    public void clear(){
        modelListeners.clear();
    }
}
