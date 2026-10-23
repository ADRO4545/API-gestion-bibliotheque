package com.information.bibliotheque.dto;

import jakarta.validation.constraints.*;

public class AuteurRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères ")
    private String nom;

    @NotBlank(message = "Le prénom de l'auteur est obligatoire")
    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
    private String prenom;

    @Size(max = 50, message = "La nationalité ne doit pas dépasser 50 caractères")
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
