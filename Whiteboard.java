import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Chris on 5/16/2016.
 */
public class Whiteboard extends JFrame implements ModelListener {
    private static Whiteboard instance = null;
    Canvas canvas;
    boolean random = true;

    protected Whiteboard() {
    }

    public static Whiteboard getInstance() {
        if (instance == null) {
            instance = new Whiteboard();
        }
        return instance;
    }

    public void theGUI() {
        // GUI stuff
        setTitle("Whiteboard");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER); // Add Canvas to center of Whiteboard

        // West side of Whiteboard
        JPanel westBox = new JPanel();
        Box box;
        GridLayout grid = new GridLayout(2, 0);
        westBox.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        westBox.setSize(400, 400);
        westBox.setLayout(grid);

        // Panel to update table
        JPanel tablePanel = new JPanel();
        tablePanel.setSize(400, 200);

        //---------------------------------------------------------------------------
        // Control Boxes
        Box addControls = Box.createHorizontalBox();
        JButton rectButton = new JButton("Rect");
        rectButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DRectModel rectModel = new DRectModel();
                        canvas.addShape(rectModel);
                        rectModel.addListener(instance);

                        //update table
                        JTable table = new JTable();
                        table = canvas.getTable();
                        table.setPreferredScrollableViewportSize(new Dimension(400, 175));
                        JScrollPane scrollPane = new JScrollPane(table);
                        //scrollPane.setVerticalScrollBar(new JScrollBar());
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                        table.setFillsViewportHeight(true);

                        // repaint tablePanel
                        tablePanel.removeAll();
                        tablePanel.add(scrollPane);
                        tablePanel.revalidate();
                        repaint();
                    }
                }
        );
        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DOvalModel ovalModel = new DOvalModel();
                        canvas.addShape(ovalModel);
                        ovalModel.addListener(instance);

                        //update table
                        JTable table = new JTable();
                        table = canvas.getTable();
                        table.setPreferredScrollableViewportSize(new Dimension(400, 175));
                        JScrollPane scrollPane = new JScrollPane(table);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                        table.setFillsViewportHeight(true);

                        tablePanel.removeAll();
                        tablePanel.add(scrollPane);
                        tablePanel.revalidate();
                        repaint();
                    }
                }
        );
        JButton lineButton = new JButton("Line");
        JButton textButton = new JButton("Text");

        addControls.add(Box.createHorizontalStrut(10));
        addControls.add(rectButton);
        addControls.add(Box.createHorizontalStrut(10));
        addControls.add(ovalButton);
        addControls.add(Box.createHorizontalStrut(10));
        addControls.add(lineButton);
        addControls.add(Box.createHorizontalStrut(10));
        addControls.add(textButton);
        addControls.add(Box.createHorizontalStrut(10));

        Box setControls = Box.createHorizontalBox();
        JButton setColorButton = new JButton("Set Color");
        setColorButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Graphics2D g2 = canvas.getBufferedImage().createGraphics();
                        Color newColor = JColorChooser.showDialog(null, "Choose Shape Color", canvas.getSelectedColor());
                        DShape shape = canvas.getSelectedShape();
                        shape.setColor(newColor);
                        shape.draw(g2);
                        revalidate();
                        repaint();
                    }
                }
        );
        setControls.add(Box.createHorizontalStrut(10));
        setControls.add(setColorButton);
        setControls.add(Box.createHorizontalStrut(10));

        Box layerControls = Box.createHorizontalBox();
        JButton moveFrontButton = new JButton("Move to Front");
        moveFrontButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        canvas.moveFront();

                        //update table
                        JTable table = new JTable();
                        table = canvas.getTable();
                        table.setPreferredScrollableViewportSize(new Dimension(400, 175));
                        JScrollPane scrollPane = new JScrollPane(table);
                        //scrollPane.setVerticalScrollBar(new JScrollBar());
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                        table.setFillsViewportHeight(true);

                        // repaint tablePanel
                        tablePanel.removeAll();
                        tablePanel.add(scrollPane);
                        tablePanel.revalidate();
                        repaint();
                    }
                }
        );
        JButton moveBackButton = new JButton("Move to Back");
        moveBackButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        canvas.moveBack();

                        //update table
                        JTable table = new JTable();
                        table = canvas.getTable();
                        table.setPreferredScrollableViewportSize(new Dimension(400, 175));
                        JScrollPane scrollPane = new JScrollPane(table);
                        //scrollPane.setVerticalScrollBar(new JScrollBar());
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                        table.setFillsViewportHeight(true);

                        // repaint tablePanel
                        tablePanel.removeAll();
                        tablePanel.add(scrollPane);
                        tablePanel.revalidate();
                        repaint();
                    }
                }
        );
        JButton removeButton = new JButton("Remove Shape");
        removeButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        canvas.removeSelected();

                        //update table
                        JTable table = new JTable();
                        table = canvas.getTable();
                        table.setPreferredScrollableViewportSize(new Dimension(400, 175));
                        JScrollPane scrollPane = new JScrollPane(table);
                        //scrollPane.setVerticalScrollBar(new JScrollBar());
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                        table.setFillsViewportHeight(true);

                        // repaint tablePanel
                        tablePanel.removeAll();
                        tablePanel.add(scrollPane);
                        tablePanel.revalidate();
                        repaint();
                    }
                }
        );

        layerControls.add(Box.createHorizontalStrut(10));
        layerControls.add(moveFrontButton);
        layerControls.add(Box.createHorizontalStrut(10));
        layerControls.add(moveBackButton);
        layerControls.add(Box.createHorizontalStrut(10));
        layerControls.add(removeButton);
        layerControls.add(Box.createHorizontalStrut(10));


        box = Box.createVerticalBox(); // Vertical box to go into Whiteboard
        // Add Control Boxes to main box
        box.add(addControls);
        box.add(setControls);
        box.add(layerControls);

        // Set Alignment
        for (Component comp : box.getComponents()) {
            ((JComponent) comp).setAlignmentX(Box.LEFT_ALIGNMENT);
        }

        //---------------------------------------------------------------------------
        //add default table
        JTable table = canvas.getTable();
        table.setPreferredScrollableViewportSize(new Dimension(400, 175));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        table.setFillsViewportHeight(true);
        tablePanel.add(scrollPane);

        //add elements to westBox
        westBox.add(box);
        westBox.add(tablePanel);
        add(westBox, BorderLayout.WEST);
        setVisible(true);
    }

    @Override
    public void modelChanged(DShapeModel model) {
        instance.repaint();
    }

    public static void main(String[] args) {
        Whiteboard wb = new Whiteboard();
        wb.theGUI();
    }
}
