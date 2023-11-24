package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Streaming {

    String path;
    private int chosenMedia;
    String usrName;
    private int whatToDoWithMovie;
    private int input;
    private String textInput;
    String fileName;
    private boolean movieChosen = false;
    UserMenu userMenu = new UserMenu();
    TextUI ui = new TextUI();
    int count=0;

    User user;

    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Serie> series = new ArrayList<>();

    public void login(){
        Scanner usrInput = new Scanner(System.in);
        System.out.println("To login - Please type your username: ");
        usrName = usrInput.nextLine();
        System.out.println("Now the password please: ");
        String usrPass = usrInput.nextLine();

        UserMenu userMenu = new UserMenu();
        if(userMenu.userLogIn(usrName,usrPass)){

        }
        else{
            System.out.println("Seems your credentials were incorrect... :(");
            login();
        }
        user = new User(usrName,usrPass);
    }

    public void createAccount(){
        userMenu.createAccount();
    }
    public void createNewAccountOrLogin(){
        if(input==1){
            login();
        }
        else if(input==2){
            createAccount();
            login();
        }
        else{
            input = ui.getNumericInput("Number missmatch - Please type one of the numbers: (1 or2):");
            createNewAccountOrLogin();
        }
    }

    public void chooseWhatToBrowse(){
        count=0;

        System.out.println("Please choose next action:");
        System.out.println("1. Browse movies");
        System.out.println("2. Browse series");
        System.out.println("3. Search movies by category");
        System.out.println("4. Search series by category");
        System.out.println("5. Exit streaming service");
        //User chooses what to browse.
        input = ui.getNumericInput("(1 to 5):");
        if(input==1){
            movieChosen = true;
            for(Movie s: movies){
                count++;
                System.out.println(count+": "+s.getTitle());
            }
            chooseMedia();
            chooseWhatToDoWithChosenMedia();
            endOfStreamLoop();
        }
        else if(input==2){
            movieChosen = false;
            for(Serie s: series){
                count++;
                System.out.println(count+": "+s.getTitle());
            }
            chooseMedia();
            chooseWhatToDoWithChosenMedia();
            endOfStreamLoop();
        }
        else if(input==3){
            searchByCategoriMovies();
            endOfStreamLoop();
        }
        else if(input==4){
            searchByCategoriSeries();
            endOfStreamLoop();
        }
        else if(input==5){

        }
        else{
            chooseWhatToBrowse();
            input = ui.getNumericInput("Number missmatch - Please type one of the numbers: (1 to 2):");
        }
    }

    public void chooseMedia() {
        int count = 0;
        input = ui.getNumericInput("to select movie please type the movies given number - (1 to 100): ");
        if(input<101&&input>0) {
            if (movieChosen) {
                System.out.println("You choose: " + movies.get(input - 1).getTitle() + " - is this correct?");
                textInput = ui.getInput("(Y/N): ");
                if (textInput.equalsIgnoreCase("y")) {
                    chosenMedia = input - 1;
                } else if (textInput.equalsIgnoreCase("n")) {
                    for (Movie s : movies) {
                        count++;
                        System.out.println(count + ": " + s.getTitle());
                    }
                    input = ui.getNumericInput("Please try again, to select movie please type the movies given number - (1 to 100):");
                    chooseMedia();
                }
            } else if (movieChosen == false) {
                System.out.println("You choose: " + series.get(input - 1).getTitle() + " - is this correct?");
                textInput = ui.getInput("(Y/N): ");
                if (textInput.equalsIgnoreCase("y")) {
                    chosenMedia = input - 1;
                } else if (textInput.equalsIgnoreCase("n")) {
                    for (Serie s : series) {
                        count++;
                        System.out.println(count + ": " + s.getTitle());
                    }
                }
                else{
                    input = ui.getNumericInput("Please try again, to select movie please type the movies given number - (1 to 100):");
                    chooseMedia();
                }
            }
        }
        else {
            input = ui.getNumericInput("Seems you typed a number that is not within the given range - please try again (1-100): ");
            chooseMedia();
        }
    }

    public void chooseWhatToDoWithChosenMedia(){
        //User chooses what to do with media option (Save/delete from savedlist/watch/display details)
        if (movieChosen) {
            System.out.println("You have selected: " + movies.get(chosenMedia).getTitle());
            System.out.println("Please choose what to do with the choosen Media:");
            System.out.println("1. Play Movie");
            System.out.println("2. Add to Saved Movies list");
            System.out.println("3. Display media details");
            input = ui.getNumericInput("(1 to 3):");
            if (input < 4 && input > 0) {
                if (movieChosen) {

                    if (input == 1) {
                        //1. Play Movie
                        System.out.println(movies.get(chosenMedia) + " is now playing");
                        System.out.println("----------------------------------------");
                        System.out.println("----------------------------------------");
                        System.out.println("----------------------------------------");

                        addToWatchedMovies(user);
                    }
                    if (input == 2) {
                        //2. Add to saved movies
                        addToSavedMovies(user);
                    }

                    if (input == 3) {
                        //3. Display details
                        System.out.println(movies.get(chosenMedia));
                        System.out.println("Press Enter key to continue...");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        else if (movieChosen == false) {

            System.out.println("You have selected: " + series.get(chosenMedia).getTitle());
            System.out.println("Please choose what to do with the choosen serie:");
            System.out.println("1. Play serie");
            System.out.println("2. Add to Saved Serie list");
            System.out.println("3. Display Serie details");
            input = ui.getNumericInput("(1 to 3):");

            if (input == 1) {
                //
                System.out.println(series.get(chosenMedia).getTitle() + " is now playing");
                System.out.println("----------------------------------------");
                System.out.println("----------------------------------------");
                System.out.println("----------------------------------------");
                addToWatchedSeries(user);
            }
            if (input == 2) {
                addToSavedSeries(user);
            }
            if (input == 3) {
                //
                System.out.println(series.get(chosenMedia));
                System.out.println("Press Enter key to continue...");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
            }
        }

        else {
            input = ui.getNumericInput("Seems you typed a number that is not within the given range - please try again (1-3): ");
            chooseWhatToDoWithChosenMedia();
        }


    }

    public void addToWatchedMovies(User user){

        ArrayList<Movie> watchedMovies = user.getSavedList();

        // Add the selected movie to the user's list of saved movies
        watchedMovies.add(movies.get(chosenMedia));

        // Create the filename based on the user's name
        fileName = "UserWatchedMedias/UserWatchedMovies/" + user.getUserName() + ".txt";
        File savedMoviesFile = new File(fileName);

        try (FileWriter writer = new FileWriter(savedMoviesFile, true)) {
            // Write the selected movie to the file
            String movieTitle = movies.get(chosenMedia).getTitle();
            writer.write(movieTitle + "\n");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }

        Movie watchedMovie= movies.get(chosenMedia);

        List<String> details = new ArrayList<>();
        details.add(watchedMovie.getTitle()+";");

        Path addSaveM= Path.of(fileName);

        try {
            List<String> lines= Files.readAllLines(addSaveM);

            boolean alreadyWatched = false;

            for (String s:lines) {
                String[] lineChop= s.split(";");
                String watchedM = lineChop[0];
                if(watchedMovie.getTitle().equals(watchedM)){
                    alreadyWatched = true;
                }
            }
            if (!alreadyWatched) {
                Files.write(addSaveM, details, StandardOpenOption.APPEND);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addToWatchedSeries(User user){

        ArrayList<Serie> watchedSeries = user.getSavedListSeries();

        // Add the selected movie to the user's list of saved movies
        watchedSeries.add(series.get(chosenMedia));

        // Create the filename based on the user's name
        fileName = "UserWatchedMedias/UserWatchedSeries/" + user.getUserName() + ".txt";
        File savedMoviesFile = new File(fileName);

        try (FileWriter writer = new FileWriter(savedMoviesFile, true)) {
            // Write the selected movie to the file
            String movieTitle = series.get(chosenMedia).getTitle();
            writer.write(movieTitle + "\n");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }

        Serie watchedSerie = series.get(chosenMedia);

        List<String> details = new ArrayList<>();
        details.add(watchedSerie.getTitle()+";");

        Path addSaveM= Path.of(fileName);

        try {
            List<String> lines= Files.readAllLines(addSaveM);

            boolean alreadyWatched = false;

            for (String s:lines) {
                String[] lineChop= s.split(";");
                String watchedS = lineChop[0];
                if(watchedSerie.getTitle().equals(watchedS)){
                    alreadyWatched = true;
                }
            }
            if (!alreadyWatched) {
                Files.write(addSaveM, details, StandardOpenOption.APPEND);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addToSavedMovies(User user) {
        // Get the user's list of saved movies
        ArrayList<Movie> savedMovies = user.getSavedList();

        // Add the selected movie to the user's list of saved movies
        savedMovies.add(movies.get(chosenMedia));

        // Create the filename based on the user's name
        String fileName = "UserSavedMedias/UserSavedMovies/" + user.getUserName() + ".txt";
        File savedMoviesFile = new File(fileName);

        try (FileWriter writer = new FileWriter(savedMoviesFile, true)) {
            // Write the selected movie to the file
            String movieTitle = movies.get(chosenMedia).getTitle();
            writer.write(movieTitle + "\n");

            // Print a message that the movie has been added to saved movies
            System.out.println(movieTitle + " added to saved movies.");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    public void searchByCategoriSeries() {
        String stringInput = ui.getInput("Write the category you want to search for:");

        // Make sure the string is not empty


        List<Serie> matchingSeries = new ArrayList<>();
        int count = 0;

        if (!stringInput.isEmpty()) {
            // Convert the first character to uppercase and concatenate it with the rest of the string
            stringInput = Character.toUpperCase(stringInput.charAt(0)) + stringInput.substring(1).toLowerCase();

            for (Serie s : series) {
                if (s.getCategories().contains(stringInput)) {
                    matchingSeries.add(s);
                    count++;
                    System.out.println(count + ". " + s.getTitle() + " - " + s.getCategories());
                }
            }
        }
        if (!stringInput.isEmpty()) {
            int input = ui.getNumericInput("To select the serie, type the serie's number");

            if (input <= count && input > 0) {
                System.out.println("You chose: " + matchingSeries.get(input - 1).getTitle() + " Category: " + matchingSeries.get(input - 1).getCategories() + "- is this correct?");
                String textInput = ui.getInput("(Y/N): ");

                if (textInput.equalsIgnoreCase("y")) {
                    chosenMedia = series.indexOf(matchingSeries.get(input - 1));
                    chooseWhatToDoWithChosenMedia();
                } else if (textInput.equalsIgnoreCase("n")) {
                    int count1 = 0;

                    for (Serie m : series) {
                        if (m.getCategories().contains(stringInput)) {
                            matchingSeries.add(m);
                            count1++;
                            System.out.println(count1 + ". " + m.getTitle() + " - " + m.getCategories());
                        }
                    }

                    input = ui.getNumericInput("Choose another serie: ");
                    if (input <= count && input > 0) {
                        chosenMedia = series.indexOf(matchingSeries.get(input - 1));
                        chooseWhatToDoWithChosenMedia();
                    } else {
                        System.out.println("Invalid input. Exiting...");

                    }
                }
            }
        } else {
            System.out.println("Invalid input. Exiting...");
            searchByCategoriSeries();
        }
    }
    public void searchByCategoriMovies() {
        String stringInput = ui.getInput("Write the category you want to search for:");

        // Make sure the string is not empty


        List<Movie> matchingMovie = new ArrayList<>();
        int count = 0;

        if (!stringInput.isEmpty()) {
            // Convert the first character to uppercase and concatenate it with the rest of the string
            stringInput = Character.toUpperCase(stringInput.charAt(0)) + stringInput.substring(1).toLowerCase();

            for (Movie s: movies) {
                if (s.getCategories().contains(stringInput)) {
                    matchingMovie.add(s);
                    count++;
                    System.out.println(count + ". " + s.getTitle() + " - " + s.getCategories());
                }
            }
        }
        if (!stringInput.isEmpty()) {
            int input = ui.getNumericInput("To select the serie, type the serie's number");

            if (input <= count && input > 0) {
                System.out.println("You chose: " + matchingMovie.get(input - 1).getTitle() + " Category: " + matchingMovie.get(input - 1).getCategories() + "- is this correct?");
                String textInput = ui.getInput("(Y/N): ");

                if (textInput.equalsIgnoreCase("y")) {
                    chosenMedia = movies.indexOf(matchingMovie.get(input - 1));
                    movieChosen = true;
                    chooseWhatToDoWithChosenMedia();
                } else if (textInput.equalsIgnoreCase("n")) {
                    int count1 = 0;

                    for (Movie m : movies) {
                        if (m.getCategories().contains(stringInput)) {
                            matchingMovie.add(m);
                            count1++;
                            System.out.println(count1 + ". " + m.getTitle() + " - " + m.getCategories());
                        }
                    }

                    input = ui.getNumericInput("Choose another serie: ");
                    if (input <= count && input > 0) {
                        chosenMedia = series.indexOf(matchingMovie.get(input - 1));
                        movieChosen = true;
                        chooseWhatToDoWithChosenMedia();
                    } else {
                        System.out.println("Invalid input. Exiting...");

                    }
                }
            }
        }
        else {
            System.out.println("Invalid input. Exiting...");
            searchByCategoriMovies();
        }

    }

    public void addToSavedSeries(User user) {
        // Get the user's list of saved movies
        ArrayList<Serie> savedSeries = user.getSavedListSeries();

        // Add the selected movie to the user's list of saved movies
        savedSeries.add(series.get(chosenMedia));

        // Create the filename based on the user's name
        String fileName = "UserSavedMedias/UserSavedSeries/" + user.getUserName() + ".txt";
        File savedMoviesFile = new File(fileName);

        try (FileWriter writer = new FileWriter(savedMoviesFile, true)) {
            // Write the selected movie to the file
            String serieTitle = series.get(chosenMedia).getTitle();
            writer.write(serieTitle + "\n");

            // Print a message that the movie has been added to saved movies
            System.out.println(serieTitle + " added to saved series.");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void endOfStreamLoop(){
        textInput = ui.getInput("To navigate to startmenu or exit press S or E - Startmenu: S   Exit: E ");
        if (textInput.equalsIgnoreCase("s")) {
            startStream();
        } else if (textInput.equalsIgnoreCase("e")) {

            System.out.println("See you next time... Kind regards AAAM");

        }
        else{
            endOfStreamLoop();
        }
    }
    public void streamingSetup(){



        FileIO io = new FileIO();

        movies=io.readMovieData("MediaFiles/100bedstefilm.txt");
        series=io.readSeriesData("MediaFiles/100bedsteserier.txt");

        System.out.println("Hi!");
        System.out.println("Welcome to AAAM's streaming service - Please choose an option to continue: ");
        System.out.println("1. Login to Existing account");
        System.out.println("2. Create new account");

        //User chooses to login or create new profile.
        input = ui.getNumericInput("(1 or 2):");
        createNewAccountOrLogin();
    }

    public void startStream(){
        chooseWhatToBrowse();
    }


}