package com.minecraft.server.SpringBootMinecraft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.Map;



@RestController
@RequestMapping("/api")
public class HolaController {

    private static final Logger logger = LoggerFactory.getLogger(HolaController.class);

    @PostMapping("/saludo")
    public Player saludar(@RequestBody Player player){
        logger.info("Petición recibida: {}", player.toString());
        return player;
    }

    @PostMapping("/realWeather")
    public String realWeather(@RequestBody MineMap map){
        return "";
    }

    @PostMapping("/realTime")
    public String realTime(@RequestBody MineMap map){
        logger.info("Petición recibida: ", map.toString());
        LocalDateTime ahora = LocalDateTime.now();
        int minutos = ahora.getMinute();
        minutos = minutos * 515;
        return String.valueOf(minutos);
    }

    @Autowired
    public Config config;


    @PostMapping("/upsertSalesforce")
    public String upsertSalesforce(@RequestBody Player player){
       String token = config.getToken();
       String response = config.upsertSalesforce(player.getName(), player.getUuid());
       return response;
    }

}
