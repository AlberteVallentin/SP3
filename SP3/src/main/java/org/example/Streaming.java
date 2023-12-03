package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Streaming {
    private int chosenMedia;
    private String usrName;
    private int input;
    private String textInput;
    private String fileName;
    private boolean movieChosen = false;
    UserMenu userMenu = new UserMenu();
    TextUI ui = new TextUI();
    private int count = 0;
    User user;
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Serie> series = new ArrayList<>();


    // Method to set up initial streaming configuration
    public void streamingSetup(){
        IO fileio = new FileIO();
        IO io = new DBConnector();

        movies = fileio.readMovieData("MediaFiles/100bedstefilm.txt");
        series = fileio.readSeriesData("MediaFiles/100bedsteserier.txt");

        System.out.println("Hi!");
        System.out.println("Welcome to AAAM's streaming service - Please choose an option to continue: ");
        System.out.println("1. Login to Existing account");
        System.out.println("2. Create new account");

        // User chooses to login or create a new profile
        input = ui.getNumericInput("(1 or 2):");
        createNewAccountOrLogin();
    }

    // Method to decide whether to log in or create a new account
    public void createNewAccountOrLogin(){
        if(input==1){
            login();
        }
        else if(input==2){
            createAccount();
            login();
        }
        else{
            input = ui.getNumericInput("Number mismatch - Please type one of the numbers: (1 or 2):");
            createNewAccountOrLogin();
        }
    }

    // Method for user login
    public void login(){
        Scanner usrInput = new Scanner(System.in);
        System.out.println("To login - Please type your username: ");
        usrName = usrInput.nextLine();
        System.out.println("Now the password please: ");
        String usrPass = usrInput.nextLine();

        // Validate user login credentials
        if(userMenu.userLogIn(usrName, usrPass)){

        }
        else{
            System.out.println("Seems your credentials were incorrect... :(");
            login();  // Recursive call to login again
        }
        user = new User(usrName, usrPass);
    }

    // Method for creating a new user account
    public void createAccount(){
        userMenu.createAccount();
    }

    // Method to start the streaming service
    public void startStream(){
        chooseWhatToBrowse();
    }

    // Method for the user to choose what to browse (movies, series, search, or exit)
    public void chooseWhatToBrowse(){
        count=0;

        System.out.println("Please choose the next action:");
        System.out.println("1. Browse movies");
        System.out.println("2. Browse series");
        System.out.println("3. Search movies by category");
        System.out.println("4. Search series by category");
        System.out.println("5. Exit streaming service");

        // User chooses what to browse
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
            searchByCategoryMovies();
            endOfStreamLoop();
        }
        else if(input==4){
            searchByCategorySeries();
            endOfStreamLoop();
        }
        else if(input==5){

        }
        else{
            chooseWhatToBrowse();
            input = ui.getNumericInput("Number mismatch - Please type one of the numbers: (1 to 5):");
        }
    }

    public void chooseMedia(){
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

    // User chooses what to do with the selected media (movie or series)
    public void chooseWhatToDoWithChosenMedia(){
        // If a movie is chosen
        if (movieChosen) {
            System.out.println("You have selected: " + movies.get(chosenMedia).getTitle());
            System.out.println("Please choose what to do with the chosen Media:");
            System.out.println("1. Play Movie");
            System.out.println("2. Add to Saved Movies list");
            System.out.println("3. Display media details");
            input = ui.getNumericInput("(1 to 3):");

            // Ensure the input is within the valid range (1 to 3)
            if (input < 4 && input > 0) {
                if (movieChosen) {
                    // If the user chooses to play the movie
                    if (input == 1) {
                        // 1. Play Movie
                        System.out.println(movies.get(chosenMedia) + " is now playing");
                        System.out.println("----------------------------------------");
                        System.out.println("----------------------------------------");
                        System.out.println("----------------------------------------");

                        // Add the movie to the user's watched movies list
                        addToWatchedMovies(user);
                    }
                    // If the user chooses to add the movie to saved movies
                    if (input == 2) {
                        // 2. Add to saved movies
                        addToSavedMovies(user);
                    }

                    // If the user chooses to display movie details
                    if (input == 3) {
                        // 3. Display details
                        System.out.println(movies.get(chosenMedia));
                        System.out.println("Press Enter key to continue...");
                        try {
                            System.in.read();
                        } catch (Exception e) {
                            // Handle exception if reading input fails
                        }
                    }
                }
            }
        }
        // If a series is chosen
        else if (!movieChosen) {
            System.out.println("You have selected: " + series.get(chosenMedia).getTitle());
            System.out.println("Please choose what to do with the chosen serie:");
            System.out.println("1. Play serie");
            System.out.println("2. Add to Saved Serie list");
            System.out.println("3. Display Serie details");
            input = ui.getNumericInput("(1 to 3):");

            // If the user chooses to play the series
            if (input == 1) {
                System.out.println(series.get(chosenMedia).getTitle() + " is now playing");
                System.out.println("----------------------------------------");
                System.out.println("----------------------------------------");
                System.out.println("----------------------------------------");

                // Add the series to the user's watched series list
                addToWatchedSeries(user);
            }
            // If the user chooses to add the series to saved series
            if (input == 2) {
                addToSavedSeries(user);
            }
            // If the user chooses to display series details
            if (input == 3) {
                System.out.println(series.get(chosenMedia));
                System.out.println("Press Enter key to continue...");
                try {
                    System.in.read();
                } catch (Exception e) {
                    // Handle exception if reading input fails
                }
            }
        } else {
            // Invalid input, prompt the user to try again
            input = ui.getNumericInput("Seems you typed a number that is not within the given range - please try again (1-3): ");
            chooseWhatToDoWithChosenMedia();
        }
    }

    // Add the selected movie to the user's list of watched movies
    public void addToWatchedMovies(User user) {
        // Get the user's list of watched movies
        ArrayList<Movie> watchedMovies = user.getSavedList();

        // Add the selected movie to the user's list of watched movies
        watchedMovies.add(movies.get(chosenMedia));

        // Create the filename based on the user's name
        fileName = "UserWatchedMedias/UserWatchedMovies/" + user.getUserName() + ".txt";
        File savedMoviesFile = new File(fileName);

        try (FileWriter writer = new FileWriter(savedMoviesFile, true)) {
            // Write the selected movie title to the file
            String movieTitle = movies.get(chosenMedia).getTitle();
            writer.write(movieTitle + "\n");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }

        // Get the watched movie details
        Movie watchedMovie = movies.get(chosenMedia);

        // Prepare details to be written to the file
        List<String> details = new ArrayList<>();
        details.add(watchedMovie.getTitle() + ";");

        // Create a Path object for the file
        Path addSaveM = Path.of(fileName);

        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(addSaveM);

            // Check if the movie is already watched
            boolean alreadyWatched = false;

            for (String s : lines) {
                String[] lineChop = s.split(";");
                String watchedM = lineChop[0];
                if (watchedMovie.getTitle().equals(watchedM)) {
                    alreadyWatched = true;
                }
            }

            // If the movie is not already watched, append the details to the file
            if (!alreadyWatched) {
                Files.write(addSaveM, details, StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            // Throw a runtime exception if an IO error occurs
            throw new RuntimeException(e);
        }
    }

    // Add the selected series to the user's list of watched series
    public void addToWatchedSeries(User user) {
        // Get the user's list of watched series
        ArrayList<Serie> watchedSeries = user.getSavedListSeries();

        // Add the selected series to the user's list of watched series
        watchedSeries.add(series.get(chosenMedia));

        // Create the filename based on the user's name
        fileName = "UserWatchedMedias/UserWatchedSeries/" + user.getUserName() + ".txt";
        File savedSeriesFile = new File(fileName);

        try (FileWriter writer = new FileWriter(savedSeriesFile, true)) {
            // Write the selected series title to the file
            String serieTitle = series.get(chosenMedia).getTitle();
            writer.write(serieTitle + "\n");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }

        // Get the watched series details
        Serie watchedSerie = series.get(chosenMedia);

        // Prepare details to be written to the file
        List<String> details = new ArrayList<>();
        details.add(watchedSerie.getTitle() + ";");

        // Create a Path object for the file
        Path addSaveM = Path.of(fileName);

        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(addSaveM);

            // Check if the series is already watched
            boolean alreadyWatched = false;

            for (String s : lines) {
                String[] lineChop = s.split(";");
                String watchedS = lineChop[0];
                if (watchedSerie.getTitle().equals(watchedS)) {
                    alreadyWatched = true;
                }
            }

            // If the series is not already watched, append the details to the file
            if (!alreadyWatched) {
                Files.write(addSaveM, details, StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            // Throw a runtime exception if an IO error occurs
            throw new RuntimeException(e);
        }
    }

    // Add the selected movie to the user's list of saved movies
    public void addToSavedMovies(User user) {
        // Get the user's list of saved movies
        ArrayList<Movie> savedMovies = user.getSavedList();

        // Add the selected movie to the user's list of saved movies
        savedMovies.add(movies.get(chosenMedia));

        // Create the filename based on the user's name
        String fileName = "UserSavedMedias/UserSavedMovies/" + user.getUserName() + ".txt";
        File savedMoviesFile = new File(fileName);

        try (FileWriter writer = new FileWriter(savedMoviesFile, true)) {
            // Write the selected movie title to the file
            String movieTitle = movies.get(chosenMedia).getTitle();
            writer.write(movieTitle + "\n");

            // Print a message that the movie has been added to saved movies
            System.out.println(movieTitle + " added to saved movies.");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Add the selected series to the user's list of saved series and update the corresponding file
    public void addToSavedSeries(User user) {
        // Get the user's list of saved series
        ArrayList<Serie> savedSeries = user.getSavedListSeries();

        // Add the selected series to the user's list of saved series
        savedSeries.add(series.get(chosenMedia));

        // Create the filename based on the user's name
        String fileName = "UserSavedMedias/UserSavedSeries/" + user.getUserName() + ".txt";
        File savedSeriesFile = new File(fileName);

        try (FileWriter writer = new FileWriter(savedSeriesFile, true)) {
            // Write the selected series to the file
            String serieTitle = series.get(chosenMedia).getTitle();
            writer.write(serieTitle + "\n");

            // Print a message that the series has been added to saved series
            System.out.println(serieTitle + " added to saved series.");

        } catch (IOException e) {
            // Handle any errors that occur during file writing
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Allow the user to search for a series based on categories
    public void searchByCategorySeries() {
        // Prompt the user to input the category to search for
        String stringInput = ui.getInput("Write the category you want to search for:");

        // Make sure the string input is not empty
        List<Serie> matchingSeries = new ArrayList<>();  // List to store matching series
        int count = 0;  // Counter to track the number of matching series

        if (!stringInput.isEmpty()) {
            // Convert the first character to uppercase and concatenate it with the rest of the string
            stringInput = Character.toUpperCase(stringInput.charAt(0)) + stringInput.substring(1).toLowerCase();

            // Iterate through the series to find matching categories
            for (Serie s : series) {
                if (s.getCategories().contains(stringInput)) {
                    matchingSeries.add(s);  // Add matching series to the list
                    count++;
                    System.out.println(count + ". " + s.getTitle() + " - " + s.getCategories());
                }
            }
        }

        if (!stringInput.isEmpty()) {
            // Prompt the user to select a series by entering its number
            int input = ui.getNumericInput("To select the serie, type the serie's number");

            if (input <= count && input > 0) {
                // Confirm the user's choice and proceed accordingly
                System.out.println("You chose: " + matchingSeries.get(input - 1).getTitle() +
                        " Category: " + matchingSeries.get(input - 1).getCategories() + "- is this correct?");
                String textInput = ui.getInput("(Y/N): ");

                if (textInput.equalsIgnoreCase("y")) {
                    chosenMedia = series.indexOf(matchingSeries.get(input - 1));
                    chooseWhatToDoWithChosenMedia();
                } else if (textInput.equalsIgnoreCase("n")) {
                    int count1 = 0;

                    // Display another set of matching series for user to choose from
                    for (Serie m : series) {
                        if (m.getCategories().contains(stringInput)) {
                            matchingSeries.add(m);
                            count1++;
                            System.out.println(count1 + ". " + m.getTitle() + " - " + m.getCategories());
                        }
                    }

                    // Prompt the user to choose another series
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
            // If the input is empty, inform the user and restart the search
            System.out.println("Invalid input. Exiting...");
            searchByCategorySeries();
        }
    }

    // Allow the user to search for a movie based on categories
    public void searchByCategoryMovies() {
        // Prompt the user to input the category to search for
        String stringInput = ui.getInput("Write the category you want to search for:");

        // Make sure the string input is not empty
        List<Movie> matchingMovie = new ArrayList<>();  // List to store matching movies
        int count = 0;  // Counter to track the number of matching movies

        if (!stringInput.isEmpty()) {
            // Convert the first character to uppercase and concatenate it with the rest of the string
            stringInput = Character.toUpperCase(stringInput.charAt(0)) + stringInput.substring(1).toLowerCase();

            // Iterate through the movies to find matching categories
            for (Movie s : movies) {
                if (s.getCategories().contains(stringInput)) {
                    matchingMovie.add(s);  // Add matching movies to the list
                    count++;
                    System.out.println(count + ". " + s.getTitle() + " - " + s.getCategories());
                }
            }
        }

        if (!stringInput.isEmpty()) {
            // Prompt the user to select a movie by entering its number
            int input = ui.getNumericInput("To select the movie, type the movie's number");

            if (input <= count && input > 0) {
                // Confirm the user's choice and proceed accordingly
                System.out.println("You chose: " + matchingMovie.get(input - 1).getTitle() +
                        " Category: " + matchingMovie.get(input - 1).getCategories() + "- is this correct?");
                String textInput = ui.getInput("(Y/N): ");

                if (textInput.equalsIgnoreCase("y")) {
                    chosenMedia = movies.indexOf(matchingMovie.get(input - 1));
                    movieChosen = true;
                    chooseWhatToDoWithChosenMedia();
                } else if (textInput.equalsIgnoreCase("n")) {
                    int count1 = 0;

                    // Display another set of matching movies for user to choose from
                    for (Movie m : movies) {
                        if (m.getCategories().contains(stringInput)) {
                            matchingMovie.add(m);
                            count1++;
                            System.out.println(count1 + ". " + m.getTitle() + " - " + m.getCategories());
                        }
                    }

                    // Prompt the user to choose another movie
                    input = ui.getNumericInput("Choose another movie: ");
                    if (input <= count && input > 0) {
                        chosenMedia = series.indexOf(matchingMovie.get(input - 1));
                        movieChosen = true;
                        chooseWhatToDoWithChosenMedia();
                    } else {
                        System.out.println("Invalid input. Exiting...");

                    }
                }
            }
        } else {
            // If the input is empty, inform the user and restart the search
            System.out.println("Invalid input. Exiting...");
            searchByCategoryMovies();
        }
    }

    // Provide the user with options to navigate back to the start menu or exit the program
    public void endOfStreamLoop() {
        // Prompt the user for input to either navigate to the start menu (S) or exit the program (E)
        textInput = ui.getInput("To navigate to the start menu, press S. To exit, press E: ");

        // Check the user's input and take appropriate actions
        if (textInput.equalsIgnoreCase("s")) {
            // If 'S' is pressed, restart the streaming process
            startStream();
        } else if (textInput.equalsIgnoreCase("e")) {
            // If 'E' is pressed, display a farewell message and exit the program
            System.out.println("See you next time... Kind regards AAAM");
        } else {
            // If an invalid input is provided, recursively call the endOfStreamLoop method again
            System.out.println("Invalid input. Please press 'S' to return to the start menu or 'E' to exit.");
            endOfStreamLoop();
        }
    }
}