
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE roles IS 'Roles del sistema para control de acceso';
COMMENT ON COLUMN roles.id IS 'Identificador único del rol';
COMMENT ON COLUMN roles.name IS 'Nombre del rol (ej: ROLE_AFILIADO, ROLE_ADMIN)';
COMMENT ON COLUMN roles.description IS 'Descripción del propósito del rol';

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    enabled BOOLEAN NOT NULL DEFAULT true,
    account_non_expired BOOLEAN NOT NULL DEFAULT true,
    account_non_locked BOOLEAN NOT NULL DEFAULT true,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE users IS 'Usuarios del sistema con credenciales de acceso';

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) 
        REFERENCES roles(id) ON DELETE CASCADE
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_enabled ON users(enabled);
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);

-- Insertar roles base
INSERT INTO roles (name, description) VALUES
    ('ROLE_AFILIADO', 'Afiliado del sistema'),
    ('ROLE_ANALISTA', 'Analista de riesgos'),
    ('ROLE_ADMIN', 'Administrador del sistema con todos los privilegios');

-- Usuarios de ejemplo (las contraseñas deben estar en BCrypt)
INSERT INTO users (username, email, password, first_name, last_name) VALUES
    ('afiliado', 'afiliado@coopCredit.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Afiliado', 'Estándar'),
    ('analista', 'analista@coopCredit.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36c8c6s8nY5r5e5Q5Q5Q5e', 'Analista', 'Datos'),
    ('admin', 'admin@coopCredit.com', '$2a$10$DOwnh2./8gzj4cVgBPCPz.QhJ1KqK3PmPvDGYCQwmrJXH/PImLYE6', 'Administrador', 'Sistema');

-- Asignar roles a los usuarios insertados (usando subconsultas para robustez)
INSERT INTO user_roles (user_id, role_id)
VALUES
    ((SELECT id FROM users WHERE username = 'afiliado'), (SELECT id FROM roles WHERE name = 'ROLE_AFILIADO')),
    ((SELECT id FROM users WHERE username = 'analista'), (SELECT id FROM roles WHERE name = 'ROLE_ANALISTA')),
    ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));
