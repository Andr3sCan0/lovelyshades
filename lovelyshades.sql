-- =============================================
-- SCRIPT COMPLETO - LOVELYSHADES
-- BASE DE DATOS PARA TIENDA
-- =============================================
-- Autor: Deepsi
-- Fecha: 2026-05-29
-- =============================================

-- =============================================
-- 1. CREACION DE LA BASE DE DATOS
-- =============================================

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'lovelyshades')
BEGIN
    CREATE DATABASE lovelyshades;
END
GO

USE lovelyshades;
GO

-- =============================================
-- 2. TABLAS PRINCIPALES
-- =============================================

-- 2.1 Tabla Permiso
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Permiso')
BEGIN
    CREATE TABLE Permiso (
        Id_permiso INT IDENTITY(1,1) PRIMARY KEY,
        nombre_permiso NVARCHAR(100) NOT NULL,
        descripcion NVARCHAR(255) NULL,
        estado BIT NOT NULL DEFAULT 1
    );
END
GO

-- 2.2 Tabla Rol
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Rol')
BEGIN
    CREATE TABLE Rol (
        Id_rol INT IDENTITY(1,1) PRIMARY KEY,
        nombre_rol NVARCHAR(100) NOT NULL,
        estado BIT NOT NULL DEFAULT 1
    );
END
GO

-- 2.3 Tabla RolPermiso (Relacion muchos a muchos)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'RolPermiso')
BEGIN
    CREATE TABLE RolPermiso (
        Id_rol_permiso INT IDENTITY(1,1) PRIMARY KEY,
        id_rol INT NOT NULL,
        id_permiso INT NOT NULL,
        fecha_asignacion DATETIME NOT NULL DEFAULT GETDATE(),
        CONSTRAINT FK_RolPermiso_Rol FOREIGN KEY (id_rol) REFERENCES Rol(Id_rol) ON DELETE CASCADE,
        CONSTRAINT FK_RolPermiso_Permiso FOREIGN KEY (id_permiso) REFERENCES Permiso(Id_permiso) ON DELETE CASCADE,
        CONSTRAINT UK_RolPermiso UNIQUE (id_rol, id_permiso)
    );
END
GO

-- 2.4 Tabla Usuario
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Usuario')
BEGIN
    CREATE TABLE Usuario (
        Id_usuario INT IDENTITY(1,1) PRIMARY KEY,
        nombre_usuario NVARCHAR(100) NOT NULL,
        contrasena_hash NVARCHAR(255) NOT NULL,
        email NVARCHAR(100) NOT NULL UNIQUE,
        id_rol INT NULL,
        estado BIT NOT NULL DEFAULT 1,
        fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
        ultimo_acceso DATETIME NULL,
        CONSTRAINT FK_Usuario_Rol FOREIGN KEY (id_rol) REFERENCES Rol(Id_rol)
    );
END
GO

-- 2.5 Tabla Marca
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Marca')
BEGIN
    CREATE TABLE Marca (
        Id_marca INT IDENTITY(1,1) PRIMARY KEY,
        nombre_marca NVARCHAR(100) NOT NULL,
        estado BIT NOT NULL DEFAULT 1
    );
END
GO

-- 2.6 Tabla Categoria
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Categoria')
BEGIN
    CREATE TABLE Categoria (
        Id_categoria INT IDENTITY(1,1) PRIMARY KEY,
        nombre_categoria NVARCHAR(100) NOT NULL,
        descripcion NVARCHAR(255) NULL,
        estado BIT NOT NULL DEFAULT 1
    );
END
GO

-- 2.7 Tabla Producto
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'producto')
BEGIN
    CREATE TABLE producto (
        Id_produto INT IDENTITY(1,1) PRIMARY KEY,
        codigo_interno NVARCHAR(50) NOT NULL UNIQUE,
        nombre_produto NVARCHAR(100) NOT NULL,
        descripcion NVARCHAR(255) NULL,
        id_marca INT NULL,
        id_categoria INT NULL,
        color NVARCHAR(50) NULL,
        valor_venta DECIMAL(10,2) NOT NULL DEFAULT 0,
        valor_costo DECIMAL(10,2) NOT NULL DEFAULT 0,
        stock_actual INT NOT NULL DEFAULT 0,
        fecha_ingreso DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
        estado BIT NOT NULL DEFAULT 1,
        CONSTRAINT FK_producto_Marca FOREIGN KEY (id_marca) REFERENCES Marca(Id_marca),
        CONSTRAINT FK_producto_Categoria FOREIGN KEY (id_categoria) REFERENCES Categoria(Id_categoria),
        CONSTRAINT CK_producto_valor_venta CHECK (valor_venta >= 0),
        CONSTRAINT CK_producto_valor_costo CHECK (valor_costo >= 0),
        CONSTRAINT CK_producto_stock CHECK (stock_actual >= 0)
    );
