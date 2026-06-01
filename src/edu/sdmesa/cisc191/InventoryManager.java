package edu.sdmesa.cisc191;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
 * InventoryManager manages the store's inventory.
 * InventoryManager has-many InventoryItem objects.
 * This is aggregation because the manager stores and organizes many item objects.
 */
public class InventoryManager
{
	// InventoryManager has-many InventoryItem objects stored in an ArrayList.
	// This demonstrates aggregation and generic collections.
	private ArrayList<InventoryItem> inventory;

	// InventoryManager has-a 2D array that represents the surf shop display rack.
	// This demonstrates LO2 because it uses a multidimensional array.
	private String[][] rackLayout;

	/**
	 * Creates an empty inventory manager.
	 */
	public InventoryManager()
	{
		// Start with an empty inventory list.
		inventory = new ArrayList<InventoryItem>();

		// Create a 2D array to represent a 3 row by 4 column display rack.
		// Each spot can store the name or ID of an item on the rack.
		rackLayout = new String[3][4];

		// Fill the rack with "Empty" so the display is clear.
		for (int row = 0; row < rackLayout.length; row++)
		{
			for (int col = 0; col < rackLayout[row].length; col++)
			{
				rackLayout[row][col] = "Empty";
			}
		}
	}

	/**
	 * Adds one InventoryItem object to the inventory list.
	 * This method also checks that the item is not null and that its ID is not already used.
	 *
	 * @param item item to add
	 * @throws InvalidInventoryException if item is null or duplicate
	 */
	public void addItem(InventoryItem item) throws InvalidInventoryException
	{
		// Prevents a null object from being added to the inventory.
		if (item == null)
		{
			throw new InvalidInventoryException("Cannot add a null item.");
		}

		// Prevents duplicate item IDs because each inventory item should have a unique ID.
		if (findItemById(item.getItemId()) != null)
		{
			throw new InvalidInventoryException("An item with that ID already exists.");
		}

		// Adds the valid item to the ArrayList.
		inventory.add(item);
	}

	/**
	 * Adds several InventoryItem objects to the inventory.
	 * This is useful when loading multiple items from a file.
	 *
	 * @param items items to add
	 * @throws InvalidInventoryException if the list is null or if any item is invalid
	 */
	public void addItems(ArrayList<InventoryItem> items) throws InvalidInventoryException
	{
		// Checks that the list itself exists before looping through it.
		if (items == null)
		{
			throw new InvalidInventoryException("Item list cannot be null.");
		}

		// Adds each item one at a time so each item still goes through addItem validation.
		for (InventoryItem item : items)
		{
			addItem(item);
		}
	}

	/**
	 * Searches for an item by ID and removes it if it exists.
	 *
	 * @param itemId ID of item to remove
	 * @return true if item was removed, otherwise false
	 */
	public boolean removeItemById(String itemId)
	{
		// Reuses findItemById so the searching logic is not repeated.
		InventoryItem item = findItemById(itemId);

		// If the item exists, remove it from the ArrayList.
		if (item != null)
		{
			inventory.remove(item);
			removeItemFromRack(itemId);
			return true;
		}

		// If no matching item is found, nothing is removed.
		return false;
	}

	/**
	 * Loops through the ArrayList to find an item with a matching ID.
	 *
	 * @param itemId ID to search for
	 * @return matching item, or null if not found
	 */
	public InventoryItem findItemById(String itemId)
	{
		// If the ID is null, there is nothing to search for.
		if (itemId == null)
		{
			return null;
		}

		// Checks each InventoryItem in the ArrayList.
		for (InventoryItem item : inventory)
		{
			// equalsIgnoreCase makes the search more flexible for the user.
			if (item.getItemId().equalsIgnoreCase(itemId.trim()))
			{
				return item;
			}
		}

		// Returns null if no item has the requested ID.
		return null;
	}

	/**
	 * Searches the inventory for items whose names contain the keyword.
	 *
	 * @param keyword search word
	 * @return list of matching items
	 */
	public ArrayList<InventoryItem> searchByName(String keyword)
	{
		// Creates a new ArrayList to store matching search results.
		ArrayList<InventoryItem> results = new ArrayList<InventoryItem>();

		// If the keyword is null, return an empty results list.
		if (keyword == null)
		{
			return results;
		}

		// Loops through every item and checks whether the item name contains the keyword.
		for (InventoryItem item : inventory)
		{
			if (item.getName().toLowerCase().contains(keyword.toLowerCase().trim()))
			{
				results.add(item);
			}
		}

		// Returns all matching items.
		return results;
	}

	/**
	 * Builds and returns a list of items that need to be restocked.
	 *
	 * @return list of items with low stock
	 */
	public ArrayList<InventoryItem> getLowStockItems()
	{
		// Creates a new list only for low stock items.
		ArrayList<InventoryItem> lowStockItems = new ArrayList<InventoryItem>();

		// Loops through inventory and uses the isLowStock method from InventoryItem.
		for (InventoryItem item : inventory)
		{
			if (item.isLowStock())
			{
				lowStockItems.add(item);
			}
		}

		// Returns the list of items with quantity 3 or less.
		return lowStockItems;
	}

	/**
	 * Updates the quantity of an item.
	 *
	 * @param itemId item ID
	 * @param newQuantity new quantity
	 * @throws InvalidInventoryException if item is missing or quantity is invalid
	 */
	public void updateQuantity(String itemId, int newQuantity) throws InvalidInventoryException
	{
		// Find the item first before changing its quantity.
		InventoryItem item = findItemById(itemId);

		// If no item is found, throw an exception instead of causing an error.
		if (item == null)
		{
			throw new InvalidInventoryException("Item was not found.");
		}

		// Uses the InventoryItem setter so negative quantities are still rejected.
		item.setQuantity(newQuantity);
	}

