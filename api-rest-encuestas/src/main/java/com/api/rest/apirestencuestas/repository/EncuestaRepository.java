package com.api.rest.apirestencuestas.repository;

import com.api.rest.apirestencuestas.model.Encuesta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncuestaRepository extends CrudRepository<Encuesta,Long> {
}
