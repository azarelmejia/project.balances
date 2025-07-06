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
    @JoinColumn(name = "numerocuenta", referencedColumnName = "numeroCuenta", nullable = false)
    private CuentaModel cuenta;
    
    @Column(name = "monto", precision = 15, scale = 2, nullable = false)
    private BigDecimal monto;
    
    @Column(name = "saldo", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Column(name = "tipotransaccion", length = 50, nullable = false)
    private String tipoTransaccion;

    @ManyToOne
    @JoinColumn(name = "idestadotransaccion", nullable = false)
    private EstadoTransaccionModel estadoTransaccion;

    private LocalDate fecha;

    private LocalDateTime creado;
    
    //@Column(name = "numerocuenta", nullable = false)
    @Transient
    private Integer numCuenta;
    
    //@Column(name = "idestadotransaccion", nullable = false)
    @Transient
    private Integer estadoTransaccionid;
    
    @Column(name = "descripciontrx", nullable = false)
    private String descripciontrx;
    
}

