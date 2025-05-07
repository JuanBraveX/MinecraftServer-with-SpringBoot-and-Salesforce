package com.minecraft.server;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public class eventoBotones implements Listener{
    private final OkHttpClient httpClient = new OkHttpClient();
    private MiPlugin plugin;

    public eventoBotones(MiPlugin plugin) {
        this.plugin = plugin;
    }

    // Evento cuando el jugador interactúa con un bloque
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            Block block = event.getClickedBlock();
            plugin.getLogger().info(" Respuesta recibida: " + event.getAction().toString() +" "+ block.getType().toString() + " "+ block);

            if (block != null && block.getType() == Material.STONE_BUTTON) {
                if(block.getX() == -142 && block.getY()== -60 && block.getZ() == 33){
                    event.getPlayer().sendMessage("🔄 Enviando solicitud para insertar a Salesforce a la API...");
                    insertarCuenta(event.getPlayer());
                }else{
                event.getPlayer().sendMessage("🔄 Enviando solicitud POST a la API...");
                hacerLlamadaPOST(event.getPlayer());
                }
            }
        }
    }

    private void hacerLlamadaPOST(Player player){
        // Datos del jugador
        World world = player.getWorld();

        // Cuerpo JSON
        String json = "{"
                + "\"weather\": " + world.hasStorm() + ","
                + "\"time\": \"" + world.getTime() + "\","
                + "\"name\": \"" + world.getName() + "\","
                + "\"biome\": \"" + player.getLocation().getBlock().getBiome() + "\","
                + "\"hour\": " + System.currentTimeMillis() + ","
                + "\"players\": " + Bukkit.getOnlinePlayers().size()
                + "}";
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder().url("http://localhost:8080/api/realTime").post(body).build();

        //Ejecuta en otro hilo para no congelar el servidor
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                plugin.getLogger().warning(" Error al hacer POST: " + e.getMessage());
                player.sendMessage(" Error al conectar del cambio de hora");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                plugin.getLogger().info(" Respuesta recibida: " + respuesta);

                String mensaje = "El servicio mando la hora : \n"+respuesta;

                // Ejecutar esto en el hilo pricipal del serviciodor
                plugin.getServer().getScheduler().runTask(plugin, ()-> {
                    world.setTime(Long.parseLong(respuesta));
                    player.sendMessage(mensaje);
                });
            }
        });
    }

    private void insertarCuenta(Player player){
        UUID uuid = player.getUniqueId();
        // Datos del jugador
        String playerName = player.getName();

        String uuidString = uuid.toString();
        String json = "{" +
                "\"uuid\":\""+ uuidString+"\","+
                "\"name\":\""+playerName+"\""+
                "}";

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json")
        );
        String baseUrl = plugin.getConfig().getString("api.baseUrl");
        Request request = new Request.Builder().url(baseUrl+"/upsertSalesforce").post(body).build();

        //Ejecuta en otro hilo para no congelar el servidor
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                plugin.getLogger().warning(" Error al hacer la upsert de Salesforce: " + e.getMessage());
                player.sendMessage(" Error en el registro");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                plugin.getLogger().info(" Respuesta recibida: " + respuesta);

                String mensaje = "Se registro correctamente : \n"+respuesta;

                // Ejecutar esto en el hilo pricipal del serviciodor
                plugin.getServer().getScheduler().runTask(plugin, ()-> {

                    player.sendMessage(mensaje);
                });
            }
        });
    }
}
