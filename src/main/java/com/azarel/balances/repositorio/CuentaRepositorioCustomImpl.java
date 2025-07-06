package com.azarel.balances.repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.azarel.balances.model.TipoCuentaModel;

@Repository
public class CuentaRepositorioCustomImpl implements CuentaRepositorioCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer getNextNumeroCuenta(Integer tipoCuentaId) {
        String secuencia = switch (tipoCuentaId) {
            case 1 -> "seq_cuenta_tipo1";
            case 2 -> "seq_cuenta_tipo2";
            case 3 -> "seq_cuenta_tipo3";
            default -> throw new IllegalArgumentException("Tipo de cuenta no válido: " + tipoCuentaId);
        };

        return ((Number) entityManager
            .createNativeQuery("SELECT nextval('" + secuencia + "')")
            .getSingleResult()).intValue();
    }
}



