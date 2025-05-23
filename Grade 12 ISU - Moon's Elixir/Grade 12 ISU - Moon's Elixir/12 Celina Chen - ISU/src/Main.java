//Celina Chen
//June 17th 2024 :(
//game where you do cool witch things

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

@SuppressWarnings("serial")
public class Main extends JPanel implements Runnable, KeyListener, MouseListener {
	
	int screenWidth = 1000;
	int screenHeight = 600;
	Thread thread;
	int FPS = 60;
	
	int currentMap = 0;
	Map[] maps = new Map[5];
	
	boolean up, down, left, right;
	int speed = 2;
	Player player;
	int playerWidth = 33; //33 ; 25
	int playerHeight = 59; //59 ; 45
	Rectangle hitbox = new Rectangle(552, 300, 23, 23);
	
	//npc stuff
	Player nepenthe;
	Image nepentheIcon;
	int nepentheText = 0;
	int nepenthex = 547;
	int nepenthey = 265;
	
	// to see if any of the buttons are being held, so program can draw their pressed versions
	boolean startButtonHeld = false;
	boolean aboutButtonHeld = false;
	boolean instructionsButtonHeld = false;
	
	//to return back to home page or wtv
	boolean pageOpen = false;
	
	//to see if the player is allowed to move
	boolean allowMove = false;
	
	//this is for the selecting your avatar screen
	boolean characterSelect = false;
	//beginning cutscene
	boolean beginningCutscene = false;
	//end of day cutscene
	boolean endDay = false;
	//to know that the game has started
	boolean gameStart = false;
	
	//to let the graphcis thingy know if the user searched in the water
	boolean spawnStart = false;
	//timer for spawn text so you cant just spam it meow
	int spawnTimer = 0;
	//number to know wwhat text to write... in generate method but ised to spawn hraphcis
	int spawnText = 0;
	//Item to display what the player got from the water rahh
	Item spawnItemHolder;
	
	
	//timer for spawning items on map3
	int spawnTimer2 = 0;
	//stores what item is spawned at each spawn location in map3
	Item[] currentSpawn = new Item[5];
	
	//day number
	int day = 1;
	
	//hermm... interface variables
	boolean showDay;
	boolean showHotbar;
	boolean showInventory;
	
	//for deleting items in inventory
	boolean delete;
	
	//for adding ingredients into the cauldron for potion making
	Item[] cauldron = new Item[4];
	Item result;
	
	//the transparency for the black screen (fade in/out)
	int black = 0;
	//to determine if game needs to fade in or fade out
	boolean fadeIn = false;
	boolean fadeOut = false;
	
	//for win screen
	int white = 0;
	
	//holds all the items of the game
	ArrayList<Item> itemList = new ArrayList<Item>();
	//hold all the recipes of the game
	Recipe[] recipeList = new Recipe[6];
	
	//luck stat hum hum
	int luck = 0;
	
	//pink screen yay
	int pink = 0;
	
	//winning n losing
	boolean win;
	boolean lose;
	
	//audio
	Clip background, click;
	
	// x and y coordinates of mouse
	int x, y;
	
