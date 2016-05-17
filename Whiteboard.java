import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris on 5/16/2016.
 */
public class Whiteboard extends JFrame {
    Canvas canvas;

    Whiteboard(){
        // GUI stuff
        setTitle("Whiteboard");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER); // Add Canvas to center of Whiteboard

        // West side of Whiteboard
        JPanel westBox = new JPanel();
        Box box;
        GridLayout grid = new GridLayout(2,0);
        westBox.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        westBox.setSize(400,400);
        westBox.setLayout(grid);

        // Panel to update table
        JPanel tablePanel = new JPanel();
        tablePanel.setSize(400,200);

        //---------------------------------------------------------------------------
        // Control Boxes
        Box addControls = Box.createHorizontalBox();
        JButton rectButton = new JButton("Rect");
        rectButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DShape rect = new DRect();
                        DRectModel rectModel = new DRectModel();
                        rect.addModel(rectModel);
                        canvas.addShape(rect);
                        //update table
                        JTable table = new JTable();
                        table = canvas.getTable();
                        tablePanel.removeAll();
                        tablePanel.add(table);
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
                        DShape oval = new DOval();
                        DOvalModel ovalModel = new DOvalModel();
                        oval.addModel(ovalModel);
                        canvas.addShape(oval);
                        //update table
                        JTable table = new JTable();
                        table = canvas.getTable();
                        tablePanel.removeAll();
                        tablePanel.add(table);
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

        setControls.add(Box.createHorizontalStrut(10));
        setControls.add(setColorButton);
        setControls.add(Box.createHorizontalStrut(10));

        Box layerControls = Box.createHorizontalBox();
        JButton moveFrontButton = new JButton("Move to Front");
        JButton moveBackButton = new JButton("Move to Back");
        JButton removeButton = new JButton("Remove Shape");

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
            ((JComponent)comp).setAlignmentX(Box.LEFT_ALIGNMENT);
        }

        //---------------------------------------------------------------------------
        //add default table
        JTable table = canvas.getTable();
        tablePanel.add(table);

        //add elements to westBox
        westBox.add(box);
        westBox.add(tablePanel);
        add(westBox,BorderLayout.WEST);
        setVisible(true);
    }

    public static void main(String[] args){
        Whiteboard wb = new Whiteboard();
    }

}
