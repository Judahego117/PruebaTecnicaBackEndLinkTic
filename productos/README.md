# Productos Service

## Descripción

Microservicio encargado de la gestión de productos dentro del sistema. Expone endpoints RESTful para realizar operaciones de creación y consulta de productos, siguiendo el estándar JSON:API. Desarrollado como parte de una prueba técnica para evaluar buenas prácticas de diseño, pruebas y documentación.

---

## Instrucciones de instalación y ejecución

### Requisitos previos
- Docker
- Java 17 o superior
- Maven 3.9+
- PostgreSQL (opcional si se utiliza Docker)

### 2. Crear base de datos PostgreSQL con Docker
```bash
docker run --name inventario-db -p 5432:5432 \
  -e POSTGRES_DB=pruebaTecnica \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=1234567 \
  -d postgres
```

> Estas credenciales están configuradas únicamente para entorno local. Para entornos productivos, deben gestionarse mediante variables de entorno seguras y mecanismos de gestión de secretos.

### 3. Configurar el archivo `application.properties`
```properties
server.port=8082
spring.datasource.url=jdbc:postgresql://localhost:5432/pruebaTecnica
spring.datasource.username=postgres
spring.datasource.password=1234567
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.application.name=inventario-service
```

### Ejecutar el proyecto

```bash
./mvnw spring-boot:run
```

---

## Arquitectura

El microservicio se construyó utilizando Spring Boot y sigue una arquitectura hexagonal simplificada basada en capas:

- `controller`: Exposición de endpoints REST.
- `dto`: Transferencia de datos entre capas.
- `entity`: Mapeo JPA a base de datos.
- `repository`: Abstracción del acceso a datos.
- `service`: Lógica de negocio.

Además, se integran pruebas unitarias utilizando JUnit y Mockito, con cobertura superior al 90%.

---

## Decisiones técnicas y justificaciones

- **Estandarización JSON:API**: Se implementó un wrapper genérico que asegura la salida de respuestas alineadas con JSON:API. Esto garantiza consistencia y desacoplamiento entre cliente y servidor.
- **DTOs separados para request y response**: Permite controlar con precisión qué datos se reciben y cuáles se exponen, mejorando la seguridad y mantenibilidad.
- **Manejo de errores centralizado**: Se capturan errores comunes como validaciones o entidad no encontrada y se devuelven mensajes estructurados.
- **No uso de librerías externas como ModelMapper**: Para mantener el control explícito sobre el mapeo de datos y garantizar trazabilidad en cada conversión.
- **Endpoint de compra**: Esta funcionalidad, al involucrar lógica transaccional y múltiples microservicios (productos, inventario), se implementa en el microservicio de órdenes. Esta decisión mantiene los servicios cohesivos y evita acoplamiento entre dominios.

---

## Diagrama de interacción entre servicios

```text
[Cliente API] ---> [gateway/orden-service] ---> [productos-service]
                                      \         [inventario-service]
```

El cliente interactúa con un punto central (orden-service) que orquesta llamadas a productos e inventario para realizar la operación de compra.

---

## Flujo de compra implementado

1. El cliente solicita la compra de un producto desde el microservicio de órdenes.
2. El microservicio de órdenes valida la existencia del producto con `productos-service`.
3. Consulta y descuenta el stock disponible desde `inventario-service`.
4. Registra la orden como transacción exitosa.

Cada paso se maneja de forma asincrónica o transaccional según el caso, priorizando consistencia eventual entre servicios.

---

## Uso de herramientas de IA

Durante el desarrollo se integraron herramientas de inteligencia artificial para mejorar productividad y calidad:

- **ChatGPT (OpenAI):** asistencia en la corrección de pruebas unitarias, refactorización, y validación de buenas prácticas.
- **Análisis con IA local:** detección de código repetitivo, oportunidad de abstracciones y mejoras en documentación técnica.

Todas las sugerencias generadas por IA fueron revisadas y validadas manualmente. Se priorizó la calidad del diseño, el cumplimiento de principios SOLID, y la claridad del código.

---

## Uso de Git Flow

Se siguió el modelo de ramas Git Flow para garantizar trazabilidad, control de versiones y claridad en la colaboración durante el desarrollo. La estrategia aplicada fue la siguiente:

### Ramas

- `main`: rama estable utilizada para despliegues a producción.
- `develop`: rama base para integrar funcionalidades en desarrollo.
- `feature/nombre-funcionalidad`: ramas destinadas al desarrollo de nuevas funcionalidades. Ejemplos:
    - `feature/crear-producto`
    - `feature/validar-datos`
    - `feature/tests-controller`
- `release/x.y.z`: ramas destinadas a estabilizar una versión antes de ser integrada en `main`.
- `hotfix/descripcion-fix`: ramas para corregir errores críticos detectados en producción.

### Flujo de trabajo aplicado

1. Se crea una rama `feature/*` desde `develop` para cada funcionalidad.
2. Una vez completada, se hace merge vía pull request hacia `develop`.
3. Las ramas `release/*` se crean desde `develop` para pruebas y ajustes finales antes del despliegue.
4. Una vez validadas, se fusionan tanto en `main` como en `develop`.
5. Las ramas `hotfix/*` se crean desde `main` y se fusionan nuevamente en `main` y `develop`.

### Convención de commits

Se empleó una convención semántica para los mensajes de commit:
- `feat:` nuevas funcionalidades
- `fix:` corrección de errores
- `test:` adición o corrección de pruebas
- `refactor:` cambios en el código que no afectan la funcionalidad
- `docs:` cambios en documentación

Este flujo asegura consistencia, auditabilidad y cumplimiento con buenas prácticas de control de versiones profesionales.
