package com.DigitalHouse.Service;

import com.DigitalHouse.DTO.CuentaDTO;
import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Repository.CardRepository;
import com.DigitalHouse.Repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CuentaServiceImp implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CardRepository cardRepository;

    // Crear una nueva cuenta
    @Override
    public ResponseEntity<Cuenta> crearCuenta(CuentaDTO cuentaDTO) {
        // Lógica para crear una nueva cuenta
        Cuenta cuenta = new Cuenta();
        cuenta.setUserId(cuentaDTO.getUserId()); // Asumiendo que CuentaDTO tiene un campo userId
        cuenta.setAlias(cuentaDTO.getAlias());   // Asumiendo que CuentaDTO tiene un campo alias

        // Aquí puedes agregar más lógica de inicialización de campos según sea necesario
        cuenta = cuentaRepository.save(cuenta);

        // Retornar ResponseEntity con estado HTTP 201 (Created) y la cuenta recién creada
        return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
    }

    // Obtener cuenta por ID
    @Override
    public ResponseEntity<Cuenta> obtenerCuentaPorId(Long id) {
        try {
            Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);
            return cuentaOpt.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            // Registrar el error o manejar excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // Actualizar una cuenta
    @Override
    public ResponseEntity<Cuenta> actualizarCuenta(Long id, CuentaDTO cuentaDTO) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id);

        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();
            cuenta.setAlias(cuentaDTO.getAlias());
            cuenta.setUserId(cuentaDTO.getUserId());
            // Aquí puedes agregar más lógica de actualización de campos según sea necesario
            Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

            // Devuelve la cuenta actualizada con código de estado 200 OK
            return ResponseEntity.ok(cuentaActualizada);
        }

        // Si la cuenta no se encuentra, devuelve 404 Not Found
        return ResponseEntity.notFound().build();
    }

    // Verificar si un alias ya existe
    @Override
    public boolean aliasExistente(String alias) {
        return cuentaRepository.findByAlias(alias).isPresent();
    }

    // Actualizar alias de una cuenta
    @Override
    public ResponseEntity<Void> actualizarAlias(Long userId, String alias) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findByUserId(userId);

        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();
            cuenta.setAlias(alias);
            cuentaRepository.save(cuenta);
            // Si se actualiza correctamente, se retorna 204 No Content
            return ResponseEntity.noContent().build();
        } else {
            // Si no se encuentra la cuenta, se retorna 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar una cuenta
        @Override
        public ResponseEntity<Void> eliminarCuenta(Long id) {
            Optional<Cuenta> cuentaOpt = cuentaRepository.findById(id); // Buscar la cuenta por su ID
            if (cuentaOpt.isPresent()) {
                cuentaRepository.deleteById(id); // Eliminar la cuenta
                return ResponseEntity.noContent().build(); // 204 No Content
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found si la cuenta no existe
            }
        }

        @Override
        public ResponseEntity<List<Card>> obtenerTarjetasPorCuentaId(Long id) {
            List<Card> tarjetas = cardRepository.findCardsByCuentaId(id); // Asumiendo que este método existe en el repositorio de Card
            if (tarjetas.isEmpty()) {
                return ResponseEntity.notFound().build(); // Retorna un 404 si no hay tarjetas
            }
            return ResponseEntity.ok(tarjetas); // Retorna las tarjetas con un estado 200 OK
        }
    @Override
    public ResponseEntity<List<Cuenta>> listarCuentas() {
        List<Cuenta> cuentas = (List<Cuenta>) cuentaRepository.findAll(); // findAll() devuelve Iterable, por eso lo convertimos a List
        return ResponseEntity.ok(cuentas);
    }
}
