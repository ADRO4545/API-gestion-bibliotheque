package com.information.bibliotheque.controller;

import com.information.bibliotheque.dto.*;
import com.information.bibliotheque.mapper.AuteurMapper;
import com.information.bibliotheque.mapper.EmpruntMapper;
import com.information.bibliotheque.mapper.LivreMapper;
import com.information.bibliotheque.model.Auteur;
import com.information.bibliotheque.model.Emprunt;
import com.information.bibliotheque.model.Livre;
import com.information.bibliotheque.service.AuteurService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;

@Tag(name = "Auteurs", description = "Gestion des auteurs")
@RestController
@RequestMapping("/api/v1/auteurs")
public class AuteurController {

    private final AuteurService auteurService;

    public AuteurController(AuteurService auteurService) {
        this.auteurService = auteurService;
    }

    @Operation(summary = "Méthode pour trouver tout les auteurs, sortie par page de 10 trié par le nom.")
    @GetMapping
    public ResponseEntity<List<AuteurResponse>> findAll(@PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        Page<AuteurResponse> page = auteurService.findAll(pageable)
                .map(AuteurMapper::versResponse);
        return ResponseEntity.ok((List<AuteurResponse>) page);
    }

    @Operation(summary = "Trouver un auteur par son id")
    @GetMapping("/{id}")
    public ResponseEntity<AuteurResponse> findById(@PathVariable Long id) {
        return auteurService.findById(id)
                .map(AuteurMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Ajouter un auteur")
    @PostMapping
    public ResponseEntity<AuteurResponse> creer(@Valid @RequestBody AuteurRequest request) {
        Auteur aCree = AuteurMapper.versEntite(request);
        Auteur auteurCree = auteurService.create(aCree);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(AuteurMapper.versResponse(auteurCree));
    }

    @Operation(summary = "Mettre à jour un auteur")
    @PutMapping("/{id}")
    public ResponseEntity<AuteurResponse> mettreAJour(@PathVariable Long id,
            @Valid @RequestBody AuteurRequest request) {
        Optional<Auteur> resultat = auteurService.findById(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }
        Auteur nouvellesValeurs = AuteurMapper.versEntite(request);
        Auteur misAJour = auteurService.mettreAJour(resultat.get(), nouvellesValeurs);
        return ResponseEntity.ok(AuteurMapper.versResponse(misAJour)); // 200
    }

    @Operation(summary = "Supprimer un auteur")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (auteurService.delete(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 si non trouvé
    }

    @Operation(summary = "Lister les livres d'un auteur")
    @GetMapping("/{id}/livres")
    public ResponseEntity<PagedModel<LivreResponse>> listerLivres(@PathVariable Long id,
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        if (!auteurService.existe(id)) {
            return ResponseEntity.notFound().build(); // 404
        }
        Page<Livre> page = auteurService.recupererLivres(id, pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page)); // 200
    }

}
