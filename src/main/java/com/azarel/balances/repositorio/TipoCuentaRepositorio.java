package com.azarel.balances.repositorio;


import com.azarel.balances.model.TipoCuentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCuentaRepositorio extends JpaRepository<TipoCuentaModel, Integer> {
}

