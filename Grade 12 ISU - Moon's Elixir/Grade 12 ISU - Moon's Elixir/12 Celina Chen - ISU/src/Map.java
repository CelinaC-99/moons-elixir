import java.awt.*;
import java.util.*;

public class Map {

	private Image map;
	private ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> spawnAreas = new ArrayList<Rectangle>();
	
	//constructor
	//parameter is name of map's image
	public Map(String name){
		map = Toolkit.getDefaultToolkit().getImage("pictures/" + name + ".png");
	}
	
	//getters
	public Image getMap() {
		return map;
	}
	
	public ArrayList<Rectangle> getWalls(){
		return walls;
	}
	
	public ArrayList<Rectangle> getSpawnAreas(){
		return spawnAreas;
	}
	
	//setters
	public void setMap(String name) {
		map = Toolkit.getDefaultToolkit().getImage("pictures/" + name + ".png");
	}
	
	//creates walls to block player movement
	//parameters are the rectangle's coords and dimensions
	public void createWall(int x, int y, int width, int height) {
		walls.add(new Rectangle(x, y, width, height));
	}
	
	//creates areas where player has to be at to interact with stuff
	//parameters are the rectangle's coords and dimensions
	public void createSpawnArea(int x, int y, int width, int height) {
		spawnAreas.add(new Rectangle(x, y, width, height));
	}
	
}
