-- =============================================
-- SCRIPT COMPLETO - LOVELYSHADES
-- =============================================
-- Ejecutar todo de una vez, está ordenado por dependencias
-- =============================================

-- Creación de la base de datos
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'lovelyshades')
BEGIN
    CREATE DATABASE lovelyshades;
END
GO

USE lovelyshades;
GO

-- =============================================
-- 1. MODIFICAR TABLA cliente (agregar campos faltantes)
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'cliente')
BEGIN
    -- Agregar columnas si no existen
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('cliente') AND name = 'tipo_identificacion')
        ALTER TABLE cliente ADD tipo_identificacion NVARCHAR(20) NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('cliente') AND name = 'numero_identificacion')
        ALTER TABLE cliente ADD numero_identificacion NVARCHAR(50) NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('cliente') AND name = 'direccion')
        ALTER TABLE cliente ADD direccion NVARCHAR(200) NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('cliente') AND name = 'estado')
        ALTER TABLE cliente ADD estado BIT NOT NULL DEFAULT 1;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('cliente') AND name = 'fecha_registro')
        ALTER TABLE cliente ADD fecha_registro DATETIME NOT NULL DEFAULT GETDATE();

    -- Renombrar columna 'nombre' a 'nombre_completo' si existe
    IF EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('cliente') AND name = 'nombre')
        EXEC sp_rename 'cliente.nombre', 'nombre_completo', 'COLUMN';

    -- Unique constraint
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('cliente') AND name = 'UQ_cliente_numero_identificacion')
        ALTER TABLE cliente ADD CONSTRAINT UQ_cliente_numero_identificacion UNIQUE (numero_identificacion);

    -- Check constraint
    IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE name = 'CK_cliente_tipo_identificacion')
        ALTER TABLE cliente ADD CONSTRAINT CK_cliente_tipo_identificacion 
        CHECK (tipo_identificacion IN ('CC', 'NIT', 'CE', 'Pasaporte'));

    -- Índices
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('cliente') AND name = 'IX_cliente_identificacion')
        CREATE INDEX IX_cliente_identificacion ON cliente(numero_identificacion);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('cliente') AND name = 'IX_cliente_nombre')
        CREATE INDEX IX_cliente_nombre ON cliente(nombre_completo);
END
GO

-- =============================================
-- 2. MODIFICAR TABLA producto (agregar campos faltantes)
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'producto')
BEGIN
    -- Agregar columnas si no existen
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'código_interno')
        ALTER TABLE producto ADD código_interno NVARCHAR(50) NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'id_marca')
        ALTER TABLE producto ADD id_marca INT NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'id_categoria')
        ALTER TABLE producto ADD id_categoria INT NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'color')
        ALTER TABLE producto ADD color NVARCHAR(50) NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'valor_costo')
        ALTER TABLE producto ADD valor_costo DECIMAL(10,2) NOT NULL DEFAULT 0;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'fecha_ingreso')
        ALTER TABLE producto ADD fecha_ingreso DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE);
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'estado')
        ALTER TABLE producto ADD estado BIT NOT NULL DEFAULT 1;

    -- Renombrar 'precio' a 'valor_venta' si existe
    IF EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'precio')
        EXEC sp_rename 'producto.precio', 'valor_venta', 'COLUMN';

    -- Unique constraint
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('producto') AND name = 'UQ_producto_codigo_interno')
        ALTER TABLE producto ADD CONSTRAINT UQ_producto_codigo_interno UNIQUE (código_interno);

    -- Check constraints
    IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE name = 'CK_producto_valor_venta')
        ALTER TABLE producto ADD CONSTRAINT CK_producto_valor_venta CHECK (valor_venta >= 0);
    
    IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE name = 'CK_producto_valor_costo')
        ALTER TABLE producto ADD CONSTRAINT CK_producto_valor_costo CHECK (valor_costo >= 0);
    
    IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE name = 'CK_producto_stock')
        ALTER TABLE producto ADD CONSTRAINT CK_producto_stock CHECK (stock >= 0);

    -- Índices
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('producto') AND name = 'IX_producto_nombre')
        CREATE INDEX IX_producto_nombre ON producto(nombre);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('producto') AND name = 'IX_producto_codigo')
        CREATE INDEX IX_producto_codigo ON producto(código_interno);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('producto') AND name = 'IX_producto_marca')
        CREATE INDEX IX_producto_marca ON producto(id_marca);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('producto') AND name = 'IX_producto_categoria')
        CREATE INDEX IX_producto_categoria ON producto(id_categoria);
