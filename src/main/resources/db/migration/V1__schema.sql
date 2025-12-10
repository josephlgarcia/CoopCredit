-- Flyway migration inicial: tablas para afiliados, solicitudes y evaluaciones
CREATE TABLE IF NOT EXISTS affiliates (
    id BIGSERIAL PRIMARY KEY,
    documento VARCHAR(100) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    salario FLOAT(53) NOT NULL,
    fecha_afiliacion DATE,
    estado VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS credit_evaluations (
    id BIGSERIAL PRIMARY KEY,
    score INTEGER,
    nivel_riesgo VARCHAR(50),
    detalle TEXT,
    fecha DATE,
    decision VARCHAR(50),
    motivo TEXT
);

CREATE TABLE IF NOT EXISTS credit_applications (
    id BIGSERIAL PRIMARY KEY,
    affiliate_id BIGINT REFERENCES affiliates(id),
    monto FLOAT(53),
    plazo INTEGER,
    tasa FLOAT(53),
    fecha_solicitud DATE,
    estado VARCHAR(50),
    evaluation_id BIGINT REFERENCES credit_evaluations(id)
);
