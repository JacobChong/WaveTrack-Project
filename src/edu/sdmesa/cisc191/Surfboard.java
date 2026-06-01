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
 * Surfboard is-a InventoryItem because it extends InventoryItem.
 * Surfboard is also Discountable because it implements the Discountable interface.
 * This class stores surfboard-specific details like length and board type.
 */
public class Surfboard extends InventoryItem implements Discountable
{
	// These fields are specific to surfboards, not every inventory item.
	private double lengthInFeet;
	private String boardType;

	/**
	 * Creates a surfboard item.
	 *
	 * @param itemId unique item ID
	 * @param name item name
	 * @param price item price
	 * @param quantity number in stock
	 * @param lengthInFeet board length in feet
	 * @param boardType type of surfboard
	 * @throws InvalidInventoryException if data is invalid
	 */
	public Surfboard(String itemId, String name, double price, int quantity,
			double lengthInFeet, String boardType) throws InvalidInventoryException
	{
		// Call the superclass constructor to set shared InventoryItem fields.
		super(itemId, name, price, quantity);

		// Set surfboard-specific fields after the shared fields are created.
		setLengthInFeet(lengthInFeet);
		setBoardType(boardType);
	}

	/**
	 * Gets the surfboard length.
	 *
	 * @return length in feet
	 */
	public double getLengthInFeet()
	{
		return lengthInFeet;
	}

	/**
	 * Sets the surfboard length.
	 *
	 * @param lengthInFeet board length in feet
	 * @throws InvalidInventoryException if length is zero or negative
	 */
	public void setLengthInFeet(double lengthInFeet) throws InvalidInventoryException
	{
		if (lengthInFeet <= 0)
		{
			throw new InvalidInventoryException("Surfboard length must be greater than zero.");
		}

		this.lengthInFeet = lengthInFeet;
	}

	/**
	 * Gets the board type.
	 *
	 * @return board type
	 */
	public String getBoardType()
	{
		return boardType;
	}

	/**
	 * Sets the board type.
	 *
	 * @param boardType type of board
	 * @throws InvalidInventoryException if board type is blank
	 */
	public void setBoardType(String boardType) throws InvalidInventoryException
	{
		if (boardType == null || boardType.trim().isEmpty())
		{
			throw new InvalidInventoryException("Board type cannot be blank.");
		}

		this.boardType = boardType.trim();
	}

	/**
	 * This method overrides the abstract method from InventoryItem.
	 */
	@Override
	public String getCategory()
	{
		return "Surfboard";
	}

	/**
	 * This method gives the surfboard-specific details for display.
	 */
	@Override
	public String getDetails()
	{
		return lengthInFeet + " ft " + boardType;
	}

	/**
	 * This method comes from the Discountable interface.
	 * It changes the price by applying a percentage discount.
	 */
	@Override
	public void applyDiscount(double percentOff) throws InvalidInventoryException
	{
		if (percentOff < 0 || percentOff > 100)
		{
			throw new InvalidInventoryException("Discount must be between 0 and 100.");
		}

		setPrice(getPrice() * (1 - percentOff / 100));
	}

	/**
	 * Converts this surfboard into one line of text for future file saving.
	 */
	@Override
	public String toFileString()
	{
		return "Surfboard," + getItemId() + "," + getName() + "," + getPrice()
				+ "," + getQuantity() + "," + lengthInFeet + "," + boardType;
	}
}