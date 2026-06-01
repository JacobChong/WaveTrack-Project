package edu.sdmesa.cisc191;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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
 * Version/date: Week 6-7
 *
 * Responsibilities of class:
 * InventoryFileHandler saves and loads inventory data using text file I/O.
 * This class supports LO8 because it writes inventory objects to a text file
 * and reads inventory objects back into the program.
 *
 * InventoryFileHandler has no InventoryItem objects permanently.
 * Instead, it temporarily uses InventoryItem objects from InventoryManager
 * when saving, and creates InventoryItem objects when loading.
 */
public class InventoryFileHandler
{
	/**
	 * Saves the inventory to a text file.
	 * Each inventory item is saved as one line using its toFileString method.
	 *
	 * This method uses try-with-resources so the PrintWriter is always closed,
	 * even if an exception happens while writing.
	 *
	 * @param manager the inventory manager containing items
	 * @param fileName the name of the file
	 * @throws FileNotFoundException if the file cannot be created or opened
	 */
	public void saveInventory(InventoryManager manager, String fileName) throws FileNotFoundException
	{
		// PrintWriter is created inside try-with-resources.
		// This makes sure the writer is automatically closed.
		try (PrintWriter writer = new PrintWriter(fileName))
		{
			// InventoryManager has-many InventoryItem objects.
			// This loop writes each InventoryItem into the text file.
			for (InventoryItem item : manager.getInventory())
			{
				writer.println(item.toFileString());
			}
		}
	}

	/**
	 * Loads inventory items from a text file.
	 * Each line is converted back into the correct InventoryItem subclass.
	 *
	 * This method uses try-with-resources so the Scanner is always closed,
	 * even if an exception happens while reading.
	 *
	 * @param fileName the name of the file
	 * @return an ArrayList of InventoryItem objects
	 * @throws FileNotFoundException if the file cannot be found
	 * @throws InvalidInventoryException if the file data is invalid
	 */
	public ArrayList<InventoryItem> loadInventory(String fileName)
			throws FileNotFoundException, InvalidInventoryException
	{
		// This ArrayList has-many InventoryItem objects that were loaded from the file.
		ArrayList<InventoryItem> loadedItems = new ArrayList<InventoryItem>();

		File file = new File(fileName);

		// Scanner is created inside try-with-resources.
		// This makes sure the scanner is automatically closed.
		try (Scanner scanner = new Scanner(file))
		{
			// Reads the file one line at a time.
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();

				// Skips blank lines so they do not cause errors.
				if (!line.trim().isEmpty())
				{
					// Creates the correct InventoryItem subclass from the file line.
					InventoryItem item = createItemFromFileLine(line);

					// Adds the item to the loadedItems ArrayList.
					loadedItems.add(item);
				}
			}
		}

		// Returns all items that were loaded from the file.
		return loadedItems;
	}

	/**
	 * Creates an inventory item from one line of text.
	 * This method checks the first part of the line to decide which subclass to create.
	 *
	 * This demonstrates polymorphism because the return type is InventoryItem,
	 * but the actual object may be a Surfboard, Wetsuit, or Accessory.
	 *
	 * @param line one line from the inventory file
	 * @return an InventoryItem object
	 * @throws InvalidInventoryException if the line does not have valid data
	 */
	private InventoryItem createItemFromFileLine(String line) throws InvalidInventoryException
	{
		// Splits the line wherever there is a comma.
		String[] parts = line.split(",");

		// Every item needs at least category, ID, name, price, quantity, and one detail.
		if (parts.length < 6)
		{
			throw new InvalidInventoryException("File line does not have enough information: " + line);
		}

		// The first five parts are shared by every InventoryItem.
		String category = parts[0];
		String itemId = parts[1];
		String name = parts[2];
		double price = parseDouble(parts[3], "price");
		int quantity = parseInt(parts[4], "quantity");

		// Surfboard is-a InventoryItem.
		if (category.equalsIgnoreCase("Surfboard"))
		{
			// Surfboards need 7 parts because they have length and board type.
			if (parts.length != 7)
			{
				throw new InvalidInventoryException("Surfboard line must have 7 parts: " + line);
			}

			double length = parseDouble(parts[5], "surfboard length");
			String boardType = parts[6];

			return new Surfboard(itemId, name, price, quantity, length, boardType);
		}
		// Wetsuit is-a InventoryItem.
		else if (category.equalsIgnoreCase("Wetsuit"))
		{
			// Wetsuits need 7 parts because they have size and thickness.
			if (parts.length != 7)
			{
				throw new InvalidInventoryException("Wetsuit line must have 7 parts: " + line);
			}

			String size = parts[5];
			String thickness = parts[6];

			return new Wetsuit(itemId, name, price, quantity, size, thickness);
		}
		// Accessory is-a InventoryItem.
		else if (category.equalsIgnoreCase("Accessory"))
		{
			// Accessories need 6 parts because they only have one extra detail.
			if (parts.length != 6)
			{
				throw new InvalidInventoryException("Accessory line must have 6 parts: " + line);
			}

			String accessoryType = parts[5];

			return new Accessory(itemId, name, price, quantity, accessoryType);
		}
		else
		{
			throw new InvalidInventoryException("Unknown inventory category: " + category);
		}
	}

	/**
	 * Converts text into a double.
	 * This is used for prices and surfboard lengths.
	 *
	 * @param text the text to convert
	 * @param fieldName the field being converted
	 * @return double value
	 * @throws InvalidInventoryException if the text is not a valid double
	 */
	private double parseDouble(String text, String fieldName) throws InvalidInventoryException
	{
		try
		{
			return Double.parseDouble(text);
		}
		catch (NumberFormatException exception)
		{
			throw new InvalidInventoryException("Invalid " + fieldName + ": " + text);
		}
	}

	/**
	 * Converts text into an int.
	 * This is used for item quantities.
	 *
	 * @param text the text to convert
	 * @param fieldName the field being converted
	 * @return int value
	 * @throws InvalidInventoryException if the text is not a valid integer
	 */
	private int parseInt(String text, String fieldName) throws InvalidInventoryException
	{
		try
		{
			return Integer.parseInt(text);
		}
		catch (NumberFormatException exception)
		{
			throw new InvalidInventoryException("Invalid " + fieldName + ": " + text);
		}
	}
}