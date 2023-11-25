package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileIO {
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Serie> series = new ArrayList<>();

    // Read movie data from the specified file path and return a list of Movie objects
    public ArrayList<Movie> readMovieData(String path) {
        // Create a File object with the specified file path
        File file = new File(path);

        try {
            // Create a Scanner to read from the file
            Scanner scanner = new Scanner(file);

            // Continue reading as long as there are lines in the file
            while (scanner.hasNextLine()) {
                // Read the next line from the file
                String line = scanner.nextLine();

                // Split the line into an array using ";" as the delimiter
                String[] lineChop;
                lineChop = line.split(";");

                // Extract movie information from the split line
                String title = lineChop[0];
                String year1 = lineChop[1].trim();
                String categories = lineChop[2];
                String rating1 = lineChop[3].trim();
                rating1 = rating1.replace(",", ".");

                // Parse extracted information to appropriate data types
                int year = Integer.parseInt(year1);
                double rating = Double.parseDouble(rating1);

                // Create a Movie object using the extracted information and add it to the list
                Movie movie = new Movie(title, year, categories, rating);
                movies.add(movie);
            }

            // Return the list of Movie objects
            return movies;

        } catch (FileNotFoundException e) {
            // Handle the case where the file is not found
            System.err.println("Error: File not found");
        } catch (NumberFormatException e) {
            // Handle the case where there is an invalid format in the file (e.g., non-numeric values)
            System.err.println("Error: Invalid format in the file");
        }

        // Return null if there was an error during the reading process
        return null;
    }



    // Read series data from the specified file path and return a list of Serie objects
    public ArrayList<Serie> readSeriesData(String path) {
        // Create a File object with the specified file path
        File file = new File(path);

        try {
            // Create a Scanner to read from the file
            Scanner scanner = new Scanner(file);

            // Continue reading as long as there are lines in the file
            while (scanner.hasNextLine()) {
                // Read the next line from the file
                String line = scanner.nextLine();

                // Split the line into an array using ";" as the delimiter
                String[] lineChop = line.split(";");
                String title = lineChop[0];
                String yearRange = lineChop[1].trim();

                // Split the year range into starting and ending years
                String[] years = yearRange.split("-");
                int yearFrom = Integer.parseInt(years[0]);
                String yearTo = (years.length > 1) ? years[1] : "still running";

                // Extract series information from the split line
                String categories = lineChop[2];
                String rating1 = lineChop[3].trim();
                rating1 = rating1.replace(",", ".");
                double rating = Double.parseDouble(rating1);

                // Extract season information
                String[] seasons = lineChop[4].split(", ");
                List<Serie.Season> serieSeasons = new ArrayList<>();

                // Parse and add each season to the list
                for (String season : seasons) {
                    String[] seasonInfo = season.split("-");
                    String seasonNumber = seasonInfo[0];
                    String numberOfEpisodes = seasonInfo[1];
                    Serie.Season serieSeason = new Serie.Season(seasonNumber, numberOfEpisodes);
                    serieSeasons.add(serieSeason);
                }

                // Create a Serie object using the extracted information and add it to the list
                Serie serie = new Serie(title, yearFrom, yearTo, categories, rating, serieSeasons);
                series.add(serie);
            }

            // Return the list of Serie objects
            return series;

        } catch (FileNotFoundException e) {
            // Handle the case where the file is not found
            System.err.println("Error: File not found");
        } catch (NumberFormatException e) {
            // Handle the case where there is an invalid format in the file (e.g., non-numeric values)
            System.err.println("Error: Invalid format in the file");
        }

        // Return null if there was an error during the reading process
        return null;
    }
}