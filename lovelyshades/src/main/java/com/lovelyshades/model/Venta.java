package com.lovelyshades.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Venta {

    private Integer idVenta;
    private LocalDateTime fecha;
    private Integer idCliente;
    private BigDecimal total;

    public Venta() {
    }

    public Venta(Integer idVenta, LocalDateTime fecha, Integer idCliente, BigDecimal total) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.total = total;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
