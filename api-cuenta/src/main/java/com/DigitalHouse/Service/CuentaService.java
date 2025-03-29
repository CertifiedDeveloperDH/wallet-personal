package com.DigitalHouse.Service;

import com.DigitalHouse.DTO.CardDTO;
import com.DigitalHouse.DTO.CuentaDTO;
import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Entity.Cuenta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface CuentaService {

    // Crear una nueva cuenta
    ResponseEntity<Cuenta> crearCuenta(CuentaDTO cuentaDTO);

    // Obtener una cuenta por su ID
    ResponseEntity<Cuenta> obtenerCuentaPorId(Long id);

    // Actualizar una cuenta existente
    ResponseEntity<Cuenta> actualizarCuenta(Long id, CuentaDTO cuentaDTO);

    // Eliminar una cuenta
    ResponseEntity<Void> eliminarCuenta(Long id);

    // Actualizar el alias de una cuenta
    ResponseEntity<Void> actualizarAlias(Long userId, String alias);

    // Verificar si un alias ya existe
    boolean aliasExistente(String alias);

    // Obtener todas las tarjetas asociadas a una cuenta
    ResponseEntity<List<Card>> obtenerTarjetasPorCuentaId(Long id);

    public ResponseEntity<List<Cuenta>> listarCuentas();
}
