package edu.sdmesa.cisc191;

/**
 * Lead Author(s):
 * @author Jacob Chong
 *
 * Other contributors:
 * None
 *
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 * Version/date: Week 8
 *
 * Responsibilities of class:
 * WaveTrackDemo is a temporary demo class.
 * It lets me test the model classes before using the GUI.
 * Later, the GUI will become the main way the user interacts with the program.
 */
public class WaveTrackDemo
{
	/**
	 * Starts the demo program.
	 *
	 * @param args not used
	 */
	public static void main(String[] args)
	{
		// Use try/catch because constructors and manager methods can throw InvalidInventoryException.
		try
		{
			// Create the InventoryManager, which has-many InventoryItem objects.
			InventoryManager manager = new InventoryManager();

			// Create different types of InventoryItem objects.
			// This demonstrates polymorphism because Surfboard, Wetsuit, and Accessory
			// can all be treated as InventoryItem objects.
			Surfboard fishBoard = new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish");
			Wetsuit fullSuit = new Wetsuit("W1", "Full Suit", 220.00, 5, "M", "4/3mm");
			Accessory wax = new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax");

			// Add each item to the manager's ArrayList.
			manager.addItem(fishBoard);
			manager.addItem(fullSuit);
			manager.addItem(wax);

			// Test the 2D rack layout by placing item IDs into row/column positions.
			// This demonstrates LO2 because rackLayout is a multidimensional array.
			manager.placeItemOnRack(0, 0, "S1");
			manager.placeItemOnRack(0, 1, "W1");
			manager.placeItemOnRack(1, 0, "A1");

			System.out.println("WaveTrack Inventory Manager");
			System.out.println("---------------------------");

			// Print inventory and the 2D rack layout.
			System.out.println(manager.getInventoryDisplayText());

			System.out.println();
			System.out.println("Low stock items:");

			// Print only items that have a quantity of 3 or less.
			for (InventoryItem item : manager.getLowStockItems())
			{
				System.out.println(item);
			}

			System.out.println();
			System.out.println("Applying 10% discount to Fish Board...");

			// Surfboard can use this method because it implements Discountable.
			fishBoard.applyDiscount(10);

			System.out.println();
			System.out.println("Updated inventory after discount:");
			System.out.println(manager.getInventoryDisplayText());

			System.out.println();
			System.out.println("Testing rack clear method...");

			// Clear one rack spot to test the 2D array clear method.
			manager.clearRackSpot(0, 1);

			System.out.println(manager.getRackDisplayText());
		}
		catch (InvalidInventoryException exception)
		{
			// If invalid inventory data is entered, display a friendly error message
			// instead of crashing the program.
			System.out.println("Inventory error: " + exception.getMessage());
		}
		catch (Exception exception)
		{
			// This catches any unexpected error so the program does not crash with no explanation.
			System.out.println("Unexpected error: " + exception.getMessage());
		}
	}
}