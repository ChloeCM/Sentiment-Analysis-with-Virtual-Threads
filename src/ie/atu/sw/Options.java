package ie.atu.sw;

import java.util.*;

/**
 * Class responsible for managing extra options in the application.
 * This class allows users to view settings related to input, output, and lexicon paths.
 */
public class Options {
    private Scanner scanner;
    private FilePathManager inputDirectory;
    private OutputDirectory outputDirectory;
    private FilePathManager lexiconDirectory;

    /**
     * Constructor for Options.
     * Initialises the options with directory managers and scanner.
     * Complexity: O(1) - constant time complexity as it involves only object initialisation.
     *
     * @param inputDirectory   Manager for input directory paths.
     * @param outputDirectory  Manager for output directory paths.
     * @param lexiconDirectory Manager for lexicon directory paths.
     * @param scanner          Scanner used for user input.
     */
    public Options(FilePathManager inputDirectory, OutputDirectory outputDirectory,
                   FilePathManager lexiconDirectory, Scanner scanner) {
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
        this.lexiconDirectory = lexiconDirectory;
        this.scanner = scanner;
    }

    /**
     * Displays the options menu to the user.
     * Complexity: O(n), where n is the number of user interactions within the options menu.
     */
    public void displayOptionsMenu() {
        welcomeOptionsDisplay();
        handleUserOptionChoice();
    }

    /**
     * Displays a header for the options menu.
     * Complexity: O(1) - constant time as it prints static content.
     */
    private static void welcomeOptionsDisplay() {
        System.out.println("_____________________________________");
        System.out.println("                                     ");
        System.out.println("          Optional Menu              ");
        System.out.println("_____________________________________");
        System.out.println(" ");
    }

    /**
     * Displays a body for the options menu.
     * Complexity: O(1) - constant time as it prints content.
     */
    private void welcomeOptionsMessage() {
        System.out.println("Pick one of the following options below from 1 or 2: ");
        System.out.println("");
        System.out.println(" 1. View Settings");
        System.out.println(" 2. Return to Main Menu");
        System.out.println("");
    }

    /**
     * Handles the user's choice within the options menu.
     * Complexity: O(n), where n is the number of times the user interacts with the options menu.
     */
    private void handleUserOptionChoice() {
        while (true) {
            System.out.println("");
            welcomeOptionsMessage();
            System.out.println("");

            String input = scanner.nextLine();
            try {
                int userChoice = Integer.parseInt(input.trim());
                switch (userChoice) {
                    case 1:
                        viewUserSettings();
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid option. Please choose between 1 and 2.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Displays the current settings for the user.
     * Shows paths set for input, output, and lexicon directories.
     * Complexity: O(1) - constant time as it only involves returning values.
     */
    private void viewUserSettings() {
        String inputPath = inputDirectory.getTweetFilePath();
        String outputPath = outputDirectory.getUserPath();
        String lexiconPath = lexiconDirectory.getLexiconFilePath();

        System.out.println(" Input directory: " + (inputPath != null ? inputPath : "Not set"));
        System.out.println(" Output directory: " + (outputPath != null ? outputPath : "Not set"));
        System.out.println(" Lexicon directory: " + (lexiconPath != null ? lexiconPath : "Not set"));

    }

}
