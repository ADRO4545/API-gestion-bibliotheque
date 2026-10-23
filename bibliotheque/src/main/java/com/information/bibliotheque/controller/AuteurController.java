package com.information.bibliotheque.controller;

import com.information.bibliotheque.dto.AuteurRequest;
import com.information.bibliotheque.dto.AuteurResponse;
import com.information.bibliotheque.dto.LivreResponse;
import com.information.bibliotheque.mapper.AuteurMapper;
import com.information.bibliotheque.mapper.LivreMapper;
import com.information.bibliotheque.model.Auteur;
import com.information.bibliotheque.model.Livre;
import com.information.bibliotheque.service.AuteurService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Auteurs", description = "Gestion des auteurs")
@RestController
@RequestMapping("/api/v1/auteurs")
public class AuteurController {

    private final AuteurService auteurService;

    public AuteurController(AuteurService auteurService) {
        this.auteurService = auteurService;
    }

    @Operation(summary = "Méthode pour trouver tout les auteurs, sortie par page de 10 trié par le nom.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des auteurs récupérée avec succès")
    })
    @GetMapping
    public ResponseEntity<Page<AuteurResponse>> findAll(@PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        Page<AuteurResponse> page = auteurService.findAll(pageable)
                .map(AuteurMapper::versResponse);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Trouver un auteur par son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur trouvé"),
            @ApiResponse(responseCode = "404", description = "Auteur inexistant")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuteurResponse> findById(@PathVariable Long id) {
        return auteurService.findById(id)
                .map(AuteurMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Ajouter un auteur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Auteur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation des données")
    })
    @PostMapping
    public ResponseEntity<AuteurResponse> creer(@Valid @RequestBody AuteurRequest request) {
        Auteur aCree = AuteurMapper.versEntite(request);
        Auteur auteurCree = auteurService.create(aCree);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AuteurMapper.versResponse(auteurCree));
    }

    @Operation(summary = "Mettre à jour un auteur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation des données"),
            @ApiResponse(responseCode = "404", description = "Auteur inexistant")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AuteurResponse> mettreAJour(@PathVariable Long id,
            @Valid @RequestBody AuteurRequest request) {
        Optional<Auteur> resultat = auteurService.findById(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Auteur nouvellesValeurs = AuteurMapper.versEntite(request);
        Auteur misAJour = auteurService.mettreAJour(resultat.get(), nouvellesValeurs);
        return ResponseEntity.ok(AuteurMapper.versResponse(misAJour));
    }

    @Operation(summary = "Supprimer un auteur (impossible s'il a encore des livres)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Auteur supprimé"),
            @ApiResponse(responseCode = "404", description = "Auteur inexistant"),
            @ApiResponse(responseCode = "409", description = "L'auteur a encore des livres")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (auteurService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Lister les livres d'un auteur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des livres de l'auteur récupérée"),
            @ApiResponse(responseCode = "404", description = "Auteur inexistant")
    })
    @GetMapping("/{id}/livres")
    public ResponseEntity<PagedModel<LivreResponse>> listerLivres(@PathVariable Long id,
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        if (!auteurService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        Page<Livre> page = auteurService.recupererLivres(id, pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page));
    }
}