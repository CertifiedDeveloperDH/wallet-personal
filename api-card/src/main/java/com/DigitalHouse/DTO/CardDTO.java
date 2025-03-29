package com.DigitalHouse.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class CardDTO {
    private Long id;
    private String numero;
    private String tipo;
    private Long cuentaId; // Solo enviamos el ID de la cuenta para evitar referencias circulares

    public CardDTO(Long id, String numero, String tipo, Long cuentaId) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.cuentaId = cuentaId;
    }

    public CardDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public Long getCuentaId() {
        return cuentaId;
    }
}
