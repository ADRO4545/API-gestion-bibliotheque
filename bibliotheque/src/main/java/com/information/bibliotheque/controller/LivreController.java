package com.information.bibliotheque.controller;

import com.information.bibliotheque.dto.LivreRequest;
import com.information.bibliotheque.dto.LivreResponse;
import com.information.bibliotheque.mapper.LivreMapper;
import com.information.bibliotheque.model.Livre;
import com.information.bibliotheque.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/livres")
public class LivreController {
    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping
    public PagedModel<LivreResponse> findAll(Pageable pageable) {
        return LivreMapper.versPage(livreService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivreResponse> findById(@PathVariable Long id) {
        return livreService.findById(id)
                .map(livre -> ResponseEntity.ok(LivreMapper.versResponse(livre))) // 200 OK si trouvé
                .orElse(ResponseEntity.notFound().build()); // 404 si non trouvé
    }

    @GetMapping("/recherche")
    public PagedModel<LivreResponse> rechercherParTitre(@RequestParam String titre, Pageable pageable) {
        return LivreMapper.versPage(livreService.rechercherParTitre(titre, pageable));
    }

    @GetMapping("/disponibles")
    public PagedModel<LivreResponse> recupererDisponibles(Pageable pageable) {
        return LivreMapper.versPage(livreService.recupererDisponibles(pageable));
    }

    @PostMapping
    public ResponseEntity<LivreResponse> creer(@Valid @RequestBody LivreRequest livreRequest) {
        if (livreService.isbnExiste(livreRequest.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 si ISBN déjà pris
        }
        Livre livre = livreService.creer(LivreMapper.versEntite(livreRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(LivreMapper.versResponse(livre)); // 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivreResponse> mettreAJour(@PathVariable Long id, @Valid @RequestBody LivreRequest livreRequest) {
        Optional<Livre> livreExistant = livreService.findById(id);
        if (livreExistant.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }
        if (livreService.isbnUtiliseParUnAutreLivre(livreRequest.getIsbn(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }
        Livre livre = livreService.mettreAJour(livreExistant.get(), LivreMapper.versEntite(livreRequest));
        return ResponseEntity.ok(LivreMapper.versResponse(livre)); // 200
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<LivreResponse> ajouterStock(@PathVariable Long id, @RequestParam Integer quantite) {
        return livreService.findById(id)
                .map(livre -> ResponseEntity.ok(LivreMapper.versResponse(livreService.ajouterStock(livre, quantite))))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!livreService.existeById(id)) {
            return ResponseEntity.notFound().build(); // 404
        }
        livreService.supprimer(id);
        return ResponseEntity.noContent().build(); // 204
    }
}

