package com.lovelyshades.utils;

public class ProductoNoEncontradoException
        extends RuntimeException {

    public ProductoNoEncontradoException(
            String mensaje) {

        super(mensaje);
    }
}
