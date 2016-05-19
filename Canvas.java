import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by Chris on 5/16/2016.
 */
public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
    ArrayList<DShape> shapes = new ArrayList<>();
    private DShape selectedShape;
    Point movingPoint = null;
    Point anchorPoint = new Point(0,0);
    Rectangle movingKnob = null;
    private Color selected;
    private Point currentPoint;

    private JTable table = null;
    private String[] columns = {"x", "y", "width", "height", "type"};
    private String[][] rowData;

    private BufferedImage bufferedImage = null;
    private BufferedImage bufferedNoSelected = null;
    private boolean resize = false;
    private boolean remove = false;
    Rectangle bounds;

    private int WIDTH = 400;
    private int HEIGHT = 400;

    // Mouse
    private int currentX = 0;
    private int currentY = 0;
    private int startX = 0;
    private int startY = 0;
    private int endX = 0;
    private int endY = 0;
    private int xCoor = 0;
    private int yCoor = 0;

    Canvas() {
        setSize(WIDTH, HEIGHT);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public DShape getSelectedShape(){
        return selectedShape;
    }

    public Color getSelectedColor(){
        return selected;
    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    public JTable getTable() {
        rowData = new String[shapes.size() + 1][5];
        int i = shapes.size() - 1;
        // List shapes
        for (DShape shape : shapes) {
            rowData[i][0] = Integer.toString(shape.getX());
            rowData[i][1] = Integer.toString(shape.getY());
            rowData[i][2] = Integer.toString(shape.getWidth());
            rowData[i][3] = Integer.toString(shape.getHeight());
            rowData[i][4] = shape.getType();
            i--;
        }
        table = new JTable(rowData, columns);

        // Set column width
        TableColumn column = null;
        for (int j = 0; j < 5; j++) {
            column = table.getColumnModel().getColumn(j);
            column.setPreferredWidth(80);
        }

        return table;
    }

    public void addShape(DShapeModel shapeModel) {
        // check if shapeModel is properly filled out
        if (shapeModel.getShape() != null) {
            // create shape
            shapes.add(shapeModel.getShape());
        }
        getTable();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // draws base white board
        if (bufferedImage == null) {
            bufferedImage = (BufferedImage) createImage(WIDTH, HEIGHT);
            Graphics2D gc = bufferedImage.createGraphics();
            gc.setColor(Color.WHITE);
            gc.fillRect(0, 0, WIDTH, HEIGHT);
        }

        if (bufferedNoSelected == null) {
            bufferedNoSelected = (BufferedImage) createImage(WIDTH, HEIGHT);
            Graphics2D gc = bufferedNoSelected.createGraphics();
            gc.setColor(Color.WHITE);
            gc.fillRect(0, 0, WIDTH, HEIGHT);
        }

        // update image
        g2.drawImage(bufferedImage, null, 0, 0);

        // Paint shapes
        for (DShape ds : shapes) {
            ds.draw(g2);
        }
        if (selectedShape != null) {
            selectedShape.drawSelected(g2, selectedShape.getBounds());
        }
        getTable();
    }

    private void updateBufferedImage(){
        //create white
        bufferedNoSelected = (BufferedImage) createImage(WIDTH, HEIGHT);
        Graphics2D g2 = bufferedNoSelected.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        //create shapes without selected shape
        for (DShape ds : shapes) {
            if (ds.getShape() != selectedShape){
                ds.draw(g2);
            }
        }
        if (!remove) {
            if (selectedShape != null) {
                selectedShape.draw(g2, bounds);
                bufferedImage = bufferedNoSelected;
            }
        }
    }

    public void resizeShape(Rectangle r){
        resize = true;
        System.out.println("initiate resize");
        // initial click is on moving point
        movingPoint = currentPoint;
        movingKnob = r;

        // opposite corner from moving point is anchor point
        ArrayList<Rectangle> temp = selectedShape.getKnobRect();
        int pointIndex = temp.indexOf(r);
        int anchorIndex = pointIndex + 2;
        if (anchorIndex > 3){
            anchorIndex = (anchorIndex - 4); // pseudo circular array
        }
        anchorPoint.setLocation(temp.get(anchorIndex).getX(), temp.get(anchorIndex).getY());
    }

    public void removeSelected(){
        remove = true;
        Graphics2D g2 = null;
        outerloop:
        for (DShape shape : shapes){
            if (selectedShape == shape){
                g2 = bufferedNoSelected.createGraphics();
                selectedShape.clear();
                selectedShape = null;
                shapes.remove(shape);
                break outerloop;
            }
        }

        getTable();

        //draw image
        updateBufferedImage();
        if (g2 != null) {
            g2.drawImage(bufferedNoSelected, null, 0, 0);
        }
        repaint();
        remove = false;
    }

    public void moveFront(){
        int i = -1;
        i = shapes.indexOf(selectedShape);

        //found
        if (i != -1){
            DShape temp = shapes.get(i+1);
            shapes.set(i+1,shapes.get(i));
            shapes.set(i,temp);
        }
        getTable();
        repaint();
    }

    public void moveBack(){
        int i = -1;
        i = shapes.indexOf(selectedShape);

        //found
        if (i != -1){
            DShape temp = shapes.get(i-1);
            shapes.set(i-1,shapes.get(i));
            shapes.set(i,temp);
        }
        getTable();
        repaint();
    }

    public void updateCanvas(){
        //create white
        bufferedNoSelected = (BufferedImage) createImage(WIDTH, HEIGHT);
        Graphics2D g2 = bufferedNoSelected.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        //create shapes without selected shape
        for (DShape ds : shapes) {
            if (ds.getShape() != selectedShape){
                ds.draw(g2);
            }
        }
        bufferedImage = bufferedNoSelected;
    }

    //--------------------------------------------------------------------------------
    //Mouse Events
    @Override
    public void mousePressed(MouseEvent e) {

        // get coordinates
        currentX = e.getX();
        currentY = e.getY();
        int x, y, width, height;
        System.out.println("X: " + currentX);
        System.out.println("Y: " + currentY);

        // if shape found at coordinates, adjust outer ones first
        outerloop:
        for (int i = (shapes.size() - 1); i >= 0; i--) {
            x = shapes.get(i).getX();
            y = shapes.get(i).getY();
            width = shapes.get(i).getWidth();
            height = shapes.get(i).getHeight();

            if ((x < currentX && currentX < (x + width))
                    && (y < currentY && currentY < (y + height))) {
                selectedShape = shapes.get(i);
                break outerloop;
            }
        }

        // if no shape found at coordinates, do nothing
        if (selectedShape != null) {
            Graphics2D graphics2D = bufferedImage.createGraphics(); //Graphics
            System.out.println("Shape Type: " + selectedShape);
            selected = selectedShape.getColor();
            //refresh canvas
            updateCanvas();
            selectedShape.drawSelected(graphics2D, selectedShape.getBounds()); // Problem with double printing

            // set coordinates for move
            xCoor = selectedShape.getX();
            yCoor = selectedShape.getY();
        }

        startX = e.getX();
        startY = e.getY();
        endX = e.getX();
        endY = e.getY();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // get current mouse location
        endX = e.getX();
        endY = e.getY();

        // find displacement
        int dx = endX - startX;
        int dy = endY - startY;

        // if resizing
        if (resize){
            //find displacement from anchor point
            int rdx = (int)anchorPoint.getX() - endX;
            int rdy = (int)anchorPoint.getY() - endY;

            // resize cases: flip or no flip
            if ((rdx < 0)||( rdy < 0)){  //flip required
                if (rdx < 0 && rdy > 0){ // flip to opposite x
                    bounds = new Rectangle(
                            (int)anchorPoint.getX(),
                            endY,
                            (int) Math.abs(endX - anchorPoint.getX()),
                            (int) Math.abs(endY - anchorPoint.getY())
                    );
                }
                if (rdx > 0 && rdy < 0){ // flip to opposite y
                    bounds = new Rectangle(
                            endX,
                            (int)anchorPoint.getY(),
                            (int) Math.abs(endX - anchorPoint.getX()),
                            (int) Math.abs(endY - anchorPoint.getY())
                    );
                }
                if (rdx < 0 && rdy < 0){ // flip to opposite quadrant
                    bounds = new Rectangle(
                            (int)anchorPoint.getX(),
                            (int)anchorPoint.getY(),
                            (int) Math.abs(endX - anchorPoint.getX()),
                            (int) Math.abs(endY - anchorPoint.getY())
                    );
                }
            }
            else { // no flip required
                bounds = new Rectangle(
                        endX,
                        endY,
                        (int) Math.abs(endX - anchorPoint.getX()),
                        (int) Math.abs(endY - anchorPoint.getY())
                );
            }
            //draw image
            Graphics2D g2 = bufferedNoSelected.createGraphics();
            updateBufferedImage();
            selectedShape.draw(g2, bounds);
        }

        // new bounds
        if (!resize) { // not resizing
            if (selectedShape != null) { // shape found
                bounds = new Rectangle(
                        xCoor + dx,
                        yCoor + dy,
                        selectedShape.getWidth(),
                        selectedShape.getHeight()
                );

                //draw image
                Graphics2D g2 = bufferedNoSelected.createGraphics();
                updateBufferedImage();
                selectedShape.draw(g2, bounds);
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        if (resize){
            resize = false;
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        // get coordinates
        currentX = e.getX();
        currentY = e.getY();
        int x, y, width, height;
        System.out.println("X: " + currentX);
        System.out.println("Y: " + currentY);

        // if knob found at coordinates, initiate resize
        currentPoint = new Point(currentX, currentY);
        if (selectedShape != null) {
            for (Rectangle rect : selectedShape.getKnobRect()) {
                if (rect.contains(currentPoint)) {
                    resizeShape(rect);
                }
            }
        }

        // if shape found at coordinates, adjust outer ones first
        outerloop:
        for (int i = (shapes.size() - 1); i >= 0; i--) {
            x = shapes.get(i).getX();
            y = shapes.get(i).getY();
            width = shapes.get(i).getWidth();
            height = shapes.get(i).getHeight();

            if ((x < currentX && currentX < (x + width))
                    && (y < currentY && currentY < (y + height))) {
                selectedShape = shapes.get(i);
                break outerloop;
            }
        }

        // if no shape found at coordinates, do nothing
        if (selectedShape != null) {
            Graphics2D graphics2D = bufferedImage.createGraphics(); //Graphics
            System.out.println("Shape Type: " + selectedShape);
            selected = selectedShape.getColor();
            selectedShape.drawSelected(graphics2D, selectedShape.getBounds());
        }
    }


    // Unused Mouse Events
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
