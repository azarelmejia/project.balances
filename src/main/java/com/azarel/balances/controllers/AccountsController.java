package com.azarel.balances.controllers;

import com.azarel.balances.model.TransaccionesCuentasDTO;
import com.azarel.balances.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountsService accountsService;

    @GetMapping("/movimientos")
    public List<TransaccionesCuentasDTO> obtenerTransacciones(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam("clienteId") Integer clienteId) {

        return accountsService.obtenerTransaccionesPorFecha(fechaInicio, fechaFin, clienteId);
    }
}
