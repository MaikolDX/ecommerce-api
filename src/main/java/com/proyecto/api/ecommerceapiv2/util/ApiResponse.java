package com.proyecto.api.ecommerceapiv2.util;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {

    private Date tiempo = new Date();
    private String mensaje;
    private String url;

    public ApiResponse(String mensaje, String url) {
        this.mensaje = mensaje;
        this.url = url.replace("uri=", "");
    }
}
