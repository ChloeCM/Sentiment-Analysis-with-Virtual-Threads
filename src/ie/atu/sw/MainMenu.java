package ie.atu.sw;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.*;

/**
 * Main menu for the Sentiment Analysis application.
 * This class provides a user interface to interact with the application,
 * allowing users to specify file paths, execute analysis, and access other options.
 */
public class MainMenu extends Menu {
    private FilePathManager inputDirectory;
    private OutputDirectory outputDirectory;
    private FilePathManager lexiconDirectory;
    private Options options;

    /**
     * Constructor for MainMenu.
     * Initialises file path managers and options.
     * Complexity: O(1) - constant time complexity as it involves only object initialization.
     */
    public MainMenu() {
        super(new Scanner(System.in));
        this.inputDirectory = new FilePathManager(this.scanner);
        this.outputDirectory = new OutputDirectory(this.scanner);
        this.lexiconDirectory = new FilePathManager(this.scanner);
        this.options = new Options(inputDirectory, outputDirectory, lexiconDirectory, this.scanner);
    }

    /**
     * Starts the main menu of the application.
     * Displays the main menu and processes user choices.
     * Complexity: O(n), where n is the number of times user interacts with the menu.
     */
    public void startMenu() {
        openingDisplayMainMenu();
        displayMainMenu();
        userMenuChoice();
    }

    /**
     * Returns the user to the main menu.
     * Complexity: O(1) - constant time complexity as it involves method calls.
     */
    private void returningToMenu() {
        displayMainMenu();
        userMenuChoice();
    }

    /**
     * Displays the opening header of the main menu.
     * Complexity: O(1) - constant time complexity as it prints content.
     */
    private void openingDisplayMainMenu() {
        System.out.println(ConsoleColour.WHITE_BRIGHT);
        System.out.println("************************************************************");
        System.out.println("*                                                          *");
        System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
        System.out.println("*                                                          *");
        System.out.println("*           Virtual Threaded Sentiment Analyser            *");
        System.out.println("*                                                          *");
        System.out.println("*           Chloe Mills - Student ID: G00425733            *");
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
        System.out.println("");
    }

    /**
     * Displays the opening header of the main menu.
     * Complexity: O(1) - constant time complexity as it prints content.
     */
    @Override
    protected void displayMainMenu() {
        System.out.println("________________________________________________________________________________");
        System.out.println("");
        System.out.println(" (1) Specify a Text File");
        System.out.println(" (2) Specify an Output File - with results");
        System.out.println(" (3) Configure Lexicons");
        System.out.println(" (4) Execute, Analyse and Report");
        System.out.println(" (5) Options Menu");
        System.out.println(" (6) Quit the Program");

        System.out.print(ConsoleColour.CYAN_BOLD);
        System.out.println("");
        System.out.print(" Select Option: 1 - 6: ");
        System.out.println("");
    }

    /**
     * Processes user input to select different menu options.
     * Complexity: O(n), where n is the number of menu options user navigates through.
     */
    @Override
    protected void userMenuChoice() {
        boolean running = true;

        while (running) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        inputDirectory.organiseInputDirectory("tweet"); // For tweet file paths
                        returnToMenu();
                    }
                    case 2 -> {
                        outputDirectory.organiseOutputDirectory();
                        returnToMenu();
                    }
                    case 3 -> {
                        configureLexicons();
                        returnToMenu();
                    }

                    case 4 -> {
                        performSentimentAnalysis();
                        returnToMenu();
                    }
                    case 5 -> {
                        options.displayOptionsMenu();
                        returnToMenu();
                    }
                    case 6 -> closingMenuOption();
                    default -> {
                        System.out.println("Invalid Input! Please select an option above from 1 - 6: ");
                    }
                }

            } catch (InputMismatchException exception) {
                System.out.println("Invalid Input! Please select an option above from 1 - 6: ");
                scanner.nextLine();
            }
        }
    }

    /**
     * Directs the user back to the main menu.
     * Complexity: O(1) - constant time complexity as it involves method calls.
     */
    @Override
    protected void returnToMenu() {
        System.out.println("Returning you to main menu...");
        returningToMenu();
    }

    /**
     * Handles the option to close the application.
     * Asks the user for confirmation before exiting.
     * Complexity: O(1) in the typical case, but could be O(n) in the worst case where n is the number of times the user attempts to input a valid response.
     */
    private void closingMenuOption() {
        System.out.println("");
        System.out.println("Are you sure you would like to exit this program? Y/N?");

        while (true) {
            String userChoice = scanner.nextLine();

            if (userChoice.equalsIgnoreCase("y")) {
                closingProgramDisplay();
                System.exit(0);
            } else if (userChoice.equalsIgnoreCase("n")) {
                System.out.println("Returning you to the Main Menu Display...");
                returningToMenu();
                break;
            } else {
                System.out.println("Oopps!! Invalid Input!!!");
                System.out.println("Please press y/n to continue: ");
            }
        }

    }

    /**
     * Initiates the sentiment analysis process.
     * Checks for necessary file paths and calls the SentimentAnalysisManager to perform analysis.
     * Complexity: O(1), as the method primarily orchestrates calls to other methods, but actual complexity depends on the analysis performed.
     */
    private void performSentimentAnalysis() {
        String lexiconPath = inputDirectory.getLexiconFilePath();
        String tweetFilePath = inputDirectory.getTweetFilePath();
        String outputFilePath = outputDirectory.getUserPath();

        // Check if any path is null
        if (lexiconPath == null || tweetFilePath == null || outputFilePath == null) {
            System.err.println("Error: One or more required paths are not set.");
            if (lexiconPath == null) {
                System.err.println("Lexicon path is not set.");
            }
            if (tweetFilePath == null) {
                System.err.println("Tweet file path is not set.");
            }
            if (outputFilePath == null) {
                System.err.println("Output file path is not set.");
            }
            return;
        }

        System.out.println("Starting sentiment analysis...");
        System.out.println("Lexicon path: " + lexiconPath);
        System.out.println("Tweet file path: " + tweetFilePath);
        System.out.println("Output file path: " + outputFilePath);

        SentimentAnalysisManager analysisManager = new SentimentAnalysisManager();

        try {
            analysisManager.performAnalysis(lexiconPath, tweetFilePath, outputFilePath);
        } catch (IOException e) {
            System.err.println("Error in performing sentiment analysis: " + e.getMessage());
        }
    }

    /**
     * Configures lexicon file paths.
     * Complexity: O(1), as it delegates the task to FilePathManager.
     */
    private void configureLexicons() {
        inputDirectory.organiseInputDirectory("lexicon"); // Set lexicon file path
    }

    /**
     * Displays a closing message when exiting the program.
     * Complexity: O(1), as it only involves printing content to the console.
     */
    private void closingProgramDisplay() {
        System.out.println("_____________________________________");
        System.out.println("                                     ");
        System.out.println("                                     ");
        System.out.println("          Exiting Program            ");
        System.out.println("                                     ");
        System.out.println("              Goodbye!               ");
        System.out.println("                                     ");
        System.out.println("_____________________________________");
        System.out.println(" ");

    }
}
