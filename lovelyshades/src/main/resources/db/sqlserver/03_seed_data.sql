USE lovelyshades;
GO

INSERT INTO clientes (nombre, telefono, email) VALUES
('Maria Lopez', '3001234567', 'maria@email.com'),
('Juan Perez', '3019876543', 'juan@email.com');
GO

INSERT INTO productos (nombre, descripcion, precio, stock) VALUES
('Labial', 'Labial rojo mate', 25000, 50),
('Base', 'Base líquida tono medio', 45000, 30);
GO

INSERT INTO ventas (id_cliente, total) VALUES (1, 70000);
GO

INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal)
VALUES (1, 1, 2, 25000, 50000);
GO

INSERT INTO caja (fecha, total_ventas, monto_inicial, monto_final, diferencia)
VALUES (CAST(GETDATE() AS DATE), 70000, 100000, 170000, 0);
GO
