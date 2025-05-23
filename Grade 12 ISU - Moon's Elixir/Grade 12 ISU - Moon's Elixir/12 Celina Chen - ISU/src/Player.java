import java.awt.*;

public class Player {
	
	private String gender;
	private Image[] playerUp = new Image[9];
	private Image[] playerDown = new Image[9];
	private Image[] playerLeft = new Image[9];
	private Image[] playerRight = new Image[9];
	private int timer = 0;
	private int imageNum;
	private String direction;
	private Item holding;
	private int holdingSlot;
	private Item[] inventory = new Item[36];
		
	
	//constructor
	//parameter is player's selected gender
	public Player(String gender) {
		this.gender = gender;
		
		//updates arrays w/ images
		for (int i = 0; i != 9; i++)
			playerUp[i] = Toolkit.getDefaultToolkit().getImage("pictures/" + gender + "up" + i + ".png");
		for (int i = 0; i != 9; i++)
			playerDown[i] = Toolkit.getDefaultToolkit().getImage("pictures/" + gender + "down" + i + ".png");
		for (int i = 0; i != 9; i++)
			playerLeft[i] = Toolkit.getDefaultToolkit().getImage("pictures/" + gender + "left" + i + ".png");
		for (int i = 0; i != 9; i++)
			playerRight[i] = Toolkit.getDefaultToolkit().getImage("pictures/" + gender + "right" + i + ".png");
		
		imageNum = 0;
		direction = "down";
		holdingSlot = 0;
	}
	
	//getters
	public String getGender() {
		return gender;
	}
	
	public Image getImage() {
		if (direction.equals("up"))
			return playerUp[imageNum];
		else if (direction.equals("down"))
			return playerDown[imageNum];
		else if (direction.equals("left"))
			return playerLeft[imageNum];
		else
			return playerRight[imageNum];
	}
	
	public int getHoldingSlot() {
		return holdingSlot;
	}
	
	public Item getHolding() {
		return holding;
	}
	
	public Item[] getInventory() {
		return inventory;
	}
	
	//setters
	public void setImage(String dir) {
		direction = dir;
		
		timer++;
		if(timer%6 == 0)
			imageNum++;
		if (imageNum == 9)
			imageNum = 0;
	}
	
	public void setDirection(String dir) {
		direction = dir;
	}
	
	public void setHoldingSlot(int n) {
		holdingSlot = n;
		holding = inventory[n];
	}
	
	public void setGender(String gender) {
		this.gender = gender;
		
		for (int i = 0; i != 9; i++)
			playerUp[i] = Toolkit.getDefaultToolkit().getImage("pictures/" + gender + "up" + i + ".png");
		for (int i = 0; i != 9; i++)
			playerDown[i] = Toolkit.getDefaultToolkit().getImage("pictures/" + gender + "down" + i + ".png");
		for (int i = 0; i != 9; i++)
			playerLeft[i] = Toolkit.getDefaultToolkit().getImage("pictures/" + gender + "left" + i + ".png");
		for (int i = 0; i != 9; i++)
			playerRight[i] = Toolkit.getDefaultToolkit().getImage("pictures/" + gender + "right" + i + ".png");
	}
	
	//this is to reset the player's image to the standing one if they stop moving
	public void stop() {
		timer = 0;
		imageNum = 0;
		setImage(direction);
	}
	
	//adds an item to the player's inventory
	public void addToInventory(Item item) {
		//if inventory is full, item will not be added... it poofs :(!!
		for(int i = 0; i != inventory.length; i++) {
			if(inventory[i] == null) {
				inventory[i] = item;
				break;
			}
		}	
		//updates holding slot
		setHoldingSlot(holdingSlot);
	}
	

}
