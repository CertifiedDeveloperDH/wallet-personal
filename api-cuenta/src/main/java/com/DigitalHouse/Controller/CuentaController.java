package com.DigitalHouse.Controller;

import com.DigitalHouse.Entity.Cuenta;
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

    @PostMapping
    public ResponseEntity<Cuenta> crearCuenta(@RequestBody Cuenta cuenta) {
        Cuenta nuevaCuenta = cuentaService.crearCuenta(cuenta);
        return ResponseEntity.ok(nuevaCuenta);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Long id) {
        return cuentaService.obtenerCuentaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        try {
            Cuenta cuentaActualizada = cuentaService.actualizarCuenta(id, cuenta);
            return ResponseEntity.ok(cuentaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // o un mensaje con más detalle
        }
    }


    @PutMapping("/usuario/{userId}/alias")
    public ResponseEntity<Void> actualizarAlias(@PathVariable Long userId, @RequestParam String alias) {
        // Verifica si el usuario existe
        Optional<Cuenta> cuentaOpt = cuentaService.obtenerCuentaPorId(userId);
        if (cuentaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Si no se encuentra el usuario, responde con 404
        }

        // Verifica si el alias ya está en uso
        if (cuentaService.aliasExistente(alias)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Si el alias ya existe, responde con 400
        }

        // Actualiza el alias
        cuentaService.actualizarAlias(userId, alias);

        // Retorna respuesta 204 No Content si la actualización fue exitosa
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        try {
            cuentaService.eliminarCuenta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
