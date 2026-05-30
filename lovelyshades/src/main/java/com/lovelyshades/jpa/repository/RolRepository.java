package com.lovelyshades.jpa.repository;

import com.lovelyshades.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombreRol(String nombreRol);
    Optional<Rol> findByIdRol(Integer idRol);
}
