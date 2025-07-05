package com.azarel.balances.repositorio;

import com.azarel.balances.model.EstadoTransaccionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoTransaccionRepositorio extends JpaRepository<EstadoTransaccionModel, Integer> {
    
}
