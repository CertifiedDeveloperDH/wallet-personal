package com.DigitalHouse.Service;

import com.DigitalHouse.Entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    public List<Card> obtenerTodasLasTarjetas();
    public Optional<Card> obtenerTarjetaPorId(Long id);
    public Card crearTarjeta(Card card);
    public Optional<Card> actualizarTarjeta(Long id, Card card);
    public boolean eliminarTarjeta(Long id);
}