	public Main() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		initialize();
		while(true) {
			//main game loop
			update();
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//no parameters or return
	//initializes stuff 
	public void initialize() {
		//setups before the game starts running
		
		maps[0] = new Map("map0");
		
		//creates maps and walls
		maps[1] = new Map("map1");
		maps[1].createWall(0, 0, 285, 285);
		maps[1].createWall(225, 285, 60, 45);
		maps[1].createWall(285, 0, 100, 345);
		maps[1].createWall(385, 0, 275, 295);
		maps[1].createWall(385, 295, 80, 55);
		maps[1].createWall(385, 295, 80, 55);
		maps[1].createWall(465, 325, 55, 15);
		maps[1].createWall(465, 340, 65, 10);
		maps[1].createWall(645, 295, 15, 30);	
		maps[1].createWall(605, 325, 55, 15);
		maps[1].createWall(590, 340, 70, 10);
		maps[1].createWall(660, 0, 340, 375);
		maps[1].createWall(685, 375, 315, 225);
		maps[1].createWall(280, 415, 405, 185);
		maps[1].createWall(260, 445, 20, 155);
		maps[1].createWall(235, 470, 25, 130);
		maps[1].createWall(215, 520, 20, 80);
		maps[1].createWall(205, 570, 10, 30);
		maps[1].createWall(0, 500, 145, 100);
		maps[1].createWall(0, 375, 180, 125);
		maps[1].createWall(0, 335, 120, 40);		
		
		maps[2] = new Map("map2");
		maps[2].createWall(0, 0, 540, 335);
		maps[2].createWall(540, 0, 20, 320);
		maps[2].createWall(560, 0, 20, 305);
		maps[2].createWall(580, 0, 20, 255);
		maps[2].createWall(600, 0, 5, 230);
		maps[2].createWall(605, 0, 20, 210);
		maps[2].createWall(625, 0, 375, 185);
		maps[2].createWall(710, 185, 290, 35);
		maps[2].createWall(670, 220, 330, 30);
		maps[2].createWall(630, 250, 370, 25);
		maps[2].createWall(620, 275, 380, 20);
		maps[2].createWall(620, 295, 300, 20);
		maps[2].createWall(615, 315, 160, 20);
		maps[2].createWall(620, 335, 60, 20);
		maps[2].createWall(645, 355, 15, 40);
		maps[2].createWall(0, 335, 475, 265);
		maps[2].createWall(475, 365, 50, 235);
		maps[2].createWall(525, 435, 30, 165);
		maps[2].createWall(555, 445, 55, 155);
		maps[2].createWall(610, 460, 55, 140);
		maps[2].createWall(665, 450, 90, 150);
		maps[2].createWall(755, 430, 20, 170);
		maps[2].createWall(775, 420, 30, 180);
		maps[2].createWall(805, 410, 25, 190);
		maps[2].createWall(830, 390, 25, 210);
		maps[2].createWall(855, 365, 145, 235);
		
		maps[3] = new Map("map3");
		maps[3].createWall(0, 0, 360, 270);
		maps[3].createWall(360, 0, 30, 180);
		maps[3].createWall(390, 0, 25, 210);
		maps[3].createWall(415, 0, 30, 165);
		maps[3].createWall(445, 0, 105, 160);
		maps[3].createWall(550, 0, 25, 210);
		maps[3].createWall(575, 0, 115, 165);
		maps[3].createWall(655, 165, 55, 70);
		maps[3].createWall(690, 0, 105, 165);
		maps[3].createWall(740, 165, 55, 35);
		maps[3].createWall(795, 0, 30, 115);
		maps[3].createWall(900, 0, 100, 85);
		maps[3].createWall(885, 85, 115, 515);
		maps[3].createWall(860, 140, 25, 45);
		maps[3].createWall(850, 185, 35, 75);
		maps[3].createWall(845, 295, 40, 305);
		maps[3].createWall(640, 325, 205, 275);
		maps[3].createWall(680, 305, 95, 20);
		maps[3].createWall(575, 355, 65, 245);
		maps[3].createWall(520, 375, 55, 225);
		maps[3].createWall(500, 405, 20, 195);
		maps[3].createWall(510, 395, 10, 10);
		maps[3].createWall(525, 355, 20, 20);
		maps[3].createWall(390, 440, 110, 160);
		maps[3].createWall(360, 445, 30, 155);
		maps[3].createWall(0, 375, 360, 225);
		maps[3].createWall(0, 270, 335, 105);
		maps[3].createWall(335, 370, 5, 5);
		
		maps[4] = new Map("map4");
		
		//creates spawn areas
		maps[1].createSpawnArea(545, 295, 35, 5);
		
		maps[2].createSpawnArea(475, 335, 65, 5);
		maps[2].createSpawnArea(540, 320, 20, 10);
		maps[2].createSpawnArea(560, 305, 25, 10);
		maps[2].createSpawnArea(580, 255, 5, 50);
		maps[2].createSpawnArea(580, 255, 20, 10);
		maps[2].createSpawnArea(600, 230, 10, 25);
		maps[2].createSpawnArea(605, 210, 5, 20);
		maps[2].createSpawnArea(605, 210, 20, 5);
		maps[2].createSpawnArea(625, 185, 5, 25);
		
		maps[3].createSpawnArea(365, 200, 20, 20);
		maps[3].createSpawnArea(585, 185, 20, 20);
		maps[3].createSpawnArea(465, 265, 20, 20);
		maps[3].createSpawnArea(600, 330, 20, 20);
		maps[3].createSpawnArea(375, 410, 20, 20);
		
		//creates the NPC
		nepenthe = new Player("nepenthe");
		
		//creates all the items
		//potions vvv
		itemList.add(new Item("moonPotion", 1, "potion", -100));
		itemList.add(new Item("luckPotion", 2, "potion", -100)); 
		itemList.add(new Item("speedPotion", 3, "potion", -100));
		itemList.add(new Item("pinkPotion", 4, "potion", -100));
		itemList.add(new Item("genderPotion", 5, "potion", -100));
		itemList.add(new Item("smallPotion", 6, "potion", -100));
		//items that spawn on map2 vvv
		itemList.add(new Item("coal", 7, "ingredient", 6));
		itemList.add(new Item("copper", 8, "ingredient", 6));
		itemList.add(new Item("silver", 9, "ingredient", 5));
		itemList.add(new Item("gold", 10, "ingredient", 5));
		itemList.add(new Item("aquamarine", 11, "ingredient", 5));
		itemList.add(new Item("sapphire", 12, "ingredient", 5));
		itemList.add(new Item("blue fish", 13, "ingredient", 5));
		itemList.add(new Item("orange fish", 14, "ingredient", 5));
		itemList.add(new Item("neon fish", 15, "ingredient", 4));
		itemList.add(new Item("king fish", 16, "ingredient", 4));
		itemList.add(new Item("frog", 17, "ingredient", 4));
		itemList.add(new Item("bone", 18, "ingredient", 3));
		itemList.add(new Item("skull", 19, "ingredient", 3));
		itemList.add(new Item("moon", 20, "ingredient", 2));
		itemList.add(new Item("sun", 21, "ingredient", 2));
		//items that spawn on map3 vvv
		itemList.add(new Item("apple", 22, "ingredient", 5));
		itemList.add(new Item("citrus", 23, "ingredient", 3));
		itemList.add(new Item("berry", 24, "ingredient", 3));
		itemList.add(new Item("pumpkin", 25, "ingredient", 4));
		itemList.add(new Item("beetroot", 26, "ingredient", 5));
		itemList.add(new Item("radish", 27, "ingredient", 5));
		itemList.add(new Item("leek", 28, "ingredient", 4));
		itemList.add(new Item("mushroom", 29, "ingredient", 4));
		itemList.add(new Item("butterfly", 30, "ingredient", 4));
		itemList.add(new Item("blue flower", 31, "ingredient", 3));
		itemList.add(new Item("pink flower", 32, "ingredient", 3));
		itemList.add(new Item("purple flower", 33, "ingredient", 2));
		itemList.add(new Item("demon", 34, "ingredient", 2));
		
		//adds the recipes
		//gender
		recipeList[0] = new Recipe(itemList.get(4), itemList.get(6), itemList.get(7), itemList.get(10), itemList.get(11));
		//pink
		recipeList[1] = new Recipe(itemList.get(3), itemList.get(20), itemList.get(23), itemList.get(29), itemList.get(31));
		//speed
		recipeList[2] = new Recipe(itemList.get(2), itemList.get(9), itemList.get(15), itemList.get(21), itemList.get(22));
		//small
		recipeList[3] = new Recipe(itemList.get(5), itemList.get(12), itemList.get(16), itemList.get(17), itemList.get(28));
		//luck
		recipeList[4] = new Recipe(itemList.get(1), itemList.get(14), itemList.get(24), itemList.get(25), itemList.get(30));
		//moon's elixir
		recipeList[5] = new Recipe(itemList.get(0), itemList.get(8), itemList.get(18), itemList.get(19), itemList.get(32));
		
		//sound input
		try {
			//bg music
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File ("pictures/background music.wav"));
			background = AudioSystem.getClip();
			background.open(sound);			
			background.setFramePosition (0);
			background.loop(Clip.LOOP_CONTINUOUSLY);
			
			//click sfx
			sound = AudioSystem.getAudioInputStream(new File ("pictures/click.wav"));
			click = AudioSystem.getClip();
			click.open(sound);
		} 
		catch (Exception e) {
		}
		
	}
	