END
GO

-- 2.8 Tabla Cliente
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'cliente')
BEGIN
    CREATE TABLE cliente (
        Id_cliente INT IDENTITY(1,1) PRIMARY KEY,
        nombre_completo NVARCHAR(150) NOT NULL,
        tipo_identificacion NVARCHAR(20) NOT NULL,
        numero_identificacion NVARCHAR(50) NOT NULL UNIQUE,
        telefono NVARCHAR(20) NULL,
        correo NVARCHAR(100) NULL,
        direccion NVARCHAR(200) NULL,
        fecha_registro DATETIME NOT NULL DEFAULT GETDATE(),
        estado BIT NOT NULL DEFAULT 1,
        CONSTRAINT CK_cliente_tipo_identificacion CHECK (tipo_identificacion IN ('CC', 'NIT', 'CE', 'Pasaporte'))
    );
END
GO

-- 2.9 Tabla Factura (Cabecera)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Factura')
BEGIN
    CREATE TABLE Factura (
        Id_factura INT IDENTITY(1,1) PRIMARY KEY,
        numero_factura NVARCHAR(50) NOT NULL UNIQUE,
        id_cliente INT NOT NULL,
        fecha DATETIME NOT NULL DEFAULT GETDATE(),
        subtotal DECIMAL(10,2) NOT NULL DEFAULT 0,
        descuento DECIMAL(10,2) NOT NULL DEFAULT 0,
        iva DECIMAL(10,2) NOT NULL DEFAULT 0,
        total DECIMAL(10,2) NOT NULL DEFAULT 0,
        medio_pago NVARCHAR(50) NOT NULL,
        id_usuario INT NOT NULL,
        estado_factura NVARCHAR(20) NOT NULL DEFAULT 'Pagada',
        CONSTRAINT FK_Factura_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(Id_cliente),
        CONSTRAINT FK_Factura_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(Id_usuario),
        CONSTRAINT CK_Factura_medio_pago CHECK (medio_pago IN ('Efectivo', 'Tarjeta Debito', 'Tarjeta Credito', 'Transferencia', 'Otro')),
        CONSTRAINT CK_Factura_total CHECK (total >= 0),
        CONSTRAINT CK_Factura_estado CHECK (estado_factura IN ('Pagada', 'Anulada', 'Pendiente'))
    );
END
GO

-- 2.10 Tabla DetalleFactura (Detalle)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DetalleFactura')
BEGIN
    CREATE TABLE DetalleFactura (
        Id_detalle INT IDENTITY(1,1) PRIMARY KEY,
        id_factura INT NOT NULL,
        id_producto INT NOT NULL,
        cantidad INT NOT NULL,
        valor_unitario DECIMAL(10,2) NOT NULL,
        subtotal_linea DECIMAL(10,2) NOT NULL,
        CONSTRAINT FK_DetalleFactura_factura FOREIGN KEY (id_factura) REFERENCES Factura(Id_factura) ON DELETE CASCADE,
        CONSTRAINT FK_DetalleFactura_producto FOREIGN KEY (id_producto) REFERENCES producto(Id_produto),
        CONSTRAINT CK_DetalleFactura_cantidad CHECK (cantidad > 0),
        CONSTRAINT CK_DetalleFactura_valor_unitario CHECK (valor_unitario >= 0)
    );
END
GO

-- 2.11 Tabla Servicio
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Servicio')
BEGIN
    CREATE TABLE Servicio (
        Id_servicio INT IDENTITY(1,1) PRIMARY KEY,
        codigo_interno NVARCHAR(50) NOT NULL UNIQUE,
        descripcion NVARCHAR(255) NULL,
        estado BIT NOT NULL DEFAULT 1,
        valor_total DECIMAL(10,2) NOT NULL,
        fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
        id_usuario INT NOT NULL,
        CONSTRAINT FK_Servicio_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(Id_usuario),
        CONSTRAINT CK_Servicio_valor_total CHECK (valor_total >= 0)
    );
END
GO

-- 2.12 Tabla Usuario_Servicio (Relación Usuario-Servicio)
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Usuario_Servicio')
BEGIN
    CREATE TABLE Usuario_Servicio (
        Id_usuario_servicio INT IDENTITY(1,1) PRIMARY KEY,
        id_usuario INT NOT NULL,
        id_servicio INT NOT NULL,
        fecha_asignacion DATETIME NOT NULL DEFAULT GETDATE(),
        estado BIT NOT NULL DEFAULT 1,
        observaciones NVARCHAR(255) NULL,
        CONSTRAINT FK_Usuario_Servicio_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(Id_usuario) ON DELETE CASCADE,
        CONSTRAINT FK_Usuario_Servicio_Servicio FOREIGN KEY (id_servicio) REFERENCES Servicio(Id_servicio) ON DELETE CASCADE,
        CONSTRAINT UK_Usuario_Servicio UNIQUE (id_usuario, id_servicio)
    );
