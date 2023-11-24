package org.example;
import java.util.ArrayList;

public class User {
    private String userName;
    private String passWord;
    private ArrayList<Movie> watchedListMovies = new ArrayList<>();
    private ArrayList<Movie> savedListMovies = new ArrayList<>();

    private ArrayList<Serie> watchedListSeries = new ArrayList<>();
    private ArrayList<Serie> savedListSeries = new ArrayList<>();


    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.watchedListMovies = watchedListMovies;
        this.savedListMovies = savedListMovies;
        this.watchedListSeries = watchedListSeries;
        this.savedListSeries = savedListSeries;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public ArrayList<Movie> getWatchedList() {

        return watchedListMovies;
    }

    public ArrayList<Serie> getWatchedListSeries() {

        return watchedListSeries;
    }

    public ArrayList<Movie> getSavedList() {

        return savedListMovies;
    }
    public ArrayList<Serie> getSavedListSeries() {
        return savedListSeries;
    }
}
