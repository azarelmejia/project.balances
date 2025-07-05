package com.azarel.balances.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "EstadoTransacciones")
@Data
public class EstadoTransaccionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 255)
    private String descripcion;
}