	//no parameters or return
	//for updating game
	public void update() {
		//movement methods if user is allowed to move
		if (allowMove) {
			move();
			keepInBound();
			for(int i = 0; i < maps[currentMap].getWalls().size(); i++)
				checkCollision(maps[currentMap].getWalls().get(i));
			updateMap();
		}
		//if game has started
		if (gameStart) {
			//this is to spawn the items in map3 every 40 seconds
			if(spawnTimer2%2400 == 0) {
				for(int i = 0; i != maps[3].getSpawnAreas().size(); i++)
					currentSpawn[i] = itemSpawn(21, 33);
			}
			spawnTimer2++;
						
			//also using spawnTimer2 for the day time cuz im lazy
			if(spawnTimer2%6000 == 5999 && endDay)
				spawnTimer2--;
			
			//darkens map to tell the user the day is ending soon
			if(spawnTimer2%6000 > 4000 && spawnTimer2%100 == 0 && !fadeIn && !fadeOut) {
				black+=5;
			}
			//freezes game when day is at end
			if(spawnTimer2%6000 == 5998) {
				allowMove = false;
				showHotbar = false;
				showInventory = false;
				endDay = true;
				
				//this resets some variables in case user is searching in water when day ends
				spawnTimer = 0;
				spawnText = 0;
				spawnStart = false;
			}
			//1 day = 100 secs
			if(spawnTimer2%6000 == 0 && !fadeOut) {
				day++;
				fadeIn = true;
			}
			//moving onto new day and resetting variables n stuff
			if(black == 255 && !fadeOut && endDay && !fadeIn) {
				endDay = false;
				
				//need to put player position here
				currentMap = 1;
				hitbox.x = 552;
				hitbox.y = 295;
				player.setDirection("down");
				
				//resets variables in case they were in the middle of making smth
				for(int i = 0; i != 4; i++) {
					if(cauldron[i] != null) {
						player.addToInventory(cauldron[i]);
						cauldron[i] = null;
					}
				}
				result = null;
			
				//need to reset these vairables
				allowMove = true;
				showHotbar = true;
				
				//RESET POTION HERE
				//resetting luck potion
				itemList.get(17).setRarity(itemList.get(17).getRarity()-luck);
				itemList.get(18).setRarity(itemList.get(18).getRarity()-luck);
				itemList.get(19).setRarity(itemList.get(19).getRarity()-luck);
				itemList.get(20).setRarity(itemList.get(20).getRarity()-luck);
					
				itemList.get(22).setRarity(itemList.get(22).getRarity()-luck);
				itemList.get(23).setRarity(itemList.get(23).getRarity()-luck);
					
				itemList.get(30).setRarity(itemList.get(30).getRarity()-luck);
				itemList.get(31).setRarity(itemList.get(31).getRarity()-luck);
				itemList.get(32).setRarity(itemList.get(32).getRarity()-luck);
				itemList.get(33).setRarity(itemList.get(33).getRarity()-luck);
				
				luck = 0;
				
				//resetting speed
				speed = 2;
				
				//resetting pink
				pink = 0;

				//resetting size
				playerWidth = 33;
				playerHeight = 59;
				hitbox.width = 23;
				hitbox.height = 23;
			}
			//this is if user runs out of time and loses
			if(day == 8) {
				gameStart = false;
				win = false;
				lose = true;
				
				reset();
			}
		}
	}

	//returns nothing, parameters is graphics to draw stuff
	//draws stuff sniff
	public void paintComponent(Graphics g) {
		
		//draws my map
		g.drawImage(maps[currentMap].getMap(), 0, 0, screenWidth, screenHeight, this);
		
		//draws my invisible walls
		g.setColor(new Color(255, 0, 0, 0));
		for(int i = 0; i < maps[currentMap].getWalls().size(); i++)
			((Graphics2D) g).draw(maps[currentMap].getWalls().get(i));
		
		//draws my invisible spawn areas
		g.setColor(new Color(0, 255, 0, 0));
		for(int i = 0; i < maps[currentMap].getSpawnAreas().size(); i++)
			((Graphics2D) g).draw(maps[currentMap].getSpawnAreas().get(i));

		//seperated into methods for organizaton
		if(characterSelect)
			characterSelectionScreen(g);
		else if(beginningCutscene)
			cutscene(g);
		
		//runs if game has started
		if(gameStart) {
			//if you wana show the # of days graphic
			if(showDay) {
				//insert the "day" graphic here
				Image dayBox = Toolkit.getDefaultToolkit().getImage("pictures/dayBox.png");
				g.drawImage(dayBox, 0, 0, 179, 60, this);
				g.setFont(new Font("Calisto MT", Font.BOLD, 30));
				g.setColor(new Color(85, 58, 30, 255));
				g.drawString("Day " + day, 45, 40);
			}
			
			//for showing the hotbar
			if(showHotbar) {
				Image hotbar = Toolkit.getDefaultToolkit().getImage("pictures/hotbar.png");
				g.drawImage(hotbar, 280, 500, 455, 60, this);
				//draws which slot the user is holding
				Image highlight = Toolkit.getDefaultToolkit().getImage("pictures/hotbarhighlight.png");
				g.drawImage(highlight, 302 + (player.getHoldingSlot()*47), 510, 37, 37, this);
				//47
				
				//displays item images
				for(int i = 0; i != 9; i++)
					if(player.getInventory()[i] != null)
						g.drawImage(player.getInventory()[i].getImage(), 310 + (i*47), 520, 20, 20, this);
					//IF INVENTORY IS FULL... the item you grab poofs
			}
			
			//shows inventory
			if(showInventory) {
				Image inventory = Toolkit.getDefaultToolkit().getImage("pictures/inventory.png");
				g.drawImage(inventory, 180, 125, 622, 350, this);
				
				//item images
				for(int i = 0; i != player.getInventory().length; i++) {
					if(player.getInventory()[i] != null)
						g.drawImage(player.getInventory()[i].getImage(), 244 + ((i%6) * 35), 195 + ((i/6) * 36), 25, 25, this);
				}
				
				//to show that youre in deletion mode
				if(delete) {
					g.setColor(new Color(255, 0, 0, 50));
					g.fillRect(225, 170, 245, 260);
				}
			}
			
			//potion making screen graphics
			if(currentMap == 4) {
				Image potionMakingImage = Toolkit.getDefaultToolkit().getImage("pictures/potionMakingImage.png");
				g.drawImage(potionMakingImage, 180, 125, 622, 350, this);
				//draws inventory
				for(int i = 0; i != player.getInventory().length; i++) {
					if(player.getInventory()[i] != null)
						g.drawImage(player.getInventory()[i].getImage(), 244 + ((i%6) * 35), 195 + ((i/6) * 36), 25, 25, this);
				}
				//draws the stuff you put in cauldron
				for(int i = 0; i != 4; i++) {
					if(cauldron[i] != null)
						g.drawImage(cauldron[i].getImage(), 565 + (i*35), 375, 25, 25, this);
				}
				//draws potion made
				if(result != null)
					g.drawImage(result.getImage(), 617, 250, 35, 35, this);
			}
			
			//this is for searching in the water
			if(spawnStart && !endDay) {
				Image textbox = Toolkit.getDefaultToolkit().getImage("pictures/textbox.png");
				g.drawImage(textbox, 300, 400, 396, 191, this);
				
				g.setFont(new Font("Calisto MT", Font.BOLD, 20));
				g.setColor(new Color(85, 58, 30, 255));
				
				if(spawnText == 0)
					g.drawString("You search around in the water.", 330, 440);
				if(spawnText == 1)
					g.drawString(".", 330, 440);
				if(spawnText == 2)
					g.drawString(". .", 330, 440);
				if(spawnText == 3)
					g.drawString(". . .", 330, 440);
				if(spawnText == 4) {
					if(spawnTimer == 400) {
						//spawns item
						spawnItemHolder = itemSpawn(6, 20);
						//adds item to player's inventory
						player.addToInventory(spawnItemHolder);
					}
					//text to show result
					if(spawnItemHolder != null)
						g.drawString("You obtained " + spawnItemHolder.getName() + "!", 330, 440);
					else
						g.drawString("You obtained nothing!", 330, 440);
				}
				//timers, resetting variables, etc
				spawnTimer++;
				if(spawnTimer%100 == 0)
					spawnText++;
				if(spawnText == 5) {
					spawnTimer = 0;
					spawnText = 0;
					spawnStart = false;
					showHotbar = true;
					allowMove = true;
				}		
			}
			//for map 3, grassy area
			if (currentMap == 3 && !showInventory) {
				//draws all the items spawned
				for(int i = 0; i != maps[currentMap].getSpawnAreas().size(); i++) {
					if(currentSpawn[i] != null)
						g.drawImage(currentSpawn[i].getImage(), maps[currentMap].getSpawnAreas().get(i).x, maps[currentMap].getSpawnAreas().get(i).y, 20, 20, this);
				}
			}
			
			//the cutscene that happens when day ends
			if(endDay) {
				endDayCutscene(g);
			}
		}
		
		//draws avatar
		if (!characterSelect && !beginningCutscene && !showInventory && (currentMap == 1 || currentMap == 2 || currentMap == 3)) {
			g.setColor(new Color(0, 0, 255, 0));
			g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
			g.drawImage(player.getImage(), hitbox.x - 5, hitbox.y - 35, playerWidth, playerHeight, this);
		}
		
		//pink screen
		g.setColor(new Color(252, 152, 205, pink));
		g.fillRect(0, 0, 1000, 600);
		
		//for fading in and out
		if(!beginningCutscene)
			fade();
		
		//use this for "night" and set a variable for transparency that increases based on frames?
		//but it'd have to be frames from when a new day "begins"
		g.setColor(new Color(0, 0, 0, black));
		g.fillRect(0, 0, 1000, 600);

		//losing screen
		if(lose) {
			if(black != 255 && !fadeOut)
				fadeOut = true;
			else if (black == 255){
				Image textbox = Toolkit.getDefaultToolkit().getImage("pictures/textbox.png");
				g.drawImage(textbox, 300, 400, 396, 191, this);
				
				g.setFont(new Font("Calisto MT", Font.BOLD, 20));
				g.setColor(new Color(85, 58, 30, 255));
				
				g.drawString("You ran out of time.", 330, 440);
			}
		}
		
		//winning screen
		if(win) {
			//fades to white
			g.setColor(new Color(255, 255, 255, white));
			g.fillRect(0, 0, 1000, 600);
			
			if(white != 255) {
				white+=5;
			}
			else if (white == 255){
				Image textbox = Toolkit.getDefaultToolkit().getImage("pictures/textbox.png");
				g.drawImage(textbox, 300, 400, 396, 191, this);
				
				Image potion = Toolkit.getDefaultToolkit().getImage("pictures/game icon.png");
				g.drawImage(potion, 400, 150, 200, 200, this);
				
				g.setFont(new Font("Calisto MT", Font.BOLD, 20));
				g.setColor(new Color(85, 58, 30, 255));
				
				g.drawString("Congratulations! You've made", 330, 440);
				g.drawString("\"Moon's Elixir\"!", 330, 465);
			}			
		}
	}
	
