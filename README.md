# 🏋️️ Schedule Service (`schedule-svc`)

Servicio autónomo de agendamiento y reserva de sesiones de entrenamiento para plataformas de gimnasios y centros deportivos. Administra la asignación de entrenadores (coaches), la creación de bloques de entrenamiento y el registro de participantes.

---

## 🏛️ Arquitectura Hexagonal (Ports & Adapters)

El proyecto está estructurado como un **proyecto multimódulo de Maven** siguiendo los principios de la Arquitectura Hexagonal (Puertos y Adaptadores) para aislar la lógica del negocio de los detalles de infraestructura.

 ┌─────────────────────────────────────────────────────────┐
 │                      adapter-rest                       │
 │        (Controllers REST, DTOs Beans, Validaciones)     │
 └────────────────────────────┬────────────────────────────┘
                              │
                              ▼
 ┌─────────────────────────────────────────────────────────┐
 │                       application                       │
 │      (Application Services, Casos de Uso del Gym)       │
 └────────────────────────────┬────────────────────────────┘
                              │
                              ▼
 ┌─────────────────────────────────────────────────────────┐
 │                         domain                          │
 │      (Modelos de Dominio Records, Puertos/Interfaces)   │
 └────────────────────────────▲────────────────────────────┘
                              │ (Implementa Puertos)
 ┌────────────────────────────┴────────────────────────────┐
 │                       persistence                       │
 │      (Spring Data JDBC + PostgreSQL, RowMappers)        │
 └─────────────────────────────────────────────────────────┘

Submódulos del Proyecto
domain: Núcleo puro de negocio. Contiene las entidades y reglas de negocio expresadas en Java Records puros. No depende de ningún framework ni de ningún otro módulo.

application: Orquesta los casos de uso (asignar coach, crear sesión, registrar participantes) y aplica las validaciones semánticas. Depende únicamente de domain.

adapter-rest: Adaptador primario (entrada HTTP). Contiene Controllers REST, DTOs (Beans) y validaciones sintácticas con Spring Validation. Depende de application.

persistence: Adaptador secundario (salida a base de datos). Mapea y persiste las entidades usando Spring Data JDBC y PostgreSQL. Depende de domain.

boot: Módulo ensamblador y ejecutable. Contiene la clase @SpringBootApplication, la configuración global (application.yml) y los scripts de migración de Flyway.

Documentación Interactiva de la API (Swagger UI)

http://localhost:8080/swagger-ui.html
Especificación OpenAPI (JSON): http://localhost:8080/v3/api-docs

