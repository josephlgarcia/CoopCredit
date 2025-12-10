# CoopCredit-App - Sistema de Solicitudes de Crédito 🏦

## 📋 Descripción

Sistema profesional de gestión de solicitudes de crédito para cooperativas, implementando **Arquitectura Hexagonal** (Puertos y Adaptadores) con principios **SOLID**, autenticación **JWT**, evaluación de riesgo automatizada, y control de acceso basado en roles.

## ✨ Características Principales

✅ **Arquitectura Hexagonal Completa** - Separación clara entre dominio, aplicación e infraestructura  
✅ **Autenticación JWT** - Seguridad stateless con tokens  
✅ **Control de Acceso por Roles** - AFILIADO, ANALISTA, ADMIN  
✅ **Evaluación de Riesgo Automatizada** - Integración con servicio externo  
✅ **Políticas de Crédito Internas** - Validación automática  
✅ **Principios SOLID** - Diseño orientado a objetos  
✅ **Validación con Bean Validation** - Anotaciones declarativas  
✅ **PostgreSQL + Flyway** - Migración de BD versionada  
✅ **MapStruct** - Mapeo automático de DTOs  
✅ **Swagger/OpenAPI** - Documentación interactiva  
✅ **Docker Ready** - Contenedorización lista  

## 🚀 Quick Start

### Requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 12+ (local o Docker)
- Docker & Docker Compose (opcional)

### Ejecución Rápida

```bash
# 1. Compilar
mvn clean package -DskipTests

# 2. Ejecutar
java -jar target/CoopCredit-0.0.1-SNAPSHOT.jar

# 3. Acceder a Swagger
# http://localhost:8080/swagger-ui/index.html
```

### Con Docker Compose

```bash
docker-compose up --build
```

## 📐 Arquitectura Hexagonal

```
┌──────────────────────────────────────────────────┐
│              INFRASTRUCTURE                      │
│  REST Controllers │ JPA │ External Services     │
└─────────┬────────────────────┬──────────────────┘
          │                    │
   ┌──────▼──────┐      ┌──────▼──────┐
   │  IN Ports   │      │  OUT Ports  │
   │ (UseCases)  │      │ (Repository)│
   └──────┬──────┘      └──────┬──────┘
          │                    │
┌─────────▼────────────────────▼──────────────────┐
│          APPLICATION LAYER           │
│  Use Case Implementations            │
└──────────────┬───────────────────────────────────┘
               │
┌──────────────▼───────────────────────────────────┐
│           DOMAIN LAYER                           │
│  Pure Business Models - No Dependencies          │
└──────────────────────────────────────────────────┘
```

### Estructura de Directorios

```
src/main/java/com/credits/coopCredit/
├── domain/
│   ├── model/         # POJOs puros (Affiliate, CreditApplication, CreditEvaluation)
│   └── ports/
│       ├── in/        # Casos de uso (interfaces)
│       └── out/       # Puertos de salida (Repository, RiskCentral)
├── application/
│   └── usecases/      # Implementaciones de casos de uso
│       ├── affiliate/ # CreateAffiliate, UpdateAffiliate, GetAffiliate
│       ├── credit/    # CreateCredit, EvaluateCredit, GetCreditApplication
│       └── auth/      # Login, Register
└── infrastructure/
    ├── adapters/
    │   └── out/       # RiskCentralAdapter, RepositoryAdapters
    ├── config/        # Spring Config, Security
    ├── entities/      # JPA Entities
    ├── repositories/  # Spring Data JPA
    └── web/
        ├── controller/# REST Controllers
        ├── dto/       # Request/Response DTOs
        └── mapper/    # MapStruct Mappers
```

## 🔐 Seguridad y Roles

### Autenticación

```bash
# Registro
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "analista1",
    "password": "Pass123!",
    "email": "analista@coopcredit.com",
    "role": "ROLE_ANALISTA"
  }'

# Login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "analista1",
    "password": "Pass123!"
  }'
```

### Permisos por Rol

| Endpoint | AFILIADO | ANALISTA | ADMIN |
|----------|----------|----------|-------|
| POST /affiliates | ❌ | ❌ | ✅ |
| GET /affiliates/{id} | ❌ | ✅ | ✅ |
| POST /credit-applications | ✅ (propias) | ❌ | ✅ |
| POST /credit-applications/{id}/evaluate | ❌ | ✅ | ✅ |

## 📊 API Endpoints

### Afiliados

