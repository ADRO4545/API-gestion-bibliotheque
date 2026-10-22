package com.information.bibliotheque.mapper;

import com.information.bibliotheque.model.Emprunt;
import com.information.bibliotheque.dto.EmpruntRequest;
import com.information.bibliotheque.dto.EmpruntResponse;

public class EmpruntMapper {

    /**
     * Convertit un DTO de requête en entité JPA.
     */
    public static Emprunt versEntite(EmpruntRequest request) {
        Emprunt emprunt = new Emprunt();
        emprunt.setLivreId(request.getLivreId());
        emprunt.setEmprunteur(request.getEmprunteur());
        emprunt.setEmailEmprunteur(request.getEmailEmprunteur());
        return emprunt;
    }

    /**
     * Convertit une entité JPA en DTO de réponse.
     */
    public static EmpruntResponse versResponse(EmpruntResponse emprunt) {
        return new EmpruntResponse(
                emprunt.getId(),
                emprunt.getLivreId(),
                emprunt.getEmprunteur(),
                emprunt.getDateEmprunt(),
                emprunt.getDateRetourPrevue(),
                emprunt.getDateRetourEffective(),
                emprunt.getRendu(),
                emprunt.getEnRetard());
    }

}
