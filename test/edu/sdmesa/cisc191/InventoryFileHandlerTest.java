package edu.sdmesa.cisc191;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * Lead Author(s):
 * @author Jacob Chong
 *
 * Other contributors:
 * None
 *
 * References:
 * JUnit 5 User Guide.
 * Retrieved from https://junit.org/junit5/docs/current/user-guide/
 *
 * Version/date: Week 3-4
 *
 * Responsibilities of class:
 * InventoryFileHandlerTest tests saving and loading inventory data.
 */
class InventoryFileHandlerTest
{
	/**
	 * Tests that inventory can be saved to a file and loaded back.
	 * This confirms that text file output and input are both working.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 * @throws FileNotFoundException if the file cannot be found
	 */
	@Test
	void testSaveAndLoadInventory() throws InvalidInventoryException, FileNotFoundException
	{
		// Create the manager and file handler.
		InventoryManager manager = new InventoryManager();
		InventoryFileHandler fileHandler = new InventoryFileHandler();

		// Add sample inventory items to the manager.
		manager.addItem(new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish"));
		manager.addItem(new Wetsuit("W1", "Full Suit", 220.00, 5, "M", "4/3mm"));
		manager.addItem(new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax"));

		// This temporary file will be created during the test.
		String fileName = "test_inventory.txt";

		// Save the manager's inventory to the text file.
		fileHandler.saveInventory(manager, fileName);

		// Load the items back from the text file.
		ArrayList<InventoryItem> loadedItems = fileHandler.loadInventory(fileName);

		// Check that all three items were loaded.
		assertEquals(3, loadedItems.size());

		// Check that the names match the original items.
		assertEquals("Fish Board", loadedItems.get(0).getName());
		assertEquals("Full Suit", loadedItems.get(1).getName());
		assertEquals("Cold Water Wax", loadedItems.get(2).getName());

		// Delete the temporary test file after the test is done.
		File file = new File(fileName);
		file.delete();
	}

	/**
	 * Tests that loading a missing file throws an exception.
	 * This checks that file errors are handled instead of ignored.
	 */
	@Test
	void testMissingFileThrowsException()
	{
		// Create the file handler.
		InventoryFileHandler fileHandler = new InventoryFileHandler();

		// Try to load a file that does not exist and confirm FileNotFoundException is thrown.
		assertThrows(FileNotFoundException.class, () ->
			fileHandler.loadInventory("file_that_does_not_exist.txt")
		);
	}
}