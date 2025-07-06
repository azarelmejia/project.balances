package com.azarel.balances;

import com.azarel.balances.model.CuentaModel;
import com.azarel.balances.model.ResponseModel;
import com.azarel.balances.model.TipoCuentaModel;
import com.azarel.balances.model.TransaccionModel;
import com.azarel.balances.service.AccountsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccountsServiceIntegrationTest {

    @Autowired
    private AccountsService accountsService;

    @Test
    public void IntegracionCrearAndReporte() {
    	/*Prueba de Integracion
    	 * 
    	 * ------------------------------
    	 * Garantizar la creacion del numero de cuenta de forma dinamica de acuerdo al tipo de cuenta se genera la secuencia
    	 * Garantizar la transaccion valindado estado activo de la cunta, si existe
    	 * Garantizar transaccion validando saldo disponible\
    	 * Garantizar la transaccion para actualiar el nuevo saldo disponible
    	 * General el estado de cuenta
    	 * */
    	
    	
        
    	CuentaModel cuenta = CuentaModel.builder()
    		    .idCliente(1)
    		    .tipoCuenta(TipoCuentaModel.builder().id(1).build()) 
    		    .saldoInicial(BigDecimal.ZERO) 
    		    .saldoDisponible(BigDecimal.ZERO)
    		    .estado("Activo")
    		    .creado(LocalDateTime.now())
    		    .build();

        CuentaModel cuentaCreada = accountsService.crearCuenta(cuenta);
        
        assertThat(cuentaCreada.getNumeroCuenta()).isNotNull();
        System.out.println("Número de cuenta creado: " + cuentaCreada.getNumeroCuenta());

        TransaccionModel trx = new TransaccionModel();
        trx.setCuenta(CuentaModel.builder().numeroCuenta(cuentaCreada.getNumeroCuenta()).build());
        trx.setNumCuenta(cuentaCreada.getNumeroCuenta());
        trx.setDescripciontrx("Depósito inicial");
        trx.setFecha(LocalDate.now());
        trx.setMonto(new BigDecimal("200.00"));
        trx.setTipoTransaccion("Deposito");

        ResponseModel trxResponse = accountsService.guardarTransaccion(trx);

        assertThat(trxResponse).isNotNull();
        assertThat(trxResponse.getStatus()).isEqualTo("success");

        List<?> movimientos = accountsService.obtenerTransaccionesPorFecha(
                LocalDate.now(),
                LocalDate.now(),
                cuentaCreada.getIdCliente()
        );

        assertThat(movimientos).isNotNull();
        assertThat(movimientos.size()).isGreaterThan(0);
    }
}
