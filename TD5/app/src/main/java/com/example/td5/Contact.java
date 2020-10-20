package com.example.td5;

public class Contact {
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    String nom;
    String prenom;
    String imageUrl;

    public Contact(String prenom, String nom, String imageUrl){
        this.prenom=prenom;
        this.nom=nom;
        this.imageUrl=imageUrl;
    }
}
