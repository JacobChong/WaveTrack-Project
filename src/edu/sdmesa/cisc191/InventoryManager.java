package edu.sdmesa.cisc191;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * InventoryManager has-many InventoryItem objects.
 * It stores, searches, adds, removes, and updates shop inventory.
 */
public class InventoryManager
{
    private ArrayList<InventoryItem> inventory;

    public InventoryManager()
    {
        inventory = new ArrayList<InventoryItem>();
    }

    public void addItem(InventoryItem item) throws InvalidInventoryException
    {
        if (item == null)
        {
            throw new InvalidInventoryException("Cannot add a null item.");
        }
        if (findItemById(item.getItemId()) != null)
        {
            throw new InvalidInventoryException("An item with that ID already exists.");
        }
        inventory.add(item);
    }

    public boolean removeItemById(String itemId)
    {
        InventoryItem item = findItemById(itemId);
        if (item != null)
        {
            inventory.remove(item);
            return true;
        }
        return false;
    }

    public InventoryItem findItemById(String itemId)
    {
        if (itemId == null)
        {
            return null;
        }
        for (InventoryItem item : inventory)
        {
            if (item.getItemId().equalsIgnoreCase(itemId.trim()))
            {
                return item;
            }
        }
        return null;
    }

    public ArrayList<InventoryItem> searchByName(String keyword)
    {
        ArrayList<InventoryItem> results = new ArrayList<InventoryItem>();
        if (keyword == null)
        {
            return results;
        }
        for (InventoryItem item : inventory)
        {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase().trim()))
            {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<InventoryItem> getLowStockItems()
    {
        ArrayList<InventoryItem> lowStockItems = new ArrayList<InventoryItem>();
        for (InventoryItem item : inventory)
        {
            if (item.isLowStock())
            {
                lowStockItems.add(item);
            }
        }
        return lowStockItems;
    }

    public void updateQuantity(String itemId, int newQuantity) throws InvalidInventoryException
    {
        InventoryItem item = findItemById(itemId);
        if (item == null)
        {
            throw new InvalidInventoryException("Item was not found.");
        }
        item.setQuantity(newQuantity);
    }

    public double getTotalInventoryValue()
    {
        double total = 0;
        for (InventoryItem item : inventory)
        {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public List<InventoryItem> getInventory()
    {
        return Collections.unmodifiableList(inventory);
    }

    public int getNumberOfItems()
    {
        return inventory.size();
    }
}
