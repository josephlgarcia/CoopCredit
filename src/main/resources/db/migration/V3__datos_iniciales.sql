-- Flyway V3: Insertar datos iniciales de prueba para afiliados y solicitudes

-- Afiliados de ejemplo
INSERT INTO affiliates (documento, nombre, salario, fecha_afiliacion, estado)
VALUES
    ('1017654311', 'Juan Perez', 1500000.00, (CURRENT_DATE - INTERVAL '18 months')::date, 'ACTIVO'),
    ('2023456789', 'Ana Gomez', 800000.00, (CURRENT_DATE - INTERVAL '3 months')::date, 'ACTIVO'),
    ('3039988776', 'Carlos Ruiz', 2500000.00, (CURRENT_DATE - INTERVAL '36 months')::date, 'INACTIVO');

-- Evaluaciones de ejemplo (para ilustrar estados)
INSERT INTO credit_evaluations (score, nivel_riesgo, detalle, fecha, decision, motivo)
VALUES
    (720, 'BAJO', 'Historial sano.', CURRENT_DATE, 'APROBADO', 'Cumple políticas internas'),
    (480, 'ALTO', 'Historial con problemas.', CURRENT_DATE, 'RECHAZADO', 'Score de riesgo alto');

-- Solicitudes: una aprobada (con evaluación), una pendiente (sin evaluación)
-- Obtener ids dinámicamente
WITH a AS (
    SELECT id AS affiliate_id FROM affiliates WHERE documento = '1017654311' LIMIT 1
), e AS (
    SELECT id AS eval_id FROM credit_evaluations ORDER BY id ASC LIMIT 1
)
INSERT INTO credit_applications (affiliate_id, monto, plazo, tasa, fecha_solicitud, estado, evaluation_id)
SELECT a.affiliate_id, 2000000.00, 24, 12.5, CURRENT_DATE, 'APROBADO', e.eval_id FROM a, e;

-- Solicitud pendiente sin evaluación
INSERT INTO credit_applications (affiliate_id, monto, plazo, tasa, fecha_solicitud, estado)
SELECT id, 500000.00, 12, 10.0, CURRENT_DATE, 'PENDIENTE' FROM affiliates WHERE documento = '2023456789' LIMIT 1;

-- Fin V3
