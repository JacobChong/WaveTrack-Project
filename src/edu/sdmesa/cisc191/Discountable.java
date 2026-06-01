package edu.sdmesa.cisc191;

/**
 * Lead Author(s):
 * @author Jacob Chong
 *
 * Other contributors:
 * None
 *
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 * Version/date: Week 2
 *
 * Responsibilities of interface:
 * Discountable is an interface.
 * It describes behavior that some inventory items can have.
 * A class that implements Discountable promises that it can apply a discount.
 */
public interface Discountable
{
	/**
	 * Classes that implement this interface must define how a discount changes the price.
	 *
	 * @param percentOff percentage off, such as 10 for 10 percent
	 * @throws InvalidInventoryException if the discount is invalid
	 */
	void applyDiscount(double percentOff) throws InvalidInventoryException;
}