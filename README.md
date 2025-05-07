# 🎮 Salesforce x Minecraft Integration 🚀

**Integración experimental** entre las dos cosas que más disfruto: **videojuegos** y **programación**.  
Este proyecto conecta un servidor de Minecraft con Salesforce usando Java y Spring Boot.

## 📚 Tecnologías Usadas

- 🎮 **Minecraft Server Plugin** (Spigot / PaperMC - Java)
- ☕ **Spring Boot** (REST API)
- 🌀 **Salesforce** (Apex REST Integrations)

---

## 📊 Arquitectura

```mermaid
sequenceDiagram
    Jugador ->> Plugin: Click en botón (UUID)
    Plugin ->> API: POST /api/realTime (datos del jugador)
    API ->> Salesforce: POST /services/apexrest/Cuenta (con UUID)
    Salesforce -->> API: Respuesta (Cuenta creada)
    API -->> Plugin: Nueva hora del mundo (ejemplo)
    Plugin -->> Jugador: Mensaje "Cuenta creada"
