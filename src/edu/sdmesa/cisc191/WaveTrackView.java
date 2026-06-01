package edu.sdmesa.cisc191;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
 * WaveTrackView creates the graphical user interface for the inventory manager.
 * This class is the View part of MVC. It displays text fields, buttons,
 * labels, and an inventory display area.
 */
public class WaveTrackView extends JFrame
{
	// WaveTrackView has-a JTextField for each piece of user input.
	private JTextField itemIdField;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField quantityField;
	private JTextField detailOneField;
	private JTextField detailTwoField;

	// These labels change depending on the selected category.
	private JLabel detailOneLabel;
	private JLabel detailTwoLabel;

	// WaveTrackView has-a combo box so the user can choose an item category.
	private JComboBox<String> categoryComboBox;

	// WaveTrackView has-many buttons that the controller will connect to actions.
	private JButton addButton;
	private JButton removeButton;
	private JButton searchButton;
	private JButton updateQuantityButton;
	private JButton lowStockButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton clearButton;
	private JButton showAllButton;

	// WaveTrackView has-a text area to display inventory information.
	private JTextArea inventoryTextArea;

	/**
	 * Creates the WaveTrack GUI window.
	 * This constructor sets up the layout and calls helper methods to build each part of the GUI.
	 */
	public WaveTrackView()
	{
		setTitle("WaveTrack Surf Shop Inventory Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		createInputPanel();
		createButtonPanel();
		createInventoryDisplay();

		updateDetailLabels();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Creates the top input panel where the user can type item information.
	 * This panel contains labels, text fields, and a combo box for selecting the category.
	 */
	private void createInputPanel()
	{
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(8, 2));

		JLabel titleLabel = new JLabel("Enter Inventory Item Information");
		JLabel instructionLabel = new JLabel("Choose a category and fill in the fields.");

		JLabel categoryLabel = new JLabel("Category:");
		categoryComboBox = new JComboBox<String>();
		categoryComboBox.addItem("Surfboard");
		categoryComboBox.addItem("Wetsuit");
		categoryComboBox.addItem("Accessory");

		// When the category changes, the detail labels update to guide the user.
		categoryComboBox.addActionListener(event -> updateDetailLabels());

		JLabel itemIdLabel = new JLabel("Item ID:");
		itemIdField = new JTextField(15);

		JLabel nameLabel = new JLabel("Name:");
		nameField = new JTextField(15);

		JLabel priceLabel = new JLabel("Price:");
		priceField = new JTextField(15);

		JLabel quantityLabel = new JLabel("Quantity:");
		quantityField = new JTextField(15);

		detailOneLabel = new JLabel("Detail 1:");
		detailOneField = new JTextField(15);

		detailTwoLabel = new JLabel("Detail 2:");
		detailTwoField = new JTextField(15);

		inputPanel.add(titleLabel);
		inputPanel.add(instructionLabel);

		inputPanel.add(categoryLabel);
		inputPanel.add(categoryComboBox);

		inputPanel.add(itemIdLabel);
		inputPanel.add(itemIdField);

		inputPanel.add(nameLabel);
		inputPanel.add(nameField);

		inputPanel.add(priceLabel);
		inputPanel.add(priceField);

		inputPanel.add(quantityLabel);
		inputPanel.add(quantityField);

		inputPanel.add(detailOneLabel);
		inputPanel.add(detailOneField);

		inputPanel.add(detailTwoLabel);
		inputPanel.add(detailTwoField);

		add(inputPanel, BorderLayout.NORTH);
	}

	/**
	 * Creates the center button panel.
	 * These buttons are connected to event handling in the controller.
	 */
	private void createButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 3));

		addButton = new JButton("Add Item");
		removeButton = new JButton("Remove Item");
		searchButton = new JButton("Search");
		updateQuantityButton = new JButton("Update Quantity");
		lowStockButton = new JButton("Low Stock");
		saveButton = new JButton("Save");
		loadButton = new JButton("Load");
		clearButton = new JButton("Clear Fields");
		showAllButton = new JButton("Show All");

		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(searchButton);
		buttonPanel.add(updateQuantityButton);
		buttonPanel.add(lowStockButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(showAllButton);

		add(buttonPanel, BorderLayout.CENTER);
	}

	/**
	 * Creates the inventory display area at the bottom of the GUI.
	 * A scroll pane is used so the user can scroll if there are many inventory items.
	 */
	private void createInventoryDisplay()
	{
		inventoryTextArea = new JTextArea(15, 60);
		inventoryTextArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(inventoryTextArea);
		add(scrollPane, BorderLayout.SOUTH);
	}

	/**
	 * Updates the detail labels depending on the category selected.
	 * This makes the GUI easier to use because the user knows what each detail field means.
	 */
	public void updateDetailLabels()
	{
		String category = getSelectedCategory();

		if (category.equals("Surfboard"))
		{
			detailOneLabel.setText("Length in Feet:");
			detailTwoLabel.setText("Board Type:");
			detailTwoField.setEnabled(true);
		}
		else if (category.equals("Wetsuit"))
		{
			detailOneLabel.setText("Size:");
			detailTwoLabel.setText("Thickness:");
			detailTwoField.setEnabled(true);
		}
		else
		{
			detailOneLabel.setText("Accessory Type:");
			detailTwoLabel.setText("Detail 2 Not Needed:");
			detailTwoField.setText("");
			detailTwoField.setEnabled(false);
		}
	}

	/**
	 * Gets the selected item category from the combo box.
	 *
	 * @return selected category
	 */
	public String getSelectedCategory()
	{
		return (String) categoryComboBox.getSelectedItem();
	}

	/**
	 * Gets the item ID typed by the user.
	 *
	 * @return item ID text
	 */
	public String getItemIdText()
	{
		return itemIdField.getText();
	}

	/**
	 * Gets the item name typed by the user.
	 *
	 * @return item name text
	 */
	public String getNameText()
	{
		return nameField.getText();
	}

	/**
	 * Gets the item price typed by the user.
	 *
	 * @return price text
	 */
	public String getPriceText()
	{
		return priceField.getText();
	}

	/**
	 * Gets the quantity typed by the user.
	 *
	 * @return quantity text
	 */
	public String getQuantityText()
	{
		return quantityField.getText();
	}

	/**
	 * Gets the first detail typed by the user.
	 *
	 * @return detail one text
	 */
	public String getDetailOneText()
	{
		return detailOneField.getText();
	}

	/**
	 * Gets the second detail typed by the user.
	 *
	 * @return detail two text
	 */
	public String getDetailTwoText()
	{
		return detailTwoField.getText();
	}

	/**
	 * Gives access to the Add button so the controller can attach an ActionListener.
	 *
	 * @return add button
	 */
	public JButton getAddButton()
	{
		return addButton;
	}

	/**
	 * Gives access to the Remove button so the controller can attach an ActionListener.
	 *
	 * @return remove button
	 */
	public JButton getRemoveButton()
	{
		return removeButton;
	}

	/**
	 * Gives access to the Search button so the controller can attach an ActionListener.
	 *
	 * @return search button
	 */
	public JButton getSearchButton()
	{
		return searchButton;
	}

	/**
	 * Gives access to the Update Quantity button so the controller can attach an ActionListener.
	 *
	 * @return update quantity button
	 */
	public JButton getUpdateQuantityButton()
	{
		return updateQuantityButton;
	}

	/**
	 * Gives access to the Low Stock button so the controller can attach an ActionListener.
	 *
	 * @return low stock button
	 */
	public JButton getLowStockButton()
	{
		return lowStockButton;
	}

	/**
	 * Gives access to the Save button so the controller can attach an ActionListener.
	 *
	 * @return save button
	 */
	public JButton getSaveButton()
	{
		return saveButton;
	}

	/**
	 * Gives access to the Load button so the controller can attach an ActionListener.
	 *
	 * @return load button
	 */
	public JButton getLoadButton()
	{
		return loadButton;
	}

	/**
	 * Gives access to the Clear button so the controller can attach an ActionListener.
	 *
	 * @return clear button
	 */
	public JButton getClearButton()
	{
		return clearButton;
	}

	/**
	 * Gives access to the Show All button so the controller can attach an ActionListener.
	 *
	 * @return show all button
	 */
	public JButton getShowAllButton()
	{
		return showAllButton;
	}

	/**
	 * Sets the inventory display text.
	 * The controller can call this method to update what the user sees.
	 *
	 * @param text text to display
	 */
	public void setInventoryDisplayText(String text)
	{
		inventoryTextArea.setText(text);
	}

	/**
	 * Clears all input fields.
	 * This is useful after adding an item or when the user clicks the Clear Fields button.
	 */
	public void clearInputFields()
	{
		itemIdField.setText("");
		nameField.setText("");
		priceField.setText("");
		quantityField.setText("");
		detailOneField.setText("");
		detailTwoField.setText("");
	}
}