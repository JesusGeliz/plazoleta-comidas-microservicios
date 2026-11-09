# Plazoleta de Comidas - Proyecto Integrador

Aplicación web basada en microservicios con Java y Spring Boot, gestionada con Scrum-Kanban y Git Flow.

## Microservicios

| Microservicio | Puerto | Base de datos | Estado |
|---|---|---|---|
| usuarios | 8081 | usuarios_db | Sprint 1 y 2 |
| plazoleta | 8082 | plazoleta_db | Sprint 1 y 2 |
| trazabilidad | - | - | Sprint 3 |
| notificaciones | - | - | Sprint 3 |

Los microservicios se comunican por REST: `plazoleta` consulta a `usuarios` el rol de un usuario y el restaurante de un empleado, y `usuarios` consulta a `plazoleta` quién es el propietario de un restaurante. Por eso **ambos deben estar en ejecución** al mismo tiempo.

## Requisitos

- JDK 17 o superior
- MySQL 8
- IntelliJ IDEA y Postman

## Cómo ejecutar

1. Crear las bases de datos en MySQL:
   ```sql
   CREATE DATABASE usuarios_db;
   CREATE DATABASE plazoleta_db;
   ```
2. Definir estas variables de entorno en la configuración de ejecución de cada microservicio:

   | Variable | Microservicio | Descripción |
   |---|---|---|
   | `DB_PASSWORD` | usuarios, plazoleta | Contraseña de MySQL (usuario root) |
   | `JWT_SECRET` | usuarios, plazoleta | Clave para firmar tokens (mínimo 32 caracteres, la misma en ambos) |
   | `ADMIN_CLAVE` | usuarios | Clave del administrador inicial (`admin@plazoleta.com`) |

3. Ejecutar `UsuariosApplication` y `PlazoletaApplication`.

## Endpoints

Las rutas protegidas requieren el encabezado `Authorization: Bearer <token>` con el token que devuelve el login.

### Sprint 1

| HU | Método | Ruta | Rol requerido |
|---|---|---|---|
| HU-05 | POST | `usuarios:8081/usuarios/login` | Público |
| HU-01 | POST | `usuarios:8081/usuarios/propietarios` | Administrador |
| HU-02 | POST | `plazoleta:8082/restaurantes` | Administrador |
| HU-03 | POST | `plazoleta:8082/platos` | Propietario |
| HU-04 | PUT | `plazoleta:8082/platos/{id}` | Propietario |

### Sprint 2

| HU | Método | Ruta | Rol requerido |
|---|---|---|---|
| HU-06 | POST | `usuarios:8081/usuarios/empleados` | Propietario |
| HU-07 | PATCH | `plazoleta:8082/platos/{id}/estado` | Propietario |
| HU-08 | POST | `usuarios:8081/usuarios/clientes` | Público |
| HU-09 | GET | `plazoleta:8082/restaurantes?pagina=0&tamanio=10` | Autenticado |
| HU-10 | GET | `plazoleta:8082/platos?idRestaurante=1&categoria=Sopas&pagina=0&tamanio=10` | Autenticado |
| HU-11 | POST | `plazoleta:8082/pedidos` | Cliente |
| HU-12 | GET | `plazoleta:8082/pedidos?estado=PENDIENTE&pagina=0&tamanio=10` | Empleado |

### Endpoints de uso interno entre microservicios

| Método | Ruta | Lo consulta |
|---|---|---|
| GET | `usuarios:8081/usuarios/{id}/rol` | plazoleta |
| GET | `usuarios:8081/usuarios/{id}/restaurante` | plazoleta |
| GET | `plazoleta:8082/restaurantes/{id}/propietario` | usuarios |

## Control de versiones

- `main`: versión estable y entregable.
- `develop`: integración del trabajo del sprint.
- `feature/HU-xx-nombre`: una rama por historia de usuario.
- `release/sprint-x`: estabilización al cierre de cada sprint.

Formato de commits: `tipo(modulo): descripción corta [HU-xx]`.