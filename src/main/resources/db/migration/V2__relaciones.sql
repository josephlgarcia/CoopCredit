-- Flyway V2: Relaciones, índices y restricciones adicionales

-- 1) Asegurar que las columnas de FK no permitan valores inconsistentes
ALTER TABLE IF EXISTS credit_applications
    ALTER COLUMN affiliate_id SET NOT NULL;

-- 2) Crear FK con nombre explícito y ON DELETE CASCADE para mantener integridad
ALTER TABLE IF EXISTS credit_applications
    DROP CONSTRAINT IF EXISTS fk_credit_applications_affiliate,
    ADD CONSTRAINT fk_credit_applications_affiliate
        FOREIGN KEY (affiliate_id) REFERENCES affiliates(id) ON DELETE CASCADE;

-- 3) Relación a evaluación (1-1). Permitir que evaluation_id sea NULL (aplicaciones pendientes)
ALTER TABLE IF EXISTS credit_applications
    DROP CONSTRAINT IF EXISTS fk_credit_applications_evaluation;

ALTER TABLE IF EXISTS credit_applications
    ADD CONSTRAINT fk_credit_applications_evaluation
        FOREIGN KEY (evaluation_id) REFERENCES credit_evaluations(id) ON DELETE SET NULL;

-- 4) Asegurar unicidad de evaluation_id para representar 1-1
CREATE UNIQUE INDEX IF NOT EXISTS uq_credit_applications_evaluation_id ON credit_applications(evaluation_id);

-- 5) Índices para optimizar consultas comunes
CREATE INDEX IF NOT EXISTS idx_affiliates_documento ON affiliates(documento);
CREATE INDEX IF NOT EXISTS idx_credit_applications_affiliate_id ON credit_applications(affiliate_id);
CREATE INDEX IF NOT EXISTS idx_credit_evaluations_score ON credit_evaluations(score);

-- 6) Checks y validaciones de dominio en BD
-- Estado de afiliado debe ser ACTIVO o INACTIVO
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint c JOIN pg_class t ON c.conrelid = t.oid
        WHERE c.conname = 'chk_affiliates_estado') THEN
        ALTER TABLE affiliates
            ADD CONSTRAINT chk_affiliates_estado CHECK (estado IS NULL OR estado IN ('ACTIVO','INACTIVO'));
    END IF;
END$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint c JOIN pg_class t ON c.conrelid = t.oid
        WHERE c.conname = 'chk_credit_applications_estado') THEN
        ALTER TABLE credit_applications
            ADD CONSTRAINT chk_credit_applications_estado CHECK (estado IS NULL OR estado IN ('PENDIENTE','APROBADO','RECHAZADO'));
    END IF;
END$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint c JOIN pg_class t ON c.conrelid = t.oid
        WHERE c.conname = 'chk_credit_applications_plazo') THEN
        ALTER TABLE credit_applications
            ADD CONSTRAINT chk_credit_applications_plazo CHECK (plazo IS NULL OR plazo > 0);
    END IF;
END$$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint c JOIN pg_class t ON c.conrelid = t.oid
        WHERE c.conname = 'chk_credit_applications_monto') THEN
        ALTER TABLE credit_applications
            ADD CONSTRAINT chk_credit_applications_monto CHECK (monto IS NULL OR monto >= 0);
    END IF;
END$$;

-- 7) Limpiar nombres de columnas a formato snake_case consistente si fuera necesario (se asume V1 correcto)

-- Fin V2
