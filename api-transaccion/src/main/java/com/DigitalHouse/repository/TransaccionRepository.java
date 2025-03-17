package com.DigitalHouse.repository;

import com.DigitalHouse.entity.Transaccion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransaccionRepository extends CrudRepository<Transaccion,Long> {

    List<Transaccion> findByAccountIdAndType(Long accountId, String type);

}
