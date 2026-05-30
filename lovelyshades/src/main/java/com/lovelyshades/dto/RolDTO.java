package com.lovelyshades.dto;

import java.util.Set;

public class RolDTO {

    private Integer idRol;
    private String nombreRol;
    private Boolean estado;
    private Set<PermisoDTO> permisos;

    public RolDTO() {
    }

    public RolDTO(Integer idRol, String nombreRol, Boolean estado) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<PermisoDTO> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<PermisoDTO> permisos) {
        this.permisos = permisos;
    }
}
