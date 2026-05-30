package com.lovelyshades.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.*;
import java.time.LocalDate;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;
    @NotBlank
    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    @NotNull
    @DecimalMin("0.0")
    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;
    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @Column(name = "valor_costo", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorCosto = BigDecimal.ZERO;

    @Column(name = "valor_venta", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorVenta = BigDecimal.ZERO;

    @Column(name = "id_marca")
    private Integer idMarca;

    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "codigo_interno", length = 80)
    private String codigoInterno;

    @Column(name = "color", length = 60)
    private String color;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    public Producto() {
    }

    public Producto(Integer idProducto, String nombre, String descripcion, BigDecimal precio, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public BigDecimal getValorCosto() {
        return valorCosto;
    }

    public void setValorCosto(BigDecimal valorCosto) {
        this.valorCosto = valorCosto;
    }

    public BigDecimal getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(BigDecimal valorVenta) {
        this.valorVenta = valorVenta;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
