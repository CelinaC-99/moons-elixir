import java.util.Comparator;

public class Sort implements Comparator<Item>{
	
	//Description: sorts by id, low to high. this specifically handles nulls in the inventory and puts them at the end
	//Parameters: a item object that needs to be sorted
	//Returns: an int to tell the code where to sort the item
	public int compare (Item i1, Item i2) {
		if (i1 == null && i2 == null) {
            return 0;
        }
        if (i1 == null) {
            return 1;
        }
        if (i2 == null) {
            return -1;
        }
        return i1.compareTo(i2);
	}

}