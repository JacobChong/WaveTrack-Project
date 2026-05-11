package edu.sdmesa.cisc191;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests the Week 2 model classes for the WaveTrack project.
 */
class InventoryManagerTest
{
    @Test
    void testAddAndFindItem() throws InvalidInventoryException
    {
        InventoryManager manager = new InventoryManager();
        Surfboard board = new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish");
        manager.addItem(board);
        assertEquals(1, manager.getNumberOfItems());
        assertEquals(board, manager.findItemById("S1"));
    }

    @Test
    void testDuplicateIdThrowsException() throws InvalidInventoryException
    {
        InventoryManager manager = new InventoryManager();
        manager.addItem(new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax"));
        assertThrows(InvalidInventoryException.class, () ->
            manager.addItem(new Accessory("A1", "Warm Water Wax", 3.00, 20, "Wax"))
        );
    }

    @Test
    void testLowStockItems() throws InvalidInventoryException
    {
        InventoryManager manager = new InventoryManager();
        manager.addItem(new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish"));
        manager.addItem(new Accessory("A1", "Leash", 25.00, 10, "Leash"));
        assertEquals(1, manager.getLowStockItems().size());
        assertEquals("Fish Board", manager.getLowStockItems().get(0).getName());
    }

    @Test
    void testDiscountableSurfboard() throws InvalidInventoryException
    {
        Surfboard board = new Surfboard("S1", "Longboard", 600.00, 1, 9.0, "Longboard");
        board.applyDiscount(10);
        assertEquals(540.00, board.getPrice(), 0.001);
    }

    @Test
    void testUpdateQuantity() throws InvalidInventoryException
    {
        InventoryManager manager = new InventoryManager();
        manager.addItem(new Wetsuit("W1", "Full Suit", 220.00, 5, "M", "4/3mm"));
        manager.updateQuantity("W1", 8);
        assertEquals(8, manager.findItemById("W1").getQuantity());
    }
}
