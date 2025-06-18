package org.example.patientapp;

public class Patient {
    private String nom;
    private String consultation;

    public Patient() {}

    public Patient(String nom, String consultation) {
        this.nom = nom;
        this.consultation = consultation;
    }

    public String getNom() { return nom; }
    public String getConsultation() { return consultation; }

    public void setNom(String nom) { this.nom = nom; }
    public void setConsultation(String consultation) { this.consultation = consultation; }

    @Override
    public String toString() {
        return nom + " - " + consultation;
    }
}
