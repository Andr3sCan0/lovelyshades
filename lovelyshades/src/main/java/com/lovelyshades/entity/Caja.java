package com.lovelyshades.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Caja {

    private Integer idCaja;
    private LocalDate fecha;
    private BigDecimal totalVentas;
    private BigDecimal montoInicial;
    private BigDecimal montoFinal;
    private BigDecimal diferencia;

    public Caja() {
    }

    public Caja(Integer idCaja, LocalDate fecha, BigDecimal totalVentas, BigDecimal montoInicial,
                BigDecimal montoFinal, BigDecimal diferencia) {
        this.idCaja = idCaja;
        this.fecha = fecha;
        this.totalVentas = totalVentas;
        this.montoInicial = montoInicial;
        this.montoFinal = montoFinal;
        this.diferencia = diferencia;
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(BigDecimal totalVentas) {
        this.totalVentas = totalVentas;
    }

    public BigDecimal getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }

    public BigDecimal getMontoFinal() {
        return montoFinal;
    }

    public void setMontoFinal(BigDecimal montoFinal) {
        this.montoFinal = montoFinal;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }
}