END
GO

-- 2.13 Tabla CuadreCaja
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'CuadreCaja')
BEGIN
    CREATE TABLE CuadreCaja (
        Id_cuadre INT IDENTITY(1,1) PRIMARY KEY,
        fecha DATETIME NOT NULL DEFAULT GETDATE(),
        base_caja DECIMAL(10,2) NOT NULL DEFAULT 0,
        total_ingresos DECIMAL(10,2) NOT NULL DEFAULT 0,
        total_egresos DECIMAL(10,2) NOT NULL DEFAULT 0,
        efectivo_fisico DECIMAL(10,2) NOT NULL,
        diferencia DECIMAL(10,2) NOT NULL,
        comentario NVARCHAR(255) NULL,
        id_usuario INT NOT NULL,
        CONSTRAINT FK_CuadreCaja_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(Id_usuario),
        CONSTRAINT CK_CuadreCaja_diferencia CHECK (diferencia >= 0)
    );
END
GO

-- 2.14 Tabla Inventario
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Inventario')
BEGIN
    CREATE TABLE Inventario (
        Id_inventario INT IDENTITY(1,1) PRIMARY KEY,
        id_producto INT NOT NULL,
        tipo_movimiento NVARCHAR(20) NOT NULL,
        cantidad INT NOT NULL,
        stock_antes INT NOT NULL,
        stock_despues INT NOT NULL,
        fecha_movimiento DATETIME NOT NULL DEFAULT GETDATE(),
        id_usuario INT NOT NULL,
        id_factura INT NULL,
        id_servicio INT NULL,
        motivo NVARCHAR(255) NULL,
        observaciones NVARCHAR(500) NULL,
        CONSTRAINT FK_Inventario_Producto FOREIGN KEY (id_producto) REFERENCES producto(Id_produto) ON DELETE CASCADE,
        CONSTRAINT FK_Inventario_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(Id_usuario),
        CONSTRAINT FK_Inventario_Factura FOREIGN KEY (id_factura) REFERENCES Factura(Id_factura) ON DELETE SET NULL,
        CONSTRAINT FK_Inventario_Servicio FOREIGN KEY (id_servicio) REFERENCES Servicio(Id_servicio) ON DELETE SET NULL,
        CONSTRAINT CK_Inventario_tipo_movimiento CHECK (tipo_movimiento IN ('Entrada', 'Salida', 'Ajuste', 'Devolución')),
        CONSTRAINT CK_Inventario_cantidad CHECK (cantidad > 0),
        CONSTRAINT CK_Inventario_stock_antes CHECK (stock_antes >= 0),
        CONSTRAINT CK_Inventario_stock_despues CHECK (stock_despues >= 0)
    );
END
GO

-- 2.15 Tabla Caja
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'caja')
BEGIN
    CREATE TABLE caja (
        Id_caja INT IDENTITY(1,1) PRIMARY KEY,
        fecha_apertura DATETIME NOT NULL DEFAULT GETDATE(),
        fecha_cierre DATETIME NULL,
        monto_apertura DECIMAL(10,2) NOT NULL DEFAULT 0,
        monto_cierre DECIMAL(10,2) NULL,
        estado_caja NVARCHAR(20) NOT NULL DEFAULT 'Abierta',
        id_usuario_apertura INT NOT NULL,
        id_usuario_cierre INT NULL,
        observaciones NVARCHAR(255) NULL,
        CONSTRAINT FK_caja_usuario_apertura FOREIGN KEY (id_usuario_apertura) REFERENCES Usuario(Id_usuario),
        CONSTRAINT FK_caja_usuario_cierre FOREIGN KEY (id_usuario_cierre) REFERENCES Usuario(Id_usuario),
        CONSTRAINT CK_caja_estado CHECK (estado_caja IN ('Abierta', 'Cerrada'))
    );
END
GO

-- 2.16 Tabla detalle_venta
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'detalle_venta')
BEGIN
    CREATE TABLE detalle_venta (
        Id_detalle_venta INT IDENTITY(1,1) PRIMARY KEY,
        id_factura INT NOT NULL,
        id_producto INT NOT NULL,
        cantidad INT NOT NULL,
        precio_unitario DECIMAL(10,2) NOT NULL,
        subtotal DECIMAL(10,2) NOT NULL,
        CONSTRAINT FK_detalle_venta_factura FOREIGN KEY (id_factura) REFERENCES Factura(Id_factura) ON DELETE CASCADE,
        CONSTRAINT FK_detalle_venta_producto FOREIGN KEY (id_producto) REFERENCES producto(Id_produto),
        CONSTRAINT CK_detalle_venta_cantidad CHECK (cantidad > 0)
    );
