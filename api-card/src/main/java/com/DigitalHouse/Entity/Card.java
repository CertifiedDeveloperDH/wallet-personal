package com.DigitalHouse.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import static com.DigitalHouse.Entity.AliasGenerator.generateAlias;

@Entity
@Builder
@Data
@Getter
@Setter
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String tipo; // Visa, Mastercard, etc.

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    public Card(Long id, String numero, String tipo, Cuenta cuenta) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.cuenta = cuenta;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
