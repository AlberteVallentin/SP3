package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    private Scanner scan = new Scanner(System.in);

    //Shows a message and returns the user's input as a String
    public String getInput(String msg){
        this.displayMessage(msg);
        return scan.nextLine();
    }


    public void displayMessage(String msg){
        System.out.println(msg);

    }


    // Get numeric input from the user with a given message
    public int getNumericInput(String msg){
        System.out.println(msg);

        // Read user input as a string to avoid Scanner bug
        String input = scan.nextLine();          // Provide users a place to enter their response and wait for it

        int num = 0;
        try {
            // Parse the input string to an integer
            num = Integer.parseInt(input);       // Convert the user's response to a number
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            System.out.println("This was not a number, try again.");
            // Recursively call getNumericInput to get a valid input
            num = getNumericInput(msg);
        }
        return num;
    }

    // Get user choice from a list of options with a given message
    public String getChoice(ArrayList<String> options, String msg){
        System.out.println(msg);

        // Get user input as a string
        String input = getInput(""); // Assuming that there is a method named getInput which is being called here

        // Check if the input is in the list of options, if not, prompt the user again
        if(!options.contains(input)){
            System.out.println("Does not exist in the list");
            // Recursively call getChoice to get a valid input
            input = getChoice(options, msg);
        }

        return input;
    }
}


