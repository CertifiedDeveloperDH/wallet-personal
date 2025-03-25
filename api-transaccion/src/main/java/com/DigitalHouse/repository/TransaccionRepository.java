package com.DigitalHouse.repository;

import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransaccionRepository extends CrudRepository<Transaccion,Long> {
    List<Transaccion> findByCuentaIdAndTipo(Long cuentaId, String tipo);

}
