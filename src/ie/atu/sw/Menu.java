package ie.atu.sw;

import java.util.Scanner;

/**
 * Abstract base class for menus in the Sentiment Analysis application.
 * Provides a structure for displaying a menu, handling user choices, and returning to the menu.
 */
public abstract class Menu {
    protected Scanner scanner;

    /**
     * Constructs a Menu with a given scanner.
     * Complexity: O(1) - constant time complexity for initialising the scanner reference.
     *
     * @param scanner The scanner object to be used for user input.
     */
    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Displays the main menu to the user.
     */
    protected abstract void displayMainMenu();

    /**
     * Handles the user's menu choices.
     */
    protected abstract void userMenuChoice();

    /**
     * Returns the user to the main menu.
     */
    protected abstract void returnToMenu();

}
