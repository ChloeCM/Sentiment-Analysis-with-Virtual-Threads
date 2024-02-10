package ie.atu.sw;

import java.io.File;
import java.util.Scanner;

/**
 * Manages file paths for tweets and lexicons.
 * This class provides functionalities to set and get paths for tweet files and lexicon files.*
 *
 * @author Chloe
 */
public class FilePathManager implements PathManager {
    private Scanner scanner;
    private String tweetFilePath;
    private String lexiconFilePath;
    private String currentPathType;

    /**
     * Constructs a FilePathManager with a given scanner.
     * Complexity: O(1) - constant time complexity.
     *
     * @param scanner Scanner used for user input.
     */
    public FilePathManager(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Gets the file path for tweets.
     * Complexity: O(1) - constant time complexity as it's a return operation.
     *
     * @return The current set file path for tweets as a String.
     */
    public String getTweetFilePath() {
        return tweetFilePath;
    }

    /**
     * Gets the file path for lexicon.
     * Complexity: O(1) - constant time complexity as it's a return operation.
     *
     * @return The current set file path for lexicon as a String.
     */
    public String getLexiconFilePath() {
        return lexiconFilePath;
    }

    /**
     * Organises the input directory based on the specified path type.
     * This method prompts the user to set a path if not already set, or change the existing path.
     * Complexity: O(n) in the worst case, where n is the number of times the user inputs a path before a valid one is entered.
     *
     * @param pathType The type of path to organise.
     */
    public void organiseInputDirectory(String pathType) {
        this.currentPathType = pathType;
        welcomeDisplay(pathType);
        String currentPath = getCurrentPath();

        if (currentPath == null) {
            selectPath();
        } else {
            handleUserPath();
        }
    }

    /**
     * Allows the user to select a path.
     * The method reads user input for a path and validates it.
     * Complexity: O(n) in the worst case, where n is the number of times the user inputs a path before a valid one is entered.
     */
    public void selectPath() {
        System.out.println("Enter a file or directory path, or 'q' to quit: ");
        boolean running = true;

        while (running) {
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("q")) {
                System.out.println("Exiting path selection.");
                running = false;
            } else if (isValidPath(userInput)) {
                setUserPath(userInput);
                System.out.println("Path set to: " + userInput);
                running = false;
            } else {
                System.out.println("Invalid path. Please try again.");
            }
        }
    }

    /**
     * Retrieves the current path based on the path type.
     * Complexity: O(1) - constant time complexity as it involves conditional checks.
     *
     * @return The current path for tweets or lexicons.
     */
    private String getCurrentPath() {
        if ("tweet".equals(currentPathType)) {
            return tweetFilePath;
        } else if ("lexicon".equals(currentPathType)) {
            return lexiconFilePath;
        }
        return null;
    }

    /**
     * Displays a welcome message specific to the path type.
     * Complexity: O(1) - constant time complexity as it only prints content.
     *
     * @param pathType The type of path ('tweet' or 'lexicon') for the welcome message.
     */
    private static void welcomeDisplay(String pathType) {
        System.out.println("________________________________________");
        System.out.println("                                     ");
        System.out.println("     The " + pathType + " file/ directory       ");
        System.out.println("________________________________________");
        System.out.println(" ");
    }

    /**
     * Handles user interaction for updating the current path.
     * Asks the user if they want to change the path and acts accordingly.
     * Complexity: O(n) in the worst case, where n is the number of times the user is prompted before giving a valid response.
     */
    private void handleUserPath() {
        String currentPath = getCurrentPath();
        if (currentPath != null && !currentPath.isEmpty()) {
            while (true) {
                System.out.println("Current path: " + currentPath);
                System.out.println("Would you like to change the path? (y/n): ");
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase("n")) {
                    return;
                } else if (userInput.equalsIgnoreCase("y")) {
                    selectPath();
                    return;
                } else {
                    System.out.println("Invalid input. Please select 'y' or 'n'.");
                }
            }
        } else {
            selectPath();
        }
    }

    /**
     * Validates whether a given path is a valid file or directory.
     * Complexity: O(1) - constant time complexity for file system checks.
     *
     * @param path The path to be checked.
     * @return true if the path is valid, false otherwise.
     */
    private boolean isValidPath(String path) {
        File fileOrDirectory = new File(path);
        if (!fileOrDirectory.exists()) {
            System.out.println("Path does not exist: " + path);
            return false;
        } else if (!fileOrDirectory.isFile() && !fileOrDirectory.isDirectory()) {
            System.out.println("Path is not a valid file or directory: " + path);
            return false;
        }
        return true;
    }

    /**
     * Sets the user-defined path for the current path type.
     * Complexity: O(1) - constant time complexity as it involves basic assignment operations.
     *
     * @param userPath The path specified by the user.
     */
    private void setUserPath(String userPath) {
        if ("tweet".equals(currentPathType)) {
            this.tweetFilePath = userPath;
        } else if ("lexicon".equals(currentPathType)) {
            this.lexiconFilePath = userPath;
        }
    }

}

