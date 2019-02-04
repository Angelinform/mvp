package com.angelinform.moviesfeed.movies;

public class ViewModel {
    private String name;
    private String country;

    public ViewModel(String title, String country) {
        this.name = title;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
