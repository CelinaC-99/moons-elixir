import java.awt.Image;
import java.awt.Toolkit;

public class Item implements Comparable <Item>{

	private Image item;
	private String name;
	private int id;
	private String type;
	private int rarity;
	
	//constructor
	//parameters are item's name, id, type, and how rare they are (lower = rarer)
	public Item(String name, int id, String type, int rarity) {
		item = Toolkit.getDefaultToolkit().getImage("pictures/" + name + ".png");
		this.name = name;
		this.id = id;
		this.type = type;
		this.rarity = rarity;
	}
	
	//getters
	public Image getImage() {
		return item;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getRarity() {
		return rarity;
	}
	
	//setters
	public void setRarity(int rarity) {
		this.rarity = rarity;
	}
	
	//compareTo for sorting (via id, ascending order)
	public int compareTo(Item i) {
		return this.id - i.id;
	}
}
