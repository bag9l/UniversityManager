package com.botscrew.universitymanager.controller;

import com.botscrew.universitymanager.dto.LectorDTO;
import com.botscrew.universitymanager.model.Lector;
import com.botscrew.universitymanager.service.LectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/lectors")
public class LectorController {

    private final LectorService lectorService;


    @GetMapping
    public ResponseEntity<List<Lector>> getLectors() {
        return ResponseEntity.status(HttpStatus.OK).body(
                lectorService.getAllLectors());
    }

    @GetMapping("{id}")
    public ResponseEntity<Lector> getLectorById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                lectorService.getLectorById(id));
    }

    @PostMapping
    public ResponseEntity<Lector> createLector(@RequestBody @Valid LectorDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                lectorService.addLector(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<Lector> updateLector(@RequestBody @Valid LectorDTO dto,
                                               @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                lectorService.updateLector(dto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLector(@PathVariable("id") String id) {
        lectorService.deleteLectorById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<Lector>> findByTemplate(@RequestParam("template") String template) {
        return ResponseEntity.status(HttpStatus.OK).body(
                lectorService.findLectorsByTemplate(template));
    }
}
