package com.minecraft.server;

import okhttp3.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class eventoChat implements CommandExecutor {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final JavaPlugin plugin;

    public eventoChat(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Manejar comando
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("api")){
            if(!(sender instanceof Player)){
                sender.sendMessage(" Solo jugadores pueden usar este comando");
                return true;
            }
            Player player = (Player) sender;
            sender.sendMessage("Enviando solicitud POST a la API...");
            // llamada POST en segundo plano
            hacerLlamadaPOST(player);
            sender.sendMessage("Solicitud enviada");
            return true;
        }
        return false;
    }

    private void hacerLlamadaPOST(Player player){
        // Datos del jugador
        String playerName = player.getName();
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();

        // Cuerpo JSON
        String json = "{"
                + "\"name\": \"" + playerName + "\","
                + "\"x\": " + x + ","
                + "\"y\": " + y + ","
                + "\"z\": " + z
                + "}";

        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json")
        );
        String baseUrl = plugin.getConfig().getString("api.baseUrl");
        Request request = new Request.Builder()
                .url(baseUrl+"/saludo")
                .post(body)
                .build();

        // Ejecuta en otro hilo
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                plugin.getLogger().warning("Error al hacer POST: " + e.getMessage());
                player.sendMessage("Error al conectar con la API");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String respuesta = response.body().string();
                plugin.getLogger().info("Respuesta recibida: " + respuesta);

                String mensaje = "El servicio contestó:\n" + respuesta;

                // Ejecutar en el hilo principal del servidor
                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    player.sendMessage(mensaje);
                });
            }
        });
    }
}