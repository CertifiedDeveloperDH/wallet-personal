package com.DigitalHouse.Controller;

import com.DigitalHouse.DTO.CuentaDTO;
import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Service.CardService;
import com.DigitalHouse.Service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody CuentaDTO cuentaDTO) {
        return cuentaService.crearCuenta(cuentaDTO);  // Llamamos al servicio que ya retorna ResponseEntity
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Long id) {
        return cuentaService.obtenerCuentaPorId(id); // Llamamos al servicio que devuelve ResponseEntity
    }

    // Actualizar una cuenta
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO) {
        return cuentaService.actualizarCuenta(id, cuentaDTO); // Llamamos al servicio que devuelve ResponseEntity
    }

    // Actualizar el alias de una cuenta
    @PutMapping("/usuario/{userId}/alias")
    public ResponseEntity<Void> actualizarAlias(@PathVariable Long userId, @RequestParam String alias) {
        return cuentaService.actualizarAlias(userId, alias); // Llamamos al servicio
    }

    // Eliminar una cuenta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        return cuentaService.eliminarCuenta(id); // Llamamos al servicio que devuelve ResponseEntity
    }

    // Obtener las tarjetas asociadas a una cuenta
    @GetMapping("/{id}/cards")
    public ResponseEntity<List<Card>> obtenerTarjetasPorCuentaId(@PathVariable Long id) {
        return cuentaService.obtenerTarjetasPorCuentaId(id); // Llama al servicio
    }

}