- `POST /api/v1/affiliates` - Crear afiliado (ADMIN)
- `PUT /api/v1/affiliates/{id}` - Actualizar afiliado (ADMIN)
- `GET /api/v1/affiliates/{id}` - Obtener por ID (ANALISTA, ADMIN)
- `GET /api/v1/affiliates/documento/{documento}` - Obtener por documento

### Solicitudes de Crédito

- `POST /api/v1/credit-applications` - Crear solicitud (AFILIADO, ADMIN)
- `GET /api/v1/credit-applications/{id}` - Obtener solicitud
- `GET /api/v1/credit-applications` - Listar con filtros
  - `?estado=PENDIENTE`
  - `?affiliateId=1`
- `POST /api/v1/credit-applications/{id}/evaluate` - Evaluar (ANALISTA, ADMIN)

## 🔄 Flujo de Evaluación de Crédito

1. **Creación de Solicitud**: Afiliado ACTIVO crea solicitud → `PENDIENTE`
2. **Llamada Externa**: Sistema consulta `risk-central-mock-service`
3. **Validación de Políticas**:
   - ✅ Cuota mensual ≤ 40% salario
   - ✅ Monto ≤ 20 × salario
   - ✅ Score ≥ 501
   - ✅ Antigüedad ≥ 6 meses
4. **Decisión Automática**: `APROBADO` o `RECHAZADO`
5. **Persistencia**: Evaluación guardada y estado actualizado

## ⚙️ Configuración

### Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `SERVER_PORT` | Puerto del servidor | `8080` |
| `DB_NAME` | Nombre de BD | `coopcredit_db` |
| `DB_USER` | Usuario PostgreSQL | `postgres` |
| `DB_PASSWORD` | Contraseña PostgreSQL | `postgres` |
| `JWT_SECRET` | Secreto JWT (256 bits) | - |
| `JWT_EXPIRATION` | Expiración token (ms) | `3600000` |
| `RISK_SERVICE_URL` | URL servicio riesgo | `http://localhost:8081` |

### application.properties

```properties
server.port=${SERVER_PORT:8080}
spring.datasource.url=${SPRING_DATABASE}:${DB_PORT}/${DB_NAME}
jwt.secret=${JWT_SECRET}
risk.service.url=${RISK_SERVICE_URL:http://localhost:8081}
```

## 🛠️ Stack Tecnológico

- **Framework**: Spring Boot 3.3.4
- **Seguridad**: Spring Security 6 + JWT
- **Base de Datos**: PostgreSQL 15 + Flyway
- **ORM**: JPA/Hibernate
- **Mapeo**: MapStruct 1.5.5
- **Validación**: Bean Validation
- **Utilidades**: Lombok
- **Documentación**: Swagger/OpenAPI 2.5.0
- **Build**: Maven 3.11.0
- **Contenedores**: Docker + Docker Compose

## 📝 Ejemplos de Uso

### 1. Crear Afiliado

```bash
curl -X POST http://localhost:8080/api/v1/affiliates \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "documento": "1234567890",
    "nombre": "Juan Pérez",
    "salario": 5000000,
    "fechaAfiliacion": "2024-01-15"
  }'
```

### 2. Crear Solicitud de Crédito

```bash
curl -X POST http://localhost:8080/api/v1/credit-applications \
  -H "Authorization: Bearer YOUR_AFILIADO_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "documento": "1234567890",
    "monto": 10000000,
    "plazo": 36,
    "tasa": 1.5
  }'
```

### 3. Evaluar Solicitud

```bash
curl -X POST http://localhost:8080/api/v1/credit-applications/1/evaluate \
  -H "Authorization: Bearer YOUR_ANALISTA_TOKEN"
```

## 🐳 Docker

```bash
# Construir imagen
docker build -t coopcredit-app:latest .

# Docker Compose
docker-compose up -d
docker-compose logs -f app
docker-compose down
```

## 📚 Documentación

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## ✅ Estado del Proyecto

- ✅ **75 archivos Java** completados
- ✅ **Compilación**: BUILD SUCCESS
- ✅ **Jar**: CoopCredit-0.0.1-SNAPSHOT.jar (60MB)
- ✅ **Arquitectura Hexagonal**: Completa
- ✅ **Principios SOLID**: Implementados
- ✅ **Todos los requisitos**: Cumplidos

## 📄 Licencia

Proyecto académico para CoopCredit.

---

**Versión**: 1.0.0  
**Estado**: ✅ Producción Listo  
**Última Actualización**: Diciembre 2024