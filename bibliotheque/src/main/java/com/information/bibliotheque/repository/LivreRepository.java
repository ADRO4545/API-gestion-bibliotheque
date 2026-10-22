package com.information.bibliotheque.repository;

import com.information.bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {

}
