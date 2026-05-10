package com.lovelyshades.service.producto;

import com.lovelyshades.dao.producto.ProductoDao;
import com.lovelyshades.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductoService {

     List<Producto> listar();

     Producto obtener( Integer id);

     Producto crear(Producto producto);

     Producto actualizar( Producto producto);

     String eliminar( Integer id);

     List<Producto> obtenerBajoStock();
}
