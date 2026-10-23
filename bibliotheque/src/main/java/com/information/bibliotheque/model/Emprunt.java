package com.information.bibliotheque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;

@Entity
@Table(name = "emprunts")
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "L'identifiant du livre est obligatoire")
    private Long livreId;

    @NotNull(message = "L'emprunteur est obligatoire")
    @Size(max = 100, message = "Le nom de l'emprunteur ne doit pas dépasser 100 caractères")
    private String emprunteur;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String emailEmprunteur;

    @Column(nullable = false)
    private LocalDate dateEmprunt;

    @Column(nullable = false)
    private LocalDate dateRetourPrevue;

    private boolean rendu;

    private LocalDate dateRetourEffective;

    public Emprunt() {
    }

    public Emprunt(Long livreId, String emprunteur, String emailEmprunteur, LocalDate dateEmprunt,
            LocalDate dateRetourPrevue, boolean rendu, LocalDate dateRetourEffective) {
        this.livreId = livreId;
        this.emprunteur = emprunteur;
        this.emailEmprunteur = emailEmprunteur;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourEffective = dateRetourEffective;
        this.rendu = rendu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLivreId() {
        return livreId;
    }

    public void setLivreId(Long livreId) {
        this.livreId = livreId;
    }

    public String getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(String emprunteur) {
        this.emprunteur = emprunteur;
    }

    public String getEmailEmprunteur() {
        return emailEmprunteur;
    }

    public void setEmailEmprunteur(String emailEmprunteur) {
        this.emailEmprunteur = emailEmprunteur;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public boolean isRendu() {
        return rendu;
    }

    public void setRendu(boolean rendu) {
        this.rendu = rendu;
    }

    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    @Override
    public String toString() {
        return "Emprunt{id=" + id + ", livreId=" + livreId + ", emprunteur=" + emprunteur + ", emailEmprunteur="
                + emailEmprunteur + ", dateEmprunt=" + dateEmprunt + ", dateRetourPrevue=" + dateRetourPrevue
                + ", rendu=" + rendu + "}";
    }

}
