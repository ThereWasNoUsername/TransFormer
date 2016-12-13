
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

import System.PropertiesPanel;
import System.SystemElement;
import System.GraphicsPanel;

public class Window {
	String file_name = "0.txt";
	File file;
	Writer writer;
	BufferedReader reader;
	JFrame frame;
	
	JPanel frame_panel;
	PropertiesPanel properties_panel;
	GraphicsPanel graphics_panel;
	JPanel options_panel;
	
	JList orbitalRenderControl;
	
	ArrayList<StationType> StationTypes;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Window window = new Window();
		window.initialize();
	}
	
	public Window(){
	}
	public void initialize()
	{
		frame = new JFrame();
		frame.setTitle("TransFormer");
		frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame_panel = new JPanel();
		frame_panel.setLayout(new BoxLayout(frame_panel, BoxLayout.X_AXIS));
		frame.add(frame_panel);
		
		//The left sidebar shows properties for the selected item
		properties_panel = new PropertiesPanel();
		properties_panel.setLayout(new BoxLayout(properties_panel, BoxLayout.Y_AXIS));
		properties_panel.setPreferredSize(new Dimension(320, 1080));
		properties_panel.setName("Options");
		properties_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		properties_panel.add(new JLabel("Element Properties"));
		properties_panel.setAlignmentY(0f);
		frame_panel.add(properties_panel);
		
		graphics_panel = new GraphicsPanel();
		graphics_panel.setLayout(null);
		graphics_panel.setPreferredSize(new Dimension(1280, 1080));
		graphics_panel.setName("View");
		graphics_panel.init();
		frame_panel.add(graphics_panel);
		
		options_panel = new JPanel();
		options_panel.setPreferredSize(new Dimension(320, 1080));
		frame_panel.add(options_panel);
		
		properties_panel.setSystemPanel(graphics_panel);
		graphics_panel.setOptionsPanel(properties_panel);
		
		frame.pack();
		frame.setVisible(true);
		System.out.println("Done");
	}
	public void print()
	{
		try {
			for(String s: Files.readAllLines(Paths.get(file_name)))
			{
				System.out.println(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void write(String input)
	{
		   try {
			Files.write(Paths.get(file_name), input.getBytes("utf-16"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delete()
	{
		try {
			Files.delete(Paths.get(file_name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close()
	{
		System.exit(0);
	}

}