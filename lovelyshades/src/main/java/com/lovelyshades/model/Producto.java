package com.lovelyshades.model;

feature/dao
import java.math.BigDecimal;

public class Producto {

    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;

    public Producto() {
    }

    public Producto(Integer idProducto, String nombre, String descripcion, BigDecimal precio, Integer stock) {
public class Producto {

    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

    public Producto() {}

    public Producto(int idProducto, String nombre, String descripcion, double precio, int stock) {
  main
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    feature/dao
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
main
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

feature/dao
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
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
main
        this.stock = stock;
    }
}
