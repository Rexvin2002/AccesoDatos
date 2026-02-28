Base de datos utilizada

-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS spring_example_db;
USE spring_example_db;

-- Tabla clientes
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(40) UNIQUE NOT NULL,
    apellido VARCHAR(40),
    direccion VARCHAR(100),
    email VARCHAR(100),
    telefono VARCHAR(20),
    fecha_registro DATE
);

-- Tabla categorias_productos
CREATE TABLE IF NOT EXISTS categorias_productos (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200)
);

-- Tabla productos
CREATE TABLE IF NOT EXISTS productos (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    id_categoria BIGINT,
    fecha_creacion DATE,
    FOREIGN KEY (id_categoria) REFERENCES categorias_productos(id_categoria)
);

-- Tabla pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    fecha_pedido DATETIME NOT NULL,
    estado VARCHAR(30) NOT NULL,
    total DECIMAL(12,2),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_empleado)
);

-- Tabla detalles_pedido
CREATE TABLE IF NOT EXISTS detalles_pedido (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(12,2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

-- Datos iniciales
INSERT INTO categorias_productos (nombre, descripcion) VALUES 
('Electrónica', 'Productos electrónicos y dispositivos'),
('Hogar', 'Artículos para el hogar'),
('Alimentación', 'Productos alimenticios');

INSERT INTO productos (nombre, descripcion, precio, stock, id_categoria) VALUES
('Smartphone X', 'Teléfono inteligente de última generación', 599.99, 50, 1),
('Sartén antiadherente', 'Sartén de 24cm con revestimiento cerámico', 29.99, 100, 2),
('Arroz integral', 'Arroz integral ecológico 1kg', 2.49, 200, 3);

-Limpieza completa de la base de datos
SET FOREIGN_KEY_CHECKS = 0;
SET FOREIGN_KEY_CHECKS = 1;