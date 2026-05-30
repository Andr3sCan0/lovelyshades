package com.lovelyshades.jpa.repository;

import com.lovelyshades.model.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Integer> {
    List<RolPermiso> findByRolIdRol(Integer idRol);
    List<RolPermiso> findByPermisoIdPermiso(Integer idPermiso);
    Optional<RolPermiso> findByRolIdRolAndPermisoIdPermiso(Integer idRol, Integer idPermiso);
}
