-- =====================================================
-- BASE DE DATOS: SOCIEDAD AGRÍCOLA ARONA S.A.
-- Sistema de Gestión Agroexportadora - v2.0
-- Reestructuración: parcelas → campos, eliminación de mensajes
-- =====================================================

CREATE DATABASE aronadb;
GO

USE aronadb;
GO

-- =====================================================
-- TABLA: usuarios
-- Roles: ENCARGADO, ADMINISTRADOR
-- =====================================================
CREATE TABLE usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50),
    password VARCHAR(255),
    rol VARCHAR(30) DEFAULT 'ENCARGADO',
    -- Roles: ENCARGADO, ADMINISTRADOR
    area VARCHAR(20),
    -- Áreas: CAMPO, PLANTA, ALMACEN, ADMINISTRACION, CALIDAD
    fecha_registro DATETIME DEFAULT GETDATE(),
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- =====================================================
-- TABLA: campos (antes: parcelas)
-- Terrenos de cultivo (300+ hectáreas en valle de Cañete)
-- =====================================================
DROP TABLE IF EXISTS mensajes;
GO

DROP TABLE IF EXISTS parcelas;
GO

CREATE TABLE campos (
    id_campo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    ubicacion VARCHAR(200),
    hectareas DECIMAL(10,2) NOT NULL,
    tipo_cultivo VARCHAR(80),
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- =====================================================
-- TABLA: cultivos
-- HU1: Consulta del estado de los cultivos
-- Tipos: MANDARINA, PALTA, ARANDANO, CAQUI
-- =====================================================
CREATE TABLE cultivos (
    id_cultivo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_cultivo VARCHAR(80) NOT NULL,
    frecuencia_riego_dias INT NOT NULL,
    temperatura_ideal DECIMAL(5,2) NOT NULL,
    fecha_siembra DATE,
    requiere_sombra BIT DEFAULT 0,
    estado_salud VARCHAR(20) DEFAULT 'BUENO',
    -- Estados: BUENO, EN_RIESGO, CON_PROBLEMAS
    observaciones VARCHAR(MAX),
    id_campo INT,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2,
    CONSTRAINT FK_cultivos_campo FOREIGN KEY (id_campo) REFERENCES campos(id_campo)
);
GO

-- =====================================================
-- TABLA: actividades_campo
-- HU2: Anotación de trabajos en campo
-- =====================================================
CREATE TABLE actividades_campo (
    id_actividad INT IDENTITY(1,1) PRIMARY KEY,
    tipo_actividad VARCHAR(30) NOT NULL,
    -- Tipos: RIEGO, PODA, FUMIGACION, FERTILIZACION, OTRO
    fecha DATE NOT NULL,
    id_campo INT NOT NULL,
    id_cultivo INT,
    id_usuario INT NOT NULL,
    observaciones VARCHAR(MAX),
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2,
    CONSTRAINT FK_actividades_campo FOREIGN KEY (id_campo) REFERENCES campos(id_campo),
    CONSTRAINT FK_actividades_cultivo FOREIGN KEY (id_cultivo) REFERENCES cultivos(id_cultivo),
    CONSTRAINT FK_actividades_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);
GO

-- =====================================================
-- TABLA: cosechas
-- HU3: Registro de la cosecha
-- =====================================================
CREATE TABLE cosechas (
    id_cosecha INT IDENTITY(1,1) PRIMARY KEY,
    fecha DATE NOT NULL,
    id_campo INT NOT NULL,
    id_cultivo INT NOT NULL,
    cantidad_kg DECIMAL(10,2),
    cantidad_unidades INT,
    tipo_cultivo VARCHAR(80) NOT NULL,
    id_usuario INT NOT NULL,
    observaciones VARCHAR(MAX),
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2,
    CONSTRAINT FK_cosechas_campo FOREIGN KEY (id_campo) REFERENCES campos(id_campo),
    CONSTRAINT FK_cosechas_cultivo FOREIGN KEY (id_cultivo) REFERENCES cultivos(id_cultivo),
    CONSTRAINT FK_cosechas_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);
GO

-- =====================================================
-- TABLA: clasificaciones
-- HU4: Separación por tamaño y calidad
-- =====================================================
CREATE TABLE clasificaciones (
    id_clasificacion INT IDENTITY(1,1) PRIMARY KEY,
    id_cosecha INT NOT NULL,
    calibre VARCHAR(20) NOT NULL,
    -- Calibres: PEQUEÑO, MEDIANO, GRANDE, EXTRA_GRANDE
    estado_fruta VARCHAR(20) NOT NULL,
    -- Estados: BUENA, DAÑADA
    cantidad_kg DECIMAL(10,2),
    cantidad_unidades INT,
    apto_exportacion BIT DEFAULT 0,
    fecha DATE NOT NULL,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2,
    CONSTRAINT FK_clasificaciones_cosecha FOREIGN KEY (id_cosecha) REFERENCES cosechas(id_cosecha)
);
GO

-- =====================================================
-- TABLA: revisiones_calidad
-- HU6: Revisión de calidad antes del despacho
-- =====================================================
CREATE TABLE revisiones_calidad (
    id_revision INT IDENTITY(1,1) PRIMARY KEY,
    id_cosecha INT NOT NULL,
    cumple_requisitos BIT NOT NULL,
    observaciones VARCHAR(MAX),
    id_usuario_supervisor INT NOT NULL,
    fecha DATE NOT NULL,
    notificado BIT DEFAULT 0,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2,
    CONSTRAINT FK_revisiones_cosecha FOREIGN KEY (id_cosecha) REFERENCES cosechas(id_cosecha),
    CONSTRAINT FK_revisiones_supervisor FOREIGN KEY (id_usuario_supervisor) REFERENCES usuarios(id_usuario)
);
GO

-- =====================================================
-- TABLA: envios
-- HU5: Registro de envíos al extranjero
-- =====================================================
CREATE TABLE envios (
    id_envio INT IDENTITY(1,1) PRIMARY KEY,
    pais_destino VARCHAR(100) NOT NULL,
    tipo_cultivo VARCHAR(80) NOT NULL,
    cantidad_kg DECIMAL(10,2),
    estado_envio VARCHAR(20) DEFAULT 'EN_PREPARACION',
    -- Estados: EN_PREPARACION, DESPACHADO, ENTREGADO
    fecha_envio DATE,
    fecha_entrega DATE,
    documento_detalle VARCHAR(MAX),
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- =====================================================
-- TABLA: envios_detalle
-- Tabla intermedia: Clasificaciones incluidas en cada envío
-- =====================================================
CREATE TABLE envios_detalle (
    id_envio_detalle INT IDENTITY(1,1) PRIMARY KEY,
    id_envio INT NOT NULL,
    id_clasificacion INT NOT NULL,
    cantidad_kg DECIMAL(10,2),
    CONSTRAINT FK_enviodet_envio FOREIGN KEY (id_envio) REFERENCES envios(id_envio),
    CONSTRAINT FK_enviodet_clasificacion FOREIGN KEY (id_clasificacion) REFERENCES clasificaciones(id_clasificacion)
);
GO

-- =====================================================
-- TABLA: producto
-- HU8: Seguimiento del inventario (fruta en almacén)
-- =====================================================
CREATE TABLE producto (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo_cultivo VARCHAR(80) NOT NULL,
    descripcion VARCHAR(MAX),
    cantidad_kg DECIMAL(10,2) NOT NULL DEFAULT 0,
    cantidad_cajas INT DEFAULT 0,
    unidad_medida VARCHAR(20),
    fecha_ingreso DATE,
    umbral_minimo DECIMAL(10,2) DEFAULT 100.00,
    id_cosecha INT,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2,
    CONSTRAINT FK_producto_cosecha FOREIGN KEY (id_cosecha) REFERENCES cosechas(id_cosecha)
);
GO

-- =====================================================
-- TABLA: alertas_fitosanitarias
-- HU7: Aviso de plagas o enfermedades
-- =====================================================
CREATE TABLE alertas_fitosanitarias (
    id_alerta INT IDENTITY(1,1) PRIMARY KEY,
    id_campo INT NOT NULL,
    id_cultivo INT,
    descripcion_problema VARCHAR(MAX) NOT NULL,
    tipo_problema VARCHAR(20) NOT NULL,
    -- Tipos: PLAGA, ENFERMEDAD, OTRO
    estado_alerta VARCHAR(20) DEFAULT 'PENDIENTE',
    -- Estados: PENDIENTE, ATENDIDO
    solucion_aplicada VARCHAR(MAX),
    fecha_deteccion DATE NOT NULL,
    fecha_resolucion DATE,
    id_usuario_reporta INT NOT NULL,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2,
    CONSTRAINT FK_alertas_campo FOREIGN KEY (id_campo) REFERENCES campos(id_campo),
    CONSTRAINT FK_alertas_cultivo FOREIGN KEY (id_cultivo) REFERENCES cultivos(id_cultivo),
    CONSTRAINT FK_alertas_usuario FOREIGN KEY (id_usuario_reporta) REFERENCES usuarios(id_usuario)
);
GO

-- =====================================================
-- DATOS INICIALES
-- =====================================================

-- Usuario administrador por defecto
INSERT INTO usuarios (nombre_completo, correo, username, password, rol, area, created_at)
VALUES ('Administrador General', 'admin@arona.com.pe', 'admin', 'AronaAdmin2026!', 'ADMINISTRADOR', 'ADMINISTRACION', GETDATE());
GO

-- Campos de ejemplo (valle de Cañete) - migración de parcelas
INSERT INTO campos (nombre, ubicacion, hectareas, tipo_cultivo, estado, created_at)
VALUES 
('Campo Norte A', 'Valle de Cañete - Sector Norte', 50.00, 'MANDARINA', 1, GETDATE()),
('Campo Norte B', 'Valle de Cañete - Sector Norte', 80.00, 'PALTA', 1, GETDATE()),
('Campo Sur A', 'Valle de Cañete - Sector Sur', 60.00, 'ARANDANO', 1, GETDATE()),
('Campo Sur B', 'Valle de Cañete - Sector Sur', 40.00, 'CAQUI', 1, GETDATE()),
('Campo Central', 'Valle de Cañete - Sector Central', 70.00, 'MANDARINA', 1, GETDATE());
GO