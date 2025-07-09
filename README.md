# Sistema de Gestión de Inventario y Productos

## 📌 Descripción General

Este sistema está compuesto por dos microservicios desarrollados con Spring Boot que forman parte de una arquitectura de compras desacoplada y basada en buenas prácticas:

- **Productos Service**: Gestión de productos, creación y consulta mediante endpoints REST.
- **Inventario Service**: Control de stock, registro de movimientos y trazabilidad de inventario.

Ambos servicios interactúan entre sí y con otros servicios potenciales como `orden-service`, bajo una arquitectura orientada a microservicios.

---

## ⚙️ Requisitos

- Java 17+
- Maven 3.9+
- Docker y Docker Compose

---

## 🚀 Instrucciones de instalación y ejecución con Docker

### 1. Clona el repositorio

```bash
git clone <URL-REPOSITORIO>
cd "Prueba tecnica test"
```

### 2. Compila los servicios

```bash
cd productos
./mvnw clean package -DskipTests
cd ../inventario
./mvnw clean package -DskipTests
cd ..
```

### 3. Estructura esperada del proyecto

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

### 4. Dockerfiles (ya deben estar en cada servicio)

Ejemplo básico (ya incluido):

```dockerfile
# Dockerfile para productos/inventario
FROM openjdk:17
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 5. Levanta el entorno completo

Desde la raíz del proyecto:

```bash
docker-compose up -d --build
```

Esto levantará:

- PostgreSQL en el puerto `5432`
- `productos-service` en el puerto `8081`
- `inventario-service` en el puerto `8082`

---

## 🛠️ Configuración

### Archivos `application.properties` de cada servicio

#### Productos

```properties
server.port=8081
spring.datasource.url=jdbc:postgresql://postgres:5432/pruebaTecnica
spring.datasource.username=postgres
spring.datasource.password=Herrera117343
spring.jpa.hibernate.ddl-auto=update
spring.application.name=productos-service
```

#### Inventario

```properties
server.port=8082
spring.datasource.url=jdbc:postgresql://postgres:5432/pruebaTecnica
spring.datasource.username=postgres
spring.datasource.password=Herrera117343
spring.jpa.hibernate.ddl-auto=update
spring.application.name=inventario-service
```

---

## 🧱 Arquitectura de cada microservicio

### Estructura común en ambos:

- `controller`: Exposición de endpoints.
- `dto`: Modelos de entrada/salida.
- `entity`: Mapeo JPA.
- `repository`: Abstracción del acceso a datos.
- `service`: Lógica de negocio.

### Inventario Service adicionalmente:

- Incluye el módulo `HistorialInventario` para trazabilidad.
- Cada operación de descuento de stock se registra automáticamente.

---

## 🔄 Flujo de compra implementado

```text
[Cliente API] ---> [orden-service] ---> [productos-service]
                                    \ 
                                     ---> [inventario-service]
```

1. El cliente realiza la compra a través de `orden-service`.
2. Se valida el producto con `productos-service`.
3. Se descuenta stock con `inventario-service`, registrando cada cambio.
4. Se confirma la orden según disponibilidad.

---

## 📈 Pruebas y calidad

- Se implementaron pruebas unitarias con **JUnit 5** y **Mockito**.
- La cobertura supera el 90% en ambos servicios.
- Se cubren flujos exitosos, fallidos, validaciones y límites.

---

## 📚 Metodología y control de versiones

Se aplicó **Git Flow** con las siguientes ramas:

- `main`: versión de producción
- `develop`: integración continua
- `feature/*`: nuevas funcionalidades
- `hotfix/*`: correcciones críticas

### Convención de commits:

- `feat:` nueva funcionalidad
- `fix:` corrección de errores
- `test:` pruebas unitarias
- `refactor:` mejoras sin cambiar comportamiento
- `docs:` documentación

---

## 🤖 Asistencia técnica

Durante el desarrollo se contó con apoyo puntual de herramientas como **OpenAI** para validar estructuras, depurar pruebas unitarias y mejorar la documentación técnica. Todas las decisiones fueron revisadas manualmente garantizando la calidad final del producto.

---

## 📄 Licencia

Proyecto académico con fines educativos. No destinado a uso comercial.