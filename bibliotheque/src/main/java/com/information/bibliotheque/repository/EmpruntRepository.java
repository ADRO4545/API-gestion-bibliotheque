package com.information.bibliotheque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.information.bibliotheque.model.Emprunt;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

}
