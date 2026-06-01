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
 * Responsibilities of class:
 * InvalidInventoryException is used when invalid inventory data is entered.
 */
public class InvalidInventoryException extends Exception
{
	/**
	 * Creates a custom inventory exception.
	 *
	 * @param message explanation of the problem
	 */
	public InvalidInventoryException(String message)
	{
	// Passes the error message to the parent Exception class.
		super(message);
	}
}