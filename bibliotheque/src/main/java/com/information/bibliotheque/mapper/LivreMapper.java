package com.information.bibliotheque.mapper;


import com.information.bibliotheque.dto.LivreRequest;
import com.information.bibliotheque.dto.LivreResponse;
import com.information.bibliotheque.model.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

import java.util.ArrayList;
import java.util.List;

public class LivreMapper {
    /**
     * Convertit un DTO de requête en entité JPA.
     */
    public static Livre versEntite(LivreRequest livreCree) {
        Livre livre = new Livre();
        livre.setTitre(livreCree.getTitre());
        livre.setIsbn(livreCree.getIsbn());
        livre.setPrix(livreCree.getPrix());
        livre.setAnneePublication(livreCree.getAnneePublication());
        livre.setStock(livreCree.getStock());
        livre.setAuteurId(livreCree.getAuteurId());
        return livre;
    }
    /**
     * Convertit une entité JPA en DTO de réponse.
     */
    public static LivreResponse versResponse(Livre livreCree) {
        return new LivreResponse(
                livreCree.getId(),
                livreCree.getTitre(),
                livreCree.getIsbn(),
                livreCree.getPrix(),
                livreCree.getAnneePublication(),
                livreCree.getStock(),
                livreCree.getAuteurId()
        );
    }

    public static List<LivreResponse> versResponses(List<Livre> livres) {
        List<LivreResponse> reponses = new ArrayList<>();
        for (Livre livre : livres) {
            reponses.add(versResponse(livre));
        }
        return reponses;
    }

    public static PagedModel<LivreResponse> versPage(Page<Livre> page) {
        List<LivreResponse> contenu = versResponses(page.getContent());
        Page<LivreResponse> pageDto = new PageImpl<>(contenu, page.getPageable(), page.getTotalElements());
        return new PagedModel<>(pageDto);
    }
}