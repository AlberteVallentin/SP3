package org.example;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;

// UserMenu class handles user interactions and account management
public class UserMenu {

    // File object representing the "Accounts.txt" file with an absolute path
    File file = new File("Accounts/Accounts.txt");

    // Static instance of TextUI for handling user input/output
    static TextUI ui = new TextUI();

    // ArrayList to store User objects (not used in the provided code)
    private ArrayList<User> users;

    // Map to store user credentials (username, password)
    protected Map<String, String> userCredentials;

    // Variables to store user input (username and password)
    String userName;
    String passWord;


    public void runUserSetupDialog() {
        // Implementation details can be added here
    }


    public void displayUsers() {
        // Implementation details can be added here
    }

    // Method to check user login credentials
    public boolean userLogIn(String userName, String passWord) {
        try {
            // Create a Scanner to read from the file
            Scanner scan = new Scanner(file);

            // Iterate through each line in the file
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] lineChop;
                lineChop = line.split(";");

                // Extract username and password from the split line
                String userN = lineChop[0].trim();
                String userP = lineChop[1].trim();

                // Check if provided credentials match any in the file
                if (userName.equals(userN) && passWord.equals(userP)) {
                    return true; // Login successful
                }
            }
            return false; // Login unsuccessful
        } catch (Exception e) {
            // Handle runtime exceptions
            System.out.println("Runtime exception...");
            throw new RuntimeException(e);
        }
    }


    public void logOut() {
        // Implementation details can be added here
    }

    // Method to create a new user account
    public void createAccount() {
        // Create a new HashMap to store usernames and passwords
        userCredentials = new HashMap<>();

        String input = "";

        // Use TextUI to get the username from the user
        input = ui.getInput("Enter username: ");
        userName = input.trim();

        // Check if the entered username is already taken
        while (userNameTaken(userName)) {
            System.out.println("Please try another username");
            input = ui.getInput("Enter username: ");
            userName = input.trim();
        }

        // Get the password from the user
        input = ui.getInput("Enter password: ");
        passWord = input.trim();

        // Prompt the user to try again if the password is blank
        if (passWord.isBlank()) {
            System.out.println("Try again");
            input = ui.getInput("Enter password: ");
            passWord = input.trim();
        }

        // Add the username and password to the HashMap
        userCredentials.put(userName, passWord);

        // Save user data to the file "Accounts.txt"
        saveUserData(userCredentials, "Accounts/Accounts.txt");
    }

    // Method to check if a username is already taken
    private boolean userNameTaken(String userName) {
        try {
            // Check if the username is blank
            if (userName.isBlank()) {
                return true;
            }

            // Create a Scanner to read from the file
            Scanner scan = new Scanner(file);

            // Iterate through each line in the file
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] lineChop;
                lineChop = line.split(";");

                // Extract the username from the split line
                String userN = lineChop[0].trim();

                // Return true if the entered username already exists in the file
                if (userName.equals(userN)) {
                    return true;
                }
            }
        } catch (Exception e) {
            // Handle runtime exceptions
            System.out.println("Runtime exception...");
            throw new RuntimeException(e);
        }
        return false; // Username is not taken
    }

    // Method to save user data to a file
    protected static void saveUserData(Map<String, String> userCredentials, String path) {
        try (FileWriter writer = new FileWriter(path, true)) {
            // Iterate through all usernames and passwords in userCredentials using a foreach loop
            for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
                // Create a line containing the username and password, separated by a semicolon
                String lineToWrite = entry.getKey() + ";" + entry.getValue();
                // Write the line to the file
                writer.write("\n" + lineToWrite);
            }
            // Print a message indicating that user data has been saved
            System.out.println("User data saved.");
        } catch (IOException e) {
            // Handle any errors during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public void playMedia() {
        // Implementation details can be added here
    }

    public void deleteSavedMedia() {

    }
}

