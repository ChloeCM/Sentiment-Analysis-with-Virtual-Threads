package ie.atu.sw;

import java.io.File;
import java.util.Scanner;

/**
 * Manages the output directory for storing results.
 * Provides functionalities for setting and verifying the output directory path.
 */
public class OutputDirectory {
    private Scanner scanner = new Scanner(System.in);
    private boolean directoryCreationSuccessful = false;
    private String outputUserPath;

    /**
     * Constructor for OutputDirectory.
     * Initialises the output directory manager with a scanner for user input.
     * Complexity: O(1) - constant time complexity for initialising fields.
     *
     * @param scanner The scanner object for user input.
     */
    public OutputDirectory(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Getter for the output user path.
     * Complexity: O(1) - constant time for returning a field value.
     *
     * @return The output user path.
     */
    public String getUserPath() {
        return outputUserPath;
    }

    /**
     * Setter for the output user path.
     * Complexity: O(1) - constant time for setting a field value.
     *
     * @param userPath The path to set as the output directory.
     */
    public void setUserPath(String userPath) {
        this.outputUserPath = userPath;
    }

    /**
     * Organises the output directory by allowing the user to select or confirm the path.
     * Complexity: O(n), where n is the number of user interactions to set the path.
     */
    public void organiseOutputDirectory() {
        welcomeDisplay();
        if (outputUserPath == null) {
            selectOutputDirectory();
        } else {
            handleUserPath();
        }
    }

    private void handleUserPath() {
        if (userPathIsSet()) {
            while (true) {
                System.out.println("Current output is set to: " + outputUserPath);
                System.out.println("Would you like to change it? Y/N?");
                String userPath = scanner.nextLine();
                if (userPath.equalsIgnoreCase("y")) {
                    selectOutputDirectory();
                    return;
                } else if (userPath.equalsIgnoreCase("n")) {
                    System.out.println("Output of '" + outputUserPath + "' is unchanged");
                    return;
                } else {
                    System.out.println("Invalid Input!!! Please pres 'y' or 'n': ");
                }
            }
        }
    }

    /**
     * Checks if a valid user path is set.
     * Complexity: O(1) - constant time for checking values.
     *
     * @return True if a valid path is set, false otherwise.
     */
    private boolean userPathIsSet() {
        return this.outputUserPath != null && !this.outputUserPath.isEmpty();
    }

    /**
     * Displays a header for the output directory selection menu.
     * Complexity: O(1) - constant time as it prints content.
     */
    private static void welcomeDisplay() {
        System.out.println("_____________________________________");
        System.out.println("                                     ");
        System.out.println("        The Output directory         ");
        System.out.println("_____________________________________");
        System.out.println(" ");
    }

    /**
     * Allows the user to select an output directory.
     * The method reads user input for a path and validates or offers to create it.
     * Complexity: O(n), where n is the number of times the user inputs a path before a valid one is entered or created.
     */
    private void selectOutputDirectory() {
        System.out.println("Please select an output directory or press 'q' to exit: ");

        while (true) {
            String userPath = scanner.nextLine();
            if (userPath.equalsIgnoreCase("q")) {
                System.out.println("Returning to main menu");
                return;
            } else if (isValidPath(userPath)) {
                confirmAndSetDirectory(userPath);
                return;
            } else {
                offerDirectoryCreation(userPath);
                if (directoryCreationSuccessful) {
                    return;
                }
            }
        }
    }

    /**
     * Validates whether a given path is a valid file or directory.
     * Complexity: O(1) - constant time for file system checks.
     *
     * @param path The path to be checked.
     * @return true if the path is valid, false otherwise.
     */
    private boolean isValidPath(String path) {
        File fileOrDirectory = new File(path);
        return fileOrDirectory.exists() && fileOrDirectory.isFile() || fileOrDirectory.isDirectory();
    }

    /**
     * Offers the user the option to create a new directory if it does not exist.
     * Asks the user for confirmation before attempting to create a directory.
     * Complexity: O(n), where n is the number of times the user is prompted before providing a valid response.
     */
    private void offerDirectoryCreation(String path) {
        while (true) {
            System.out.println("The directory '" + path + "' does not exist. Do you want to create it? (y/n)");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("y")) {
                if (createDirectory(path)) {
                    System.out.println("Directory created successfully.");
                    setUserPath(path);
                    directoryCreationSuccessful = true;
                    return;
                } else {
                    System.out.println("Couldn't create directory. Please try again.");
                    return;
                }
            } else if (userInput.equalsIgnoreCase("n")) {
                System.out.println("Directory not created.");
                System.out.println("Please select an output directory or press 'q' to exit");
                break;
            } else {
                System.out.println("Opps!!! Invalid Input!!!");
            }
        }
    }

    /**
     * Attempts to create a new directory at the specified path.
     * Complexity: O(1) for the directory creation operation.
     *
     * @param path The path where the directory should be created.
     * @return True if the directory was successfully created, false otherwise.
     */
    private boolean createDirectory(String path) {
        File newDirectory = new File(path);
        return newDirectory.mkdirs();
    }

    /**
     * Confirms with the user and sets the specified directory as the output directory.
     * Asks the user to confirm using the selected directory and sets it if confirmed.
     * Complexity: O(n), where n is the number of times the user is prompted before providing a valid response.
     *
     * @param path The selected directory path to be confirmed and set as the output directory.
     */
    private void confirmAndSetDirectory(String path) {
        System.out.println("Directory '" + path + "' selected. Do you want to use this directory? (y/n)");

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("y")) {
                setUserPath(path);
                System.out.println("Output directory is set to: " + path);
                break;
            } else if (userInput.equalsIgnoreCase("n")) {
                System.out.println("Directory not set. Returning to directory selection...");
                selectOutputDirectory();
                break;
            } else {
                System.out.println("Invalid Input! Please enter 'y' for Yes or 'n' for No.");
            }
        }
    }

}
