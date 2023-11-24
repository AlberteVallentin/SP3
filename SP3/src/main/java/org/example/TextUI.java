package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    private Scanner scan = new Scanner(System.in);

    //shows a message and returns the user's input as a String
    public String getInput(String msg){
        this.displayMessage(msg);
        return scan.nextLine();
    }


    public void displayMessage(String msg){
        System.out.println(msg);

    }


    public int getNumericInput(String msg){
        System.out.println(msg);//Stille brugeren et spørgsmål

        // int input = scan.nextInt();
        // This is ok, but BEWARE OF THE SCANNERBUG: When using nextInt() right before nextLine(): the nextLine call will be skipped...
        // fix this by simply calling nextLine() once before you actually need it
        // Another fix: read it as string, then parse it:

        String input = scan.nextLine();          //Give brugere et sted at placere sit svar og vente på svaret
        int num = 0;
        try {
            num = Integer.parseInt(input);       //Konvertere svaret til et tal

        }catch (NumberFormatException e){
            System.out.println("This was not a number, try again.");
            num = getNumericInput(msg);

        }
        return num;

    }
    /*
     *
     * ' shows a message, lists the content of a list and returns the user's choice
     * */
    public String getChoice(ArrayList<String> options, String msg){
        System.out.println(msg);
        //  diplayMenu(options);
        String input = getInput("");

        //tjek om input findes i listen, hvis ikke, smid en exception
        if(!options.contains(input)){
            System.out.println("findes ikke på listen");
            input = getChoice(options, msg);
        }

        return input;
    }


    public void diplayMenu(ArrayList<String> options) {

        for(int i = 0; i <options.size();i++){

            System.out.println(options.get(i));
        }

    }
}


