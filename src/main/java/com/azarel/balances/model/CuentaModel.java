package com.azarel.balances.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cuentas")
@Data
@Builder
public class CuentaModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numerocuenta")
    private Integer numeroCuenta;

    @Column(name = "idcliente", nullable = false)
    private Integer idCliente;

    @ManyToOne
    @JoinColumn(name = "idtipocuenta", nullable = false)
    private TipoCuentaModel tipoCuenta;

    @Column(name = "saldoinicial", precision = 15, scale = 2)
    private BigDecimal saldoInicial;

    @Column(name = "saldodisponible", precision = 15, scale = 2)
    private BigDecimal saldoDisponible;

    @Column(length = 20)
    private String estado;

    private LocalDateTime creado;
}

