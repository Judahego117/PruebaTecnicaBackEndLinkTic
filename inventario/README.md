# Inventario Service

## Descripción

Microservicio encargado de la gestión del inventario y el registro histórico de los movimientos de stock asociados a los productos. Forma parte de un sistema basado en microservicios orientado a la gestión de compras. Este servicio permite consultar el stock disponible de un producto, descontar unidades como parte de una transacción y registrar cada operación en una tabla de historial.

---

## Instrucciones de instalación y ejecución

### Requisitos previos
- Java 17 o superior
- Maven 3.9+
- Docker (opcional, para PostgreSQL)

### 1. Clonar el repositorio
```bash
git clone <repositorio>
cd inventario
```

### 2. Crear base de datos PostgreSQL con Docker
```bash
docker run --name inventario-db -p 5432:5432 \
  -e POSTGRES_DB=pruebaTecnica \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=1234567 \
  -d postgres
```

### 3. Configurar el archivo `application.properties`
```properties
server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/pruebaTecnica
spring.datasource.username=postgres
spring.datasource.password=1234567
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.application.name=inventario-service
```

### 4. Ejecutar el proyecto
```bash
./mvnw spring-boot:run
```

---

## Arquitectura

El microservicio se desarrolla con Spring Boot bajo una arquitectura en capas:

- `controller`: Manejo de peticiones HTTP.
- `dto`: Encapsulación de datos de entrada y salida.
- `entity`: Entidades JPA para persistencia.
- `repository`: Interfaces para acceso a datos.
- `service`: Lógica de negocio del inventario y su historial.

Se incluye además una entidad adicional (`HistorialInventario`) y su respectivo flujo para garantizar la trazabilidad de cambios.

---

## Decisiones técnicas y justificaciones

- **Separación de DTOs para request y response:** permite un mayor control sobre los datos expuestos y recibidos, y mejora la validación.
- **No uso de ModelMapper ni librerías externas:** se prefirió la transformación explícita para mayor claridad y trazabilidad.
- **Persistencia del historial de inventario:** se optó por registrar toda modificación del stock como una práctica orientada a auditoría y mantenimiento de consistencia.
- **Ubicación del endpoint de compra:** la lógica de compra, que involucra múltiples servicios, debe orquestarse desde un microservicio central como `orden-service`, ya que una transacción completa depende de inventario y productos.

---

## Diagrama de interacción entre servicios

```text
[Cliente API] ---> [gateway/orden-service] ---> [productos-service]
                                      \
                                       ---> [inventario-service]
```

---

## Flujo de compra implementado

1. El cliente realiza una solicitud de compra al `orden-service`.
2. Se valida la existencia del producto vía `productos-service`.
3. Se consulta el stock y se descuenta el inventario vía `inventario-service`.
4. Se registra el cambio en la tabla `historial_inventario`.
5. Se confirma la orden como exitosa o fallida según disponibilidad.

Este enfoque garantiza que cada microservicio mantenga la responsabilidad de su dominio, cumpliendo el principio de responsabilidad única.

---

## Uso de Git Flow

Se aplicó un flujo Git Flow estructurado:

- `main`: versión estable y de producción.
- `develop`: integración continua de nuevas funcionalidades.
- `feature/*`: ramas por funcionalidad (ej. `feature/historial`, `feature/descontar-stock`).
- `test/*`: ramas para cobertura de pruebas unitarias y correcciones.
- `hotfix/*`: corrección de errores directamente desde producción.

Todos los commits siguen la convención semántica:
```bash
feat: agregar historial de inventario
fix: corregir validación de cantidad
refactor: simplificar lógica de descuento
```

Pull requests son revisados para asegurar consistencia, buenas prácticas, y trazabilidad completa del desarrollo.

---

## Cobertura de pruebas

- Se implementaron pruebas unitarias para todos los métodos del servicio y controlador.
- Se cubren tanto flujos exitosos como fallidos.
- La cobertura alcanza el 100% incluyendo DTOs, casos límite y validaciones.
- Las pruebas se realizan con JUnit 5 y Mockito.

---

## Uso de herramientas de IA

Durante el desarrollo se integraron herramientas de inteligencia artificial para mejorar productividad y calidad:

- **ChatGPT (OpenAI):** asistencia en la corrección de pruebas unitarias, refactorización, y validación de buenas prácticas.
- **Análisis con IA local:** detección de código repetitivo, oportunidad de abstracciones y mejoras en documentación técnica.

Todas las sugerencias generadas por IA fueron revisadas y validadas manualmente. Se priorizó la calidad del diseño, el cumplimiento de principios SOLID, y la claridad del código.

---

## Licencia

Proyecto académico desarrollado como prueba técnica. Uso exclusivamente educativo y demostrativo.
