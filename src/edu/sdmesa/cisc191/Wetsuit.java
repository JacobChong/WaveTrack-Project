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
 * Wetsuit is-a InventoryItem because it extends InventoryItem.
 * This class stores wetsuit-specific details like size and thickness.
 */
public class Wetsuit extends InventoryItem
{
	// These fields are specific to wetsuits, not every inventory item.
	private String size;
	private String thickness;

	/**
	 * Creates a wetsuit item.
	 *
	 * @param itemId unique item ID
	 * @param name item name
	 * @param price item price
	 * @param quantity number in stock
	 * @param size wetsuit size
	 * @param thickness wetsuit thickness
	 * @throws InvalidInventoryException if data is invalid
	 */
	public Wetsuit(String itemId, String name, double price, int quantity,
			String size, String thickness) throws InvalidInventoryException
	{
		// Call the superclass constructor to set shared InventoryItem fields.
		super(itemId, name, price, quantity);

		// Set wetsuit-specific fields after the shared fields are created.
		setSize(size);
		setThickness(thickness);
	}

	/**
	 * Gets the wetsuit size.
	 *
	 * @return wetsuit size
	 */
	public String getSize()
	{
		return size;
	}

	/**
	 * Sets the wetsuit size.
	 *
	 * @param size wetsuit size
	 * @throws InvalidInventoryException if size is blank
	 */
	public void setSize(String size) throws InvalidInventoryException
	{
		if (size == null || size.trim().isEmpty())
		{
			throw new InvalidInventoryException("Wetsuit size cannot be blank.");
		}

		this.size = size.trim();
	}

	/**
	 * Gets the wetsuit thickness.
	 *
	 * @return wetsuit thickness
	 */
	public String getThickness()
	{
		return thickness;
	}

	/**
	 * Sets the wetsuit thickness.
	 *
	 * @param thickness wetsuit thickness
	 * @throws InvalidInventoryException if thickness is blank
	 */
	public void setThickness(String thickness) throws InvalidInventoryException
	{
		if (thickness == null || thickness.trim().isEmpty())
		{
			throw new InvalidInventoryException("Wetsuit thickness cannot be blank.");
		}

		this.thickness = thickness.trim();
	}

	/**
	 * This method overrides the abstract method from InventoryItem.
	 */
	@Override
	public String getCategory()
	{
		return "Wetsuit";
	}

	/**
	 * This method gives the wetsuit-specific details for display.
	 */
	@Override
	public String getDetails()
	{
		return "Size " + size + ", " + thickness;
	}

	/**
	 * Converts this wetsuit into one line of text for future file saving.
	 */
	@Override
	public String toFileString()
	{
		return "Wetsuit," + getItemId() + "," + getName() + "," + getPrice()
				+ "," + getQuantity() + "," + size + "," + thickness;
	}
}