package com.azarel.balances.service;

import com.azarel.balances.model.CuentaModel;
import com.azarel.balances.model.EstadoTransaccionModel;
import com.azarel.balances.model.ResponseModel;
import com.azarel.balances.model.TransaccionModel;
import com.azarel.balances.model.TransaccionesCuentasDTO;
import com.azarel.balances.repositorio.CuentaRepositorioCustom;
import com.azarel.balances.repositorio.CuentaModelRepositorio;
import com.azarel.balances.repositorio.TransaccionRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final CuentaModelRepositorio cuentaRepository;
    private final TransaccionRepositorio transaccionRepository;
    private ResponseModel response = new ResponseModel();

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
    public CuentaModel crearCuenta(CuentaModel cuenta) {
    	
        if (cuenta.getTipoCuenta() == null || cuenta.getTipoCuenta().getId() == null) {
            throw new IllegalArgumentException("Tipo de cuenta es requerido.");
        }
        Integer numeroCuenta = cuentaRepository.getNextNumeroCuenta(cuenta.getTipoCuenta().getId());
        cuenta.setNumeroCuenta(numeroCuenta);
        
        return cuentaRepository.save(cuenta);
    }
    
public CuentaModel actualizarSaldoCuenta(CuentaModel cuenta) {
    	
        if (cuenta.getTipoCuenta() == null || cuenta.getTipoCuenta().getId() == null) {
            throw new IllegalArgumentException("Tipo de cuenta es requerido.");
        }
        
        return cuentaRepository.save(cuenta);
    }


    /**
     * Registrar una nueva transacción
     */
    public ResponseModel guardarTransaccion(TransaccionModel transaccion) {
    	
    	Optional<CuentaModel> estadoCuenta = cuentaRepository.findAll()
	            .stream()
	            .filter(cuenta -> cuenta.getNumeroCuenta().equals(transaccion.getNumCuenta()))
	            .findFirst();
    	
    	if(estadoCuenta.get().getNumeroCuenta() > 0) {
    		
    		/*Aqui validamos si la cuenta existe, esta activa y tiene saldo*/
    		BigDecimal vSaldo = null;
    		BigDecimal vNuevoSaldo = null;
    		
    		if(estadoCuenta.get().getEstado().equals("Activo")) {
    			/*
    			transaccionRepository.findAll().stream()
    			.filter(T -> T.getNumeroCuenta() == transaccion.getNumeroCuenta())
    			.sorted(Comparator.comparing(TransaccionModel::getId).reversed())
    			.findFirst(); */
    			
    			// Recuperar la ultima trx de la cuenta
    			
    			vSaldo = calcularSaldoDisponible(transaccion);
    			vNuevoSaldo = nuevoSaldoDisponible(transaccion);
    			transaccion.setSaldo(vNuevoSaldo);
    			transaccion.setEstadoTransaccion(new EstadoTransaccionModel());
    			transaccion.getEstadoTransaccion().setId(1);
    			switch (transaccion.getTipoTransaccion()) {
    			case "Deposito": 
    				
    				transaccionRepository.save(transaccion);
    				
    			break;
    			case "Retiro":
    				
    				if(existeSaldoDisponible(transaccion).compareTo(BigDecimal.ZERO) > 0) {
    					transaccionRepository.save(transaccion);
    					
    					
    				} else {
    					response.setStatus("warning");
    	                response.setMsg("Saldo insuficiente");
    				}
    				
    			break;
    			default:
    				throw new IllegalArgumentException("Tipo de transaccion invalida: " + transaccion.getTipoTransaccion());
    			
    			}
    			response.setStatus("success");
    			response.setMsg("Transaccion exitosa");
    			
    			/*CuentaModel actualizarSaldo = CuentaModel.builder()
    				    .numeroCuenta(transaccion.getNumCuenta()) 
    				    .idCliente(transaccion.getId())
    				    .saldoDisponible(vSaldo)
    				    //.estado("Activo")
    				    .build(); */
    			
    			estadoCuenta.get().setSaldoDisponible(vSaldo);
    			
    			var saldoActualizado = actualizarSaldoCuenta(estadoCuenta.orElse(null));
    			if(saldoActualizado.getSaldoDisponible().equals(vSaldo)) response.setMsg("Transaccion exitosa | Saldo actualizado");
    			
    		} else {
    			response.setStatus("warning");
                response.setMsg("Cuenta inactiva");
    		}
    		
    	} else {
    		response.setStatus("warning");
            response.setMsg("Cuenta no existe");
    	}
        return response;
    }
    
    
    private BigDecimal existeSaldoDisponible(TransaccionModel data) {
    	
    	Optional<CuentaModel> cm = obtenerCuentaPorNumero(data.getNumCuenta());
    	
    	return cm.get().getSaldoDisponible().subtract(data.getMonto());
    }
    
    private BigDecimal calcularSaldoDisponible(TransaccionModel data) {
    	
    	Optional<CuentaModel> cm = obtenerCuentaPorNumero(data.getNumCuenta());
    	BigDecimal Disponible = null;
    	if ("Deposito".equals(data.getTipoTransaccion())) {
    		Disponible = cm.get().getSaldoDisponible().add(data.getMonto());
    	} else {
    		Disponible = cm.get().getSaldoDisponible().subtract(data.getMonto());
    	}
    	return Disponible;
    }
    
    private BigDecimal nuevoSaldoDisponible(TransaccionModel data) {
    	
    	Optional<TransaccionModel> cm = lastTrx(data.getNumCuenta());
    	BigDecimal Disponible = null;
    	if ("Deposito".equals(data.getTipoTransaccion())) {
    		Disponible = cm.get().getSaldo().add(data.getMonto());
    	} else {
    		Disponible = cm.get().getSaldo().subtract(data.getMonto());
    	}
    	return Disponible;
    }
    
    
    public Optional<TransaccionModel> lastTrx(Integer numeroCuenta){
    	Optional<TransaccionModel> lastreg = transaccionRepository.ultimaTransaccionByCuenta(numeroCuenta);
    	return lastreg;
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
    	
    	var rs = transaccionRepository.transaccionesByFecha(fechaInicio, fechaFin, clienteId);

        return rs;
    } 
    
    
}

