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
 * Version/date: Week 2
 *
 * Responsibilities of class:
 * InventoryItem is an abstract superclass.
 * It represents the shared information that all inventory items have.
 * Surfboard, Wetsuit, and Accessory are all InventoryItems.
 */
public abstract class InventoryItem
{
	// Every inventory item has an item ID, name, price, and quantity.
	// These fields are private to protect the data and support encapsulation.
	private String itemId;
	private String name;
	private double price;
	private int quantity;

	/**
	 * Creates an inventory item with shared product information.
	 *
	 * @param itemId the unique item ID
	 * @param name the item name
	 * @param price the item price
	 * @param quantity the number of items in stock
	 * @throws InvalidInventoryException if the item data is invalid
	 */
	public InventoryItem(String itemId, String name, double price, int quantity) throws InvalidInventoryException
	{
		// Use setter methods instead of directly assigning fields.
		// This makes sure invalid data is checked when the object is created.
		setItemId(itemId);
		setName(name);
		setPrice(price);
		setQuantity(quantity);
	}

	/**
	 * Gets the item ID.
	 *
	 * @return item ID
	 */
	public String getItemId()
	{
		return itemId;
	}

	/**
	 * Sets the item ID.
	 *
	 * @param itemId the new item ID
	 * @throws InvalidInventoryException if the item ID is blank
	 */
	public void setItemId(String itemId) throws InvalidInventoryException
	{
		if (itemId == null || itemId.trim().isEmpty())
		{
			throw new InvalidInventoryException("Item ID cannot be blank.");
		}

		this.itemId = itemId.trim();
	}

	/**
	 * Gets the item name.
	 *
	 * @return item name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the item name.
	 *
	 * @param name the new item name
	 * @throws InvalidInventoryException if the name is blank
	 */
	public void setName(String name) throws InvalidInventoryException
	{
		if (name == null || name.trim().isEmpty())
		{
			throw new InvalidInventoryException("Item name cannot be blank.");
		}

		this.name = name.trim();
	}

	/**
	 * Gets the item price.
	 *
	 * @return item price
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * Sets the item price.
	 *
	 * @param price the new item price
	 * @throws InvalidInventoryException if the price is negative
	 */
	public void setPrice(double price) throws InvalidInventoryException
	{
		if (price < 0)
		{
			throw new InvalidInventoryException("Price cannot be negative.");
		}

		this.price = price;
	}

	/**
	 * Gets the quantity in stock.
	 *
	 * @return quantity in stock
	 */
	public int getQuantity()
	{
		return quantity;
	}

	/**
	 * Sets the quantity in stock.
	 *
	 * @param quantity the new quantity
	 * @throws InvalidInventoryException if the quantity is negative
	 */
	public void setQuantity(int quantity) throws InvalidInventoryException
	{
		if (quantity < 0)
		{
			throw new InvalidInventoryException("Quantity cannot be negative.");
		}

		this.quantity = quantity;
	}

	/**
	 * Adds stock to the item.
	 *
	 * @param amount amount to add
	 * @throws InvalidInventoryException if amount is negative
	 */
	public void addStock(int amount) throws InvalidInventoryException
	{
		if (amount < 0)
		{
			throw new InvalidInventoryException("Amount added cannot be negative.");
		}

		quantity += amount;
	}

	/**
	 * Removes stock from the item.
	 *
	 * @param amount amount to remove
	 * @throws InvalidInventoryException if amount is invalid or greater than quantity
	 */
	public void removeStock(int amount) throws InvalidInventoryException
	{
		if (amount < 0)
		{
			throw new InvalidInventoryException("Amount removed cannot be negative.");
		}

		if (amount > quantity)
		{
			throw new InvalidInventoryException("Cannot remove more items than are in stock.");
		}

		quantity -= amount;
	}

	/**
	 * This helper method checks whether an item needs to be restocked.
	 *
	 * @return true if quantity is 3 or less, otherwise false
	 */
	public boolean isLowStock()
	{
		return quantity <= 3;
	}

	/**
	 * Subclasses must provide their own category because each item type is different.
	 *
	 * @return product category
	 */
	public abstract String getCategory();

	/**
	 * Subclasses must provide their own details because surfboards, wetsuits,
	 * and accessories have different extra information.
	 *
	 * @return item details
	 */
	public abstract String getDetails();

	/**
	 * Subclasses must describe how they should be saved to a text file.
	 *
	 * @return item data separated by commas
	 */
	public abstract String toFileString();

	/**
	 * Polymorphism is used here because getCategory() and getDetails()
	 * will call the correct subclass version depending on the object type.
	 */
	@Override
	public String toString()
	{
		return getCategory() + " - " + name + " - $" + String.format("%.2f", price)
				+ " - Qty: " + quantity + " - " + getDetails();
	}
}