END
GO

-- =============================================
-- 3. ÍNDICES PARA OPTIMIZACIÓN
-- =============================================

-- Índices Factura
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Factura_fecha' AND object_id = OBJECT_ID('Factura'))
    CREATE INDEX IX_Factura_fecha ON Factura(fecha);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Factura_cliente' AND object_id = OBJECT_ID('Factura'))
    CREATE INDEX IX_Factura_cliente ON Factura(id_cliente);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Factura_usuario' AND object_id = OBJECT_ID('Factura'))
    CREATE INDEX IX_Factura_usuario ON Factura(id_usuario);

-- Índices DetalleFactura
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_DetalleFactura_factura' AND object_id = OBJECT_ID('DetalleFactura'))
    CREATE INDEX IX_DetalleFactura_factura ON DetalleFactura(id_factura);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_DetalleFactura_producto' AND object_id = OBJECT_ID('DetalleFactura'))
    CREATE INDEX IX_DetalleFactura_producto ON DetalleFactura(id_producto);

-- Índices Inventario
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Inventario_producto' AND object_id = OBJECT_ID('Inventario'))
    CREATE INDEX IX_Inventario_producto ON Inventario(id_producto);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Inventario_fecha' AND object_id = OBJECT_ID('Inventario'))
    CREATE INDEX IX_Inventario_fecha ON Inventario(fecha_movimiento);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Inventario_tipo' AND object_id = OBJECT_ID('Inventario'))
    CREATE INDEX IX_Inventario_tipo ON Inventario(tipo_movimiento);

-- Índices Cliente
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_cliente_identificacion' AND object_id = OBJECT_ID('cliente'))
    CREATE INDEX IX_cliente_identificacion ON cliente(numero_identificacion);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_cliente_nombre' AND object_id = OBJECT_ID('cliente'))
    CREATE INDEX IX_cliente_nombre ON cliente(nombre_completo);

-- Índices Producto
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_producto_nombre' AND object_id = OBJECT_ID('producto'))
    CREATE INDEX IX_producto_nombre ON producto(nombre_produto);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_producto_codigo' AND object_id = OBJECT_ID('producto'))
    CREATE INDEX IX_producto_codigo ON producto(codigo_interno);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_producto_marca' AND object_id = OBJECT_ID('producto'))
    CREATE INDEX IX_producto_marca ON producto(id_marca);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_producto_categoria' AND object_id = OBJECT_ID('producto'))
    CREATE INDEX IX_producto_categoria ON producto(id_categoria);

-- Índices Usuario
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Usuario_email' AND object_id = OBJECT_ID('Usuario'))
    CREATE INDEX IX_Usuario_email ON Usuario(email);

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_Usuario_rol' AND object_id = OBJECT_ID('Usuario'))
    CREATE INDEX IX_Usuario_rol ON Usuario(id_rol);

-- =============================================
-- 4. TRIGGERS
-- =============================================

