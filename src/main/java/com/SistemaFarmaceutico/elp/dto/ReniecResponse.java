package com.SistemaFarmaceutico.elp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReniecResponse {
    private boolean success;
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String numero;
        private String nombres;

        @JsonProperty("apellido_paterno")
        private String apellidoPaterno;

        @JsonProperty("apellido_materno")
        private String apellidoMaterno;

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getNombres() {
            return nombres;
        }

        public void setNombres(String nombres) {
            this.nombres = nombres;
        }

        public String getApellidoPaterno() {
            return apellidoPaterno;
        }

        public void setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
        }

        public String getApellidoMaterno() {
            return apellidoMaterno;
        }

        public void setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
        }

        // ✅ MÉTODO CORRECTO PARA OBTENER NOMBRE COMPLETO
        public String getNombreCompleto() {
            return String.format("%s %s %s",
                    nombres != null ? nombres : "",
                    apellidoPaterno != null ? apellidoPaterno : "",
                    apellidoMaterno != null ? apellidoMaterno : ""
            ).trim();
        }
    }
}
