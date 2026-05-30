package com.lovelyshades.dto;

import java.time.LocalDateTime;

public class RolPermisoDTO {

    private Integer idRolPermiso;
    private Integer idRol;
    private Integer idPermiso;
    private LocalDateTime fechaAsignacion;

    public RolPermisoDTO() {
    }

    public RolPermisoDTO(Integer idRolPermiso, Integer idRol, Integer idPermiso, LocalDateTime fechaAsignacion) {
        this.idRolPermiso = idRolPermiso;
        this.idRol = idRol;
        this.idPermiso = idPermiso;
        this.fechaAsignacion = fechaAsignacion;
    }

    // Getters y Setters
    public Integer getIdRolPermiso() {
        return idRolPermiso;
    }

    public void setIdRolPermiso(Integer idRolPermiso) {
        this.idRolPermiso = idRolPermiso;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Integer idPermiso) {
        this.idPermiso = idPermiso;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
