CooperativaRiverMall

Aplicación Android para la gestión de servicios de una cooperativa de taxis. Permite registrar carreras (origen, destino, cliente, conductor, taxi, valor), consultarlas, sincronizarlas con un backend remoto y ubicar al usuario mediante GPS.

Proyecto final individual de la asignatura Aplicaciones Móviles.

Descripción de la app

La app está pensada para el personal de una cooperativa de taxis que necesita llevar un registro de los servicios realizados:

Pantalla de inicio: bienvenida y navegación hacia las demás secciones.
Pantalla de servicios: lista de carreras registradas (local + remoto), con estado de carga/éxito/error, y foto del conductor cargada desde internet.
Pantalla de registrar servicio: formulario para crear un nuevo servicio, guardado primero en local y sincronizado luego con el servidor.
Pantalla de configuración: ajustes de usuario (modo oscuro) persistidos con DataStore.
Arquitectura

MVVM + patrón Repositorio, separado en capas:

UI (Compose)  →  ViewModel  →  Repository  →  Room (local) / Retrofit (remoto)
UI: pantallas en Jetpack Compose (ui/pantallas), navegación con NavHost (Navegacion.kt).
ViewModel: expone estado con StateFlow, usa corrutinas (viewModelScope.launch). Nunca accede directamente a Room ni a Retrofit.
Repository (data/repository/ServicioRepository.kt): combina la fuente local (Room) y la remota (Retrofit), decide qué mostrar y cómo sincronizar.
Local: Room (data/local) para los servicios registrados, DataStore (data/preferences) para el ajuste de modo oscuro.
Remoto: Retrofit (data/remote) contra una API REST pública y gratuita.
API utilizada

MockAPI.io, una API REST pública y gratuita (opción contemplada en el enunciado del proyecto: "API REST pública y gratuita"). Se definió un recurso servicios con los mismos campos que usa la app (cliente_id, conductor_id, taxi_id, origen, destino, fecha, hora, valor, estado), consumido desde RetrofitClient.kt / ServicioApi.kt:

https://6a8b1bf355d899aede9ba390.mockapi.io/servicios

La app maneja los tres estados típicos de la llamada remota (carga, éxito y error de conexión), visibles en la pantalla de Servicios.

Hardware y permisos
GPS: obtención de ubicación (ubicacion/UbicacionManager.kt), con solicitud de permisos en tiempo de ejecución (ACCESS_FINE_LOCATION / ACCESS_COARSE_LOCATION) y manejo del caso en que el usuario los rechace.
Persistencia
Room: tabla servicios, con bandera sincronizado para distinguir los registros guardados solo en el teléfono de los ya confirmados por el servidor.
DataStore: preferencia de modo oscuro (ConfiguracionRepository.kt).
Tecnologías

Kotlin, Jetpack Compose, Navigation Compose, MVVM, Room, DataStore, Retrofit + Gson, Coil (carga de imágenes), corrutinas + StateFlow.

Capturas de pantalla

(pendiente: agregar capturas de Inicio, Servicios, Registrar y Configuración)

Cómo ejecutar el proyecto
Clonar el repositorio.
Abrir con Android Studio.
Ejecutar sobre un emulador o un dispositivo físico conectado por USB — no requiere ningún backend local, ya que consume directamente la API pública MockAPI.io.
Autor

Proyecto individual — Aplicaciones Móviles.
