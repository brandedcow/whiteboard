import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Chris on 5/16/2016.
 */
public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
    ArrayList<DShape> shapes = new ArrayList<>();

    JTable table = null;
    String[] columns = {"x", "y", "width", "height", "type"};
    String[][] rowData;

    private BufferedImage bufferedImage = null;

    private int WIDTH = 400;
    private int HEIGHT = 400;

    // Mouse
    private int startX = 0;
    private int startY = 0;
    private int endX = 0;
    private int endY = 0;

    Canvas() {
        setSize(WIDTH, HEIGHT);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public JTable getTable() {
        rowData = new String[shapes.size() + 1][5];
        // Label Row
        rowData[0][0] = "x";
        rowData[0][1] = "y";
        rowData[0][2] = "width";
        rowData[0][3] = "height";
        rowData[0][4] = "type";
        int i = 1;
        // List shapes
        for (DShape shape : shapes) {
            rowData[i][0] = Integer.toString(shape.getX());
            rowData[i][1] = Integer.toString(shape.getY());
            rowData[i][2] = Integer.toString(shape.getWidth());
            rowData[i][3] = Integer.toString(shape.getHeight());
            rowData[i][4] = shape.getType();
            i++;
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (bufferedImage == null) {
            bufferedImage = (BufferedImage) createImage(WIDTH, HEIGHT);
            Graphics2D gc = bufferedImage.createGraphics();
            gc.setColor(Color.WHITE);
            gc.fillRect(0, 0, WIDTH, HEIGHT);
        }
        g2.drawImage(bufferedImage, null, 0, 0);
        // Paint shapes
        for (DShape ds : shapes) {
            ds.draw(g2);
        }

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
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        endX = e.getX();
        endY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        Graphics2D graphics2D = bufferedImage.createGraphics();
        this.repaint();
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }


}
