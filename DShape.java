import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Chris on 5/16/2016.
 */
public class DShape implements ModelListener {
    private final int KNOB_SIZE = 9;
    ArrayList<Rectangle> knobs = null;
    DShapeModel dsm;
    boolean selected = false;

    DShape() {
        dsm = new DShapeModel();
    }

    DShape(DShapeModel model) {
        dsm = model;
        dsm.addListener(this);
    }

    //getters
    public Rectangle getBounds(){
        return dsm.getBounds();
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
    public DShape getShape() {
        return dsm.getShape();
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
            for (Point point : knobPoints) {
                Rectangle rectangle = new Rectangle((int) point.getX() - 4, (int) point.getY() - 4, KNOB_SIZE, KNOB_SIZE);
                knobs.add(rectangle);
            }
        }
        return knobPoints;
    }
    public ArrayList<Rectangle> getKnobRect(){
        return knobs;
    }

    //setters
    public void setColor(Color color){
        dsm.setColor(color);
    }
    public void setBounds(Rectangle rectangle){
        dsm.setX((int)rectangle.getX());
        dsm.setY((int)rectangle.getY());
        dsm.setWidth((int) rectangle.getWidth());
        dsm.setHeight((int) rectangle.getHeight());
    }

    public void addModel(DShapeModel d) {
        dsm = d;
        dsm.addListener(this);
    }

    public void clear(){
        dsm.clear();
    }

    public void draw(Graphics2D g2) {
    }
    // used in mouse drag shape movement
    public void draw(Graphics2D g2, Rectangle bounds){
        setBounds(bounds);
        updateKnobs();
        draw(g2);
    }
    public void draw(Graphics2D g2, ArrayList<Point> knobs){
        Color save = g2.getColor();// save original color
        g2.setColor(Color.BLACK); // knobs are black
        for (Point point : knobs){
            g2.fillRect((int)point.getX()-4, (int)point.getY()-4, KNOB_SIZE,KNOB_SIZE);
        }
        g2.setColor(save);
    }
    public void drawSelected(Graphics2D g2, Rectangle bounds) {
        selected = true;
        draw(g2);
        draw(g2,getKnobs());
    }

    private void updateKnobs(){
        knobs = null;
        getKnobs();
    }

    @Override
    public void modelChanged(DShapeModel model) {
    }
}
