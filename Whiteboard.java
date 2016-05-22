import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Chris on 5/16/2016.
 */
public class Whiteboard extends JFrame implements ModelListener {
    private static Whiteboard instance = null;
	private Server serverAccepter;
	private Client clientHandler;
	private ArrayList<ObjectOutputStream> outputs;
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
		outputs = new ArrayList<>();
		serverAccepter = null;
		clientHandler = null;
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
        final JPanel tablePanel = new JPanel();
        tablePanel.setSize(400, 200);
        //--------------------------Menu Bar------------------------------------------
        
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//File I/O
		JMenu mnFile = new JMenu("File"); //File menu
		menuBar.add(mnFile);
		
		final JMenuItem mntmNew = new JMenuItem("New..");
		mnFile.add(mntmNew);
		
		mntmNew.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.reset();
				//dTable.reset();
				repaint();
			}
		});
		
		final JMenuItem mntmOpen = new JMenuItem("Open XML..");
		mnFile.add(mntmOpen);
		
		mntmOpen.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("Enter File Name", null) + ".xml";
				if(result != null)
				{
					try {
						XMLDecoder xmlIn = new XMLDecoder(
								new BufferedInputStream(
										new FileInputStream(
												new File(result))));
						DShape[] array = (DShape[]) xmlIn.readObject();
						xmlIn.close();
						canvas.reset();
						//dTable.reset();
						for(DShape d: array) {
			                canvas.addShape(d);
			            }
					} catch (IOException o) {
						o.printStackTrace();
					}
				}
			}
		});
		
		
		final JMenuItem mntmSave = new JMenuItem("Save As XML...");
		mnFile.add(mntmSave);	
		mntmSave.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("Enter File Name", null) + ".xml";
				if(result != null)
				{
					try {
						XMLEncoder xmlOut = new XMLEncoder(
								new BufferedOutputStream(
										new FileOutputStream(
												new File(result))));
						DShape[] array = canvas.getShapes().toArray(new DShape[0]);
						
						xmlOut.writeObject(array);
						xmlOut.close();
					} catch (IOException o) {
						o.printStackTrace();
					}
				}
			}
		});
		final JMenuItem mntmSaveAsPng = new JMenuItem("Save as PNG...");
		mnFile.add(mntmSaveAsPng);
		mntmSaveAsPng.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog("Enter File Name", null) + ".png";
				if(result != null)
				{
					try {
						DShape selected = canvas.getSelectedShape();
						canvas.setSelected(null);
						BufferedImage image = (BufferedImage) canvas.createImage(canvas.getWidth(), canvas.getHeight());
						Graphics g = image.getGraphics();
				        canvas.paintAll(g);
				        g.dispose();
				        javax.imageio.ImageIO.write(image, "PNG", new File(result));
				        canvas.setSelected(selected);
					} catch (IOException o) {
						o.printStackTrace();
					}
				}
			}
		});
		

		
        //---------------------------------------------------------------------------
        // Control Boxes
        Box addControls = Box.createHorizontalBox();
        final JButton rectButton = new JButton("Rect");
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
        final JButton ovalButton = new JButton("Oval");
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
        final JButton lineButton = new JButton("Line");
        lineButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DLineModel lineModel = new DLineModel();
                        canvas.addShape(lineModel);
                        lineModel.addListener(instance);

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
        final JButton textButton = new JButton("Text");
        textButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DTextModel textModel = new DTextModel();
                        canvas.addShape(textModel);
                        textModel.addListener(instance);

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
        final JButton setColorButton = new JButton("Set Color");
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
        final JButton moveFrontButton = new JButton("Move to Front");
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
        final JButton moveBackButton = new JButton("Move to Back");
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
        final JButton removeButton = new JButton("Remove Shape");
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
        
		//----------------------------------Server Connection-----------------------
		
        final JLabel lblClientMode = new JLabel("CLIENT MODE");
		lblClientMode.setForeground(Color.RED);
		lblClientMode.setVisible(false);

		final JLabel lblServerMode = new JLabel("SERVER MODE");
		lblServerMode.setHorizontalAlignment(SwingConstants.LEFT);
		lblServerMode.setForeground(Color.RED);
		lblServerMode.setVisible(false);
		
		
		final JMenu mnConnection = new JMenu("Connection"); //Connection menu
		menuBar.add(mnConnection);
		final JMenuItem mntmStartServer = new JMenuItem("Start Server");
		mnConnection.add(mntmStartServer);
		
		mntmStartServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doServer();
				mnConnection.setEnabled(false);
				lblServerMode.setVisible(true);
			}
		});
		
		final JMenuItem mntmJoinServer = new JMenuItem("Join Server");
		mnConnection.add(mntmJoinServer);

		mntmJoinServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doClient();
				canvas.reset();
				// Disable all buttons on client except save
				mntmJoinServer.setEnabled(false);
				mntmStartServer.setEnabled(false);
				mntmNew.setEnabled(false);
				mntmOpen.setEnabled(false);
				lineButton.setEnabled(false);
				moveBackButton.setEnabled(false);
				moveFrontButton.setEnabled(false);
				ovalButton.setEnabled(false);
				rectButton.setEnabled(false);
				removeButton.setEnabled(false);
				setColorButton.setEnabled(false);
				textButton.setEnabled(false);
				lblClientMode.setVisible(true);
				canvas.removeMouseListener(canvas);
				canvas.removeMouseMotionListener(canvas);
			}
		});
		
		
		
    }

    @Override
    public void modelChanged(DShapeModel model) {
        instance.repaint();
    }
	//start client
	public void doClient() {
		String result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:5555");
		while (result == null)
		{
			result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:5555");
		}
		if (result != null) {
			String[] parts = result.split(":");
			clientHandler = new Client(parts[0].trim(), Integer.parseInt(parts[1].trim()));
			clientHandler.start();
			JOptionPane.showMessageDialog(canvas, "Successful connection to server.");
		}
	}
	public void doServer() {
		String result = JOptionPane.showInputDialog("Enter Port Number (100 - 25565)", 5555);
		while (Integer.parseInt(result) < 100 || Integer.parseInt(result) > 25565)
		{
			result = JOptionPane.showInputDialog("Enter a Valid Port Number from 100 to 25565", 5555);
		}
		if (result != null) {
			serverAccepter = new Server(Integer.parseInt(result.trim()));
			serverAccepter.start();
			JOptionPane.showMessageDialog(canvas, "Server started successfully.");
		}
	}
	//add client to server
    public synchronized void addOutput(ObjectOutputStream out) {
        outputs.add(out);
    }
    //send a command object
    public void doSend(String command, DShape shape) {
        Command cmd = new Command();
        cmd.setCommand(command);
        cmd.setShape(shape);
        sendRemote(cmd);
    }
    //actual writing from server to clients
    public synchronized void sendRemote(Command message) {
        // Convert the message object into an xml string.
        OutputStream memStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(memStream);
        encoder.writeObject(message);
        encoder.close();
        String xmlString = memStream.toString();
        // Now write that xml string to all the clients.
        Iterator<ObjectOutputStream> it = outputs.iterator();
        while (it.hasNext()) {
            ObjectOutputStream out = it.next();
            try {
                out.writeObject(xmlString);
                out.flush();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                it.remove();
            }
        }
    }
    //One time sync for clients
    public void sync() {
    	if (outputs.size() != 0) {
    		doSend("reset", null);
    		for(int i = 0; i < canvas.getShapes().size(); i++)
    		{
    			doSend("add", canvas.getShapes().get(i));
    		}
    	}
    }
    public static void main(String[] args) {
        Whiteboard wb = new Whiteboard();
        wb.theGUI();
    }    

	//Server Class
	class Server extends Thread {
		
		private int port;

		public Server(int port) {
			this.port = port;
		}
		
		public void run() {
	        try {
	            ServerSocket serverSocket = new ServerSocket(port);
	            while (true) {
	                Socket toClient = null;
	                toClient = serverSocket.accept();
	                outputs = new ArrayList<ObjectOutputStream>();
	                addOutput(new ObjectOutputStream(toClient.getOutputStream()));
	                	sync();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(rootPane, "Failed to Start Server. Please restart the application.");
	            System.exit(0); // Bad way to handle
	        }
	    }
	}
	

	//Client Class
	class Client extends Thread {
		private String name;
		private int port;
		
		public Client(String name, int port) {
			this.name = name;
			this.port = port;
		}
		
		public void run() {
	        try {
	            Socket toServer = new Socket(name, port);
	            ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
	            while (true) {
	                String xmlString = (String) in.readObject();
	                XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
	                Command cmd = (Command) decoder.readObject();
	                decoder.close();
	                switch(cmd.getCommand())
	                {
	                	case "add":
	                		canvas.addShape(cmd.getShape());
	                		//dTable.addRow(cmd.getShape().getShapeModel());
	                		break;
	                	case "remove":
	                		canvas.setSelected(cmd.getShape());
	                		canvas.removeSelected();
	                		//dTable.removeRow(cmd.getShape().getShapeModel());
	                		break;
	                	case "front":
	                		canvas.moveFront();
	                		//dTable.moveRowUp(cmd.getShape().getShapeModel());
	                		break;
	                	case "back":
	                		canvas.moveBack();
	                		//dTable.moveRowDown(cmd.getShape().getShapeModel());
	                		break;
	                	case "change":
	                		//canvas.update((cmd.getShape());
	                		//dTable.updateRow(cmd.getShape().getShapeModel());
	                		break;
	                	case "reset":
	                		canvas.reset();
	                		//dTable.reset();
	                		break;
	                }
	            }
	        }
	        catch (Exception ex) { // IOException and ClassNotFoundException
	        	ex.printStackTrace();
	        	JOptionPane.showMessageDialog(rootPane, "Failed to Connect. Please restart the application.");
	        	System.exit(0); //bad way to handle
	        }				   
		}
	}
}

