package com.DigitalHouse.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

import static com.DigitalHouse.Entity.AliasGenerator.generateAlias;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String alias;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Long userId; // ID del usuario asociado a la cuenta
}
