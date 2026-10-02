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

Create participant:
curl --location 'localhost:8080/api/v1/participants' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Juan Perez",
    "email": "juan.perez@gym.com"
}'


Create coach:
curl --location 'localhost:8080/api/v1/coaches' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Carlos Soto",
    "email": "carlos.soto@gym.com"
}'

Create session:
curl --location 'localhost:8080/api/v1/sessions' \
--header 'Content-Type: application/json' \
--data '{
    "coach_id": 1,
    "start_time": "2027-10-02T18:00:00-03:00",
    "end_time": "2027-10-02T19:00:00-03:00",
    "capacity": 12,
    "location": "Room 2"
}'

Register participant to session:
curl --location 'localhost:8080/api/v1/sessions/3/registrations' \
--header 'Content-Type: application/json' \
--data '{
    "participant_id": 1
}'
