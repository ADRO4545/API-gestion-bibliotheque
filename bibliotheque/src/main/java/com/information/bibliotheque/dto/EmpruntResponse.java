package com.information.bibliotheque.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "livreId", "emprunteur", "dateEmprunt", "dateRetourPrevue",
        "dateRetourEffective", "rendu", "enRetard" })
public class EmpruntResponse {
    private Long id;
    private Long livreId;
    private String emprunteur;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;
    private Boolean rendu;
    private Boolean enRetard;

    public EmpruntResponse(Long id, Long livreId, String emprunteur,
            LocalDate dateEmprunt, LocalDate dateRetourPrevue, LocalDate dateRetourEffective, Boolean rendu,
            Boolean enRetard) {
        this.id = id;
        this.livreId = livreId;
        this.emprunteur = emprunteur;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourEffective = dateRetourEffective;
        this.rendu = rendu;
        this.enRetard = enRetard;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLivreId() {
        return this.livreId;
    }

    public void setLivreId(Long livreId) {
        this.livreId = livreId;
    }

    public String getEmprunteur() {
        return this.emprunteur;
    }

    public void setEmprunteur(String emprunteur) {
        this.emprunteur = emprunteur;
    }

    public LocalDate getDateEmprunt() {
        return this.dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return this.dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public LocalDate getDateRetourEffective() {
        return this.dateRetourEffective;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public Boolean getRendu() {
        return this.rendu;
    }

    public void setRendu(Boolean rendu) {
        this.rendu = rendu;
    }

    public Boolean getEnRetard() {
        return this.enRetard;
    }

    public void setEnRetard(Boolean enRetard) {
        this.enRetard = enRetard;
    }
}
