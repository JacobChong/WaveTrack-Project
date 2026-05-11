package edu.sdmesa.cisc191;

/**
 * Accessory is-a InventoryItem and represents smaller shop items like wax, fins, and leashes.
 */
public class Accessory extends InventoryItem
{
    private String accessoryType;

    public Accessory(String itemId, String name, double price, int quantity,
                     String accessoryType) throws InvalidInventoryException
    {
        super(itemId, name, price, quantity);
        setAccessoryType(accessoryType);
    }

    public String getAccessoryType()
    {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) throws InvalidInventoryException
    {
        if (accessoryType == null || accessoryType.trim().isEmpty())
        {
            throw new InvalidInventoryException("Accessory type cannot be blank.");
        }
        this.accessoryType = accessoryType.trim();
    }

    @Override
    public String getCategory()
    {
        return "Accessory";
    }

    @Override
    public String getDetails()
    {
        return accessoryType;
    }

    @Override
    public String toFileString()
    {
        return "Accessory," + getItemId() + "," + getName() + "," + getPrice()
                + "," + getQuantity() + "," + accessoryType;
    }
}
