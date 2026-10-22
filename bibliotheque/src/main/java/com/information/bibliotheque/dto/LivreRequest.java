package com.information.bibliotheque.dto;


import jakarta.validation.constraints.*;

public class LivreRequest {

    @Size (max = 200, message = "Le titre ne doit pas dépasser les 200 caractère")
    @NotBlank(message = "Le titre du livre est obligatoire")
    private String titre;

    @Size (max = 20, message = "L'isbn ne doit pas dépasser les 20 caractère")
    @NotBlank(message = "L'isbn du livre est obligatoire")
    private String isbn;

    @NotNull(message = "Le prix du livre est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double prix;

    @Positive(message = "L'année de publication doit être positive")
    private Integer anneePublication;

    @NotNull(message = "Le stock est obligatoire")
    @PositiveOrZero(message = "Le stock ne peut pas être négatif")
    private Integer stock;

    @NotNull(message = " L'identifiant de l'auteur est obligatoire")
    private Long auteurId;



    /** Constructeur vide : utilisé par Jackson pour créer l'objet. */
    public LivreRequest() {
    }


    public LivreRequest(String titre, String isbn, Double prix, Integer anneePublication, Integer stock, Long auteurId)
    {
        this.titre = titre;
        this.isbn = isbn;
        this.prix = prix;
        this.anneePublication = anneePublication;
        this.stock = stock;
        this.auteurId = auteurId;
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
}