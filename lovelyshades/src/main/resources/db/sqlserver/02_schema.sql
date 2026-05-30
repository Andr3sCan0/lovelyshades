USE lovelyshades;
GO

IF OBJECT_ID('rol_permisos', 'U') IS NOT NULL DROP TABLE rol_permisos;
IF OBJECT_ID('permisos', 'U') IS NOT NULL DROP TABLE permisos;
IF OBJECT_ID('usuarios', 'U') IS NOT NULL DROP TABLE usuarios;
IF OBJECT_ID('roles', 'U') IS NOT NULL DROP TABLE roles;
IF OBJECT_ID('detalle_venta', 'U') IS NOT NULL DROP TABLE detalle_venta;
IF OBJECT_ID('ventas', 'U') IS NOT NULL DROP TABLE ventas;
IF OBJECT_ID('caja', 'U') IS NOT NULL DROP TABLE caja;
IF OBJECT_ID('productos', 'U') IS NOT NULL DROP TABLE productos;
IF OBJECT_ID('clientes', 'U') IS NOT NULL DROP TABLE clientes;
GO

-- Tabla de Roles
CREATE TABLE rol (
                     id_rol INT IDENTITY(1,1) PRIMARY KEY,
                     nombre_rol VARCHAR(100) NOT NULL UNIQUE,
                     estado BIT NOT NULL DEFAULT 1,
);
GO

-- Tabla de Permisos
CREATE TABLE permiso (
                         id_permiso INT IDENTITY(1,1) PRIMARY KEY,
                         nombre_permiso VARCHAR(100) NOT NULL UNIQUE,
                         descripcion VARCHAR(255) NULL,
                         estado BIT NOT NULL DEFAULT 1,
);
GO

-- Tabla de Usuarios
CREATE TABLE usuario (
                         id_usuario INT IDENTITY(1,1) PRIMARY KEY,
                         nombre_usuario VARCHAR(100) NOT NULL UNIQUE,
                         contrasena_hash VARCHAR(255) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         id_rol INT NOT NULL,
                         estado BIT NOT NULL DEFAULT 1,
                         fecha_creacion DATETIME NOT NULL DEFAULT GETDATE(),
                         ultimo_acceso DATETIME NULL,
                         CONSTRAINT FK_usuarios_roles FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);
GO

-- Tabla de Rol-Permisos (relación many-to-many)
CREATE TABLE rol_permisos (
                              id_rol_permiso INT IDENTITY(1,1) PRIMARY KEY,
                              id_rol INT NOT NULL,
                              id_permiso INT NOT NULL,
                              fecha_asignacion DATETIME NOT NULL DEFAULT GETDATE(),
                              CONSTRAINT FK_rol_permisos_roles FOREIGN KEY (id_rol) REFERENCES roles(id_rol),
                              CONSTRAINT FK_rol_permisos_permisos FOREIGN KEY (id_permiso) REFERENCES permisos(id_permiso),
                              CONSTRAINT UQ_rol_permiso UNIQUE (id_rol, id_permiso)
);
GO

CREATE TABLE clientes (
                          id_cliente INT IDENTITY(1,1) PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          telefono VARCHAR(20) NULL,
                          email VARCHAR(100) NULL
);
GO

CREATE TABLE productos (
                           id_producto INT IDENTITY(1,1) PRIMARY KEY,
                           nombre VARCHAR(100) NOT NULL,
                           descripcion VARCHAR(255) NULL,
                           precio DECIMAL(10,2) NOT NULL,
                           stock INT NOT NULL DEFAULT 0
);
GO

CREATE TABLE ventas (
                        id_venta INT IDENTITY(1,1) PRIMARY KEY,
                        fecha DATETIME NOT NULL DEFAULT GETDATE(),
                        id_cliente INT NOT NULL,
                        total DECIMAL(10,2) NOT NULL,
                        CONSTRAINT FK_ventas_clientes FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);
GO

CREATE TABLE detalle_venta (
                               id_detalle INT IDENTITY(1,1) PRIMARY KEY,
                               id_venta INT NOT NULL,
                               id_producto INT NOT NULL,
                               cantidad INT NOT NULL,
                               precio_unitario DECIMAL(10,2) NOT NULL,
                               subtotal DECIMAL(10,2) NOT NULL,
                               CONSTRAINT FK_detalle_venta_ventas FOREIGN KEY (id_venta) REFERENCES ventas(id_venta),
                               CONSTRAINT FK_detalle_venta_productos FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);
GO

CREATE TABLE caja (
                      id_caja INT IDENTITY(1,1) PRIMARY KEY,
                      fecha DATE NOT NULL,
                      total_ventas DECIMAL(10,2) NOT NULL,
                      monto_inicial DECIMAL(10,2) NOT NULL,
                      monto_final DECIMAL(10,2) NOT NULL,
                      diferencia DECIMAL(10,2) NOT NULL
);
GO
