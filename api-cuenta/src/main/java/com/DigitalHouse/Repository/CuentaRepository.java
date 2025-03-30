package com.DigitalHouse.Repository;

import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Entity.Cuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta,Long> {
    Optional<Cuenta> findByUserId(Long userId);
    Optional<Cuenta> findByAlias(String alias);

}
