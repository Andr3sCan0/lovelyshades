package com.lovelyshades.jpa.repository;

import com.lovelyshades.jpa.entity.ProductoUtilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductoUtilJpaRepository extends JpaRepository<ProductoUtilEntity, Integer> {

    // Derived query: Spring genera el SQL automáticamente
    List<ProductoUtilEntity> findByStockLessThan(int stock);

    // JPQL query explícita
    @Query("SELECT p FROM ProductoEntity p WHERE p.nombre LIKE %:nombre%")
    List<ProductoUtilEntity> buscarPorNombre(@Param("nombre") String nombre);
}