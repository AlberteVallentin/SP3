package org.example;
import java.util.List;

public class Serie {
    private String title;
    private int yearFrom;
    private String yearTo;
    private String categories;
    private double rating;
    private List<Season> serieSeasons;


    public Serie(String title, int yearFrom, String yearTo, String categories, double rating, List<Season> serieSeasons) {
        this.title = title;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.categories = categories;
        this.rating = rating;
        this.serieSeasons = serieSeasons;
    }

    public String getTitle() {
        return title;
    }

    public int getYearFrom() {
        return yearFrom;
    }

    public String getYearTo() {
        return yearTo;
    }

    public String getCategories() {
        return categories;
    }

    public double getRating() {
        return rating;
    }


    @Override
    public String toString() {
        String result = "Title: " + title +
                "\nYear from: " + yearFrom +
                "\nYear to: " + yearTo +
                "\nCategories: " + categories +
                "\nRating: " + rating +
                "\nSeasons:\n";

        for (Season season : serieSeasons) {
            result += "  Season number: " + season.getSeasonNumber() +
                    ", Number of episodes: " + season.getNumberOfEpisodes() +
                    "\n";
        }

        return result;
    }
    public void displayDetails(){
        toString();
    }


    public static class Season {
        private String seasonNumber;
        private String numberOfEpisodes;

        public Season(String seasonNumber, String numberOfEpisodes) {
            this.seasonNumber = seasonNumber;
            this.numberOfEpisodes = numberOfEpisodes;
        }

        public String getSeasonNumber() {
            return seasonNumber;
        }

        public String getNumberOfEpisodes() {
            return numberOfEpisodes;
        }
    }
}
