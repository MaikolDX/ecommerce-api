package com.proyecto.api.ecommerceapiv2.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ProjectResponse {
    //Documentar
    private int codigo ;
    private String mensaje;
    private Boolean error ;
    private Date fecha;
    private Object object;

    //TODO - PENDIENTE
    // Constructor con fecha instanciada directamente - probar luego
    /*public ProjectResponse(int code, String message, boolean error, Object data) {
        this.code = code;
        this.message = message;
        this.error = error;
        this.timestamp = getCurrentFormattedDate();
        this.data = data;
    }

    // Método para formatear la fecha actual
    private String getCurrentFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Ajusta la zona horaria si es necesario
        return dateFormat.format(new Date());
    }*/

}
