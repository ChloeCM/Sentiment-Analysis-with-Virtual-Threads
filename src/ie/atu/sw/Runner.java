package ie.atu.sw;

/**
 * Entry point for the Sentiment Analysis application.
 * This class contains the main method to start the application.
 */
public class Runner {

    /**
     * Main method to start the Sentiment Analysis application.
     * Initialises and displays the main menu of the application.
     * Complexity: O(1) - constant time for method invocation, but the actual complexity depends
     * on the menu.
     *
     * @param args Command line arguments (not used in this application).
     * @throws Exception Exceptions that may occur during the execution of the application.
     */
    public static void main(String[] args) throws Exception {
        new MainMenu().startMenu();
    }

}
