package com.microservices.usuario.repository;

import com.microservices.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
    Optional<Usuario> findByEmail(String email);

}
