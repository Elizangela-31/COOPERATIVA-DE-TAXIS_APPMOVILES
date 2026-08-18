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

```
UI (Compose)  →  ViewModel  →  Repository  →  Room (local) / Retrofit (remoto)
```

- **UI**: pantallas en Jetpack Compose (`ui/pantallas`), navegación con
  `NavHost` (`Navegacion.kt`).
- **ViewModel**: expone estado con `StateFlow`, usa corrutinas
  (`viewModelScope.launch`). Nunca accede directamente a Room ni a Retrofit.
- **Repository** (`data/repository/ServicioRepository.kt`): combina la fuente
  local (Room) y la remota (Retrofit), decide qué mostrar y cómo sincronizar.
- **Local**: Room (`data/local`) para los servicios registrados, DataStore
  (`data/preferences`) para el ajuste de modo oscuro.
- **Remoto**: Retrofit (`data/remote`) contra la API REST del backend Laravel.

## API utilizada

Backend propio en **Laravel**, expuesto como API REST (`RetrofitClient.kt`),
con endpoints para listar y crear servicios (`ServicioApi.kt`). Durante el
desarrollo se apunta a `http://10.0.2.2:8000/api/` (dirección del emulador
hacia `localhost` de la computadora).

> Para probar en un dispositivo físico, cambiar `URL_BASE` en
> `RetrofitClient.kt` por la IP de la red local de la computadora donde corre
> el servidor Laravel, y asegurarse de que ambos estén en la misma red Wi-Fi.

## Hardware y permisos

- **GPS**: obtención de ubicación (`ubicacion/UbicacionManager.kt`), con
  solicitud de permisos en tiempo de ejecución (`ACCESS_FINE_LOCATION` /
  `ACCESS_COARSE_LOCATION`) y manejo del caso en que el usuario los rechace.

## Persistencia

- **Room**: tabla `servicios`, con bandera `sincronizado` para distinguir los
  registros guardados solo en el teléfono de los ya confirmados por el
  servidor.
- **DataStore**: preferencia de modo oscuro (`ConfiguracionRepository.kt`).

## Tecnologías

Kotlin, Jetpack Compose, Navigation Compose, MVVM, Room, DataStore, Retrofit +
Gson, Coil (carga de imágenes), corrutinas + StateFlow.

## Capturas de pantalla

*(pendiente: agregar capturas de Inicio, Servicios, Registrar y Configuración)*

## Cómo ejecutar el proyecto

1. Clonar el repositorio.
2. Abrir con Android Studio.
3. Tener el backend Laravel corriendo localmente en el puerto `8000`.
4. Ejecutar sobre un emulador (usa `10.0.2.2` automáticamente) o un
   dispositivo físico (ajustar `URL_BASE` como se indica arriba).

## Autor

Proyecto individual — Aplicaciones Móviles.
