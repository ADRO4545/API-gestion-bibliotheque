package com.information.bibliotheque.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.information.bibliotheque.model.Emprunt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    Page<Emprunt> findByRenduFalse(Pageable pageable);

    Page<Emprunt> findByRenduFalseAndDateRetourPrevueBefore(LocalDate date, Pageable pageable);
}
