package com.azarel.balances.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionesCuentasDTO {

    private Integer id;
    private Integer numeroCuenta;
    private BigDecimal monto;
    private BigDecimal saldo;
    private String nombreEstado;    
    private String tipoTransaccion;
    private LocalDate fecha;
    private LocalDateTime creado;
}