	//parameters graphics, returns nothing
	//just for organization, this is character selection screen
	public void characterSelectionScreen(Graphics g) {
		//sets the background to be black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenWidth, screenHeight);
		
		Image characterSelectionImage = Toolkit.getDefaultToolkit().getImage("pictures/characterSelectionImage.png");
		g.drawImage(characterSelectionImage, 175, 125, 665, 350, this);
		
		// Set font and color
		g.setFont(new Font("Baskerville Old Face", Font.BOLD, 35));
		g.setColor(new Color(85, 58, 30, 255));

        // Draw a string
		g.drawString("Select Your Character", 345, 240);
	}
	
	//parameters graphics, returns nothing
	//just for organization, this is the cutscene with npc
	public void cutscene(Graphics g){
		//draws avatars n stuff
		if (nepentheText < 15) {
			nepenthe.setDirection("left");
			player.setDirection("right");
		}
		g.drawImage(nepenthe.getImage(), nepenthex, nepenthey, 33, 59, this);
		g.drawImage(player.getImage(), 500, 265, 33, 59, this);
		
		//draws nepenthe's text
		if(!fadeIn && !fadeOut && nepentheText != 14) {
			Image npctextbox = Toolkit.getDefaultToolkit().getImage("pictures/npctextbox.png");
			g.drawImage(npctextbox, 200, 400, 600, 191, this);
			
			g.setFont(new Font("Calisto MT", Font.BOLD, 20));
			g.setColor(new Color(85, 58, 30, 255));
			g.drawString("Nepenthe", 268, 563);
			
			if(nepentheText == 0) {
				nepentheIcon = Toolkit.getDefaultToolkit().getImage("pictures/nepenthe0.png");
				g.drawString("Oh?", 430, 440);
			}
			else if(nepentheText == 1) {
				g.drawString("You’ve arrived so soon, Little Witch?", 430, 440);
			}
			else if(nepentheText == 2) {
				g.drawString("And I see you’re wearing the hat I", 430, 440);
				g.drawString("gifted you…", 430, 465);
			}
			else if(nepentheText == 3) {
				nepentheIcon = Toolkit.getDefaultToolkit().getImage("pictures/nepenthe1.png");
				g.drawString("How flattering~.", 430, 440);
			}
			else if(nepentheText == 4) {
				g.drawString("As you can see, it matches my own-", 430, 440);
				g.drawString("like a friendship bracelet.", 430, 465);
			}
			else if(nepentheText == 5) {
				g.drawString("A nice touch, no?", 430, 440);
			}
			else if(nepentheText == 6) {
				nepentheIcon = Toolkit.getDefaultToolkit().getImage("pictures/nepenthe0.png");
				g.drawString("Anyway, here are the keys to my spare", 430, 440);
				g.drawString("home. The cauldron’s in the kitchen.", 430, 465);
			}
			else if(nepentheText == 7) {
				g.drawString("...", 430, 440);
			}
			else if(nepentheText == 8){
				g.drawString("But...", 430, 440);
			}
			else if(nepentheText == 9){
				g.drawString("Are you sure you’ll be able to make", 430, 440);
				g.drawString("that potion in just seven days?", 430, 465);
			}
			else if(nepentheText == 10){
				g.drawString("It can be rather hard to create.", 430, 440);
			}
			else if(nepentheText == 11) {
				g.drawString("...", 430, 440);
			}
			else if(nepentheText == 12) {
				nepentheIcon = Toolkit.getDefaultToolkit().getImage("pictures/nepenthe1.png");
				g.drawString("Then again, you were always bright, so", 430, 440);
				g.drawString("I have faith in you.", 430, 465);
			}
			else if(nepentheText == 13) {
				g.drawString("Good luck, I hope everything goes to", 430, 440);
				g.drawString("your plan.", 430, 465);
			}
			g.drawImage(nepentheIcon, 259, 425, 110, 110, this);
		}
		
		//ending animation for cutscene
		if(nepentheText == 14) {
			fadeOut = true;
			nepentheText++;
		}
		else if(nepentheText >= 15) {
			nepenthey += 1;
			nepenthe.setImage("down");
		}
		
		if (nepentheText >= 15 && fadeOut == false && black == 255) {
			//ends cutscene
			beginningCutscene = false;
			//sets the player to looking down
			player.setDirection("down");
			//makes the screen fade in
			fadeIn = true;
			
			//starts game
			gameStart = true;
			//shows the day #
			showDay = true;
			//shows the hotbar
			showHotbar = true;
			//allows player to move
			allowMove = true;
		}
		//fade in out
		fade();	
	}
	
