import java.util.HashSet;
import java.util.Set;

public class Recipe {

	Item potion;
	private Set<Item> ingredients = new HashSet<Item>();
	
	//constructor
	//parameters are the potion name and its 4 ingredients
	public Recipe(Item potion, Item item1, Item item2, Item item3, Item item4) {
		this.potion = potion;
		ingredients.add(item1);
		ingredients.add(item2);
		ingredients.add(item3);
		ingredients.add(item4);
	}
	
	//getters
	public Item getPotion() {
		return potion;
	}
	
	public Set<Item> getIngredients() {
		return ingredients;
	}
	
}
