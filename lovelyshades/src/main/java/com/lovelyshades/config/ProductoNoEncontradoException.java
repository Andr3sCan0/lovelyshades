package com.lovelyshades.config;

public class ProductoNoEncontradoException
        extends RuntimeException {

    public ProductoNoEncontradoException(
            String mensaje) {

        super(mensaje);
    }
}
