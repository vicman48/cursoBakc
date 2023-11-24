package com.api.rest.apirestencuestas.controller;

import com.api.rest.apirestencuestas.model.Encuesta;
import com.api.rest.apirestencuestas.repository.EncuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class EncuestaController {

    @Autowired
    private EncuestaRepository encuestaRepository;

    @GetMapping("/encuestas")
    public ResponseEntity<Iterable<Encuesta>> listarTodasLasCuentas(){
        return new ResponseEntity<>(encuestaRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/encuestas")
    public ResponseEntity<?> crearEncuesta(@RequestBody Encuesta encuesta){
        encuesta = encuestaRepository.save(encuesta);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI newEncuestaURI = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(encuesta.getId()).toUri();
        httpHeaders.setLocation(newEncuestaURI);
        return new ResponseEntity<>(null,HttpStatus.CREATED);

    }

    @GetMapping("/encuesta/{encuestaId}")
    public ResponseEntity<?> obtenerEncuestas(@RequestBody Long encuestaId){
        Optional<Encuesta> encuesta = encuestaRepository.findById(encuestaId);

        if(encuesta.isPresent()){
            return new ResponseEntity<>(encuesta,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
    }


    @PutMapping("/encuesta/{encuestaId}")
    public ResponseEntity<?> actulizarEncuesta(@RequestBody Encuesta encuesta,@PathVariable Long encuestaId){
        encuesta.setId(encuestaId);
        Encuesta e = encuestaRepository.save(encuesta);
        return new ResponseEntity<>(e,HttpStatus.OK);
    }


    @DeleteMapping("/encuestas/{encuestaId}")
    public ResponseEntity<?> eliminarEncuesta(@PathVariable Long encuestaId){
        encuestaRepository.deleteById(encuestaId);
        return new ResponseEntity<>("Se ha eliminado la cuenta",HttpStatus.OK);
    }

}
