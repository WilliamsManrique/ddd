CREATE DATABASE agro_pacayales;
GO

USE agro_pacayales;
GO

CREATE TABLE producto (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(MAX),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    unidad_medida VARCHAR(20),
    fecha_registro DATE,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);

select * from producto;

CREATE TABLE cultivos (
    id_cultivo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_cultivo VARCHAR(80) NOT NULL,
    frecuencia_riego_dias INT NOT NULL,
    temperatura_ideal DECIMAL(5,2) NOT NULL,
    fecha_siembra DATE,
    requiere_sombra BIT DEFAULT 0,
    observaciones VARCHAR(MAX),
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);

select * from cultivos;

CREATE TABLE usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE, 
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,      
    contrasena VARCHAR(255) NOT NULL,                
    rol VARCHAR(20) DEFAULT 'OPERADOR',              
    fecha_creacion DATETIME DEFAULT GETDATE(),
    ultimo_acceso DATETIME,
    estado BIT DEFAULT 1                             
);
GO

INSERT INTO usuarios (nombre_completo, correo_electronico, nombre_usuario, contrasena, rol)
VALUES ('Administrador General', 'admin@agropacayales.com', 'admin', 'SqlPassword2026!', 'ADMIN');
GO

SELECT * FROM usuarios;
GO