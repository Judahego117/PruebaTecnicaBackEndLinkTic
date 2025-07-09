# Sistema de Gestión de Inventario y Productos

## 📌 Descripción General

Este proyecto consta de dos microservicios desarrollados con Spring Boot, orientados a la gestión de productos y el control de inventario dentro de un sistema de compras. Ambos servicios están diseñados bajo principios SOLID, alta cohesión y bajo acoplamiento, permitiendo escalabilidad, mantenimiento y trazabilidad.

### Microservicios incluidos:

- **Productos Service**: se encarga de registrar, listar y consultar productos disponibles. Implementa una arquitectura hexagonal simplificada con DTOs, controladores REST, servicios, repositorios y entidades JPA.
- **Inventario Service**: administra el stock de productos, registra entradas y salidas, y mantiene un historial detallado de movimientos. Toda modificación al inventario genera automáticamente un registro en la entidad `HistorialInventario`.

---

## ⚙️ Requisitos Técnicos

- Java 17+
- Maven 3.9+
- Docker y Docker Compose
- PostgreSQL (usado vía contenedor)

---

## 🚀 Instalación y Ejecución con Docker

### 1. Clonar el repositorio

```bash
git clone <URL-REPOSITORIO>
cd "Prueba tecnica test"
```

### 2. Compilar los servicios

```bash
cd productos
./mvnw clean package -DskipTests
cd ../inventario
./mvnw clean package -DskipTests
cd ..
```

### 3. Verificar estructura del proyecto

```
Prueba tecnica test/
├── docker-compose.yml
├── productos/
│   ├── target/productos-service.jar
│   └── Dockerfile
├── inventario/
│   ├── target/inventario-service.jar
│   └── Dockerfile
```

### 4. Dockerfiles (en cada microservicio)

```dockerfile
FROM openjdk:17
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 5. Levantar la infraestructura

```bash
docker-compose up -d --build
```

- PostgreSQL → `localhost:5432`
- productos-service → `localhost:8081`
- inventario-service → `localhost:8082`

---

## 🛠️ Configuración de Aplicaciones

### Productos (`application.properties`)

```properties
server.port=8081
spring.datasource.url=jdbc:postgresql://postgres:5432/pruebaTecnica
spring.datasource.username=postgres
spring.datasource.password=Herrera117343
spring.jpa.hibernate.ddl-auto=update
spring.application.name=productos-service
```

### Inventario (`application.properties`)

```properties
server.port=8082
spring.datasource.url=jdbc:postgresql://postgres:5432/pruebaTecnica
spring.datasource.username=postgres
spring.datasource.password=Herrera117343
spring.jpa.hibernate.ddl-auto=update
spring.application.name=inventario-service
```

---

## 🧱 Arquitectura por Servicio

### Productos Service

- **DTOs separados para request/response**
- **Controlador REST con validaciones**
- **Manejo de errores centralizado**
- **Respuesta estándar alineada con JSON:API**
- **Sin ModelMapper** (transformación explícita)

### Inventario Service

- **Descuento de stock con validación de cantidad**
- **Registro automático en `HistorialInventario`**
- **Cobertura completa de pruebas unitarias**

---

## 🔁 Flujo de Compra

```text
[Cliente API] ---> [orden-service] ---> [productos-service]
                                    \ 
                                     ---> [inventario-service]
```

1. El cliente hace la solicitud a `orden-service`.
2. Se valida existencia del producto con `productos-service`.
3. Se descuenta stock vía `inventario-service`.
4. Se registra movimiento en `historial_inventario`.

---

## ✅ Buenas Prácticas

- Principios SOLID aplicados en servicios
- Separación clara de responsabilidades
- Uso de DTOs para encapsular contratos externos
- Persistencia consistente con JPA y PostgreSQL

---

## 🔬 Pruebas y Calidad

- **JUnit 5** y **Mockito** en ambos servicios
- Pruebas para controladores y servicios
- Cobertura superior al 90%
- Validación de entradas, flujos positivos y negativos

---

## 🌱 Git Flow y Versionamiento

Se usó Git Flow con las siguientes ramas:

- `main`: versión estable
- `develop`: integración
- `feature/*`: nuevas funcionalidades
- `hotfix/*`: correcciones urgentes

### Convención de commits:

```bash
feat: agregar validación de stock negativo
fix: corregir error en respuesta de producto
refactor: simplificar lógica de descuento
test: pruebas unitarias para flujo fallido
```

---

## 🤖 Asistencia Técnica

Se emplearon herramientas de asistencia como **OpenAI** para validar buenas prácticas, depurar estructuras y optimizar pruebas. Todas las implementaciones fueron evaluadas manualmente y alineadas con estándares profesionales.

---

## 📄 Licencia

Proyecto de carácter académico. Su uso está limitado a fines educativos o demostrativos. No se recomienda su uso en entornos productivos sin revisiones adicionales.
