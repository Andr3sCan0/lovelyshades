
CREATE DATABASE lovelyshades;
GO

USE lovelyshades;
GO

-- TABLA CLIENTES
CREATE TABLE clientes (
    id_cliente INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100)
);
GO

-- TABLA PRODUCTOS
CREATE TABLE productos (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(255),
    precio DECIMAL(10,2),
    stock INT
);
GO

-- TABLA VENTAS
CREATE TABLE ventas (
    id_venta INT IDENTITY(1,1) PRIMARY KEY,
    fecha DATETIME DEFAULT GETDATE(),
    id_cliente INT,
    total DECIMAL(10,2),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);
GO


-- TABLA DETALLE_VENTA
CREATE TABLE detalle_venta (
    id_detalle INT IDENTITY(1,1) PRIMARY KEY,
    id_venta INT,
    id_producto INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    FOREIGN KEY (id_venta) REFERENCES ventas(id_venta),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);
GO

-- TABLA CAJA (CUADRE)
CREATE TABLE caja (
    id_caja INT IDENTITY(1,1) PRIMARY KEY,
    fecha DATE,
    total_ventas DECIMAL(10,2),
    monto_inicial DECIMAL(10,2),
    monto_final DECIMAL(10,2),
    diferencia DECIMAL(10,2)
);
GO

-- INSERTS DE PRUEBA
-- CLIENTES
INSERT INTO clientes (nombre, telefono, email) VALUES
('Maria Lopez', '3001234567', 'maria@email.com'),
('Juan Perez', '3019876543', 'juan@email.com');
GO

-- PRODUCTOS
INSERT INTO productos (nombre, descripcion, precio, stock) VALUES
('Labial', 'Labial rojo mate', 25000, 50),
('Base', 'Base líquida tono medio', 45000, 30);
GO

-- VENTAS
INSERT INTO ventas (id_cliente, total) VALUES (1, 70000);
GO

-- DETALLE VENTA
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal)
VALUES (1, 1, 2, 25000, 50000);
GO

-- CAJA
INSERT INTO caja (fecha, total_ventas, monto_inicial, monto_final, diferencia)
VALUES (CAST(GETDATE() AS DATE), 70000, 100000, 170000, 0);
GO

-- CONSULTAS IMPORTANTES (PRUEBAS)

-- VER PRODUCTOS
SELECT * FROM productos;
GO

-- VER CLIENTES
SELECT * FROM clientes;
GO

-- VER VENTAS CON CLIENTE
SELECT v.id_venta, c.nombre, v.total, v.fecha
FROM ventas v
INNER JOIN clientes c ON v.id_cliente = c.id_cliente;
GO

-- VER DETALLE DE VENTA
SELECT p.nombre, d.cantidad, d.subtotal
FROM detalle_venta d
INNER JOIN productos p ON d.id_producto = p.id_producto
WHERE d.id_venta = 1;
GO

-- ACTUALIZAR STOCK
UPDATE productos
SET stock = stock - 2
WHERE id_producto = 1;
GO

-- ELIMINAR PRODUCTO
DELETE FROM productos WHERE id_producto = 2;
GO


-- BONUS (ESTO SI QUIEREN LO ELIMINAN PERO ME TIENEN QUE AVISAR)

-- PRODUCTOS CON STOCK BAJO
SELECT * FROM productos WHERE stock < 10;
GO

-- TOTAL VENTAS DEL DIA
SELECT SUM(total) AS total_dia
FROM ventas
WHERE CAST(fecha AS DATE) = CAST(GETDATE() AS DATE);
GO