package com.DigitalHouse.Controller;

import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards") // Endpoint base
public class CardController {

    @Autowired
    private CardService cardService;

    // Obtener todas las tarjetas
    @GetMapping
    public ResponseEntity<List<Card>> obtenerTodasLasTarjetas() {
        List<Card> tarjetas = cardService.obtenerTodasLasTarjetas();
        return ResponseEntity.ok(tarjetas);
    }

    // Obtener tarjeta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Card> obtenerTarjetaPorId(@PathVariable Long id) {
        Optional<Card> tarjeta = cardService.obtenerTarjetaPorId(id);
        return tarjeta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nueva tarjeta
    @PostMapping
    public ResponseEntity<Card> crearTarjeta(@RequestBody Card card) {
        Card nuevaTarjeta = cardService.crearTarjeta(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarjeta);
    }

    // Actualizar tarjeta
    @PutMapping("/{id}")
    public ResponseEntity<Card> actualizarTarjeta(@PathVariable Long id, @RequestBody Card card) {
        Optional<Card> tarjetaActualizada = cardService.actualizarTarjeta(id, card);
        return tarjetaActualizada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar tarjeta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarjeta(@PathVariable Long id) {
        boolean eliminada = cardService.eliminarTarjeta(id);
        if (eliminada) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}