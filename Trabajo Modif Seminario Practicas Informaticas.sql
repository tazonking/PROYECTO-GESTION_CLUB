
-- Creacion de la Base de Datos 

CREATE SCHEMA `gestion_club` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci;
use `gestion_club`;

-- Creacion de Tabla Usuario

CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    contraseña VARCHAR(20) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL
);

-- Creacion de Tabla Socio

CREATE TABLE socio (
    id_socio INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    tipo_socio VARCHAR (30) NOT NULL     
);

-- Creacion de Tabla Instalacion

CREATE TABLE instalacion (
    id_instalacion INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    disponibilidad BOOLEAN NOT NULL
);

-- Creacion de Tabla Proveedor

CREATE TABLE proveedor (
    id_proveedor INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    con_deuda BOOLEAN NOT NULL
);

-- Creacion de Tabla Producto

CREATE TABLE producto (
    id_producto INT PRIMARY KEY,
    descripcion VARCHAR(50) NOT NULL,
    precio DECIMAL (10,2) NOT NULL,
    stock INT NOT NULL,
    stock_min INT NOT NULL,
    stock_max INT NOT NULL,
    proveedor_id INT,
    CONSTRAINT `producto_prov`
    FOREIGN KEY (proveedor_id) REFERENCES gestion_club.proveedor(id_proveedor)    
);

-- Creacion de Tabla Actividad_Deportiva

CREATE TABLE actividad_deportiva (
    id_actividad INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL
);

-- Creacion de Tabla Planteles

CREATE TABLE plantel (
    id_jugador INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    genero CHAR(1) NOT NULL,
    seguro VARCHAR(50) NOT NULL,
    actividad_id INT,
    CONSTRAINT `plantel_depor`
    FOREIGN KEY (actividad_id) REFERENCES gestion_club.actividad_deportiva (id_actividad)
);

-- Creacion de Tabla Servicios

CREATE TABLE servicio (
    id_servicio INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    descuento DECIMAL (10,2) NOT NULL
);

-- Creacion de Tabla Personal

CREATE TABLE personal (
    id_personal INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(30) NOT NULL,
    cargo VARCHAR(20) NOT NULL,
    sector VARCHAR(20) NOT NULL,
    seguro VARCHAR(20) NOT NULL,
    fecha_ingreso DATE NOT NULL    
);

-- Creacion de Tabla Agenda

CREATE TABLE agenda (
    id_agenda INT PRIMARY KEY,
    descripcion VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    usuario_id INT,
    instalacion_id INT,
    CONSTRAINT `agenda_usu`
    FOREIGN KEY (usuario_id) REFERENCES gestion_club.usuario(id_usuario),
    CONSTRAINT `agenda_inst`
    FOREIGN KEY (instalacion_id) REFERENCES gestion_club.instalacion(id_instalacion)
);

-- Creacion de Tabla Transaccion

CREATE TABLE Transaccion (
    id_transaccion INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    monto FLOAT NOT NULL,
    usuario_id INT,
    CONSTRAINT `transaccion_usuario`
    FOREIGN KEY (usuario_id) REFERENCES gestion_club.usuario(id_usuario)
);

-- Creacion de Tabla Informes

CREATE TABLE informe (
    id_informe INT PRIMARY KEY,
    tipo_informe VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    usuario_id INT,
    CONSTRAINT `informe_usuario`
    FOREIGN KEY (usuario_id) REFERENCES gestion_club.usuario(id_usuario)
);

-- Creación de Tabla de Cuotas Mensuales

CREATE TABLE cuota_mensual (
    nro_mes INT PRIMARY KEY NOT NULL,
    nombre_mes VARCHAR(10) NOT NULL,
	pago_mes BOOLEAN NOT NULL,
    monto_mes DECIMAL(10,2) NOT NULL,
    deuda_total DECIMAL(10,2) NOT NULL,
    socio_id INT NOT NULL,
    CONSTRAINT fk_socio_id
    FOREIGN KEY (socio_id) REFERENCES gestion_club.socio(id_socio)
);
    



