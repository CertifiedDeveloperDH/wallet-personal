package com.DigitalHouse.entity;

import com.DigitalHouse.Entity.Cuenta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaccion")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cuentaId", nullable = false)
    private Cuenta cuenta;

    private String tipo; // Ejemplo: "Ingreso", "Egreso"


    @Column(nullable = false)
    private BigDecimal monto;

    private LocalDateTime fecha = LocalDateTime.now();
}
