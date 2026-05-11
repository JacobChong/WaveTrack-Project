package edu.sdmesa.cisc191;

/**
 * Represents items that can have a discount applied.
 */
public interface Discountable
{
    void applyDiscount(double percentOff) throws InvalidInventoryException;
}