-- Trigger para actualizar stock automáticamente
IF NOT EXISTS (SELECT * FROM sys.triggers WHERE name = 'TR_ActualizarStock')
BEGIN
    EXEC('
    CREATE TRIGGER TR_ActualizarStock
    ON DetalleFactura
    AFTER INSERT
    AS
    BEGIN
        UPDATE p
        SET p.stock_actual = p.stock_actual - i.cantidad
        FROM producto p
        INNER JOIN inserted i ON p.Id_produto = i.id_producto
        WHERE p.stock_actual >= i.cantidad;
        
        IF EXISTS (SELECT 1 FROM producto p INNER JOIN inserted i ON p.Id_produto = i.id_producto WHERE p.stock_actual < 0)
        BEGIN
            RAISERROR(''Stock insuficiente para uno o mas productos'', 16, 1);
            ROLLBACK TRANSACTION;
        END
    END
    ');
END
GO

-- Trigger para registrar movimientos en inventario al vender
IF NOT EXISTS (SELECT * FROM sys.triggers WHERE name = 'TR_Inventario_Venta')
BEGIN
    EXEC('
    CREATE TRIGGER TR_Inventario_Venta
    ON DetalleFactura
    AFTER INSERT
    AS
    BEGIN
        SET NOCOUNT ON;
        
        INSERT INTO Inventario (id_producto, tipo_movimiento, cantidad, stock_antes, stock_despues, id_usuario, id_factura, motivo)
        SELECT 
            i.id_producto,
            ''Salida'',
            i.cantidad,
            p.stock_actual + i.cantidad AS stock_antes,
            p.stock_actual AS stock_despues,
            f.id_usuario,
            i.id_factura,
            ''Venta de producto en factura N° '' + f.numero_factura
        FROM inserted i
        INNER JOIN producto p ON i.id_producto = p.Id_produto
        INNER JOIN Factura f ON i.id_factura = f.Id_factura;
    END
    ');
END
GO

-- =============================================
-- 5. PROCEDIMIENTOS ALMACENADOS
-- =============================================

-- 5.1 Registrar entrada de inventario
CREATE OR ALTER PROCEDURE SP_RegistrarEntradaInventario
    @id_producto INT,
    @cantidad INT,
    @id_usuario INT,
    @motivo NVARCHAR(255) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @stock_actual INT;
    DECLARE @nuevo_stock INT;
    
    SELECT @stock_actual = stock_actual FROM producto WHERE Id_produto = @id_producto;
    SET @nuevo_stock = @stock_actual + @cantidad;
    
    UPDATE producto SET stock_actual = @nuevo_stock WHERE Id_produto = @id_producto;
    
    INSERT INTO Inventario (id_producto, tipo_movimiento, cantidad, stock_antes, stock_despues, id_usuario, motivo)
    VALUES (@id_producto, 'Entrada', @cantidad, @stock_actual, @nuevo_stock, @id_usuario, @motivo);
    
    SELECT 'Entrada de inventario registrada exitosamente' AS mensaje;
END
GO

-- 5.2 Registrar salida de inventario por servicio
CREATE OR ALTER PROCEDURE SP_SalidaInventarioPorServicio
    @id_producto INT,
    @cantidad INT,
    @id_servicio INT,
    @id_usuario INT,
    @motivo NVARCHAR(255) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @stock_actual INT;
    DECLARE @nuevo_stock INT;
    
    SELECT @stock_actual = stock_actual FROM producto WHERE Id_produto = @id_producto;
    
    IF @stock_actual < @cantidad
    BEGIN
        RAISERROR('Stock insuficiente para realizar la salida', 16, 1);
        RETURN;
    END
    
    SET @nuevo_stock = @stock_actual - @cantidad;
    UPDATE producto SET stock_actual = @nuevo_stock WHERE Id_produto = @id_producto;
    
    INSERT INTO Inventario (id_producto, tipo_movimiento, cantidad, stock_antes, stock_despues, id_usuario, id_servicio, motivo)
    VALUES (@id_producto, 'Salida', @cantidad, @stock_actual, @nuevo_stock, @id_usuario, @id_servicio, @motivo);
    
    SELECT 'Salida de inventario registrada exitosamente' AS mensaje;
END
GO

-- 5.3 Calcular totales de factura
CREATE OR ALTER PROCEDURE SP_CalcularTotalesFactura
    @id_factura INT
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @subtotal DECIMAL(10,2);
    DECLARE @descuento DECIMAL(10,2);
    DECLARE @iva DECIMAL(10,2);
    DECLARE @total DECIMAL(10,2);
    
    SELECT @subtotal = ISNULL(SUM(subtotal_linea), 0) FROM DetalleFactura WHERE id_factura = @id_factura;
    
    SELECT @descuento = ISNULL(descuento, 0), @iva = ISNULL(iva, 0) FROM Factura WHERE Id_factura = @id_factura;
    
    SET @total = @subtotal - @descuento + @iva;
    
    UPDATE Factura 
    SET subtotal = @subtotal, total = @total
    WHERE Id_factura = @id_factura;
    
    SELECT @subtotal AS subtotal, @descuento AS descuento, @iva AS iva, @total AS total;
END
GO

-- 5.4 Registrar nueva factura
CREATE OR ALTER PROCEDURE SP_RegistrarFactura
    @id_cliente INT,
    @id_usuario INT,
    @medio_pago NVARCHAR(50),
    @productos NVARCHAR(MAX), -- Formato: id_producto|cantidad|precio, id_producto|cantidad|precio
    @descuento DECIMAL(10,2) = 0,
    @iva DECIMAL(10,2) = 0
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @id_factura INT;
    DECLARE @numero_factura NVARCHAR(50);
    DECLARE @subtotal DECIMAL(10,2) = 0;
    DECLARE @total DECIMAL(10,2) = 0;
    
    -- Generar número de factura
    SET @numero_factura = 'FAC-' + CAST(FORMAT(GETDATE(), 'yyyyMMddHHmmss') AS NVARCHAR(50));
    
    -- Crear factura
    INSERT INTO Factura (numero_factura, id_cliente, fecha, subtotal, descuento, iva, total, medio_pago, id_usuario, estado_factura)
    VALUES (@numero_factura, @id_cliente, GETDATE(), 0, @descuento, @iva, 0, @medio_pago, @id_usuario, 'Pagada');
    
    SET @id_factura = SCOPE_IDENTITY();
    
    -- Insertar detalles (aqui se procesaria el string de productos)
    -- Por simplicidad, este SP requiere que los detalles se inserten después
    
    -- Calcular totales
    EXEC SP_CalcularTotalesFactura @id_factura;
    
    SELECT @id_factura AS id_factura, @numero_factura AS numero_factura;
END
GO

-- 5.5 Registrar cuadre de caja
CREATE OR ALTER PROCEDURE SP_RegistrarCuadreCaja
    @base_caja DECIMAL(10,2),
    @total_ingresos DECIMAL(10,2),
    @total_egresos DECIMAL(10,2),
    @efectivo_fisico DECIMAL(10,2),
    @id_usuario INT,
    @comentario NVARCHAR(255) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @diferencia DECIMAL(10,2);
    SET @diferencia = @efectivo_fisico - (@base_caja + @total_ingresos - @total_egresos);
    
    INSERT INTO CuadreCaja (fecha, base_caja, total_ingresos, total_egresos, efectivo_fisico, diferencia, comentario, id_usuario)
    VALUES (GETDATE(), @base_caja, @total_ingresos, @total_egresos, @efectivo_fisico, @diferencia, @comentario, @id_usuario);
    
    SELECT 'Cuadre registrado exitosamente' AS mensaje, @diferencia AS diferencia;
END
GO

-- 5.6 Registrar nuevo producto
CREATE OR ALTER PROCEDURE SP_RegistrarProducto
    @codigo_interno NVARCHAR(50),
    @nombre_produto NVARCHAR(100),
    @descripcion NVARCHAR(255) = NULL,
    @id_marca INT = NULL,
    @id_categoria INT = NULL,
    @color NVARCHAR(50) = NULL,
    @valor_venta DECIMAL(10,2),
    @valor_costo DECIMAL(10,2),
    @stock_actual INT = 0
AS
BEGIN
    SET NOCOUNT ON;
    
    INSERT INTO producto (codigo_interno, nombre_produto, descripcion, id_marca, id_categoria, color, valor_venta, valor_costo, stock_actual, fecha_ingreso, estado)
    VALUES (@codigo_interno, @nombre_produto, @descripcion, @id_marca, @id_categoria, @color, @valor_venta, @valor_costo, @stock_actual, GETDATE(), 1);
    
    SELECT SCOPE_IDENTITY() AS id_producto;
END
GO

-- 5.7 Registrar nuevo cliente
CREATE OR ALTER PROCEDURE SP_RegistrarCliente
    @nombre_completo NVARCHAR(150),
    @tipo_identificacion NVARCHAR(20),
    @numero_identificacion NVARCHAR(50),
    @telefono NVARCHAR(20) = NULL,
    @correo NVARCHAR(100) = NULL,
    @direccion NVARCHAR(200) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    INSERT INTO cliente (nombre_completo, tipo_identificacion, numero_identificacion, telefono, correo, direccion, fecha_registro, estado)
    VALUES (@nombre_completo, @tipo_identificacion, @numero_identificacion, @telefono, @correo, @direccion, GETDATE(), 1);
    
    SELECT SCOPE_IDENTITY() AS id_cliente;
END
GO

-- 5.8 Registrar nuevo usuario
CREATE OR ALTER PROCEDURE SP_RegistrarUsuario
    @nombre_usuario NVARCHAR(100),
    @contrasena_hash NVARCHAR(255),
    @email NVARCHAR(100),
    @id_rol INT = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    INSERT INTO Usuario (nombre_usuario, contrasena_hash, email, id_rol, estado, fecha_creacion)
    VALUES (@nombre_usuario, @contrasena_hash, @email, @id_rol, 1, GETDATE());
    
    SELECT SCOPE_IDENTITY() AS id_usuario;
END
GO

-- 5.9 Consultar ventas por rango de fechas
CREATE OR ALTER PROCEDURE SP_ConsultarVentasPorFechas
    @fecha_inicio DATE,
    @fecha_fin DATE
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT 
        f.Id_factura,
        f.numero_factura,
        c.nombre_completo AS cliente,
        f.fecha,
        f.subtotal,
        f.descuento,
        f.iva,
        f.total,
        f.medio_pago,
        u.nombre_usuario AS usuario,
        f.estado_factura
    FROM Factura f
    INNER JOIN cliente c ON f.id_cliente = c.Id_cliente
    INNER JOIN Usuario u ON f.id_usuario = u.Id_usuario
    WHERE CAST(f.fecha AS DATE) BETWEEN @fecha_inicio AND @fecha_fin
    ORDER BY f.fecha DESC;
END
GO

-- 5.10 Consultar producto mas vendido
CREATE OR ALTER PROCEDURE SP_ProductoMasVendido
    @fecha_inicio DATE = NULL,
    @fecha_fin DATE = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT TOP 1
        p.Id_produto,
        p.nombre_produto,
        p.código_interno,
        SUM(df.cantidad) AS total_vendido,
        SUM(df.subtotal_linea) AS total_ventas
    FROM producto p
    INNER JOIN DetalleFactura df ON p.Id_produto = df.id_producto
    INNER JOIN Factura f ON df.id_factura = f.Id_factura
    WHERE (@fecha_inicio IS NULL OR CAST(f.fecha AS DATE) >= @fecha_inicio)
      AND (@fecha_fin IS NULL OR CAST(f.fecha AS DATE) <= @fecha_fin)
      AND f.estado_factura = 'Pagada'
    GROUP BY p.Id_produto, p.nombre_produto, p.código_interno
    ORDER BY SUM(df.cantidad) DESC;
END
GO

-- =============================================
-- 6. VISTAS
-- =============================================

-- 6.1 Vista de ventas por producto
CREATE OR ALTER VIEW V_VentasPorProducto AS
SELECT 
    p.nombre_produto,
    p.codigo_interno,
    ISNULL(SUM(df.cantidad), 0) AS total_unidades_vendidas,
    ISNULL(SUM(df.subtotal_linea), 0) AS total_ventas,
    COUNT(DISTINCT df.id_factura) AS numero_ventas
FROM producto p
LEFT JOIN DetalleFactura df ON p.Id_produto = df.id_producto
LEFT JOIN Factura f ON df.id_factura = f.Id_factura AND f.estado_factura = 'Pagada'
GROUP BY p.nombre_produto, p.código_interno;
GO

-- 6.2 Vista de ventas diarias
CREATE OR ALTER VIEW V_VentasDiarias AS
SELECT 
    CAST(fecha AS DATE) AS fecha,
    COUNT(*) AS numero_facturas,
    ISNULL(SUM(total), 0) AS total_ventas,
    ISNULL(AVG(total), 0) AS promedio_venta,
    ISNULL(SUM(subtotal), 0) AS total_subtotal,
    ISNULL(SUM(descuento), 0) AS total_descuentos,
    ISNULL(SUM(iva), 0) AS total_iva
FROM Factura
WHERE estado_factura = 'Pagada'
GROUP BY CAST(fecha AS DATE);
GO

-- 6.3 Vista de estado de inventario
CREATE OR ALTER VIEW V_EstadoInventario AS
SELECT 
    p.Id_produto,
    p.nombre_produto,
    p.codigo_interno,
    p.stock_actual,
    p.valor_costo,
    p.valor_venta,
    (p.stock_actual * p.valor_costo) AS valor_total_inventario,
    m.nombre_marca,
    c.nombre_categoria,
    p.estado,
    CASE WHEN p.stock_actual <= 5 THEN 'Crítico'
         WHEN p.stock_actual <= 10 THEN 'Bajo'
         WHEN p.stock_actual <= 30 THEN 'Normal'
         ELSE 'Alto'
    END AS nivel_stock
FROM producto p
LEFT JOIN Marca m ON p.id_marca = m.Id_marca
LEFT JOIN Categoria c ON p.id_categoria = c.Id_categoria;
GO

-- 6.4 Vista de resumen de clientes
CREATE OR ALTER VIEW V_ResumenClientes AS
SELECT 
    c.Id_cliente,
    c.nombre_completo,
    c.tipo_identificacion,
    c.numero_identificacion,
    c.telefono,
    c.correo,
    COUNT(f.Id_factura) AS total_compras,
    ISNULL(SUM(f.total), 0) AS total_gastado,
    MAX(f.fecha) AS ultima_compra,
    DATEDIFF(DAY, MAX(f.fecha), GETDATE()) AS dias_sin_compra
FROM cliente c
LEFT JOIN Factura f ON c.Id_cliente = f.id_cliente AND f.estado_factura = 'Pagada'
GROUP BY c.Id_cliente, c.nombre_completo, c.tipo_identificacion, c.numero_identificacion, c.telefono, c.correo;
GO

-- 6.5 Vista de movimientos de inventario
CREATE OR ALTER VIEW V_MovimientosInventario AS
SELECT 
    i.Id_inventario,
    p.nombre_produto,
    p.codigo_interno,
    i.tipo_movimiento,
    i.cantidad,
    i.stock_antes,
    i.stock_despues,
    i.fecha_movimiento,
    u.nombre_usuario,
    i.motivo,
    CASE WHEN i.id_factura IS NOT NULL THEN 'Factura #' + f.numero_factura ELSE NULL END AS factura,
    CASE WHEN i.id_servicio IS NOT NULL THEN 'Servicio #' + s.codigo_interno ELSE NULL END AS servicio
FROM Inventario i
INNER JOIN producto p ON i.id_producto = p.Id_produto
INNER JOIN Usuario u ON i.id_usuario = u.Id_usuario
LEFT JOIN Factura f ON i.id_factura = f.Id_factura
LEFT JOIN Servicio s ON i.id_servicio = s.Id_servicio;
GO

-- =============================================
-- 7. DATOS DE PRUEBA (OPCIONAL)
-- =============================================

-- Insertar roles basicos
IF NOT EXISTS (SELECT 1 FROM Rol WHERE nombre_rol = 'Administrador')
    INSERT INTO Rol (nombre_rol, estado) VALUES ('Administrador', 1);

IF NOT EXISTS (SELECT 1 FROM Rol WHERE nombre_rol = 'Vendedor')
    INSERT INTO Rol (nombre_rol, estado) VALUES ('Vendedor', 1);

IF NOT EXISTS (SELECT 1 FROM Rol WHERE nombre_rol = 'Inventario')
    INSERT INTO Rol (nombre_rol, estado) VALUES ('Inventario', 1);

-- Insertar permisos basicos
IF NOT EXISTS (SELECT 1 FROM Permiso WHERE nombre_permiso = 'crear_venta')
    INSERT INTO Permiso (nombre_permiso, descripcion, estado) VALUES ('crear_venta', 'Permite crear ventas', 1);

IF NOT EXISTS (SELECT 1 FROM Permiso WHERE nombre_permiso = 'ver_inventario')
    INSERT INTO Permiso (nombre_permiso, descripcion, estado) VALUES ('ver_inventario', 'Permite ver el inventario', 1);

IF NOT EXISTS (SELECT 1 FROM Permiso WHERE nombre_permiso = 'editar_producto')
    INSERT INTO Permiso (nombre_permiso, descripcion, estado) VALUES ('editar_producto', 'Permite editar productos', 1);

IF NOT EXISTS (SELECT 1 FROM Permiso WHERE nombre_permiso = 'crear_usuario')
    INSERT INTO Permiso (nombre_permiso, descripcion, estado) VALUES ('crear_usuario', 'Permite crear usuarios', 1);

IF NOT EXISTS (SELECT 1 FROM Permiso WHERE nombre_permiso = 'cuadre_caja')
    INSERT INTO Permiso (nombre_permiso, descripcion, estado) VALUES ('cuadre_caja', 'Permite hacer cuadre de caja', 1);

-- Insertar usuario administrador por defecto
IF NOT EXISTS (SELECT 1 FROM Usuario WHERE email = 'admin@lovelyshades.com')
BEGIN
    INSERT INTO Usuario (nombre_usuario, contrasena_hash, email, id_rol, estado, fecha_creacion)
    VALUES ('admin', 'admin123', 'admin@lovelyshades.com', 1, 1, GETDATE());
END
GO

-- =============================================
-- MENSAJE FINAL
-- =============================================
PRINT '==================================================';
PRINT 'SCRIPT COMPLETADO EXITOSAMENTE';
PRINT '==================================================';
PRINT 'Base de datos: lovelyshades';
PRINT '';
PRINT 'TABLAS CREADAS (16):';
PRINT '- Permiso, Rol, RolPermiso, Usuario';
PRINT '- Marca, Categoria, producto, cliente';
PRINT '- Factura, DetalleFactura, Servicio, Usuario_Servicio';
PRINT '- CuadreCaja, Inventario, caja, detalle_venta';
PRINT '';
PRINT 'PROCEDIMIENTOS ALMACENADOS (10):';
PRINT '- SP_RegistrarEntradaInventario';
PRINT '- SP_SalidaInventarioPorServicio';
PRINT '- SP_CalcularTotalesFactura';
PRINT '- SP_RegistrarFactura';
PRINT '- SP_RegistrarCuadreCaja';
PRINT '- SP_RegistrarProducto';
PRINT '- SP_RegistrarCliente';
PRINT '- SP_RegistrarUsuario';
PRINT '- SP_ConsultarVentasPorFechas';
PRINT '- SP_ProductoMasVendido';
PRINT '';
PRINT 'VISTAS (5):';
PRINT '- V_VentasPorProducto';
PRINT '- V_VentasDiarias';
PRINT '- V_EstadoInventario';
PRINT '- V_ResumenClientes';
PRINT '- V_MovimientosInventario';
PRINT '';
PRINT 'TRIGGERS (2):';
PRINT '- TR_ActualizarStock';
PRINT '- TR_Inventario_Venta';
PRINT '';
PRINT '==================================================';
PRINT 'USUARIO POR DEFECTO: admin / admin123';
PRINT '==================================================';
GO