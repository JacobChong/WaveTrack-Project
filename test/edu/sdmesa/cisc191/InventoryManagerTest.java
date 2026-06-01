package edu.sdmesa.cisc191;

import static org.junit.jupiter.api.Assertions.*;

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
 * Version/date: Week 2
 *
 * Responsibilities of class:
 * InventoryManagerTest checks that the model classes work correctly.
 * These tests help confirm that adding, finding, updating, discounting,
 * and validating inventory items works before the GUI is built.
 */
class InventoryManagerTest
{
	/**
	 * Tests that an item can be added to the inventory and found by ID.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 */
	@Test
	void testAddAndFindItem() throws InvalidInventoryException
	{
		InventoryManager manager = new InventoryManager();
		Surfboard board = new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish");

		manager.addItem(board);

		assertEquals(1, manager.getNumberOfItems());
		assertEquals(board, manager.findItemById("S1"));
	}

	/**
	 * Tests that two items cannot use the same item ID.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 */
	@Test
	void testDuplicateIdThrowsException() throws InvalidInventoryException
	{
		InventoryManager manager = new InventoryManager();
		manager.addItem(new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax"));

		assertThrows(InvalidInventoryException.class, () ->
			manager.addItem(new Accessory("A1", "Warm Water Wax", 3.00, 20, "Wax"))
		);
	}

	/**
	 * Tests that items with quantity 3 or less are marked as low stock.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 */
	@Test
	void testLowStockItems() throws InvalidInventoryException
	{
		InventoryManager manager = new InventoryManager();
		manager.addItem(new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish"));
		manager.addItem(new Accessory("A1", "Leash", 25.00, 10, "Leash"));

		assertEquals(1, manager.getLowStockItems().size());
		assertEquals("Fish Board", manager.getLowStockItems().get(0).getName());
	}

	/**
	 * Tests that Surfboard correctly uses the Discountable interface.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 */
	@Test
	void testDiscountableSurfboard() throws InvalidInventoryException
	{
		Surfboard board = new Surfboard("S1", "Longboard", 600.00, 1, 9.0, "Longboard");

		board.applyDiscount(10);

		assertEquals(540.00, board.getPrice(), 0.001);
	}

	/**
	 * Tests that InventoryManager can update an item's quantity.
	 *
	 * @throws InvalidInventoryException if item data is invalid
	 */
	@Test
	void testUpdateQuantity() throws InvalidInventoryException
	{
		InventoryManager manager = new InventoryManager();
		manager.addItem(new Wetsuit("W1", "Full Suit", 220.00, 5, "M", "4/3mm"));

		manager.updateQuantity("W1", 8);

		assertEquals(8, manager.findItemById("W1").getQuantity());
	}

	/**
	 * Tests that negative prices are rejected through exception handling.
	 */
	@Test
	void testNegativePriceThrowsException()
	{
		assertThrows(InvalidInventoryException.class, () ->
			new Accessory("A2", "Surf Wax", -2.00, 10, "Wax")
		);
	}

	/**
	 * Tests that negative quantities are rejected through exception handling.
	 */
	@Test
	void testNegativeQuantityThrowsException()
	{
		assertThrows(InvalidInventoryException.class, () ->
			new Wetsuit("W2", "Spring Suit", 120.00, -1, "L", "2mm")
		);
	}
}