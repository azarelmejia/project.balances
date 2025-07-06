package com.azarel.balances.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.azarel.balances.model.TransaccionModel;
import com.azarel.balances.model.TransaccionesCuentasDTO;

@Repository
public interface TransaccionRepositorio extends JpaRepository<TransaccionModel, Integer>{
	
	@Query
	("""
	    SELECT new com.azarel.balances.model.TransaccionesCuentasDTO(
	        t.id,
	        t.cuenta.numeroCuenta,
	        t.monto,
	        t.saldo,
	        et.nombre,
	        t.tipoTransaccion,
	        t.fecha,
	        t.creado
	    )
	    FROM TransaccionModel t
	    JOIN t.cuenta c
	    JOIN t.estadoTransaccion et
	    JOIN c.tipoCuenta tc
	    WHERE t.fecha BETWEEN :fechaInicio AND :fechaFin
	    AND c.idCliente = :clienteId Order by t.id asc
	    """)
	List<TransaccionesCuentasDTO> transaccionesByFecha(
	        @Param("fechaInicio") LocalDate fechaInicio,
	        @Param("fechaFin") LocalDate fechaFin,
	        @Param("clienteId") Integer clienteId
	);
	
	
	@Query("SELECT t FROM TransaccionModel t WHERE t.cuenta.numeroCuenta = :numeroCuenta ORDER BY t.id DESC LIMIT 1")
	Optional<TransaccionModel> ultimaTransaccionByCuenta(@Param("numeroCuenta") Integer numeroCuenta);
	
	@Query("SELECT count(*) FROM TransaccionModel t WHERE t.cuenta.numeroCuenta = :numeroCuenta")
	Optional<Integer> existeCuenta(@Param("numeroCuenta") Integer numeroCuenta);



}
