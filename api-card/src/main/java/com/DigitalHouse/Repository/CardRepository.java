package com.DigitalHouse.Repository;

import com.DigitalHouse.Entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card,Long> {
    Optional<Card> findByUserId(Long userId);

    List<Card> findCardsByCuentaId(Long cuentaId);
}
