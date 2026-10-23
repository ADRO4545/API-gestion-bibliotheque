package com.information.bibliotheque.controller;

import com.information.bibliotheque.dto.EmpruntRequest;
import com.information.bibliotheque.dto.EmpruntResponse;
import com.information.bibliotheque.mapper.EmpruntMapper;
import com.information.bibliotheque.model.Emprunt;
import com.information.bibliotheque.service.EmpruntService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Emprunts", description = "Emprunts et retours des livres")
@RestController
@RequestMapping("api/v1/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    @Operation(summary = "Liste de tout les emprunts")
    @GetMapping
    public ResponseEntity<Page<EmpruntResponse>> listerTous(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<EmpruntResponse> page = empruntService.recupererTous(pageable)
                .map(EmpruntMapper::versResponse);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Trouver un emprunt par son id")
    @GetMapping("/{id}")
    public ResponseEntity<EmpruntResponse> obtenirParId(@PathVariable Long id) {
        return empruntService.recupererParId(id)
                .map(EmpruntMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Créer un emprunt")
    @PostMapping
    public ResponseEntity<EmpruntResponse> creer(@Valid @RequestBody EmpruntRequest request) {
        // Check si livre existe (sinon 400)
        if (!empruntService.livreExiste(request.getLivreId())) {
            return ResponseEntity.badRequest().build();
        }

        // Check si livre disponible (sinon 409)
        if (!empruntService.livreDisponible(request.getLivreId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Emprunt emprunt = EmpruntMapper.versEntite(request);
        Emprunt empruntCree = empruntService.creer(emprunt);

        return ResponseEntity.status(HttpStatus.CREATED).body(EmpruntMapper.versResponse(empruntCree));
    }

    @Operation(summary = "Retourner un emprunt")
    @PatchMapping("/{id}/retour")
    public ResponseEntity<EmpruntResponse> retourner(@PathVariable Long id) {
        Optional<Emprunt> empruntOpt = empruntService.recupererParId(id);

        if (empruntOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Emprunt emprunt = empruntOpt.get();

        if (emprunt.isRendu()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Emprunt empruntRetourne = empruntService.retourner(emprunt);
        return ResponseEntity.ok(EmpruntMapper.versResponse(empruntRetourne));
    }

    @Operation(summary = "Voir tout les emprunts en cours")
    @GetMapping("/en-cours")
    public ResponseEntity<Page<EmpruntResponse>> listerEnCours(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<EmpruntResponse> page = empruntService.recupererEnCours(pageable)
                .map(EmpruntMapper::versResponse);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Voir tout les emprunts en retard")
    @GetMapping("/en-retard")
    public ResponseEntity<Page<EmpruntResponse>> listerEnRetard(
            @PageableDefault(size = 10, sort = "dateEmprunt") Pageable pageable) {
        Page<EmpruntResponse> page = empruntService.recupererEnRetard(pageable)
                .map(EmpruntMapper::versResponse);
        return ResponseEntity.ok(page);
    }

}