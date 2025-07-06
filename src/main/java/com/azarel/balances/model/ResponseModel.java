package com.azarel.balances.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    private String status = "Warning";     
    private String msg = "Problema en la peticion con la Data";       
    private Object data =  null;      
}