	//parameters graphics, returns nothing
	//just for organization, this is just a normal screen
	public void endDayCutscene(Graphics g){
		
		Image textbox = Toolkit.getDefaultToolkit().getImage("pictures/textbox.png");
		g.drawImage(textbox, 300, 400, 396, 191, this);
		
		g.setFont(new Font("Calisto MT", Font.BOLD, 20));
		g.setColor(new Color(85, 58, 30, 255));
		
		g.drawString("It's getting late, and you start to feel", 330, 440);
		g.drawString("sleepy. You should head to bed.", 330, 465);
			
		//in mousepressed:
		//if endDay, endDay = false
		//do a fade out fade in
		//reset character to starting spawn location
		//if spawnTimer2%6000 == 0, 
	}
	
	//parameters nothing, returns nothing
	//for fading in and out
	public void fade() {
		//for fading in
		if(fadeIn && black != 0)
			black-=5;
		//ends the fade in
		else if(fadeIn && black == 0)
			fadeIn = false;
		//for fading out
		else if (fadeOut && black != 255)
			black+=5;
		//ends fade out
		else if (fadeOut && black == 255)
			fadeOut = false;
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		//sound
		click.setFramePosition (0);
		click.start ();
		
		// gets the x and y coordinates of the mouse
		x = e.getX ();
		y = e.getY ();
		
		
		//updates map but special cuz hoemscreen
		if (currentMap == 0) {
			//start button stuff
			if (x >= 725 && x <= 940 && y >= 45 && y <= 100) {
				System.out.println("start");
				startButtonHeld = true;
				maps[currentMap].setMap("map0start");
			}
			//about button stuff
			else if (x >= 725 && x <= 940 && y >= 185 && y <= 240) {
				aboutButtonHeld = true;
				maps[currentMap].setMap("map0about");
			}
			//instructons buttons stuff
			else if (x >= 725 && x <= 940 && y >= 320 && y <= 375) {
				instructionsButtonHeld = true;
				maps[currentMap].setMap("map0instructions");
			}
			//for closing pages
			if(pageOpen) {
				maps[currentMap].setMap("map0");
				pageOpen = false;
			}
			
		}
		
		//to handle character selection screen stuff
		if(characterSelect) {
			if(x >= 385 && x <= 445 && y >= 280 && y <= 390)
				player = new Player("male");
			else if(x >= 570 && x <= 630 && y >= 280 && y <= 390)
				player = new Player("female");
		}
		
		//for handing npc speech
		if (beginningCutscene && !fadeIn && nepentheText != 14) {
			nepentheText++;
		}
		
		//invnetory interface
		if(showInventory && !endDay) {
			//sorts items
			if(x >= 605 && x <= 665 && y >= 205 && y <= 265) {
				Arrays.sort(player.getInventory(), new Sort());
			}	
			//turns on delete mode
			else if(x >= 605 && x <= 665 && y >= 325 && y <= 385 && !delete)
				delete = true;
			//turns off delete mode
			else if(x >= 605 && x <= 665 && y >= 325 && y <= 385 && delete)
				delete = false;
		}
		
		//deleting interface
		if(delete && !endDay) {
			//these are just a heck ton of coordinates cuz theres 36 slots
			if (x >= 244 && x <= 244+25 && y >= 195 && y <= 195+25)
				player.getInventory()[0] = null;
			else if (x >= 279 && x <= 279+25 && y >= 195 && y <= 195+25)
				player.getInventory()[1] = null;
			else if (x >= 314 && x <= 314+25 && y >= 195 && y <= 195+25)
				player.getInventory()[2] = null;
			else if (x >= 349 && x <= 349+25 && y >= 195 && y <= 195+25)
				player.getInventory()[3] = null;
			else if (x >= 384 && x <= 384+25 && y >= 195 && y <= 195+25)
				player.getInventory()[4] = null;
			else if (x >= 419 && x <= 419+25 && y >= 195 && y <= 195+25)
				player.getInventory()[5] = null;
			
			else if (x >= 244 && x <= 244+25 && y >= 231 && y <= 231+25)
				player.getInventory()[6] = null;
			else if (x >= 279 && x <= 279+25 && y >= 231 && y <= 231+25)
				player.getInventory()[7] = null;
			else if (x >= 314 && x <= 314+25 && y >= 231 && y <= 231+25)
				player.getInventory()[8] = null;
			else if (x >= 349 && x <= 349+25 && y >= 231 && y <= 231+25)
				player.getInventory()[9] = null;
			else if (x >= 384 && x <= 384+25 && y >= 231 && y <= 231+25)
				player.getInventory()[10] = null;
			else if (x >= 419 && x <= 419+25 && y >= 231 && y <= 231+25)
				player.getInventory()[11] = null;
			
			else if (x >= 244 && x <= 244+25 && y >= 267 && y <= 267+25)
				player.getInventory()[12] = null;
			else if (x >= 279 && x <= 279+25 && y >= 267 && y <= 267+25)
				player.getInventory()[13] = null;
			else if (x >= 314 && x <= 314+25 && y >= 267 && y <= 267+25)
				player.getInventory()[14] = null;
			else if (x >= 349 && x <= 349+25 && y >= 267 && y <= 267+25)
				player.getInventory()[15] = null;
			else if (x >= 384 && x <= 384+25 && y >= 267 && y <= 267+25)
				player.getInventory()[16] = null;
			else if (x >= 419 && x <= 419+25 && y >= 267 && y <= 267+25)
				player.getInventory()[17] = null;
			
			else if (x >= 244 && x <= 244+25 && y >= 303 && y <= 303+25)
				player.getInventory()[18] = null;
			else if (x >= 279 && x <= 279+25 && y >= 303 && y <= 303+25)
				player.getInventory()[19] = null;
			else if (x >= 314 && x <= 314+25 && y >= 303 && y <= 303+25)
				player.getInventory()[20] = null;
			else if (x >= 349 && x <= 349+25 && y >= 303 && y <= 303+25)
				player.getInventory()[21] = null;
			else if (x >= 384 && x <= 384+25 && y >= 303 && y <= 303+25)
				player.getInventory()[22] = null;
			else if (x >= 419 && x <= 419+25 && y >= 303 && y <= 303+25)
				player.getInventory()[23] = null;
			
			else if (x >= 244 && x <= 244+25 && y >= 339 && y <= 339+25)
				player.getInventory()[24] = null;
			else if (x >= 279 && x <= 279+25 && y >= 339 && y <= 339+25)
				player.getInventory()[25] = null;
			else if (x >= 314 && x <= 314+25 && y >= 339 && y <= 339+25)
				player.getInventory()[26] = null;
			else if (x >= 349 && x <= 349+25 && y >= 339 && y <= 339+25)
				player.getInventory()[27] = null;
			else if (x >= 384 && x <= 384+25 && y >= 339 && y <= 339+25)
				player.getInventory()[28] = null;
			else if (x >= 419 && x <= 419+25 && y >= 339 && y <= 339+25)
				player.getInventory()[29] = null;
			
			else if (x >= 244 && x <= 244+25 && y >= 375 && y <= 375+25)
				player.getInventory()[30] = null;
			else if (x >= 279 && x <= 279+25 && y >= 375 && y <= 375+25)
				player.getInventory()[31] = null;
			else if (x >= 314 && x <= 314+25 && y >= 375 && y <= 375+25)
				player.getInventory()[32] = null;
			else if (x >= 349 && x <= 349+25 && y >= 375 && y <= 375+25)
				player.getInventory()[33] = null;
			else if (x >= 384 && x <= 384+25 && y >= 375 && y <= 375+25)
				player.getInventory()[34] = null;
			else if (x >= 419 && x <= 419+25 && y >= 375 && y <= 375+25)
				player.getInventory()[35] = null;
		}
		
		//more coordinates but this time for potion making
		if (currentMap == 4 && !endDay) {		
			//inventory section
			int n = -1;
			
			if (x >= 244 && x <= 244+25 && y >= 195 && y <= 195+25)
				n = 0;
			else if (x >= 279 && x <= 279+25 && y >= 195 && y <= 195+25)
				n = 1;
			else if (x >= 314 && x <= 314+25 && y >= 195 && y <= 195+25)
				n = 2;
			else if (x >= 349 && x <= 349+25 && y >= 195 && y <= 195+25)
				n = 3;
			else if (x >= 384 && x <= 384+25 && y >= 195 && y <= 195+25)
				n = 4;
			else if (x >= 419 && x <= 419+25 && y >= 195 && y <= 195+25)
				n = 5;
			
			else if (x >= 244 && x <= 244+25 && y >= 231 && y <= 231+25)
				n = 6;
			else if (x >= 279 && x <= 279+25 && y >= 231 && y <= 231+25)
				n = 7;
			else if (x >= 314 && x <= 314+25 && y >= 231 && y <= 231+25)
				n = 8;
			else if (x >= 349 && x <= 349+25 && y >= 231 && y <= 231+25)
				n = 9;
			else if (x >= 384 && x <= 384+25 && y >= 231 && y <= 231+25)
				n = 10;
			else if (x >= 419 && x <= 419+25 && y >= 231 && y <= 231+25)
				n = 11;
			
			else if (x >= 244 && x <= 244+25 && y >= 267 && y <= 267+25)
				n = 12;
			else if (x >= 279 && x <= 279+25 && y >= 267 && y <= 267+25)
				n = 13;
			else if (x >= 314 && x <= 314+25 && y >= 267 && y <= 267+25)
				n = 14;
			else if (x >= 349 && x <= 349+25 && y >= 267 && y <= 267+25)
				n = 15;
			else if (x >= 384 && x <= 384+25 && y >= 267 && y <= 267+25)
				n = 16;
			else if (x >= 419 && x <= 419+25 && y >= 267 && y <= 267+25)
				n = 17;
			
			else if (x >= 244 && x <= 244+25 && y >= 303 && y <= 303+25)
				n = 18;
			else if (x >= 279 && x <= 279+25 && y >= 303 && y <= 303+25)
				n = 19;
			else if (x >= 314 && x <= 314+25 && y >= 303 && y <= 303+25)
				n = 20;
			else if (x >= 349 && x <= 349+25 && y >= 303 && y <= 303+25)
				n = 21;
			else if (x >= 384 && x <= 384+25 && y >= 303 && y <= 303+25)
				n = 22;
			else if (x >= 419 && x <= 419+25 && y >= 303 && y <= 303+25)
				n = 23;
			
			else if (x >= 244 && x <= 244+25 && y >= 339 && y <= 339+25)
				n = 24;
			else if (x >= 279 && x <= 279+25 && y >= 339 && y <= 339+25)
				n = 25;
			else if (x >= 314 && x <= 314+25 && y >= 339 && y <= 339+25)
				n = 26;
			else if (x >= 349 && x <= 349+25 && y >= 339 && y <= 339+25)
				n = 27;
			else if (x >= 384 && x <= 384+25 && y >= 339 && y <= 339+25)
				n = 28;
			else if (x >= 419 && x <= 419+25 && y >= 339 && y <= 339+25)
				n = 29;
			
			else if (x >= 244 && x <= 244+25 && y >= 375 && y <= 375+25)
				n = 30;
			else if (x >= 279 && x <= 279+25 && y >= 375 && y <= 375+25)
				n = 31;
			else if (x >= 314 && x <= 314+25 && y >= 375 && y <= 375+25)
				n = 32;
			else if (x >= 349 && x <= 349+25 && y >= 375 && y <= 375+25)
				n = 33;
			else if (x >= 384 && x <= 384+25 && y >= 375 && y <= 375+25)
				n = 34;
			else if (x >= 419 && x <= 419+25 && y >= 375 && y <= 375+25)
				n = 35;
			
			if(n > -1) {
				//places the item clicked into the "cauldron" as long as its not a potion
				if(player.getInventory()[n].getType().equals("ingredient")) {
					for(int i = 0; i != cauldron.length; i++) {
						if(cauldron[i] == null) {
							//adds to cauldron
							cauldron[i] = player.getInventory()[n];
							//removed from inventory
							player.getInventory()[n] = null;
							break;
						}
					}
				}
			}
			
			//for removing the stuff you put in cauldron
			if(x >= 565 && x <= 590 && y >= 375 && y <= 400) {
				player.addToInventory(cauldron[0]);
				cauldron[0] = null;
			}
			else if(x >= 565+35 && x <= 590+35 && y >= 375 && y <= 400) {
				player.addToInventory(cauldron[1]);
				cauldron[1]= null;
			}
			else if(x >= 565+70 && x <= 590+70 && y >= 375 && y <= 400) {
				player.addToInventory(cauldron[2]);
				cauldron[2] = null;
			}
			else if(x >= 565+105 && x <= 590+105 && y >= 375 && y <= 400) {
				player.addToInventory(cauldron[3]);
				cauldron[3] = null;
			}
			
			//to check if youve made anything
			if(cauldron[3] != null)
				checkRecipe();
			
			//to take the potion youve made
			if(result != null && x >= 610 && x <= 660 && y >= 240 && y <= 290) {
				player.addToInventory(result);
				result = null;
				for(int i = 0; i != 4; i++)
					cauldron[i] = null;
			}
			
			//to exit out of the potion making screen
			if (x <= 180 || x >= 802 || y <= 125 || y >= 475) {
				//restting variables n stuff
				for(int i = 0; i != 4; i++) {
					if(cauldron[i] != null) {
						player.addToInventory(cauldron[i]);
						cauldron[i] = null;
					}
				}
				result = null;
				currentMap = 1;
				hitbox.x = 552;
				hitbox.y = 295;
				player.setDirection("down");
				showHotbar = true;
				allowMove = true;
			}
			
			//click on the inv to add ingre to the potion array
			//then call a checkrecepie method
			//in check creipe method, check all the recipes to see
			//if anythgin was made
			//have vairable for it
			//if yes, display the image + hav click thing
			//if click on square n vairable (id?) is not 0, then add to inv
			//and then clear potion array and reset vairable to 0
		}
		
		//animation stuff for end of the day
		if(endDay) {
			fadeOut = true;
		}
		
		//for resetting game after losing or winning
		if((lose && black == 255) || win) {
			lose = false;
			win = false;
			black = 0;
			white = 0;
			currentMap = 0;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// if the start button was pressed, now that the mouse is released...
		if (startButtonHeld) {
			// sets the button held variable to false
			startButtonHeld = false;
			//shifts the home screen back to normal
			maps[currentMap].setMap("map0");
			// status is 1 to set the image to gameScreen
			currentMap = 1;
			//moves to character selection screen
			characterSelect = true;
			
			return;
		}
		else if (aboutButtonHeld) {
			// sets the button held variable to false
			aboutButtonHeld = false;
			//shifts the home screen back to about page
			maps[currentMap].setMap("aboutPage");
			//for returning back to start
			pageOpen = true;
			return;
		}
		else if (instructionsButtonHeld) {
			// sets the button held variable to false
			instructionsButtonHeld = false;
			//shifts the home screen back to normal
			maps[currentMap].setMap("instructionsPage");
			//for returning back to start
			pageOpen = true;
			
			return;
		}
		
		if(characterSelect) {
			//signifies that the character has been selected and beginning cutscene is starting
			if(x >= 385 && x <= 445 && y >= 280 && y <= 390) {
				characterSelect = false;
				fadeIn = true;
				black = 255;
				beginningCutscene = true;
			}
			//same thing as above but for diff gender
			else if(x >= 570 && x <= 630 && y >= 280 && y <= 390) {
				characterSelect = false;
				fadeIn = true;
				black = 255;
				beginningCutscene = true;
			}		
		}
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		//movement rahhh
		if(key == KeyEvent.VK_A) {
			left = true;
			right = false;
		}else if(key == KeyEvent.VK_D) {
			right = true;
			left = false;
		}else if(key == KeyEvent.VK_W) {
			up = true;
			down = false;
		}else if(key == KeyEvent.VK_S) {
			down = true;
			up = false;
		}
		
		//hotbar switching slots rahhh
		if(showHotbar) {
			if (key == KeyEvent.VK_1)
				player.setHoldingSlot(0);
			else if (key == KeyEvent.VK_2)
				player.setHoldingSlot(1);
			else if (key == KeyEvent.VK_3)
				player.setHoldingSlot(2);
			else if (key == KeyEvent.VK_4)
				player.setHoldingSlot(3);
			else if (key == KeyEvent.VK_5)
				player.setHoldingSlot(4);
			else if (key == KeyEvent.VK_6)
				player.setHoldingSlot(5);
			else if (key == KeyEvent.VK_7)
				player.setHoldingSlot(6);
			else if (key == KeyEvent.VK_8)
				player.setHoldingSlot(7);
			else if (key == KeyEvent.VK_9)
				player.setHoldingSlot(8);
		}
		
		//opens n closes inventory
		if(key == KeyEvent.VK_F && gameStart) {
			//closes inventory
			if(showInventory) {
				showHotbar = true;
				showInventory = false;
				allowMove = true;
				delete = false;
			}
			//opens inventory
			else if(!showInventory && !spawnStart && currentMap != 4) {
				showHotbar = false;
				showInventory = true;
				allowMove = false;
			}
		}
		
		//"interacts" with map
		//aka grabbing spawn stuff, opening doors...
		if(key == KeyEvent.VK_E && gameStart && !showInventory && !endDay) {
			if(checkTouchingSpawnArea() > -1 && currentMap == 1) {
				showHotbar = false;
				allowMove = false;
				currentMap = 4;
			}
			if(checkTouchingSpawnArea() > -1 && currentMap == 2) {
				showHotbar = false;
				allowMove = false;
				spawnStart = true;
			}
			if(checkTouchingSpawnArea() > -1 && currentMap == 3) {
				if (currentSpawn[checkTouchingSpawnArea()] != null) {
					player.addToInventory(currentSpawn[checkTouchingSpawnArea()]);
					currentSpawn[checkTouchingSpawnArea()] = null;
				}
				else
					player.addToInventory(null);
			}
		}
		
		//this is to drink potions and then their effect is implemented
		if(key == KeyEvent.VK_C) {
			if(player.getHolding().getType().equals("potion")) {
				String potionName = player.getInventory()[player.getHoldingSlot()].getName();
				//ends game with a win
				if(potionName.equals("moonPotion")) {
					gameStart = false;
					win = true;
					lose = false;
					
					reset();
				}
				//increases luck
				else if(potionName.equals("luckPotion")) {
					luck = 4;
						
					itemList.get(17).setRarity(itemList.get(17).getRarity()+luck);
					itemList.get(18).setRarity(itemList.get(18).getRarity()+luck);
					itemList.get(19).setRarity(itemList.get(19).getRarity()+luck);
					itemList.get(20).setRarity(itemList.get(20).getRarity()+luck);
					
					itemList.get(22).setRarity(itemList.get(22).getRarity()+luck);
					itemList.get(23).setRarity(itemList.get(23).getRarity()+luck);
					
					itemList.get(30).setRarity(itemList.get(30).getRarity()+luck);
					itemList.get(31).setRarity(itemList.get(31).getRarity()+luck);
					itemList.get(32).setRarity(itemList.get(32).getRarity()+luck);
					itemList.get(33).setRarity(itemList.get(33).getRarity()+luck);
					
				}
				//increases speed
				else if(potionName.equals("speedPotion")) {
					speed = 3;
				}
				//makes screen pink
				else if(potionName.equals("pinkPotion")) {
					pink = 75;
				}
				//switches gender permanently (unless potion is used again)
				else if(potionName.equals("genderPotion")) {
					if(player.getGender().equals("male"))
						player.setGender("female");
					else if(player.getGender().equals("female"))
						player.setGender("male");
				}
				//makes player small
				else if(potionName.equals("smallPotion")) {
					playerWidth = 25;
					playerHeight = 45;
					hitbox.width = 15;
					hitbox.height = 10;
				}
				//removes potion from inventory
				player.getInventory()[player.getHoldingSlot()] = null;
			}
		}
		
		//cheat that gives you all items
		if(key == KeyEvent.VK_0) {
			for(int i = 0; i != 33; i++) {
				player.addToInventory(itemList.get(i));
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		//more movement
		if(key == KeyEvent.VK_A) {
			left = false;
		}else if(key == KeyEvent.VK_D) {
			right = false;
		}else if(key == KeyEvent.VK_W) {
			up = false;
		}else if(key == KeyEvent.VK_S) {
			down = false;
		}
	}
	
	//parameters none, returns none
	//you just move!! and sets image for animation
	void move() {
		if(left) {
			hitbox.x -= speed;
			player.setImage("left");
		}
		else if(right) {
			hitbox.x += speed;
			player.setImage("right");
		}
			
		if(up) {
			hitbox.y += -speed;
			player.setImage("up");
		}	
		else if(down) {
			hitbox.y += speed;
			player.setImage("down");
		}
		
		//resets image when player stops
		if(!left && !right && !up && !down)
			player.stop();
	}
	
	//keeps player in map
	void keepInBound() {
		if(hitbox.x < 0)
			hitbox.x = 0;
		else if(hitbox.x > screenWidth - hitbox.width)
			hitbox.x = screenWidth - hitbox.width;
		
		if(hitbox.y < 0)
			hitbox.y = 0;
		else if(hitbox.y > screenHeight - hitbox.height)
			hitbox.y = screenHeight - hitbox.height;
	}
	
	//parameters walls, returns nothing
	//makes sure player doesnt go out of screen
	void checkCollision(Rectangle wall) {
		//check if hitbox touches wall
		if(hitbox.intersects(wall)) {
			System.out.println("collision");
			//stop the hitbox from moving
			double left1 = hitbox.getX();
			double right1 = hitbox.getX() + hitbox.getWidth();
			double top1 = hitbox.getY();
			double bottom1 = hitbox.getY() + hitbox.getHeight();
			double left2 = wall.getX();
			double right2 = wall.getX() + wall.getWidth();
			double top2 = wall.getY();
			double bottom2 = wall.getY() + wall.getHeight();
			
			if(right1 > left2 && 
			   left1 < left2 && 
			   right1 - left2 < bottom1 - top2 && 
			   right1 - left2 < bottom2 - top1)
	        {
	            //hitbox collides from left side of the wall
				hitbox.x = wall.x - hitbox.width;
	        }
	        else if(left1 < right2 &&
	        		right1 > right2 && 
	        		right2 - left1 < bottom1 - top2 && 
	        		right2 - left1 < bottom2 - top1)
	        {
	            //hitbox collides from right side of the wall
	        	hitbox.x = wall.x + wall.width;
	        }
	        else if(bottom1 > top2 && top1 < top2)
	        {
	            //hitbox collides from top side of the wall
	        	hitbox.y = wall.y - hitbox.height;
	        }
	        else if(top1 < bottom2 && bottom1 > bottom2)
	        {
	            //hitbox collides from bottom side of the wall
	        	hitbox.y = wall.y + wall.height;
	        }
		}
	}
	
	//parameters nothing, returns nothing
	//updates the map
	void updateMap() {
		if (hitbox.x == 0) {
			currentMap = 2;
			hitbox.x = 970;
			hitbox.y = 325;
		}
		else if (hitbox.y + hitbox.height == 600) {
			currentMap = 3;
			hitbox.x = 870;
			hitbox.y = 10;
		}	
		else if (hitbox.x + hitbox.width == 1000) {
			currentMap = 1;
			hitbox.x = 5;
			hitbox.y = 300;
		}	
		else if (hitbox.y == 0) {
			currentMap = 1;
			hitbox.x = 165;
			hitbox.y = 565;
		}
		
	}
	
	//to see if the player is touching the spawn area or not so they can interact with wtv
	public int checkTouchingSpawnArea() {
		//goes through all the spawn areas of the map
		for(int i = 0; i != maps[currentMap].getSpawnAreas().size(); i++) {
			//check if hitbox touches the spawn area
			if(hitbox.intersects(maps[currentMap].getSpawnAreas().get(i))) {
				return i;
			}
			//returns where the player intersects if they are, returns neg # if theyre not
		}
		return -10;
	}
	
	//spawns items!! 
	//parameters are the start and end of the items that can be spawned from itemlist
	public Item itemSpawn(int start, int end) {
		int total = 0;
		//adds the rarities together
		for(int i = start; i <= end; i++)
			total += itemList.get(i).getRarity();
		//adds number for no item spawn probability
		total += 15 - luck;
		
		int number = (int)(Math.random()*(total)) + 1;
		
		int count = 0;
		int itemSpawned = 0;
		for(int i = start; i <= end; i++) {
			if(number > count + itemList.get(i).getRarity()) {
				count += itemList.get(i).getRarity();
			}
			else {
				itemSpawned = i;
				break;
			}
		}
		
		//gives resulting string of item's name to whatever called this method
		if (itemSpawned != 0)
			return itemList.get(itemSpawned);
		else
			return null;

		//rarity # = # of squaeres
		//tottal pool = sum of all rarities (plus wtv luck) + "empty" # of boxes
		//random number generated from pool
		//if # genderated > current "count" + i.getrarity --> count += i.getrarity --> move onto next i
		//else, item spawned = i
	}
	
	//just checks if the recipes match via sets
	public void checkRecipe() {
		Set<Item> cauldronSet = new HashSet<>(Arrays.asList(cauldron));
		//goes through all the recipes to check
		for(int i = 0; i != recipeList.length; i++) {
			if(cauldronSet.equals(recipeList[i].getIngredients()))
				result = recipeList[i].getPotion();
		}		
		
	}
	
	//for resetting the game
	public void reset() {
		allowMove = false;
		showHotbar = false;
		showInventory = false;
		
		//reset the days
		day = 1;
		
		//this is for the selecting your avatar screen
		characterSelect = false;
		//reset beginning cutscene
		beginningCutscene = false;
		nepentheText = 0;
		nepenthex = 547;
		nepenthey = 265;
		//end of day cutscene
		endDay = false;
		//to know that the game has started
		gameStart = false;
		
		spawnStart = false;
		spawnTimer = 0;
		spawnText = 0;
		spawnTimer2 = 0;
		
		//resets variables in case they were in the middle of making smth
		for(int i = 0; i != 4; i++) {
			if(cauldron[i] != null) {
				player.addToInventory(cauldron[i]);
				cauldron[i] = null;
			}
		}
		result = null;

		
		//RESET POTION HERE
		//resetting luck potion
		itemList.get(17).setRarity(itemList.get(17).getRarity()-luck);
		itemList.get(18).setRarity(itemList.get(18).getRarity()-luck);
		itemList.get(19).setRarity(itemList.get(19).getRarity()-luck);
		itemList.get(20).setRarity(itemList.get(20).getRarity()-luck);
			
		itemList.get(22).setRarity(itemList.get(22).getRarity()-luck);
		itemList.get(23).setRarity(itemList.get(23).getRarity()-luck);
			
		itemList.get(30).setRarity(itemList.get(30).getRarity()-luck);
		itemList.get(31).setRarity(itemList.get(31).getRarity()-luck);
		itemList.get(32).setRarity(itemList.get(32).getRarity()-luck);
		itemList.get(33).setRarity(itemList.get(33).getRarity()-luck);
		
		luck = 0;
		
		//resetting speed
		speed = 2;
		
		//resetting pink
		pink = 0;

		//resetting size
		playerWidth = 33;
		playerHeight = 59;
		hitbox.width = 23;
		hitbox.height = 23;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame ("Moon's Elixir");
		Main myPanel = new Main ();
		
		//sets the game's icon image
		Image iconImage = Toolkit.getDefaultToolkit ().getImage ("pictures/game icon.png");
		frame.setIconImage (iconImage);
		
		//adds listeners
		myPanel.addMouseListener(myPanel);
		frame.add(myPanel);
		frame.addKeyListener(myPanel);
		
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

}
