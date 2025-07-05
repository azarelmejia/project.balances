package com.azarel.balances.repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.azarel.balances.model.TipoCuentaModel;

@Repository
public class CuentaCustomRepositorio  {

    @PersistenceContext
    private EntityManager entityManager;

    public Integer getNextNumeroCuenta(Integer tipoCuentaModel) {
        String secuencia = switch (tipoCuentaModel) {
            case 1 -> "seq_cuenta_tipo1";
            case 2 -> "seq_cuenta_tipo2";
            case 3 -> "seq_cuenta_tipo3";
            default -> throw new IllegalArgumentException("Tipo de cuenta no válido: " + tipoCuentaModel);
        };

        return (int) ((Number) entityManager
                .createNativeQuery("SELECT nextval('" + secuencia + "')")
                .getSingleResult()).longValue();
    }
}

