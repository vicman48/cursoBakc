package com.api.rest.apirestencuestas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opcion {
    @Id
    @GeneratedValue
    @Column(name = "OPCION_ID")
    private Long id;

    private String value;

}
