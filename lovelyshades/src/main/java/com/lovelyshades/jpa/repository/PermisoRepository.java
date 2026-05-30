package com.lovelyshades.jpa.repository;

import com.lovelyshades.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    Optional<Permiso> findByNombrePermiso(String nombrePermiso);
    Optional<Permiso> findByIdPermiso(Integer idPermiso);
}
