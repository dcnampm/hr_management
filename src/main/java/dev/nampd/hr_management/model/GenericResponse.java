package dev.nampd.hr_management.model;

import lombok.Data;

@Data
public class GenericResponse<D> {
    private D data;
    private Integer status;
    private String message;
    private Boolean success;


    public GenericResponse() {
        this(null, 200, "success", true);
    }

    public GenericResponse(D data) {
        this(data, 200, "success", true);
    }

    public GenericResponse(D data, Integer code) {
        this(data, code, "success", true);
    }

    public GenericResponse(D data, Integer code, String message) {
        this(data, code, message, true);
    }

    public GenericResponse(D data, Integer status, String message, Boolean success) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.success = success;
    }
}
