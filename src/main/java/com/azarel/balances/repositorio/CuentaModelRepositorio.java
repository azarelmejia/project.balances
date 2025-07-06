package com.azarel.balances.repositorio;

import com.azarel.balances.model.CuentaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaModelRepositorio extends JpaRepository<CuentaModel, Integer>, CuentaRepositorioCustom {
}
