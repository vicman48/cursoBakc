package com.api.rest.apirestencuestas.controller;

import com.api.rest.apirestencuestas.model.Voto;
import com.api.rest.apirestencuestas.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class VotoController {
    @Autowired
    private VotoRepository votoRepository;

    @PostMapping("/encuestas/{encuestaId}/votos")
    public ResponseEntity<?> crearVoto(@PathVariable Long encuestaId, @RequestBody Voto voto){
        voto = votoRepository.save(voto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(voto.getId())
                    .toUri());

        return new ResponseEntity<>(null,httpHeaders, HttpStatus.CREATED);
    }
    @GetMapping("/encuestas/{encuestasId}/votos")
    public Iterable<Voto> listarTodosLosVotos(@PathVariable Long encuestasId){
        return votoRepository.findByEncuesta(encuestasId);
    }
}
