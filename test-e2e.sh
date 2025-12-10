#!/bin/bash
# Script de pruebas E2E completo para CoopCredit-App

echo "========================================="
echo "TESTING E2E CoopCredit-App"
echo "========================================="
echo ""

# Test 1: Health check
echo "1. Health Check"
curl -s http://localhost:8080/actuator/health | grep -o '"status":"[^"]*"'
echo ""

# Test 2: Risk Service
echo "2. Risk Service Mock"
curl -s -X POST http://localhost:8081/risk-evaluation -H "Content-Type: application/json" \
  -d '{"documento":"1234567890","monto":5000000,"plazo":24}' | grep -o '"score":[0-9]*'
echo ""

# Test 3: Registrar ADMIN
echo "3. Registrar ADMIN"
REGISTER_RESPONSE=$(curl -s -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin123!","email":"admin@coopcredit.com","firstName":"Admin","lastName":"User","role":"ROLE_ADMIN"}')
echo "$REGISTER_RESPONSE" | grep -o '"name":"ROLE_[^"]*"' || echo "$REGISTER_RESPONSE"
echo ""

# Test 4: Login ADMIN
echo "4. Login ADMIN"
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin123!"}' | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo "Token: ${ADMIN_TOKEN:0:50}..."
echo ""

# Test 5: Crear Afiliado
echo "5. Crear Afiliado (ADMIN)"
curl -s -X POST http://localhost:8080/api/v1/affiliates \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"documento":"1234567890","nombre":"Juan Perez","salario":5000000,"fechaAfiliacion":"2023-01-15"}' \
  | grep -o '"documento":"[^"]*"' || echo "Error al crear afiliado"
echo ""

# Test 6: Obtener Afiliado
echo "6. Obtener Afiliado por documento"
curl -s -X GET "http://localhost:8080/api/v1/affiliates/documento/1234567890" \
  -H "Authorization: Bearer $ADMIN_TOKEN" | grep -o '"nombre":"[^"]*"'
echo ""

# Test 7: Crear Solicitud de Crédito
echo "7. Crear Solicitud de Crédito (con evaluación automática)"
CREDIT_APP=$(curl -s -w "\nHTTP_CODE:%{http_code}" -X POST http://localhost:8080/api/v1/credit-applications \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"documento":"1234567890","monto":10000000,"plazo":36,"tasa":1.5}')
echo "$CREDIT_APP" | head -1 | grep -o '"estado":"[^"]*"' || echo "$CREDIT_APP"
echo ""

# Test 8: Listar Solicitudes
echo "8. Listar Solicitudes"
curl -s -X GET http://localhost:8080/api/v1/credit-applications \
  -H "Authorization: Bearer $ADMIN_TOKEN" | grep -o '\[.*\]' | head -c 100
echo "..."
echo ""

# Test 9: Registrar ANALISTA
echo "9. Registrar ANALISTA"
curl -s -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"analista","password":"Analista123!","email":"analista@coopcredit.com","firstName":"Ana","lastName":"Lista","role":"ROLE_ANALISTA"}' \
  | grep -o '"name":"ROLE_[^"]*"'
echo ""

# Test 10: Swagger UI
echo "10. Swagger UI disponible"
curl -s -o /dev/null -w "HTTP:%{http_code}" http://localhost:8080/swagger-ui/index.html
echo ""

echo ""
echo "========================================="
echo "TESTING COMPLETADO"
echo "========================================="