END
GO

-- =============================================
-- 3. MODIFICAR TABLA Categoria (agregar campos si faltan)
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Categoria')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Categoria') AND name = 'descripcion')
        ALTER TABLE Categoria ADD descripcion NVARCHAR(255) NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Categoria') AND name = 'estado')
        ALTER TABLE Categoria ADD estado BIT NOT NULL DEFAULT 1;
END
GO

-- =============================================
-- 4. MODIFICAR TABLA Marca (agregar campos si faltan)
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Marca')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Marca') AND name = 'estado')
        ALTER TABLE Marca ADD estado BIT NOT NULL DEFAULT 1;
END
GO

-- =============================================
-- 5. CREAR TABLA ROL (si no existe)
-- =============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Rol')
BEGIN
    CREATE TABLE Rol (
        Id_rol INT IDENTITY(1,1) PRIMARY KEY,
        nombre_rol NVARCHAR(100) NOT NULL,
        estado BIT NOT NULL DEFAULT 1
    );
END
GO

-- =============================================
-- 6. CREAR TABLA USUARIO (si no existe)
-- =============================================
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
        ultimo_acceso DATETIME NULL
    );
END
GO

-- Agregar FK de Usuario a Rol
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Usuario') 
   AND EXISTS (SELECT * FROM sys.tables WHERE name = 'Rol')
   AND NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Usuario_Rol')
BEGIN
    ALTER TABLE Usuario ADD CONSTRAINT FK_Usuario_Rol 
    FOREIGN KEY (id_rol) REFERENCES Rol(Id_rol);
END
GO

-- =============================================
-- 7. MODIFICAR TABLA Permiso (agregar campos si faltan)
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Permiso')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Permiso') AND name = 'descripcion')
        ALTER TABLE Permiso ADD descripcion NVARCHAR(255) NULL;
    
    IF NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('Permiso') AND name = 'estado')
        ALTER TABLE Permiso ADD estado BIT NOT NULL DEFAULT 1;
END
GO

-- =============================================
-- 8. CREAR TABLA ROLPERMISO (relación muchos a muchos)
-- =============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'RolPermiso')
BEGIN
    CREATE TABLE RolPermiso (
        Id_rol_permiso INT IDENTITY(1,1) PRIMARY KEY,
        id_rol INT NOT NULL,
        id_permiso INT NOT NULL,
        fecha_asignacion DATETIME NOT NULL DEFAULT GETDATE(),
        CONSTRAINT UK_RolPermiso UNIQUE (id_rol, id_permiso),
        CONSTRAINT FK_RolPermiso_Rol FOREIGN KEY (id_rol) REFERENCES Rol(Id_rol) ON DELETE CASCADE,
        CONSTRAINT FK_RolPermiso_Permiso FOREIGN KEY (id_permiso) REFERENCES Permiso(Id_permiso) ON DELETE CASCADE
    );
END
GO

-- =============================================
-- 9. AGREGAR FOREIGN KEYS DE producto
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Marca') 
   AND NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_producto_Marca')
   AND EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'id_marca')
BEGIN
    ALTER TABLE producto ADD CONSTRAINT FK_producto_Marca 
    FOREIGN KEY (id_marca) REFERENCES Marca(Id_marca);
END

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Categoria') 
   AND NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_producto_Categoria')
   AND EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('producto') AND name = 'id_categoria')
BEGIN
    ALTER TABLE producto ADD CONSTRAINT FK_producto_Categoria 
    FOREIGN KEY (id_categoria) REFERENCES Categoria(Id_categoria);
END
GO

