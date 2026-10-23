package com.information.bibliotheque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


@Entity
@Table(name="auteurs")
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private String nationalite;

    public Auteur(){}

    public Auteur(String nom, String prenom, String nationalite)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
    }

    public Long getId(){return id;}
    public void  setId(Long id){this.id = id;}

    public String getNom(){return nom;}
    public void setNom(String nom){this.nom = nom;}

    public String getPrenom(){return prenom;}
    public void setPrenom(String prenom){this.prenom = prenom;}

    public String getNationalite(){return nationalite;}
    public void setNationalite(String nationalite){this.nationalite = nationalite;}

    @Override
    public String toString() {
        return "Produit{id=" + id + ", nom='" + nom + "', prenom=" + prenom + "nationalité:" + nationalite;
    }
}
