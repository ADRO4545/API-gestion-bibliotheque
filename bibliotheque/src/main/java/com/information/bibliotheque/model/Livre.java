package com.information.bibliotheque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;



@Entity
@Table(name = "Livre")

public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre du livre est obligatoire")
    @Column(nullable = false)
    private String titre;

    @NotBlank(message = "L'isbn du livre est obligatoire")
    @Column(unique = true)
    private String isbn;

    @NotNull(message = "Le prix du livre est obligatoire")
    private Double prix;

    @Positive(message = "L'année de publication doit être positive")
    private Integer anneePublication;

    @NotNull(message = "Le stock est obligatoire")
    private Integer stock;

    @NotNull(message = "L'id de l'auteur est obligatoire")
    private Long auteurId;



    public Livre() {
    }

    public Livre(String titre, String isbn, Double prix, Integer anneePublication, Integer stock, Long auteurId) {
        this.titre = titre;
        this.isbn = isbn;
        this.prix = prix;
        this.anneePublication = anneePublication;
        this.stock = stock;
        this.auteurId = auteurId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrix() {
        return prix;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getAnneePublication() {
        return anneePublication;
    }
    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getAuteurId() {
        return auteurId;
    }
    public void setAuteurId(Long auteurId) {
        this.auteurId = auteurId;
    }



    // @Override
    // public String toString() {
        // return "Produit{id=" + id + ", nom='" + nom + "', prenom=" + prenom + "}";
    // }
}
