package com.api.rest.apirestencuestas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voto {

    @Id
    @GeneratedValue
    @Column(name = "VOTO_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OPCION_ID")
    private Opcion opcion;
}
