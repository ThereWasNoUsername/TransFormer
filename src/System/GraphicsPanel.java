package System;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GraphicsPanel extends JPanel implements MouseListener, KeyListener, ActionListener /*ListSelectionListener*/ {

	PropertiesPanel options_panel;
	
	ArrayList<SystemElement> elements;
	//JList orbitalRenderOptions;
	SystemGroup system;
	/*
	Rectangle bounds;
	static double bound_left;
	static double bound_top;
	static double bound_width;
	static double bound_height;
	static double bound_right;
	static double bound_bottom;
	static double center_x;
	static double center_y;
	*/
	Dimension dimensions;
	static double width;
	static double height;
	static double center_x;
	static double center_y;
	public GraphicsPanel()
	{
	}
	public void init()
	{
		addMouseListener(this);
		elements = new ArrayList<SystemElement>();
		system = new SystemGroup();
		addElement(system);
		
		/*
		orbitalRenderOptions = new JList<String>(new String[]{"Render extremes", "Render distances", "Render dots"});
		orbitalRenderOptions.addListSelectionListener(this);
		orbitalRenderOptions.setSelectedIndex(0);
		orbitalRenderOptions.setLocation(500, 500);
		orbitalRenderOptions.setVisible(true);
		orbitalRenderOptions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orbitalRenderOptions.setLayoutOrientation(JList.VERTICAL);
		add(orbitalRenderOptions);
		*/
		
		/*
		bounds = getBounds();
		bound_left = bounds.getX();
		bound_top = bounds.getY();
		bound_width = bounds.getWidth();
		bound_height = bounds.getHeight();
		bound_right = bound_left + bound_width;
		bound_bottom = bound_top + bound_height;
		center_x = bound_left + bound_width/2;
		center_y = bound_top + bound_height/2;
		
		*/
		dimensions = getPreferredSize();
		width = dimensions.getWidth();
		height = dimensions.getHeight();
		center_x = width/2;
		center_y = height/2;
		
		System.out.println("Init");
		/*
		System.out.println("Left: " + bound_left);
		System.out.println("Top: " + bound_top);
		System.out.println("Right: " + bound_right);
		System.out.println("Bottom: " + bound_bottom);
		System.out.println("Width: " + bound_width);
		System.out.println("Height: " + bound_height);
		System.out.println("CenterX: " + center_x);
		System.out.println("CenterY: " + center_y);
		*/
	}
	
	public void setOptionsPanel(PropertiesPanel op)
	{
		options_panel = op;
	}
	public void paint(Graphics g)
	{
		System.out.println("Paint");
		
		g.setColor(Color.BLACK);
		//g.fillRect((int) bound_left, (int) bound_top, (int) bound_width, (int) bound_height);
		g.fillRect(0, 0, (int) width, (int) height);
		
		g.setColor(Color.WHITE);
		
		system.paint(g);
		
		//orbitalRenderOptions.paint(g);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse pressed");
		int clicked_x = e.getX();
		int clicked_y = e.getY();
		//Check every object in the system except the SystemGroup. If no objects were clicked (clicked on empty space), we default to the SystemGroup
		elements = system.collapseHierarchy();
		elements.remove(system);
		SystemElement clickedElement = null;
		for(SystemElement se: elements)
		{
			if(se.clickedOn(clicked_x, clicked_y))
			{
				clickedElement = se;
				break;
			}
		}
		options_panel.selectElement(clickedElement == null ? system: clickedElement);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void addElement(SystemElement element)
	{
		elements.add(element);
	}
	public String getXML()
	{
		return null;
	}
	/*
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(orbitalRenderOptions))
		{
			Orbitals.setRenderOption((String) orbitalRenderOptions.getSelectedValue());
		}
	}
	*/
}