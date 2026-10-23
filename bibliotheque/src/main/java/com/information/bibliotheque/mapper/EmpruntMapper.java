package com.information.bibliotheque.mapper;

import com.information.bibliotheque.model.Emprunt;
import com.information.bibliotheque.dto.EmpruntRequest;
import com.information.bibliotheque.dto.EmpruntResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class EmpruntMapper {

    private EmpruntMapper() {
    }

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
    public static EmpruntResponse versResponse(Emprunt emprunt) {
        boolean enRetard = !emprunt.isRendu() && emprunt.getDateRetourPrevue().isBefore(LocalDate.now());
        return new EmpruntResponse(
                emprunt.getId(),
                emprunt.getLivreId(),
                emprunt.getEmprunteur(),
                emprunt.getDateEmprunt(),
                emprunt.getDateRetourPrevue(),
                emprunt.getDateRetourEffective(),
                emprunt.isRendu(),
                enRetard);
    }

    public static List<EmpruntResponse> versResponses(List<Emprunt> emprunts) {
        List<EmpruntResponse> reponses = new ArrayList<>();
        for (Emprunt emprunt : emprunts) {
            reponses.add(versResponse(emprunt));
        }
        return reponses;
    }

    public static PagedModel<EmpruntResponse> versPage(Page<Emprunt> page) {
        List<EmpruntResponse> contenu = versResponses(page.getContent());
        Page<EmpruntResponse> pageDto = new PageImpl<>(contenu, page.getPageable(), page.getTotalElements());
        return new PagedModel<>(pageDto);
    }

}
