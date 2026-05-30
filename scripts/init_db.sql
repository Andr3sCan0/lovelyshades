USE master;
GO

IF DB_ID('lovelyshades') IS NULL
BEGIN
    CREATE DATABASE lovelyshades;
END
GO

USE lovelyshades;
GO

-- Roles base
IF NOT EXISTS (SELECT 1 FROM Rol WHERE nombre_rol = 'ADMIN')
BEGIN
    INSERT INTO Rol (nombre_rol, estado) VALUES ('ADMIN', 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM Rol WHERE nombre_rol = 'USER')
BEGIN
    INSERT INTO Rol (nombre_rol, estado) VALUES ('USER', 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM Rol WHERE nombre_rol = 'CLIENTE')
BEGIN
    INSERT INTO Rol (nombre_rol, estado) VALUES ('CLIENTE', 1);
END
GO

-- Usuario administrador inicial
DECLARE @adminRoleId INT;
SELECT @adminRoleId = id_rol FROM Rol WHERE nombre_rol = 'ADMIN';

IF NOT EXISTS (SELECT 1 FROM Usuario WHERE email = 'admin@lovelyshades.com')
BEGIN
    INSERT INTO Usuario (nombre_usuario, contrasena_hash, email, id_rol, estado, fecha_creacion)
    VALUES (
        'admin',
        '$2a$10$7keQyj75/JzXxLy1zodzce08sazqhu1tOlBZcSulloSTX0YHQ88ui',
        'admin@lovelyshades.com',
        @adminRoleId,
        1,
        GETDATE()
    );
END
GO

-- Datos base de ejemplo
IF NOT EXISTS (SELECT 1 FROM Categoria WHERE nombre_categoria = 'Maquillaje')
BEGIN
    INSERT INTO Categoria (nombre_categoria, descripcion, estado)
    VALUES ('Maquillaje', 'Categoría de maquillaje y cosméticos', 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM Marca WHERE nombre_marca = 'LovelyShades')
BEGIN
    INSERT INTO Marca (nombre_marca, estado)
    VALUES ('LovelyShades', 1);
END
GO

DECLARE @categoriaId INT = (SELECT Id_categoria FROM Categoria WHERE nombre_categoria = 'Maquillaje');
DECLARE @marcaId INT = (SELECT Id_marca FROM Marca WHERE nombre_marca = 'LovelyShades');

IF NOT EXISTS (SELECT 1 FROM cliente WHERE email = 'cliente@lovelyshades.com')
BEGIN
    INSERT INTO cliente (nombre_completo, tipo_identificacion, numero_identificacion, telefono, email, direccion, fecha_registro, estado)
    VALUES ('Cliente Inicial Lovely', 'C.C.', '1234567890', '3001234567', 'cliente@lovelyshades.com', 'Calle Falsa 123', GETDATE(), 1);
END
GO

IF NOT EXISTS (SELECT 1 FROM producto WHERE nombre = 'Labial Ultra Cover')
BEGIN
    INSERT INTO producto (nombre, descripcion, precio, stock, estado, valor_costo, valor_venta, id_marca, id_categoria, codigo_interno, color, fecha_ingreso)
    VALUES (
        'Labial Ultra Cover',
        'Labial de larga duración con color intenso',
        25000.00,
        50,
        1,
        15000.00,
        25000.00,
        @marcaId,
        @categoriaId,
        'LS-LP-001',
        'Rojo',
        GETDATE()
    );
END
GO

IF NOT EXISTS (SELECT 1 FROM producto WHERE nombre = 'Paleta Sombras Natural')
BEGIN
    INSERT INTO producto (nombre, descripcion, precio, stock, estado, valor_costo, valor_venta, id_marca, id_categoria, codigo_interno, color, fecha_ingreso)
    VALUES (
        'Paleta Sombras Natural',
        'Paleta para ojos con tonos naturales',
        45000.00,
        30,
        1,
        28000.00,
        45000.00,
        @marcaId,
        @categoriaId,
        'LS-PS-002',
        'Natural',
        GETDATE()
    );
END
GO
