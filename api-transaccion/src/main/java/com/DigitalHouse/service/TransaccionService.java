package com.DigitalHouse.service;

import com.DigitalHouse.entity.Transaccion;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransaccionService {
    Transaccion crearTransaccion(Long cuentaId, String tipo, BigDecimal monto);
    Optional<Transaccion> obtenerTransaccionPorTipo(Long cuentaId, String tipo);
    Transaccion registrarTransaccion(Long cuentaId, String tipo, BigDecimal monto);

}
