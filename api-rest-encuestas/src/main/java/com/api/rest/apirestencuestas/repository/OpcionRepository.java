package com.api.rest.apirestencuestas.repository;

import com.api.rest.apirestencuestas.model.Encuesta;
import com.api.rest.apirestencuestas.model.Opcion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpcionRepository extends CrudRepository<Opcion,Long> {
}
