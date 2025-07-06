package com.azarel.balances.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    private String status = "Warning";     // Ej: "success", "error"
    private String msg = "Problema en la peticion con la Data";        // Mensaje para el servicio
    private Object data =  null;       // Puede ser cualquier tipo de objeto
}

