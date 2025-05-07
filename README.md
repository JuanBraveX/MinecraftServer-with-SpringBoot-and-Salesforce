🎮 Minecraft + Spring Boot + Salesforce Integration
Este proyecto es un experimento personal donde combino mis dos pasiones: los videojuegos y la programación. La meta es integrar eventos dentro de Minecraft con Salesforce, utilizando un backend en Java Spring Boot como puente entre ambas plataformas.

Actualmente, el primer caso de uso funcional es:
👉 Un botón dentro del juego que al ser presionado crea una cuenta en Salesforce usando el UUID único del jugador como llave externa.

🚀 Arquitectura General
csharp
Copiar
Editar
[Jugador en Minecraft] 
       ⬇️
[Plugin Java en Minecraft Server] 
       ⬇️
[API REST en Java Spring Boot]
       ⬇️
[Salesforce (Apex REST Integration)]
📦 Tecnologías Usadas
🎮 Minecraft Server Plugin (Spigot / Bukkit API en Java)

☕ Java Spring Boot (Backend REST API)

☁️ Salesforce (Integraciones vía Apex REST)

🔗 OkHttp (Cliente HTTP usado en el plugin para enviar las solicitudes)

✅ Funcionalidades actuales
 Captura eventos de interacción con botones en Minecraft.

 Envía datos del jugador (incluyendo UUID) a Spring Boot vía HTTP POST.

 Spring Boot procesa la solicitud y realiza una integración hacia Salesforce.

 Inserta cuentas en Salesforce usando el UUID como External ID.

🔧 Cómo funciona (Ejemplo paso a paso)
El jugador hace clic derecho sobre un botón de piedra en Minecraft.

El plugin detecta la acción y construye un JSON con datos del mundo y del jugador.

Se envía una solicitud POST a un endpoint de Spring Boot.

Spring Boot recibe la solicitud y ejecuta una llamada REST hacia Salesforce.

Salesforce crea (o actualiza) una cuenta usando el UUID como llave externa.

El jugador recibe un mensaje dentro del juego indicando el resultado de la operación.

🌱 Planes futuros
Agregar más acciones (actualización de oportunidades, leads, etc.).

Sincronización en tiempo real de datos entre Salesforce y Minecraft.

Uso de nombres personalizados en botones para diferentes acciones.

Mejor manejo de errores y retroalimentación al jugador.

⚠️ Notas
Este proyecto es un pasatiempo personal y una prueba de concepto.

Actualmente solo probado en entorno local con localhost y autenticaciones básicas.

📄 Licencia
Este proyecto es de uso personal y educativo. Si quieres inspirarte o colaborar, ¡bienvenido! 🚀

