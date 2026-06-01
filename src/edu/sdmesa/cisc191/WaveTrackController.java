package edu.sdmesa.cisc191;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
 * Version/date: Week 8
 *
 * Responsibilities of class:
 * WaveTrackController connects the GUI to the inventory model.
 * This class is the Controller part of MVC.
 * It adds event handling so the buttons in the GUI perform actions.
 */
public class WaveTrackController
{
	// WaveTrackController has-a InventoryManager.
	// The manager is the model that stores and manages inventory data.
	private InventoryManager manager;

	// WaveTrackController has-a WaveTrackView.
	// The view displays the GUI to the user.
	private WaveTrackView view;

	// WaveTrackController has-a InventoryFileHandler.
	// The file handler saves and loads inventory using text files.
	private InventoryFileHandler fileHandler;

	// The controller has-a file name for saving and loading inventory.
	private String fileName;

	/**
	 * Creates the controller and connects the model, view, and file handler.
	 *
	 * @param manager the inventory manager model
	 * @param view the GUI view
	 * @param fileHandler the file handler for text file I/O
	 */
	public WaveTrackController(InventoryManager manager, WaveTrackView view, InventoryFileHandler fileHandler)
	{
		this.manager = manager;
		this.view = view;
		this.fileHandler = fileHandler;

		fileName = "inventory.txt";

		addActionListeners();
		refreshInventoryDisplay();
	}

	/**
	 * Adds event listeners to each GUI button.
	 * Each button is connected to a different helper method.
	 */
	private void addActionListeners()
	{
		view.getAddButton().addActionListener(event -> handleAddItem());
		view.getRemoveButton().addActionListener(event -> handleRemoveItem());
		view.getSearchButton().addActionListener(event -> handleSearchItem());
		view.getUpdateQuantityButton().addActionListener(event -> handleUpdateQuantity());
		view.getLowStockButton().addActionListener(event -> handleLowStock());
		view.getSaveButton().addActionListener(event -> handleSaveInventory());
		view.getLoadButton().addActionListener(event -> handleLoadInventory());
		view.getClearButton().addActionListener(event -> view.clearInputFields());
		view.getShowAllButton().addActionListener(event -> refreshInventoryDisplay());
	}

	/**
	 * Handles the Add Item button.
	 * This method reads input from the GUI, creates the correct object type,
	 * and adds that object to the InventoryManager.
	 */
	private void handleAddItem()
	{
		try
		{
			InventoryItem item = createItemFromInput();
			manager.addItem(item);

			refreshInventoryDisplay();
			view.clearInputFields();

			showMessage("Item added successfully.");
		}
		catch (InvalidInventoryException exception)
		{
			showMessage("Inventory error: " + exception.getMessage());
		}
		catch (NumberFormatException exception)
		{
			showMessage("Number error: Please enter valid numbers for price, quantity, and surfboard length.");
		}
	}

	/**
	 * Creates the correct InventoryItem subclass based on the selected category.
	 * This demonstrates polymorphism because the return type is InventoryItem,
	 * but the actual object may be a Surfboard, Wetsuit, or Accessory.
	 *
	 * @return a new InventoryItem object
	 * @throws InvalidInventoryException if inventory data is invalid
	 * @throws NumberFormatException if number input is invalid
	 */
	private InventoryItem createItemFromInput() throws InvalidInventoryException, NumberFormatException
	{
		String category = view.getSelectedCategory();

		String itemId = view.getItemIdText();
		String name = view.getNameText();
		double price = Double.parseDouble(view.getPriceText());
		int quantity = Integer.parseInt(view.getQuantityText());

		String detailOne = view.getDetailOneText();
		String detailTwo = view.getDetailTwoText();

		// Surfboard is-a InventoryItem.
		if (category.equals("Surfboard"))
		{
			double lengthInFeet = Double.parseDouble(detailOne);
			String boardType = detailTwo;

			return new Surfboard(itemId, name, price, quantity, lengthInFeet, boardType);
		}
		// Wetsuit is-a InventoryItem.
		else if (category.equals("Wetsuit"))
		{
			String size = detailOne;
			String thickness = detailTwo;

			return new Wetsuit(itemId, name, price, quantity, size, thickness);
		}
		// Accessory is-a InventoryItem.
		else
		{
			String accessoryType = detailOne;

			return new Accessory(itemId, name, price, quantity, accessoryType);
		}
	}

