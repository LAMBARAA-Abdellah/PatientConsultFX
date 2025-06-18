package org.example.patientapp;

public class Patient {
    private String nom;
    private String cin;
    private String consultation;
    private double prix;

    public Patient() {}

    public Patient(String nom, String cin, String consultation, double prix) {
        this.nom = nom;
        this.cin = cin;
        this.consultation = consultation;
        this.prix = prix;
    }

    public String getNom() { return nom; }
    public String getCin() { return cin; }
    public String getConsultation() { return consultation; }
    public double getPrix() { return prix; }

    public void setNom(String nom) { this.nom = nom; }
    public void setCin(String cin) { this.cin = cin; }
    public void setConsultation(String consultation) { this.consultation = consultation; }
    public void setPrix(double prix) { this.prix = prix; }
}
