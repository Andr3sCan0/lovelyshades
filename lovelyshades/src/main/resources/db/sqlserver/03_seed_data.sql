USE lovelyshades;
GO

-- Insertar Roles
INSERT INTO roles (nombre_rol, estado) VALUES
('Admin', 1),
('Vendedor', 1),
('Gerente', 1),
('Cliente', 1);
GO

-- Insertar Permisos
INSERT INTO permisos (nombre_permiso, descripcion, estado) VALUES
('crear_usuario', 'Permiso para crear usuarios', 1),
('editar_usuario', 'Permiso para editar usuarios', 1),
('eliminar_usuario', 'Permiso para eliminar usuarios', 1),
('ver_usuario', 'Permiso para ver usuarios', 1),
('crear_producto', 'Permiso para crear productos', 1),
('editar_producto', 'Permiso para editar productos', 1),
('eliminar_producto', 'Permiso para eliminar productos', 1),
('ver_producto', 'Permiso para ver productos', 1),
('crear_venta', 'Permiso para crear ventas', 1),
('ver_venta', 'Permiso para ver ventas', 1),
('crear_rol', 'Permiso para crear roles', 1),
('editar_rol', 'Permiso para editar roles', 1),
('eliminar_rol', 'Permiso para eliminar roles', 1),
('ver_rol', 'Permiso para ver roles', 1);
GO

-- Asignar permisos a Admin (todos los permisos)
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14);
GO

-- Asignar permisos a Vendedor
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
(2, 5), (2, 8), (2, 9), (2, 10);
GO

-- Asignar permisos a Gerente
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
(3, 4), (3, 5), (3, 6), (3, 8), (3, 9), (3, 10);
GO

-- Asignar permisos a Cliente
INSERT INTO rol_permisos (id_rol, id_permiso) VALUES
(4, 8), (4, 10);
GO

-- Insertar usuario Admin (contraseña: 123456 - debe ser hasheada en la aplicación)
-- Para propósitos de prueba, esta será la contraseña sin hashear: admin123
INSERT INTO usuarios (nombre_usuario, contrasena_hash, email, id_rol, estado)
VALUES ('admin', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy.8NHO', 'admin@lovelyshades.com', 1, 1);
GO

-- Insertar usuario Carlos Andres como Admin
INSERT INTO usuarios (nombre_usuario, contrasena_hash, email, id_rol, estado)
VALUES ('CARLOS ANDRES', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy.8NHO', 'carlos@lovelyshades.com', 1, 1);
GO

-- Insertar usuario Vendedor
INSERT INTO usuarios (nombre_usuario, contrasena_hash, email, id_rol, estado)
VALUES ('vendedor', '$2a$10$slYQmyNdGzin7olVN3p5Be7DlH.PKZbv5H8KnzzVgXXbVxzy.8NHO', 'vendedor@lovelyshades.com', 2, 1);
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
