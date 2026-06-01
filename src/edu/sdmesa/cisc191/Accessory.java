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
 * Accessory is-a InventoryItem because it extends InventoryItem.
 * This class represents smaller surf shop items like wax, leashes, and fins.
 */
public class Accessory extends InventoryItem
{
	// This field stores what kind of accessory the item is.
	private String accessoryType;

	/**
	 * Creates an accessory item.
	 *
	 * @param itemId unique item ID
	 * @param name item name
	 * @param price item price
	 * @param quantity number in stock
	 * @param accessoryType type of accessory
	 * @throws InvalidInventoryException if data is invalid
	 */
	public Accessory(String itemId, String name, double price, int quantity,
			String accessoryType) throws InvalidInventoryException
	{
		// Call the superclass constructor to set shared InventoryItem fields.
		super(itemId, name, price, quantity);

		// Set the accessory-specific field after the shared fields are created.
		setAccessoryType(accessoryType);
	}

	/**
	 * Gets the accessory type.
	 *
	 * @return accessory type
	 */
	public String getAccessoryType()
	{
		return accessoryType;
	}

	/**
	 * Sets the accessory type.
	 *
	 * @param accessoryType type of accessory
	 * @throws InvalidInventoryException if accessory type is blank
	 */
	public void setAccessoryType(String accessoryType) throws InvalidInventoryException
	{
		if (accessoryType == null || accessoryType.trim().isEmpty())
		{
			throw new InvalidInventoryException("Accessory type cannot be blank.");
		}

		this.accessoryType = accessoryType.trim();
	}

	/**
	 * This method overrides the abstract method from InventoryItem.
	 */
	@Override
	public String getCategory()
	{
		return "Accessory";
	}

	/**
	 * This method gives the accessory-specific details for display.
	 */
	@Override
	public String getDetails()
	{
		return accessoryType;
	}

	/**
	 * Converts this accessory into one line of text for future file saving.
	 */
	@Override
	public String toFileString()
	{
		return "Accessory," + getItemId() + "," + getName() + "," + getPrice()
				+ "," + getQuantity() + "," + accessoryType;
	}
}