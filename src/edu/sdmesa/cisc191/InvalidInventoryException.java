package edu.sdmesa.cisc191;

/**
 * Lead Author(s): Jacob Chong
 * Responsibilities of class: Represents invalid inventory input so the program can fail gracefully.
 */
public class InvalidInventoryException extends Exception
{
    public InvalidInventoryException(String message)
    {
        super(message);
    }
}
