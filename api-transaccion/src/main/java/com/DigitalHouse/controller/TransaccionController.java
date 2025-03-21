package com.DigitalHouse.controller;

import com.DigitalHouse.entity.Transaccion;
import com.DigitalHouse.service.TransaccionServiceImp;
import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaccion")
public class TransaccionController {
    @Autowired
    private TransaccionServiceImp transactionService;

    @PostMapping
    public ResponseEntity<Transaccion> createTransaction(@RequestParam Long cuentaId, @RequestParam String tipo, @RequestParam BigDecimal monto) {
        Transaction transaction = (Transaction) transactionService.crearTransaccion(cuentaId, tipo, monto);
        return ResponseEntity.ok((Transaccion) transaction);
    }

    @GetMapping("/{cuentaId}")
    public ResponseEntity<Optional<Transaccion>> getTransactions(@PathVariable Long cuentaId, @RequestParam String tipo) {
        return ResponseEntity.ok(transactionService.obtenerTransaccionPorTipo(cuentaId, tipo));
    }
}
