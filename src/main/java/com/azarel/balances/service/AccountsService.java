package com.azarel.balances.service;

import com.azarel.balances.model.CuentaModel;
import com.azarel.balances.model.TransaccionModel;
import com.azarel.balances.model.TransaccionesCuentasDTO;
import com.azarel.balances.repositorio.CuentaCustomRepositorio;
import com.azarel.balances.repositorio.CuentaModelRepositorio;
import com.azarel.balances.repositorio.TransaccionRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final CuentaModelRepositorio cuentaRepository;
    private final TransaccionRepositorio transaccionRepository;

    /**
     * Obtener cuenta por número de cuenta
     */
    public Optional<CuentaModel> obtenerCuentaPorNumero(Integer numeroCuenta) {
        return cuentaRepository.findById(numeroCuenta);
    }

    /**
     * Obtener todas las cuentas
     */
    public List<CuentaModel> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll();
    }

    /**
     * Obtener transacciones por número de cuenta
     */
    /*public List<TransaccionModel> obtenerTransaccionesPorCuenta(Integer numeroCuenta) {
        return transaccionRepository.findByCuenta_NumeroCuenta(numeroCuenta);
    } */

    /**
     * Crear o actualizar una cuenta
     */
    public CuentaModel guardarCuenta(CuentaModel cuenta) {
    	
    	Integer sequence = new CuentaCustomRepositorio().getNextNumeroCuenta(cuenta.getTipoCuenta().getId());
    	cuenta.setNumeroCuenta(sequence);
        return cuentaRepository.save(cuenta);
    }

    /**
     * Registrar una nueva transacción
     */
    public TransaccionModel guardarTransaccion(TransaccionModel transaccion) {
        return transaccionRepository.save(transaccion);
    }

    /**
     * Eliminar cuenta por ID
     */
    public void eliminarCuenta(Integer numeroCuenta) {
        cuentaRepository.deleteById(numeroCuenta);
    }
    
    /**
     * Buscsar por cuenta y fecha
     */
    public List<TransaccionesCuentasDTO> obtenerTransaccionesPorFecha(
            LocalDate fechaInicio, LocalDate fechaFin, Integer clienteId) {

        return transaccionRepository.transaccionesByFecha(fechaInicio, fechaFin, clienteId);
    } 
    
    
}

