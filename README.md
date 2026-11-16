# Plazoleta de Comidas - Proyecto Integrador

Aplicación web basada en microservicios con Java y Spring Boot, gestionada con Scrum-Kanban y Git Flow.

## Microservicios

| Microservicio | Puerto | Base de datos | Función |
|---|---|---|---|
| usuarios | 8081 | usuarios_db | Usuarios, login con JWT y roles |
| plazoleta | 8082 | plazoleta_db | Restaurantes, platos y pedidos |
| trazabilidad | 8083 | trazabilidad_db | Historial de estados y eficiencia |
| notificaciones | 8084 | ninguna | Envío de SMS (simulado) |

Los microservicios se comunican por REST y reenvían el token del usuario. Por eso **los cuatro deben estar en ejecución** al mismo tiempo.

## Requisitos

- JDK 17 o superior
- MySQL 8
- IntelliJ IDEA y Postman

## Cómo ejecutar

1. Crear las bases de datos en MySQL:
   ```sql
   CREATE DATABASE usuarios_db;
   CREATE DATABASE plazoleta_db;
   CREATE DATABASE trazabilidad_db;
   ```
2. Definir estas variables de entorno en la configuración de ejecución de cada microservicio:

   | Variable | Microservicio | Descripción |
   |---|---|---|
   | `DB_PASSWORD` | usuarios, plazoleta, trazabilidad | Contraseña de MySQL (usuario root) |
   | `JWT_SECRET` | los cuatro | Clave para firmar tokens (mínimo 32 caracteres, la misma en todos) |
   | `ADMIN_CLAVE` | usuarios | Clave del administrador inicial (`admin@plazoleta.com`) |

3. Ejecutar `UsuariosApplication`, `PlazoletaApplication`, `TrazabilidadApplication` y `NotificacionesApplication`.

## Documentación de la API (Swagger)

Con los microservicios en ejecución, cada uno publica su documentación interactiva. En **Authorize** se pega el token del login para probar las rutas protegidas.

| Microservicio | Dirección |
|---|---|
| usuarios | `http://localhost:8081/swagger-ui/index.html` |
| plazoleta | `http://localhost:8082/swagger-ui/index.html` |
| trazabilidad | `http://localhost:8083/swagger-ui/index.html` |
| notificaciones | `http://localhost:8084/swagger-ui/index.html` |

## Patrón de diseño, algoritmo y pruebas unitarias

- **Patrón Strategy:** el envío del SMS en `notificaciones` usa la interfaz `EnviadorSms` y su implementación `EnviadorSmsSimulado`, intercambiable por un proveedor real.
- **Algoritmo de ordenamiento:** el ranking de empleados de `trazabilidad` se ordena con `OrdenamientoPorInsercion`, escrito a mano.
- **Pruebas unitarias (JUnit 5 y Mockito):** `NotificacionServiceTest`, `EnviadorSmsSimuladoTest`, `OrdenamientoPorInsercionTest` y `PedidoServiceTest` (cancelar pedido).

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

### Sprint 3

| HU | Método | Ruta | Rol requerido |
|---|---|---|---|
| HU-13 | PATCH | `plazoleta:8082/pedidos/{id}/asignar` | Empleado |
| HU-14 | PATCH | `plazoleta:8082/pedidos/{id}/listo` | Empleado |
| HU-15 | PATCH | `plazoleta:8082/pedidos/{id}/entregar` (cuerpo: `pin`) | Empleado |
| HU-16 | PATCH | `plazoleta:8082/pedidos/{id}/cancelar` | Cliente |
| HU-17 | GET | `trazabilidad:8083/trazabilidad/pedidos/{idPedido}` | Cliente |
| HU-18 | GET | `trazabilidad:8083/trazabilidad/eficiencia?idRestaurante=1` | Propietario |

Estados del pedido: `PENDIENTE`, `EN_PREPARACION`, `LISTO`, `ENTREGADO` y `CANCELADO`. Al marcar un pedido como listo se genera un PIN de 6 dígitos que se envía al cliente por SMS (simulado: se imprime en la consola de `notificaciones`).

### Endpoints de uso interno entre microservicios

| Método | Ruta | Lo consulta |
|---|---|---|
| GET | `usuarios:8081/usuarios/{id}/rol` | plazoleta |
| GET | `usuarios:8081/usuarios/{id}/restaurante` | plazoleta |
| GET | `usuarios:8081/usuarios/{id}/celular` | plazoleta |
| GET | `plazoleta:8082/restaurantes/{id}/propietario` | usuarios, trazabilidad |
| POST | `trazabilidad:8083/trazabilidad` | plazoleta |
| POST | `notificaciones:8084/notificaciones/sms` | plazoleta |

## Control de versiones

- `main`: versión estable y entregable.
- `develop`: integración del trabajo del sprint.
- `feature/HU-xx-nombre`: una rama por historia de usuario.
- `release/sprint-x`: estabilización al cierre de cada sprint.

Formato de commits: `tipo(modulo): descripción corta [HU-xx]`.
