package edu.sdmesa.cisc191;

/**
 * Surfboard is-a InventoryItem and is Discountable.
 */
public class Surfboard extends InventoryItem implements Discountable
{
    private double lengthInFeet;
    private String boardType;

    public Surfboard(String itemId, String name, double price, int quantity,
                     double lengthInFeet, String boardType) throws InvalidInventoryException
    {
        super(itemId, name, price, quantity);
        setLengthInFeet(lengthInFeet);
        setBoardType(boardType);
    }

    public double getLengthInFeet()
    {
        return lengthInFeet;
    }

    public void setLengthInFeet(double lengthInFeet) throws InvalidInventoryException
    {
        if (lengthInFeet <= 0)
        {
            throw new InvalidInventoryException("Surfboard length must be greater than zero.");
        }
        this.lengthInFeet = lengthInFeet;
    }

    public String getBoardType()
    {
        return boardType;
    }

    public void setBoardType(String boardType) throws InvalidInventoryException
    {
        if (boardType == null || boardType.trim().isEmpty())
        {
            throw new InvalidInventoryException("Board type cannot be blank.");
        }
        this.boardType = boardType.trim();
    }

    @Override
    public String getCategory()
    {
        return "Surfboard";
    }

    @Override
    public String getDetails()
    {
        return lengthInFeet + " ft " + boardType;
    }

    @Override
    public void applyDiscount(double percentOff) throws InvalidInventoryException
    {
        if (percentOff < 0 || percentOff > 100)
        {
            throw new InvalidInventoryException("Discount must be between 0 and 100.");
        }
        setPrice(getPrice() * (1 - percentOff / 100));
    }

    @Override
    public String toFileString()
    {
        return "Surfboard," + getItemId() + "," + getName() + "," + getPrice()
                + "," + getQuantity() + "," + lengthInFeet + "," + boardType;
    }
}
