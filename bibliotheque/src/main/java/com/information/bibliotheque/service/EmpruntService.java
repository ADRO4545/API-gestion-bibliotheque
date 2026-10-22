package com.information.bibliotheque.service;

import com.information.bibliotheque.model.Emprunt;
import com.information.bibliotheque.repository.EmpruntRepository;
import com.information.bibliotheque.repository.LivreRepository;

import java.util.Optional;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpruntService {
    /** Durée maximale d'un emprunt, en jours (RM5). */
    public static final int DUREE_MAX_JOURS = 14;
    private final EmpruntRepository empruntRepository;
    private final LivreRepository livreRepository;

    public EmpruntService(EmpruntRepository empruntRepository, LivreRepository livreRepository) {
        this.empruntRepository = empruntRepository;
        this.livreRepository = livreRepository;
    }

    public Page<Emprunt> recupererTous(Pageable pageable) {
        return empruntRepository.findAll(pageable);
    }

    public Optional<Emprunt> recupererParId(long id) {
        return empruntRepository.findById(id);
    }

    public boolean livreExiste(Long livreId) {
        if (livreId == null) {
            return false;
        }
        return livreRepository.findById(livreId).isPresent();
    }

    public boolean livreDisponible(Long livreId) {
        if (livreId == null) {
            return false;
        }
        return livreRepository.findById(livreId).getStock() > 0;
    }

}
