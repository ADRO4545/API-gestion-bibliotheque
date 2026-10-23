package com.information.bibliotheque.service;
import com.information.bibliotheque.model.Auteur;
import com.information.bibliotheque.model.Livre;
import com.information.bibliotheque.repository.AuteurRepository;
import com.information.bibliotheque.repository.LivreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AuteurService {

    private final AuteurRepository auteurRepository;

    private final LivreRepository livreRepository;
    public AuteurService(AuteurRepository auteurRepository, LivreRepository livreRepository){
        this.auteurRepository = auteurRepository;
        this.livreRepository = livreRepository;
    }

    public Page<Auteur> findAll(Pageable pageable) {
        return auteurRepository.findAll(pageable);
    }

    public Optional<Auteur> findById(Long id){return auteurRepository.findById(id);}

    public Auteur create(Auteur auteur){
        auteur.setId(null);
        return auteurRepository.save(auteur);
    }

    public Auteur mettreAJour(Auteur auteurExistant, Auteur nouvellesValeurs){

        auteurExistant.setNom(nouvellesValeurs.getNom());
        auteurExistant.setPrenom(nouvellesValeurs.getPrenom());
        auteurExistant.setNationalite(nouvellesValeurs.getNationalite());
        return auteurRepository.save(auteurExistant);
    }

    public boolean delete(Long id) {
        auteurRepository.deleteById(id);
        return true;
    }

    public Page<Livre> recupererLivres(Long id, Pageable pageable){
        return livreRepository.findByAuteurId(id, pageable);
    }

    public boolean existe(Long id){
        return auteurRepository.existsById(id);
    }

    public boolean aDesLivres(Long id){
        return livreRepository.existsByAuteurId(id);
    }
}
