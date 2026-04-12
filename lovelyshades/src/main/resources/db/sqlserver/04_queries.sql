USE lovelyshades;
GO

SELECT * FROM productos;
GO

SELECT * FROM clientes;
GO

SELECT v.id_venta, c.nombre, v.total, v.fecha
FROM ventas v
INNER JOIN clientes c ON v.id_cliente = c.id_cliente;
GO

SELECT p.nombre, d.cantidad, d.subtotal
FROM detalle_venta d
INNER JOIN productos p ON d.id_producto = p.id_producto
WHERE d.id_venta = 1;
GO

UPDATE productos
SET stock = stock - 2
WHERE id_producto = 1;
GO

DELETE FROM productos WHERE id_producto = 2;
GO

SELECT * FROM productos WHERE stock < 10;
GO

SELECT SUM(total) AS total_dia
FROM ventas
WHERE CAST(fecha AS DATE) = CAST(GETDATE() AS DATE);
GO
