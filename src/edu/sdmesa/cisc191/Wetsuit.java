package edu.sdmesa.cisc191;

/**
 * Wetsuit is-a InventoryItem and represents wetsuits sold by the surf shop.
 */
public class Wetsuit extends InventoryItem
{
    private String size;
    private String thickness;

    public Wetsuit(String itemId, String name, double price, int quantity,
                   String size, String thickness) throws InvalidInventoryException
    {
        super(itemId, name, price, quantity);
        setSize(size);
        setThickness(thickness);
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size) throws InvalidInventoryException
    {
        if (size == null || size.trim().isEmpty())
        {
            throw new InvalidInventoryException("Wetsuit size cannot be blank.");
        }
        this.size = size.trim();
    }

    public String getThickness()
    {
        return thickness;
    }

    public void setThickness(String thickness) throws InvalidInventoryException
    {
        if (thickness == null || thickness.trim().isEmpty())
        {
            throw new InvalidInventoryException("Wetsuit thickness cannot be blank.");
        }
        this.thickness = thickness.trim();
    }

    @Override
    public String getCategory()
    {
        return "Wetsuit";
    }

    @Override
    public String getDetails()
    {
        return "Size " + size + ", " + thickness;
    }

    @Override
    public String toFileString()
    {
        return "Wetsuit," + getItemId() + "," + getName() + "," + getPrice()
                + "," + getQuantity() + "," + size + "," + thickness;
    }
}
