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
 * Version/date: Week 8
 *
 * Responsibilities of class:
 * WaveTrackFinalTest checks major final project features before submission.
 * These tests cover adding items, searching, updating quantity, low stock,
 * and file input/output.
 */
class WaveTrackFinalTest
{
	/**
	 * Tests the main inventory workflow.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 */
	@Test
	void testMainInventoryWorkflow() throws InvalidInventoryException
	{
		InventoryManager manager = new InventoryManager();

		manager.addItem(new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish"));
		manager.addItem(new Wetsuit("W1", "Full Suit", 220.00, 5, "M", "4/3mm"));
		manager.addItem(new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax"));

		assertEquals(3, manager.getNumberOfItems());
		assertEquals("Fish Board", manager.findItemById("S1").getName());

		ArrayList<InventoryItem> searchResults = manager.searchByName("Fish");
		assertEquals(1, searchResults.size());

		assertEquals(1, manager.getLowStockItems().size());

		manager.updateQuantity("S1", 10);
		assertEquals(10, manager.findItemById("S1").getQuantity());

		assertEquals(0, manager.getLowStockItems().size());
	}

	/**
	 * Tests removing an item from the inventory.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 */
	@Test
	void testRemoveItem() throws InvalidInventoryException
	{
		InventoryManager manager = new InventoryManager();

		manager.addItem(new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax"));

		assertTrue(manager.removeItemById("A1"));
		assertEquals(0, manager.getNumberOfItems());
		assertFalse(manager.removeItemById("A1"));
	}

	/**
	 * Tests saving and loading inventory using text file I/O.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 * @throws FileNotFoundException if the file cannot be found
	 */
	@Test
	void testSaveAndLoadFinalInventory() throws InvalidInventoryException, FileNotFoundException
	{
		InventoryManager manager = new InventoryManager();
		InventoryFileHandler fileHandler = new InventoryFileHandler();

		manager.addItem(new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish"));
		manager.addItem(new Wetsuit("W1", "Full Suit", 220.00, 5, "M", "4/3mm"));
		manager.addItem(new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax"));

		String fileName = "final_test_inventory.txt";

		fileHandler.saveInventory(manager, fileName);

		ArrayList<InventoryItem> loadedItems = fileHandler.loadInventory(fileName);

		assertEquals(3, loadedItems.size());
		assertTrue(loadedItems.get(0) instanceof Surfboard);
		assertTrue(loadedItems.get(1) instanceof Wetsuit);
		assertTrue(loadedItems.get(2) instanceof Accessory);

		File file = new File(fileName);
		file.delete();
	}

	/**
	 * Tests that duplicate IDs are rejected.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 */
	@Test
	void testDuplicateIdsRejected() throws InvalidInventoryException
	{
		InventoryManager manager = new InventoryManager();

		manager.addItem(new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax"));

		assertThrows(InvalidInventoryException.class, () ->
			manager.addItem(new Accessory("A1", "Warm Water Wax", 3.00, 20, "Wax"))
		);
	}
}