-- =============================================
-- 10. CREAR TABLA FACTURA (cabecera)
-- =============================================
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
        CONSTRAINT FK_Factura_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
        CONSTRAINT FK_Factura_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(Id_usuario),
        CONSTRAINT CK_Factura_medio_pago CHECK (medio_pago IN ('Efectivo', 'Tarjeta Débito', 'Tarjeta Crédito', 'Transferencia', 'Otro')),
        CONSTRAINT CK_Factura_total CHECK (total >= 0)
    );
END
GO

-- =============================================
-- 11. CREAR TABLA DETALLE_FACTURA (detalle)
-- =============================================
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
        CONSTRAINT FK_DetalleFactura_producto FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
        CONSTRAINT CK_DetalleFactura_cantidad CHECK (cantidad > 0),
        CONSTRAINT CK_DetalleFactura_valor_unitario CHECK (valor_unitario >= 0)
    );
END
GO

-- Índices para DetalleFactura
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'DetalleFactura')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('DetalleFactura') AND name = 'IX_DetalleFactura_factura')
        CREATE INDEX IX_DetalleFactura_factura ON DetalleFactura(id_factura);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('DetalleFactura') AND name = 'IX_DetalleFactura_producto')
        CREATE INDEX IX_DetalleFactura_producto ON DetalleFactura(id_producto);
END
GO

-- =============================================
-- 12. CREAR TABLA SERVICIO
-- =============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Servicio')
BEGIN
    CREATE TABLE Servicio (
        Id_servicio INT IDENTITY(1,1) PRIMARY KEY,
        código_interno NVARCHAR(50) NOT NULL UNIQUE,
        descripcion NVARCHAR(255) NULL,
        estado BIT NOT NULL DEFAULT 1,
        valor_total DECIMAL(10,2) NOT NULL,
        fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
        id_usuario INT NOT NULL,
        id_cliente INT NULL,
        CONSTRAINT FK_Servicio_usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(Id_usuario),
        CONSTRAINT FK_Servicio_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
        CONSTRAINT CK_Servicio_valor_total CHECK (valor_total >= 0)
    );
END
GO

-- =============================================
-- 13. CREAR TABLA USUARIO_SERVICIO (Relación Usuario-Servicio)
-- =============================================
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Usuario_Servicio')
BEGIN
    CREATE TABLE Usuario_Servicio (
        Id_usuario_servicio INT IDENTITY(1,1) PRIMARY KEY,
        id_usuario INT NOT NULL,
        id_servicio INT NOT NULL,
        fecha_asignacion DATETIME NOT NULL DEFAULT GETDATE(),
        estado BIT NOT NULL DEFAULT 1,
        observaciones NVARCHAR(255) NULL,
        CONSTRAINT FK_Usuario_Servicio_Usuario FOREIGN KEY (id_usuario) 
            REFERENCES Usuario(Id_usuario) ON DELETE CASCADE,
        CONSTRAINT FK_Usuario_Servicio_Servicio FOREIGN KEY (id_servicio) 
            REFERENCES Servicio(Id_servicio) ON DELETE CASCADE,
        CONSTRAINT UK_Usuario_Servicio UNIQUE (id_usuario, id_servicio)
    );
END
GO

-- Índices Usuario_Servicio
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Usuario_Servicio')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('Usuario_Servicio') AND name = 'IX_Usuario_Servicio_usuario')
        CREATE INDEX IX_Usuario_Servicio_usuario ON Usuario_Servicio(id_usuario);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('Usuario_Servicio') AND name = 'IX_Usuario_Servicio_servicio')
        CREATE INDEX IX_Usuario_Servicio_servicio ON Usuario_Servicio(id_servicio);
END
GO

-- =============================================
-- 14. CREAR TABLA CUADRECAJA
-- =============================================
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

-- =============================================
-- 15. CREAR TABLA INVENTARIO
-- =============================================
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
        CONSTRAINT FK_Inventario_Producto FOREIGN KEY (id_producto) 
            REFERENCES producto(id_producto) ON DELETE CASCADE,
        CONSTRAINT FK_Inventario_Usuario FOREIGN KEY (id_usuario) 
            REFERENCES Usuario(Id_usuario),
        CONSTRAINT FK_Inventario_Factura FOREIGN KEY (id_factura) 
            REFERENCES Factura(Id_factura) ON DELETE SET NULL,
        CONSTRAINT FK_Inventario_Servicio FOREIGN KEY (id_servicio) 
            REFERENCES Servicio(Id_servicio) ON DELETE SET NULL,
        CONSTRAINT CK_Inventario_tipo_movimiento CHECK (tipo_movimiento IN ('Entrada', 'Salida', 'Ajuste', 'Devolución')),
        CONSTRAINT CK_Inventario_cantidad CHECK (cantidad > 0),
        CONSTRAINT CK_Inventario_stock_antes CHECK (stock_antes >= 0),
        CONSTRAINT CK_Inventario_stock_despues CHECK (stock_despues >= 0)
    );
