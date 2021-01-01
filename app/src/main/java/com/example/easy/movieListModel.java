package com.example.easy;


public class movieListModel   {


    public movieListModel() {}

    String movieName, description, documentId ;

    public movieListModel(String movieName, String description, String documentId) {
        this.movieName = movieName;
        this.description = description;
        this.documentId = documentId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

}
