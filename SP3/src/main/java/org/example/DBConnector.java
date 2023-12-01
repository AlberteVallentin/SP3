package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBConnector implements IO {
    static final String DB_URL = "jdbc:mysql://localhost/my_streaming";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Fdj38urx";
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Serie> series = new ArrayList<>();

    public ArrayList<Serie> readSeriesData(String path) {
        ArrayList<Serie> series = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 3: Execute a query
            System.out.println("Creating statement...");
            String serieQuery = "SELECT name, genre, yearToAndFrom, rating, seasonsAndEpisodes FROM my_streaming.serie WHERE SERIEID BETWEEN 1 and 99";
            stmt = conn.prepareStatement(serieQuery);

            ResultSet rs = stmt.executeQuery(serieQuery);

            // STEP 4: Extract data from result set and populate Serie objects
            while (rs.next()) {
                // Retrieve by column name
                String title = rs.getString("name");
                String categories = rs.getString("genre");
                String seasonsAndEpisodes = rs.getString("seasonsAndEpisodes");
                double rating = rs.getDouble("rating");
                String yearToAndFrom = rs.getString("yearToAndFrom");
                String[] years = yearToAndFrom.split("-");
                int yearFrom = Integer.parseInt(years[0]);
                String yearTo = (years.length > 1) ? years[1] : "still running";

                // Parse and add each season to the list
                List<Serie.Season> serieSeasons = new ArrayList<>();
                String[] seasons = seasonsAndEpisodes.split(", ");

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

            // STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try

        return series;
    }

}
