package com.information.bibliotheque.controller;

import com.information.bibliotheque.dto.LivreRequest;
import com.information.bibliotheque.dto.LivreResponse;
import com.information.bibliotheque.mapper.LivreMapper;
import com.information.bibliotheque.model.Livre;
import com.information.bibliotheque.service.LivreService;

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

@Tag(name = "Livres", description = "Catalogue des livres")
@RestController
@RequestMapping("/api/v1/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @Operation(summary = "Lister tout les livres")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des livres récupérée avec succès")
    })
    @GetMapping
    public ResponseEntity<PagedModel<LivreResponse>> listerTous(
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        Page<Livre> page = livreService.recupererTous(pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page));
    }

    @Operation(summary = "Trouver un livre par son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livre trouvé"),
            @ApiResponse(responseCode = "404", description = "Livre inexistant")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivreResponse> obtenirParId(@PathVariable Long id) {
        Optional<Livre> resultat = livreService.recupererParId(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(LivreMapper.versResponse(resultat.get()));
    }

    @Operation(summary = "Créer un livre")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livre créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation ou Auteur inexistant"),
            @ApiResponse(responseCode = "409", description = "ISBN déjà utilisé")
    })
    @PostMapping
    public ResponseEntity<LivreResponse> creer(@Valid @RequestBody LivreRequest request) {
        if (!livreService.auteurExiste(request.getAuteurId())) {
            return ResponseEntity.badRequest().build();
        }
        if (livreService.isbnExiste(request.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Livre livre = livreService.creer(LivreMapper.versEntite(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(LivreMapper.versResponse(livre));
    }

    @Operation(summary = "Mettre à jour un livre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livre mis à jour"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation ou Auteur inexistant"),
            @ApiResponse(responseCode = "404", description = "Livre inexistant"),
            @ApiResponse(responseCode = "409", description = "ISBN utilisé par un autre livre")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LivreResponse> mettreAJour(@PathVariable Long id,
                                                     @Valid @RequestBody LivreRequest request) {
        Optional<Livre> resultat = livreService.recupererParId(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (!livreService.auteurExiste(request.getAuteurId())) {
            return ResponseEntity.badRequest().build();
        }
        if (livreService.isbnUtiliseParUnAutreLivre(request.getIsbn(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Livre livre = livreService.mettreAJour(resultat.get(), LivreMapper.versEntite(request));
        return ResponseEntity.ok(LivreMapper.versResponse(livre));
    }

    @Operation(summary = "Supprimer un livre")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livre supprimé"),
            @ApiResponse(responseCode = "404", description = "Livre inexistant")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!livreService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        livreService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Rechercher un livre par son titre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des livres correspondants récupérée")
    })
    @GetMapping("/recherche")
    public ResponseEntity<PagedModel<LivreResponse>> rechercher(
            @RequestParam String titre,
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        Page<Livre> page = livreService.rechercherParTitre(titre, pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page));
    }

    @Operation(summary = "Lister les livres disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des livres disponibles récupérée")
    })
    @GetMapping("/disponibles")
    public ResponseEntity<PagedModel<LivreResponse>> listerDisponibles(
            @PageableDefault(size = 10, sort = "titre") Pageable pageable) {
        Page<Livre> page = livreService.recupererDisponibles(pageable);
        return ResponseEntity.ok(LivreMapper.versPage(page));
    }

    @Operation(summary = "Ajouter du stock à un livre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock ajouté avec succès"),
            @ApiResponse(responseCode = "400", description = "La quantité doit être supérieure à 0"),
            @ApiResponse(responseCode = "404", description = "Livre inexistant")
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<LivreResponse> ajouterStock(@PathVariable Long id,
                                                      @RequestParam Integer quantite) {
        if (quantite <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Livre> resultat = livreService.recupererParId(id);
        if (resultat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Livre livre = livreService.ajouterStock(resultat.get(), quantite);
        return ResponseEntity.ok(LivreMapper.versResponse(livre));
    }
}