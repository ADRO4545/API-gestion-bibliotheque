package com.information.bibliotheque.service;
import com.information.bibliotheque.model.Auteur;
import com.information.bibliotheque.repository.AuteurRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AuteurService {

    private final AuteurRepository auteurRepository;
    public AuteurService(AuteurRepository auteurRepository){
        this.auteurRepository = auteurRepository;
    }

    public Page<Auteur> findAll(Pageable pageable) {
        return auteurRepository.findAll(pageable);
    }

    public Optional<Auteur> findById(Long id){return auteurRepository.findById(id);}

    public Auteur create(Auteur auteur){
        auteur.setId(null);
        return auteurRepository.save(auteur);
    }

    public Optional<Auteur> update(Long id, Auteur auteurModifie){
        return auteurRepository.findById(id)
                .map(auteurExistant -> {
                    auteurExistant.setNom(auteurModifie.getNom());
                    auteurExistant.setPrenom(auteurModifie.getPrenom());
                    auteurExistant.setNationalite(auteurModifie.getNationalite());
                    return auteurRepository.save(auteurExistant);
                });
    }

    public boolean delete(Long id) {
        if (auteurRepository.existsById(id)) {
            auteurRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
