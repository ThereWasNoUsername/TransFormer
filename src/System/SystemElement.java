package System;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class SystemElement {
	static int tabCount = 1;
	int pos_x;
	int pos_y;
	
	final int PIXELS_PER_LIGHT_SECOND = 24;

	ArrayList<SystemElement> children = new ArrayList<SystemElement>();
	SystemElement parent;
	
	String[] subelements = new String[0];
	
	HashMap<String, String> attributes = new HashMap<String, String>();
	String[] attributeKeys = new String[0];
	public SystemElement() {
	}

	public void paint(Graphics g) {

	}
	public void paintChildren(Graphics g)
	{
		for(SystemElement se: children)
		{
			se.paint(g);
		}
	}

	public boolean clickedOn(int clicked_x, int clicked_y) {
		return false;
	}

	public void addChild(SystemElement child) {
		children.add(child);
		child.setParent(this);
	}

	public void removeChild(SystemElement child) {
		children.remove(child);
		child.setParent(null);
	}

	public SystemElement getParent()
	{
		return parent;
	}
	public void setParent(SystemElement newParent) {
		parent = newParent;
	}

	public void initializeAttributes()
	{
		for(String attribute: attributeKeys)
		{
			attributes.put(attribute, JOptionPane.showInputDialog(attribute));
		}
	}
/*
	public void click() {
		int choice_index = JOptionPane.showOptionDialog(null,
				"What would you like to create around this " + getClass().getName().substring(7) + "?",
				"Create New Element", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, subelements,
				null);
		if (choice_index != JOptionPane.CLOSED_OPTION) {
			String choice = subelements[choice_index];
			SystemElement element = null;
			switch (choice) {
			 //Cannot make a new <SystemGroup>
			//case "SystemGroup":
			 //element = new SystemGroup();
			//break;
			case "Group":
				element = new Group();
				break;
			case "Primary":
				element = new Primary();
				break;
			case "Siblings":
				element = new Siblings();
				break;
			case "Orbitals":
				element = new Orbitals();
				break;
			case "Station":
				element = new Station();
				break;
			case "Table":
				// element = new Table();
				break;
			case "Label":
				// element = new Label();
				break;

			}
			if (element != null) {
				element.initializeAttributes();
				addChild(element);

				System.out.println(getXML());
			}
		}
	}
	*/

	public double getPosX()
	{
		return pos_x;
	}
	public double getPosY()
	{
		return pos_y;
	}
	public String getName()
	{
		return getClass().getName().substring(7);
	}
	public String getXML() {
		String name = getName();
		String tabs = "";
		System.out.println("Tab Count: " + tabCount);
		for (int i = 0; i < tabCount; i++) {
			tabs += "\t";
		}

		String tag_open_start = tabs + "<";
		String tag_open_middle = name;
		tag_open_middle += attributesToXML();
		String tag_open_end = ">";
		String tag_open = tag_open_start + tag_open_middle + tag_open_end;
		System.out.println("Open:" + tag_open);

		tabCount += 1;
		String tag_middle = "";
		for (SystemElement e : children) {
			tag_middle += System.lineSeparator() + e.getXML();
		}
		System.out.println("Middle:" + tag_middle);
		tabCount -= 1;

		String tag_close = tabs + "</" + name + ">";
		System.out.println("Close:" + tag_close);

		return tag_open + tag_middle + System.lineSeparator() + tag_close;
	}

	public String attributesToXML() {
		String result = "";
		for(String key: attributeKeys)
		{
			String value = attributes.get(key);
			if(StringIsNotEmptyOrNull(value))
			{
				result += "\t" + key + "=\"" + value + "\"";
			}
		}
		return result;
	}
	/*
	public String attributeToXML(String name, String value) {
		return "\t" + name + "=\"" + value + "\"";
	}
	*/

	// Places every object in this object's parent-child hierarchy system into a
	// single list
	public ArrayList<SystemElement> collapseHierarchy() {
		ArrayList<SystemElement> result = new ArrayList<SystemElement>();
		result.add(this);
		for (SystemElement child : children) {
			result.addAll(child.collapseHierarchy());
		}
		return result;
	}

	public boolean StringIsNotEmptyOrNull(String input) {
		return (input != null && input.length() > 0);
	}

	public ArrayList<Integer> diceRangeToNumberList(String input) {
		// Dice
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		switch(input)
		{
		case "random":
		case "equidistant":
			for(int i = 0; i <= 359; i++)
			{
				result.add(i);
			}
			break;
		default:
			int dice_index = input.indexOf('d');
			if (dice_index != -1) {
				int rolls = Integer.valueOf(input.substring(0, dice_index));
				int sides;
				int bonus;
				int bonus_index = input.indexOf("+");
				if (bonus_index == -1) {
					bonus_index = input.indexOf("-");
				}
				// No bonus
				if (bonus_index == -1) {
					sides = Integer.valueOf(input.substring(dice_index + 1));
					bonus = 0;
				} else {
					sides = Integer.valueOf(input.substring(dice_index + 1, bonus_index));
					bonus = Integer.valueOf(input.substring(bonus_index));
				}
				result.addAll(roll(rolls, sides, bonus));
			}
			else
			{
				int range_index = input.indexOf("-");
				if (range_index != -1) {
					int min = Integer.valueOf(input.substring(0, range_index));
					int max = Integer.valueOf(input.substring(range_index + 1));
					/*
					 * for(int i = min; i < max; i++) { result.add(i); }
					 */
					for(int i = min; i <= max; i++)
					{
						result.add(i);
					}
				} else {
					int constant = Integer.valueOf(input);
					result.add(constant);
				}
			}
		}
		return result;
	}
	public ArrayList<Integer> diceRangeToMinMax(String input) {
		// Dice
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		switch(input)
		{
		case "random":
		case "equidistant":
			result.add(0);
			result.add(360);
			break;
		default:
			int dice_index = input.indexOf('d');
			if (dice_index != -1) {
				int rolls = Integer.valueOf(input.substring(0, dice_index));
				int sides;
				int bonus;
				int bonus_index = input.indexOf("+");
				if (bonus_index == -1) {
					bonus_index = input.indexOf("-");
				}
				// No bonus
				if (bonus_index == -1) {
					sides = Integer.valueOf(input.substring(dice_index + 1));
					bonus = 0;
				} else {
					sides = Integer.valueOf(input.substring(dice_index + 1, bonus_index));
					bonus = Integer.valueOf(input.substring(bonus_index));
				}
				result.add(rolls + bonus); // Min case
				result.add(rolls * sides + bonus); // Max case
			}
			else
			{
				int range_index = input.indexOf("-");
				if (range_index != -1) {
					int min = Integer.valueOf(input.substring(0, range_index));
					int max = Integer.valueOf(input.substring(range_index + 1));
					/*
					 * for(int i = min; i < max; i++) { result.add(i); }
					 */
					result.add(min);
					result.add(max);
				} else {
					int constant = Integer.valueOf(input);
					result.add(constant);
					result.add(constant);
				}
			}
		}
		return result;
	}
	public String[] getCompatibleSubElements()
	{
		return subelements;
	}
	public String[] getAttributeKeys()
	{
		return attributeKeys;
	}
	public String getAttribute(String key)
	{
		return attributes.get(key);
	}
	public void setAttribute(String key, String value)
	{
		attributes.put(key, value);
	}
	public double sinDegrees(double input)
	{
		return Math.sin(Math.toRadians(input));
	}
	public double cosDegrees(double input)
	{
		return Math.cos(Math.toRadians(input));
	}
	public void destroy()
	{
		parent.removeChild(this);
	}
	
	public static ArrayList<Integer> roll(int dice, int sides, int bonus)
	{
		ArrayList<Integer> choices = new ArrayList<Integer>();
		for(int i = 1; i <= sides; i++)
		{
			choices.add(i);
		}
		ArrayList<ArrayList> rollLists = permute(choices, dice);
		ArrayList<Integer> totals = new ArrayList<Integer>();
		for(ArrayList rolls: rollLists)
		{
			int total = 0;
			for(int roll: (ArrayList<Integer>) rolls)
			{
				total += roll;
			}
			total += bonus;
			totals.add(total);
		}
		return totals;
	}
	public static ArrayList<ArrayList> permute(ArrayList source, int k)
	{
		ArrayList<Integer> indices = new ArrayList<Integer>(); //A list of indexes with length equal to k. A permutation set is formed by an object from the source set at each index specified in the list. Every time a permutation set is taken, the first index increases by one. When an index reaches the length of the source set, it resets back to 0 and index in front of it increases by one. When the last index reaches the length of the source set, the method returns the result.
		for(int i = 0; i < k; i++)
		{
			indices.add(0);
		}
		ArrayList<ArrayList> result = new ArrayList<ArrayList>();
		boolean active = true;
		int source_indexMax = source.size() - 1;
		
		
		while(active)
		{
			ArrayList<Object> permutation = new ArrayList<Object>();
			for(int i: indices)
			{
				Object item = source.get(i);
				permutation.add(item);
			}
			int firstIndex = indices.get(0);
			firstIndex += 1;
			indices.set(0, firstIndex);
			/*
			//Since numbers generally roll over only when the first index rolls over, wait until that happens
			if(firstIndex == source_indexMax)
			{
				indices.set(0, 0);
				int secondIndex = indices.get(1);
				secondIndex += 1;
				indices.set(1, secondIndex);
				for(int i = 1; i < source_indexMax; i++)
				{
					int index_i = indices.get(i);
					if(index_i > source_indexMax)
					{
						indices.set(i, 0);
						int i_next = i + 1;
						int nextIndex = indices.get(i_next);
						nextIndex += 1;
						indices.set(i_next, nextIndex);
					}
				}
				int indices_lastIndex = indices.size() - 1;
				int lastIndex = indices.get(indices_lastIndex);
				if(lastIndex > source_indexMax)
				{
					active = false;
				}
			}
			*/
			///*
			for(int i = 0; i < k - 1; i++)
			{
				int index_i = indices.get(i);
				if(index_i > source_indexMax)
				{
					indices.set(i, 0);
					int i_next = i + 1;
					int index_next = indices.get(i_next);
					index_next += 1;
					indices.set(i_next, index_next);
				}
			}
			int indices_lastIndex = indices.size() - 1;
			int lastIndex = indices.get(indices_lastIndex);
			if(lastIndex > source_indexMax)
			{
				active = false;
			}
			//*/
			result.add(permutation);
		}
		return result;
	}
}