	/**
	 * Handles the Remove Item button.
	 * The item ID field is used to decide which item should be removed.
	 */
	private void handleRemoveItem()
	{
		String itemId = view.getItemIdText();

		boolean removed = manager.removeItemById(itemId);

		if (removed)
		{
			refreshInventoryDisplay();
			view.clearInputFields();
			showMessage("Item removed successfully.");
		}
		else
		{
			showMessage("Item was not found. Make sure the item ID is correct.");
		}
	}

	/**
	 * Handles the Search button.
	 * The name field is used as the search keyword.
	 */
	private void handleSearchItem()
	{
		String keyword = view.getNameText();

		ArrayList<InventoryItem> results = manager.searchByName(keyword);

		if (results.isEmpty())
		{
			view.setInventoryDisplayText("No items matched the search.");
		}
		else
		{
			String text = "Search results:\n\n";

			for (InventoryItem item : results)
			{
				text += item.toString() + "\n";
			}

			view.setInventoryDisplayText(text);
		}
	}

	/**
	 * Handles the Update Quantity button.
	 * The item ID field chooses the item, and the quantity field gives the new quantity.
	 */
	private void handleUpdateQuantity()
	{
		try
		{
			String itemId = view.getItemIdText();
			int newQuantity = Integer.parseInt(view.getQuantityText());

			manager.updateQuantity(itemId, newQuantity);

			refreshInventoryDisplay();
			showMessage("Quantity updated successfully.");
		}
		catch (InvalidInventoryException exception)
		{
			showMessage("Inventory error: " + exception.getMessage());
		}
		catch (NumberFormatException exception)
		{
			showMessage("Number error: Quantity must be a whole number.");
		}
	}

	/**
	 * Handles the Low Stock button.
	 * This displays all items with quantity 3 or less.
	 */
	private void handleLowStock()
	{
		ArrayList<InventoryItem> lowStockItems = manager.getLowStockItems();

		if (lowStockItems.isEmpty())
		{
			view.setInventoryDisplayText("No low stock items.");
		}
		else
		{
			String text = "Low stock items:\n\n";

			for (InventoryItem item : lowStockItems)
			{
				text += item.toString() + "\n";
			}

			view.setInventoryDisplayText(text);
		}
	}

	/**
	 * Handles the Save button.
	 * This saves the current inventory to a text file.
	 */
	private void handleSaveInventory()
	{
		try
		{
			fileHandler.saveInventory(manager, fileName);
			showMessage("Inventory saved to " + fileName + ".");
		}
		catch (FileNotFoundException exception)
		{
			showMessage("File error: Could not save inventory.");
		}
	}

	/**
	 * Handles the Load button.
	 * This loads inventory from a text file and replaces the current inventory.
	 */
	private void handleLoadInventory()
	{
		try
		{
			ArrayList<InventoryItem> loadedItems = fileHandler.loadInventory(fileName);

			manager.clearInventory();
			manager.addItems(loadedItems);

			refreshInventoryDisplay();

			showMessage("Inventory loaded from " + fileName + ".");
		}
		catch (FileNotFoundException exception)
		{
			showMessage("File error: Could not find " + fileName + ". Save inventory first.");
		}
		catch (InvalidInventoryException exception)
		{
			showMessage("Inventory error while loading file: " + exception.getMessage());
		}
	}

	/**
	 * Refreshes the text area so it displays the current inventory.
	 */
	private void refreshInventoryDisplay()
	{
		view.setInventoryDisplayText(manager.getInventoryDisplayText());
	}

	/**
	 * Shows a pop-up message to the user.
	 *
	 * @param message the message to display
	 */
	private void showMessage(String message)
	{
		JOptionPane.showMessageDialog(view, message);
	}
}