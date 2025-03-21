package com.DigitalHouse.DTO;

import java.math.BigDecimal;

public class CuentaDTO {
    private Long id;
    private String alias;
    private BigDecimal balance;
    private Long userId;

    public CuentaDTO(Long id, String alias, BigDecimal balance, Long userId) {
        this.id = id;
        this.alias = alias;
        this.balance = balance;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getUserId() {
        return userId;
    }
}