	/**
	 * Removes every item from the inventory.
	 * This will be useful when loading inventory from a file.
	 */
	public void clearInventory()
	{
		// Clears the whole ArrayList.
		inventory.clear();

		// Clears the 2D rack layout too.
		clearRackLayout();
	}

	/**
	 * Calculates the total value of all inventory by multiplying price by quantity.
	 *
	 * @return total inventory value
	 */
	public double getTotalInventoryValue()
	{
		// Starts the total at zero before adding each item's value.
		double total = 0;

		// Adds price times quantity for every item in inventory.
		for (InventoryItem item : inventory)
		{
			total += item.getPrice() * item.getQuantity();
		}

		// Returns the final inventory value.
		return total;
	}

	/**
	 * Returns a safe view of the inventory list so other classes can read it
	 * without directly changing the original ArrayList.
	 *
	 * @return list of inventory items
	 */
	public List<InventoryItem> getInventory()
	{
		// unmodifiableList protects the original ArrayList from outside changes.
		return Collections.unmodifiableList(inventory);
	}

	/**
	 * Gets the number of item entries in inventory.
	 *
	 * @return number of different item entries
	 */
	public int getNumberOfItems()
	{
		// Returns the size of the ArrayList.
		return inventory.size();
	}

	/**
	 * Places an item ID into a specific spot in the display rack.
	 * This method uses a 2D array because the rack has rows and columns.
	 *
	 * @param row the rack row
	 * @param col the rack column
	 * @param itemId the item ID to place in the rack
	 * @throws InvalidInventoryException if the row, column, or item ID is invalid
	 */
	public void placeItemOnRack(int row, int col, String itemId) throws InvalidInventoryException
	{
		// Make sure the row and column are inside the 2D array.
		if (row < 0 || row >= rackLayout.length || col < 0 || col >= rackLayout[row].length)
		{
			throw new InvalidInventoryException("Rack position is outside the display rack.");
		}

		// Make sure the item exists before placing it on the rack.
		if (findItemById(itemId) == null)
		{
			throw new InvalidInventoryException("Cannot place item on rack because the item ID was not found.");
		}

		// Store the item ID in the selected row and column.
		rackLayout[row][col] = itemId;
	}

	/**
	 * Clears a specific spot in the display rack.
	 *
	 * @param row the rack row
	 * @param col the rack column
	 * @throws InvalidInventoryException if the row or column is invalid
	 */
	public void clearRackSpot(int row, int col) throws InvalidInventoryException
	{
		// Make sure the row and column are inside the 2D array.
		if (row < 0 || row >= rackLayout.length || col < 0 || col >= rackLayout[row].length)
		{
			throw new InvalidInventoryException("Rack position is outside the display rack.");
		}

		// Mark the selected rack spot as empty.
		rackLayout[row][col] = "Empty";
	}

	/**
	 * Clears the whole rack layout.
	 */
	public void clearRackLayout()
	{
		// Loop through the 2D array and reset every spot.
		for (int row = 0; row < rackLayout.length; row++)
		{
			for (int col = 0; col < rackLayout[row].length; col++)
			{
				rackLayout[row][col] = "Empty";
			}
		}
	}

	/**
	 * Removes an item ID from the rack if it is currently placed anywhere.
	 *
	 * @param itemId the item ID to remove from the rack
	 */
	private void removeItemFromRack(String itemId)
	{
		// If the item ID is null, there is nothing to remove.
		if (itemId == null)
		{
			return;
		}

		// Loop through the 2D array and clear any matching rack spot.
		for (int row = 0; row < rackLayout.length; row++)
		{
			for (int col = 0; col < rackLayout[row].length; col++)
			{
				if (rackLayout[row][col].equalsIgnoreCase(itemId.trim()))
				{
					rackLayout[row][col] = "Empty";
				}
			}
		}
	}

	/**
	 * Converts the 2D rack layout into text for display.
	 * This demonstrates a multidimensional array by looping through rows and columns.
	 *
	 * @return display rack as text
	 */
	public String getRackDisplayText()
	{
		String rackText = "Surf Shop Display Rack:\n\n";

		// Loop through each row of the 2D array.
		for (int row = 0; row < rackLayout.length; row++)
		{
			rackText += "Row " + row + ": ";

			// Loop through each column in the current row.
			for (int col = 0; col < rackLayout[row].length; col++)
			{
				rackText += "[" + rackLayout[row][col] + "] ";
			}

			rackText += "\n";
		}

		return rackText;
	}

	/**
	 * Converts the full inventory into display text for the GUI or console.
	 *
	 * @return inventory as text
	 */
	public String getInventoryDisplayText()
	{
		// Starts with an empty String and adds item information to it.
		String inventoryText = "";

		// If there are no items, still show the rack layout.
		if (inventory.isEmpty())
		{
			return "No inventory items have been added yet.\n\n" + getRackDisplayText();
		}

		// Adds each item's toString output to the display text.
		for (InventoryItem item : inventory)
		{
			inventoryText += item.toString() + "\n";
		}

		// Adds the total inventory value at the bottom.
		inventoryText += "\nTotal inventory value: $" + String.format("%.2f", getTotalInventoryValue());

		// Adds the 2D array rack layout to the display.
		inventoryText += "\n\n" + getRackDisplayText();

		return inventoryText;
	}
}