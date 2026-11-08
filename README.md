\# Plazoleta de Comidas - Proyecto Integrador



Aplicación web basada en microservicios con Java y Spring Boot, gestionada con Scrum-Kanban y Git Flow.



\## Microservicios



| Microservicio | Puerto | Base de datos | Estado |

|---|---|---|---|

| usuarios | 8081 | usuarios\_db | Sprint 1 |

| plazoleta | 8082 | plazoleta\_db | Sprint 1 |

| trazabilidad | - | - | Sprint 3 |

| notificaciones | - | - | Sprint 3 |



\## Requisitos



\- JDK 17 o superior

\- MySQL 8

\- IntelliJ IDEA y Postman



\## Cómo ejecutar



1\. Crear las bases de datos en MySQL:

&#x20;  ```sql

&#x20;  CREATE DATABASE usuarios\_db;

&#x20;  CREATE DATABASE plazoleta\_db;

&#x20;  ```

2\. Definir estas variables de entorno en la configuración de ejecución de cada microservicio:



&#x20;  | Variable | Microservicio | Descripción |

&#x20;  |---|---|---|

&#x20;  | `DB\_PASSWORD` | usuarios, plazoleta | Contraseña de MySQL (usuario root) |

&#x20;  | `JWT\_SECRET` | usuarios, plazoleta | Clave para firmar tokens (mínimo 32 caracteres, la misma en ambos) |

&#x20;  | `ADMIN\_CLAVE` | usuarios | Clave del administrador inicial (`admin@plazoleta.com`) |



3\. Ejecutar `UsuariosApplication` y `PlazoletaApplication`.



\## Endpoints del Sprint 1



| HU | Método | Ruta | Rol requerido |

|---|---|---|---|

| HU-05 | POST | `usuarios:8081/usuarios/login` | Público |

| HU-01 | POST | `usuarios:8081/usuarios/propietarios` | Administrador |

| HU-02 | POST | `plazoleta:8082/restaurantes` | Administrador |

| HU-03 | POST | `plazoleta:8082/platos` | Propietario |

| HU-04 | PUT | `plazoleta:8082/platos/{id}` | Propietario |



Las rutas protegidas requieren el encabezado `Authorization: Bearer <token>` con el token que devuelve el login.



\## Control de versiones



\- `main`: versión estable y entregable.

\- `develop`: integración del trabajo del sprint.

\- `feature/HU-xx-nombre`: una rama por historia de usuario.

\- `release/sprint-x`: estabilización al cierre de cada sprint.



Formato de commits: `tipo(modulo): descripción corta \[HU-xx]`.