END
GO

-- Índices Inventario
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'Inventario')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('Inventario') AND name = 'IX_Inventario_producto')
        CREATE INDEX IX_Inventario_producto ON Inventario(id_producto);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('Inventario') AND name = 'IX_Inventario_fecha')
        CREATE INDEX IX_Inventario_fecha ON Inventario(fecha_movimiento);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('Inventario') AND name = 'IX_Inventario_tipo')
        CREATE INDEX IX_Inventario_tipo ON Inventario(tipo_movimiento);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('Inventario') AND name = 'IX_Inventario_usuario')
        CREATE INDEX IX_Inventario_usuario ON Inventario(id_usuario);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('Inventario') AND name = 'IX_Inventario_factura')
        CREATE INDEX IX_Inventario_factura ON Inventario(id_factura);
    
    IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID('Inventario') AND name = 'IX_Inventario_servicio')
        CREATE INDEX IX_Inventario_servicio ON Inventario(id_servicio);
END
GO

-- =============================================
-- 16. TRIGGER PARA ACTUALIZAR STOCK
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'DetalleFactura')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.triggers WHERE name = 'TR_ActualizarStock')
    BEGIN
        EXEC('
        CREATE TRIGGER TR_ActualizarStock
        ON DetalleFactura
        AFTER INSERT
        AS
        BEGIN
            UPDATE p
            SET p.stock = p.stock - i.cantidad
            FROM producto p
            INNER JOIN inserted i ON p.id_producto = i.id_producto
            WHERE p.stock >= i.cantidad;
            
            IF EXISTS (SELECT 1 FROM producto p INNER JOIN inserted i ON p.id_producto = i.id_producto WHERE p.stock < 0)
            BEGIN
                RAISERROR(''Stock insuficiente para uno o más productos'', 16, 1);
                ROLLBACK TRANSACTION;
            END
        END
        ');
    END
END
GO

-- =============================================
-- 17. TRIGGER PARA REGISTRAR MOVIMIENTOS DE INVENTARIO
-- =============================================
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'DetalleFactura')
BEGIN
    IF NOT EXISTS (SELECT * FROM sys.triggers WHERE name = 'TR_Inventario_Venta')
    BEGIN
        EXEC('
        CREATE TRIGGER TR_Inventario_Venta
        ON DetalleFactura
        AFTER INSERT
        AS
        BEGIN
            SET NOCOUNT ON;
            
            DECLARE @id_factura INT;
            SELECT @id_factura = id_factura FROM inserted;
            
            INSERT INTO Inventario (id_producto, tipo_movimiento, cantidad, stock_antes, stock_despues, id_usuario, id_factura, motivo)
            SELECT 
                i.id_producto,
                ''Salida'',
                i.cantidad,
                p.stock + i.cantidad AS stock_antes,
                p.stock AS stock_despues,
                f.id_usuario,
                @id_factura,
                ''Venta de producto en factura N° '' + f.numero_factura
            FROM inserted i
            INNER JOIN producto p ON i.id_producto = p.id_producto
            INNER JOIN Factura f ON i.id_factura = f.Id_factura;
        END
        ');
    END
END
GO

-- =============================================
-- 18. PROCEDIMIENTOS ALMACENADOS
-- =============================================

-- Procedimiento para registrar entrada de inventario (CORREGIDO)
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
    
    SELECT @stock_actual = stock FROM producto WHERE id_producto = @id_producto;
    SET @nuevo_stock = @stock_actual + @cantidad;
    
    UPDATE producto SET stock = @nuevo_stock WHERE id_producto = @id_producto;
    
    INSERT INTO Inventario (id_producto, tipo_movimiento, cantidad, stock_antes, stock_despues, id_usuario, motivo)
    VALUES (@id_producto, 'Entrada', @cantidad, @stock_actual, @nuevo_stock, @id_usuario, @motivo);
    
    SELECT 'Entrada de inventario registrada exitosamente' AS mensaje;
END
GO

-- Procedimiento para registrar salida por servicio
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
    
    SELECT @stock_actual = stock FROM producto WHERE id_producto = @id_producto;
    
    IF @stock_actual < @cantidad
    BEGIN
        RAISERROR('Stock insuficiente para realizar la salida', 16, 1);
        RETURN;
    END
    
    SET @nuevo_stock = @stock_actual - @cantidad;
    UPDATE producto SET stock = @nuevo_stock WHERE id_producto = @id_producto;
    
    INSERT INTO Inventario (id_producto, tipo_movimiento, cantidad, stock_antes, stock_despues, id_usuario, id_servicio, motivo)
    VALUES (@id_producto, 'Salida', @cantidad, @stock_actual, @nuevo_stock, @id_usuario, @id_servicio, @motivo);
    
    SELECT 'Salida de inventario registrada exitosamente' AS mensaje;
END
GO

-- =============================================
-- 19. VISTAS
-- =============================================

-- Vista de ventas por producto
CREATE OR ALTER VIEW V_VentasPorProducto AS
SELECT 
    p.nombre AS nombre_producto,
    p.código_interno,
    SUM(df.cantidad) AS total_unidades_vendidas,
    SUM(df.subtotal_linea) AS total_ventas,
    COUNT(DISTINCT df.id_factura) AS numero_ventas
FROM DetalleFactura df
INNER JOIN producto p ON df.id_producto = p.id_producto
GROUP BY p.nombre, p.código_interno;
GO

-- Vista de ventas diarias
CREATE OR ALTER VIEW V_VentasDiarias AS
SELECT 
    CAST(fecha AS DATE) AS fecha,
    COUNT(*) AS numero_facturas,
    SUM(total) AS total_ventas,
    AVG(total) AS promedio_venta
FROM Factura
WHERE estado_factura = 'Pagada'
GROUP BY CAST(fecha AS DATE);
GO

-- Vista de estado de inventario
CREATE OR ALTER VIEW V_EstadoInventario AS
SELECT 
    p.id_producto,
    p.nombre AS nombre_producto,
    p.código_interno,
    p.stock AS stock_actual,
    p.valor_costo,
    p.valor_venta,
    (p.stock * p.valor_costo) AS valor_total_inventario,
    (SELECT SUM(cantidad) FROM Inventario WHERE id_producto = p.id_producto AND tipo_movimiento = 'Entrada') AS total_entradas,
    (SELECT SUM(cantidad) FROM Inventario WHERE id_producto = p.id_producto AND tipo_movimiento = 'Salida') AS total_salidas,
    (SELECT TOP 1 fecha_movimiento FROM Inventario WHERE id_producto = p.id_producto ORDER BY fecha_movimiento DESC) AS ultimo_movimiento
FROM producto p
WHERE p.estado = 1;
GO

-- Vista de servicios por usuario
CREATE OR ALTER VIEW V_ServiciosPorUsuario AS
SELECT 
    u.Id_usuario,
    u.nombre_usuario,
    u.email,
    s.Id_servicio,
    s.código_interno AS codigo_servicio,
    s.descripcion AS descripcion_servicio,
    s.valor_total,
    us.fecha_asignacion,
    us.estado AS estado_asignacion
FROM Usuario_Servicio us
INNER JOIN Usuario u ON us.id_usuario = u.Id_usuario
INNER JOIN Servicio s ON us.id_servicio = s.Id_servicio;
GO

-- =============================================
-- MENSAJE FINAL
-- =============================================
PRINT '==========================================';
PRINT 'SCRIPT COMPLETADO EXITOSAMENTE';
PRINT 'Base de datos: lovelyshades';
PRINT 'Todas las tablas, relaciones, triggers,';
PRINT 'procedimientos y vistas han sido creados.';
PRINT '==========================================';
GO