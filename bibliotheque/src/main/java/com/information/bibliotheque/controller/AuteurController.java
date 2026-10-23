package com.information.bibliotheque.controller;

import com.information.bibliotheque.dto.AuteurRequest;
import com.information.bibliotheque.dto.AuteurResponse;
import com.information.bibliotheque.mapper.AuteurMapper;
import com.information.bibliotheque.model.Auteur;
import com.information.bibliotheque.service.AuteurService;
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

@RestController
@RequestMapping("/api/v1/auteurs")
public class AuteurController {

    private final AuteurService auteurService;
    public AuteurController(AuteurService auteurService) {
        this.auteurService = auteurService;
    }

    @GetMapping
    public ResponseEntity<List<AuteurResponse>> findAll(@PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        Page<AuteurResponse> page = auteurService.findAll(pageable)
                .map(AuteurMapper::versResponse);
        return ResponseEntity.ok((List<AuteurResponse>) page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuteurResponse> findById(@PathVariable Long id){
        return auteurService.findById(id)
                .map(AuteurMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
