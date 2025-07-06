package com.azarel.balances.controllers;

import com.azarel.balances.model.TransaccionesCuentasDTO;
import com.azarel.balances.model.CuentaModel;
import com.azarel.balances.model.ResponseModel;
import com.azarel.balances.model.TransaccionModel;
import com.azarel.balances.service.AccountsService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class AccountsController {
	
	
    private final AccountsService accountsService;
    
    @PostConstruct
    public void init() {
        System.out.println(">>> AccountsController inicializado correctamente <<<");
    }
    
    @PostMapping("/crear")
    public ResponseModel crearcuenta(@RequestBody CuentaModel cliente) {

        ResponseModel response = new ResponseModel();

        try {
        	CuentaModel cuenta = accountsService.crearCuenta(cliente);
            
            if(cuenta.getIdCliente() > 0) {
            	response.setStatus("success");
                response.setMsg("Cuenta Creada: " + cuenta.getNumeroCuenta());
                response.setData(cuenta);
            }
            

        } catch (Exception e) {
            response.setStatus("error");
            response.setMsg("Ocurrió un error al guardar los datos: " + e.getMessage());
            response.setData(null);
            
            e.printStackTrace();
        }

        return response;
    }
    

    @GetMapping("/movimientos")
    public ResponseModel obtenerTransacciones(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam("clienteId") Integer clienteId) {

        ResponseModel response = new ResponseModel();

        try {
            List<TransaccionesCuentasDTO> transacciones = accountsService.obtenerTransaccionesPorFecha(fechaInicio, fechaFin, clienteId);
            
            if(transacciones.size() > 0) {
            	response.setStatus("success");
                response.setMsg("Datos obtenidos correctamente");
                response.setData(transacciones);
            }
            

        } catch (Exception e) {
            response.setStatus("error");
            response.setMsg("Ocurrió un error al obtener los datos: " + e.getMessage());
            response.setData(null);
            
            e.printStackTrace();
        }

        return response;
    }
    
    
    @PostMapping("/creartrx")
    public ResponseModel creartrx(@RequestBody TransaccionModel trx) {

        ResponseModel response = new ResponseModel();

        try {
        	response = accountsService.guardarTransaccion(trx);
            
            if(response.getStatus().equals("success")) {
            	response.setStatus("success");
                response.setMsg("Transaccion Completada: ");
                
            }
            

        } catch (Exception e) {
            response.setStatus("error");
            response.setMsg("Ocurrió un error al guardar los datos: " + e.getMessage());
            response.setData(null);
            
            e.printStackTrace();
        }

        return response;
    }

}
