package edu.sdmesa.cisc191;

/**
 * Simple Week 2 demo for testing the model before the GUI is created.
 */
public class WaveTrackDemo
{
    public static void main(String[] args)
    {
        try
        {
            InventoryManager manager = new InventoryManager();

            manager.addItem(new Surfboard("S1", "Fish Board", 450.00, 2, 5.8, "Fish"));
            manager.addItem(new Wetsuit("W1", "Full Suit", 220.00, 5, "M", "4/3mm"));
            manager.addItem(new Accessory("A1", "Cold Water Wax", 3.00, 30, "Wax"));

            System.out.println("WaveTrack Inventory:");
            for (InventoryItem item : manager.getInventory())
            {
                System.out.println(item);
            }

            System.out.println("Total inventory value: $" + manager.getTotalInventoryValue());
        }
        catch (InvalidInventoryException exception)
        {
            System.out.println("Inventory error: " + exception.getMessage());
        }
    }
}
