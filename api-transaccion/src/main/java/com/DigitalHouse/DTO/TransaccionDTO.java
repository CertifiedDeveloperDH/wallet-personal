package com.DigitalHouse.DTO;

import com.DigitalHouse.Entity.Cuenta;

import java.math.BigDecimal;

public class TransaccionDTO {
    private Long id;
    private Cuenta cuenta;
    private String tipo;
    private BigDecimal monto;

    public TransaccionDTO(Long id, Cuenta cuenta, String tipo, BigDecimal monto) {
        this.id = id;
        this.cuenta = cuenta;
        this.tipo = tipo;
        this.monto = monto;
    }

    public Long getId() {
        return id;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }
}
