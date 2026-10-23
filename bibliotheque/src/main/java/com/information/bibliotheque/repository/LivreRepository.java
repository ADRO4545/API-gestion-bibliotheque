package com.information.bibliotheque.repository;

import com.information.bibliotheque.model.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    boolean isbnExiste(String isbn);

    boolean isbnUtiliseParUnAutreLivre(String isbn, Long id);

    Page<Livre> rechercherParTitre(String titre, Pageable pageable);

    Page<Livre> recupererDisponibles(Integer stock, Pageable pageable);

    Page<Livre> findByAuteurId(Long auteurId, Pageable pageable); // les livres d'un auteur

    Page<Livre> findByTitreContainingIgnoreCase(String titre, Pageable pageable); // titre contenant le texte

    Page<Livre> findByStockGreaterThan(Integer stock, Pageable pageable); // stock strictement supérieur
}
