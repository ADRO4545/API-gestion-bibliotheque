package com.information.bibliotheque.dto;

import jakarta.validation.constraints.*;

public class AuteurRequest {

    @NotBlank(message = "Le nom de l'auteur est obligatoire")
    @Size(max = 100)
    private String nom;

    @NotBlank(message = "Le prenom de l'auteur est obligatoire")
    @Size(max = 100)
    private String prenom;

    @Size(max = 50)
    private String nationalite;

    public AuteurRequest(){}

    public AuteurRequest(String nom, String prenom, String nationalite){
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
    }

    public String getNom(){return nom;}
    public void setNom(String nom){this.nom = nom;}

    public String getPrenom(){return prenom;}
    public void setPrenom(String prenom){this.prenom = prenom;}

    public String getNationalite(){return nationalite;}
    public void setNationalite(String nationalite){this.nationalite = nationalite;}


}
