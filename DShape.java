import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Chris on 5/16/2016.
 */
public class DShape implements ModelListener {
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

    public void draw(Graphics2D g2) {
    }
    public void draw(Graphics2D g2, Rectangle bounds){
        setBounds(bounds);
        draw(g2);
    }
    public void drawSelected(Graphics2D g2, Rectangle bounds) {
        selected = true;
        draw(g2);

    }

    @Override
    public void modelChanged(DShapeModel model) {
    }
}
