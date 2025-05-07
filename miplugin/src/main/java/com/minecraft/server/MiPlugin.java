package com.minecraft.server;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.Call;
import okhttp3.MediaType;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MiPlugin extends JavaPlugin {


    @Override
    public void onEnable(){
        getLogger().info("Mi plugin se ha activado");
        // Registrar comando /api
        this.getCommand("api").setExecutor(new eventoChat(this));
        // Registrar el listener
        getServer().getPluginManager().registerEvents(new eventoBotones(this), this);
        saveDefaultConfig();

    }

    @Override
    public void onDisable(){
        getLogger().info("Mi plugin se ha desactivado");
    }




}
