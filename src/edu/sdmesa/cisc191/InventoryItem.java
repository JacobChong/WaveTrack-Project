package edu.sdmesa.cisc191;

/**
 * Lead Author(s): Jacob Chong
 *
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 * Responsibilities of class:
 * InventoryItem is the abstract superclass for all products sold by the surf shop.
 * It stores shared item information such as name, price, quantity, and item ID.
 */
public abstract class InventoryItem
{
    private String itemId;
    private String name;
    private double price;
    private int quantity;

    public InventoryItem(String itemId, String name, double price, int quantity) throws InvalidInventoryException
    {
        setItemId(itemId);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    public String getItemId()
    {
        return itemId;
    }

    public void setItemId(String itemId) throws InvalidInventoryException
    {
        if (itemId == null || itemId.trim().isEmpty())
        {
            throw new InvalidInventoryException("Item ID cannot be blank.");
        }
        this.itemId = itemId.trim();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) throws InvalidInventoryException
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new InvalidInventoryException("Item name cannot be blank.");
        }
        this.name = name.trim();
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price) throws InvalidInventoryException
    {
        if (price < 0)
        {
            throw new InvalidInventoryException("Price cannot be negative.");
        }
        this.price = price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity) throws InvalidInventoryException
    {
        if (quantity < 0)
        {
            throw new InvalidInventoryException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public void addStock(int amount) throws InvalidInventoryException
    {
        if (amount < 0)
        {
            throw new InvalidInventoryException("Amount added cannot be negative.");
        }
        quantity += amount;
    }

    public void removeStock(int amount) throws InvalidInventoryException
    {
        if (amount < 0)
        {
            throw new InvalidInventoryException("Amount removed cannot be negative.");
        }
        if (amount > quantity)
        {
            throw new InvalidInventoryException("Cannot remove more items than are in stock.");
        }
        quantity -= amount;
    }

    public boolean isLowStock()
    {
        return quantity <= 3;
    }

    public abstract String getCategory();
    public abstract String getDetails();
    public abstract String toFileString();

    @Override
    public String toString()
    {
        return getCategory() + " - " + name + " - $" + String.format("%.2f", price)
                + " - Qty: " + quantity + " - " + getDetails();
    }
}
