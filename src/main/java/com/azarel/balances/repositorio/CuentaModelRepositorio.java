package com.azarel.balances.repositorio;

import com.azarel.balances.model.CuentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaModelRepositorio extends JpaRepository<CuentaModel, Integer> {
    // Por ejemplo: List<CuentaModel> findByIdCliente(Integer idCliente);
	
	
}

