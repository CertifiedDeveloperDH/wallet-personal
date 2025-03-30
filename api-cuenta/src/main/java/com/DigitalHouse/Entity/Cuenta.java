package com.DigitalHouse.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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

    @Column(name = "user_id", nullable = false)
    private Long userId; // ID del usuario asociado a la cuenta

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Card> cards;  // Relación con las tarjetas

    public Cuenta(BigDecimal balance, Long userId) {
        this.alias = generateAlias(); // Generar alias automáticamente al crear una cuenta
        this.balance = balance;
        this.userId = userId;
    }
    public void setAlias(String alias) {
        if (this.alias == null || this.alias.isEmpty()) {
            this.alias = alias;
        }
    }

    public String getAlias() {
        return alias;
    }
}
