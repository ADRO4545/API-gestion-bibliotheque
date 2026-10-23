package com.information.bibliotheque.service;

import com.information.bibliotheque.model.Livre;
import com.information.bibliotheque.repository.AuteurRepository;
import com.information.bibliotheque.repository.LivreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreService {
    private final LivreRepository livreRepository;
    private final AuteurRepository auteurRepository;
    public LivreService(LivreRepository livreRepository, AuteurRepository auteurRepository) {
        this.livreRepository = livreRepository;
        this.auteurRepository = auteurRepository;
    }

    public Page<Livre> findAll(Pageable pageable) {
        return livreRepository.findAll(pageable);
    }

    public Optional<Livre> findById(Long id){
        return livreRepository.findById(id);
    }

    public boolean existeById(Long id){
        return livreRepository.existsById(id);
    }

    public boolean isbnExiste(String isbn){
        return livreRepository.isbnExiste(isbn);
    }

    public boolean isbnUtiliseParUnAutreLivre(String isbn, Long idLivre){
        return livreRepository.isbnUtiliseParUnAutreLivre(isbn, idLivre);
    }

    public Livre creer(Livre livre){
        return livreRepository.save(livre);
    }

    public Livre mettreAJour(Livre livreExistant, Livre nouvellesValeurs){
        livreExistant.setTitre(nouvellesValeurs.getTitre());
        livreExistant.setIsbn(nouvellesValeurs.getIsbn());
        livreExistant.setPrix(nouvellesValeurs.getPrix());
        livreExistant.setAnneePublication(nouvellesValeurs.getAnneePublication());
        livreExistant.setStock(nouvellesValeurs.getStock());
        livreExistant.setAuteurId(nouvellesValeurs.getAuteurId());
        return livreRepository.save(livreExistant);
    }

    public void supprimer(Long id){
        livreRepository.deleteById(id);
    }

    public Page<Livre> rechercherParTitre(String titre, Pageable pageable) {
        return livreRepository.rechercherParTitre(titre, pageable);
    }

    public Page<Livre> recupererDisponibles(Pageable pageable){
        return livreRepository.recupererDisponibles(0, pageable);
    }

    public Livre ajouterStock(Livre livre, Integer quantite){
        livre.setStock(livre.getStock() + quantite);
        return livreRepository.save(livre);
    }

}