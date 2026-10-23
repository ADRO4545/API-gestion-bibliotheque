package com.information.bibliotheque.mapper;
import com.information.bibliotheque.dto.AuteurRequest;
import com.information.bibliotheque.dto.AuteurResponse;
import com.information.bibliotheque.model.Auteur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.ArrayList;

public final class AuteurMapper {

    private AuteurMapper(){}

    public static Auteur versEntite(AuteurRequest auteur){
        Auteur auteurCree = new Auteur();
        auteurCree.setNom(auteur.getNom());
        auteurCree.setPrenom(auteur.getPrenom());
        auteurCree.setNationalite(auteur.getNationalite());
        return auteurCree;
    }

    public static AuteurResponse versResponse(Auteur auteur){
        return new AuteurResponse(
                auteur.getId(),
                auteur.getNom(),
                auteur.getPrenom(),
                auteur.getNationalite()
        );
    }

    public static List<AuteurResponse> versResponses(List<Auteur> auteurs){
        ArrayList<AuteurResponse> authorList =  new ArrayList<>();
        for(Auteur auteur : auteurs){
            authorList.add(versResponse(auteur));
        }
        return authorList;
    }

    public static PagedModel<AuteurResponse> versPage(Page<Auteur> page) {
        List<AuteurResponse> contenu = versResponses(page.getContent());
        Page<AuteurResponse> pageDto = new PageImpl<>(contenu, page.getPageable(), page.getTotalElements());
        return new PagedModel<>(pageDto);
    }
}
