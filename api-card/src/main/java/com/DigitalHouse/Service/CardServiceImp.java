package com.DigitalHouse.Service;

import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImp {  // <-- SOLO UNA CLASE CardService

    @Autowired
    private CardRepository cardRepository;

    public List<Card> obtenerTodasLasTarjetas() {
        return (List<Card>) cardRepository.findAll();
    }

    public Optional<Card> obtenerTarjetaPorId(Long id) {
        return cardRepository.findById(id);
    }

    public Card crearTarjeta(Card card) {
        return cardRepository.save(card);
    }

    public Optional<Card> actualizarTarjeta(Long id, Card card) {
        return cardRepository.findById(id).map(existingCard -> {
            existingCard.setNumero(card.getNumero());
            existingCard.setTipo(card.getTipo());
            existingCard.setCuenta(card.getCuenta());
            return cardRepository.save(existingCard);
        });
    }

    public boolean eliminarTarjeta(Long id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
