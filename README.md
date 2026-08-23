# CooperativaRiverMall

Aplicación Android para la gestión de servicios de una cooperativa de taxis. Permite
registrar carreras (origen, destino, cliente, conductor, taxi, valor), consultarlas,
sincronizarlas con un backend remoto y ubicar al usuario mediante GPS.

Proyecto final individual de la asignatura **Aplicaciones Móviles**.

## Descripción de la app

La app está pensada para el personal de una cooperativa de taxis que necesita llevar
un registro de los servicios realizados:

- **Pantalla de inicio**: bienvenida y navegación hacia las demás secciones.
- **Pantalla de servicios**: lista de carreras registradas (local + remoto), con
  estado de carga/éxito/error, y foto del conductor cargada desde internet.
- **Pantalla de registrar servicio**: formulario para crear un nuevo servicio,
  guardado primero en local y sincronizado luego con el servidor.
- **Pantalla de configuración**: ajustes de usuario (modo oscuro) persistidos con
  DataStore.

## Arquitectura

MVVM + patrón Repositorio, separado en capas:
