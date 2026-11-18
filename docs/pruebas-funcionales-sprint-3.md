\# Pruebas funcionales del Sprint 3 (HU 13 a 18)



Pruebas realizadas con Postman el 18 de noviembre de 2026, con los cuatro microservicios en ejecución (usuarios, plazoleta, trazabilidad y notificaciones). La ruta se indica sin el host: `:8082` es plazoleta y `:8083` es trazabilidad.



Datos usados: pedido 5 (Camila, restaurante 4, entregado), pedido 6 (Camila, en preparación) y pedido 7 (Laura, restaurante 3). Los restaurantes 3 y 4 pertenecen a Juan (propietario).



\## HU 13 y HU 14: asignar pedido y marcarlo como listo



Se probaron con sus casos de error y de permisos (pedido ya asignado, de otro restaurante, inexistente, rol equivocado y sin token). Las peticiones quedaron guardadas en la colección de Postman.



\## HU 15: entregar pedido



`PATCH :8082/pedidos/5/entregar`



| Prueba | Esperado | Resultado |

|---|---|---|

| PIN incorrecto | 400 `PIN incorrecto` | Correcto |

| PIN vacío | 400 `El PIN es obligatorio` | Correcto |

| Propietario no puede | 403 | Correcto |

| Sin token | 401 | Correcto |

| Entregar con el PIN real | 200 `Pedido entregado correctamente` | Correcto |

| Entregar de nuevo | 400 `El pedido ya fue entregado y no admite cambios` | Correcto |

| Pedido no listo (pedido 6) | 400 `Solo se pueden entregar los pedidos en estado Listo` | Correcto |

| Pedido de otro restaurante (pedido 7) | 400 `El pedido no pertenece al restaurante del empleado` | Correcto |



\## HU 16: cancelar pedido



`PATCH :8082/pedidos/{id}/cancelar`



| Prueba | Esperado | Resultado |

|---|---|---|

| Pedido de otro cliente | 400 `El pedido no pertenece al cliente autenticado` | Correcto |

| Pedido inexistente | 400 `El pedido no existe` | Correcto |

| Empleado no puede | 403 | Correcto |

| Sin token | 401 | Correcto |

| Cancelar el pedido 7 (pendiente) | 200 `Pedido cancelado correctamente` | Correcto |



Comprobación en base de datos: el pedido 7 quedó en `CANCELADO` y su trazabilidad tiene (vacío a PENDIENTE) y (PENDIENTE a CANCELADO).



\## HU 17: consultar trazabilidad



`GET :8083/trazabilidad/pedidos/{idPedido}`



| Prueba | Esperado | Resultado |

|---|---|---|

| Ver la trazabilidad del pedido 5 (Camila) | 200 con 4 registros en orden | Correcto |

| Pedido de otro cliente | 400 `El pedido no pertenece al cliente autenticado` | Correcto |

| Pedido inexistente | 400 `El pedido no existe o no tiene registros` | Correcto |

| Empleado no puede | 403 | Correcto |

| Sin token | 401 | Correcto |



\## HU 18: consultar eficiencia



`GET :8083/trazabilidad/eficiencia?idRestaurante=4`



| Prueba | Esperado | Resultado |

|---|---|---|

| Ver eficiencia con el propietario | 200 con `pedidos` y `ranking` | Correcto |

| Restaurante de otro propietario | 400 `El restaurante no pertenece al propietario autenticado` | Correcto |

| Restaurante inexistente | 400 `El restaurante no existe` | Correcto |

| Empleado no puede | 403 | Correcto |

| Sin token | 401 | Correcto |

