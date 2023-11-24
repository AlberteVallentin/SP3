package org.example;

public class Movie{
    private String title;
    private int year;
    private String categories;
    private double rating;

    public Movie (String title, int year, String categories, double rating){
        this.title=title;
        this.year=year;
        this.categories=categories;
        this.rating=rating;

    }
    public void displayDetails(){
        toString();
    }
    public String toString() {
        return "Title:  "+title+
                "\nYear:  "+year+
                "\nCategories:  "+categories+
                "\nrating:  "+rating;
    }
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public String getCategories() {
        return categories;
    }
}

