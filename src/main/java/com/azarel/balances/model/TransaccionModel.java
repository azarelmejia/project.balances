package com.azarel.balances.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transacciones")
@Data
public class TransaccionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "numerocuenta", nullable = false)
    private CuentaModel cuenta;
    
    @Column(name = "monto", precision = 15, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "tipotransaccion", length = 50, nullable = false)
    private String tipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "idestadotransaccion", nullable = false)
    private EstadoTransaccionModel estadoTransaccion;

    private LocalDate fecha;

    private LocalDateTime creado;
    
    @Column(name = "numerocuenta", nullable = false)
    private Integer numeroCuenta;
    
    @Column(name = "idestadotransaccion", nullable = false)
    private Integer idEstadoTransaccion;
    
    @Column(name = "descripciontrx", nullable = false)
    private String descripciontrx;
    
}

