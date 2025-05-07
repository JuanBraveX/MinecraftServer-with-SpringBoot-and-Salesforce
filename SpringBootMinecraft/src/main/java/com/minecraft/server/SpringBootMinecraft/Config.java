package com.minecraft.server.SpringBootMinecraft;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Map;

@Configuration
public class Config {

    private WebClient webClient = WebClient.create();

    @Value("${salesforce.client_id}")
    private String clientId;

    @Value("${salesforce.client_secret}")
    private String clientSecret;

    @Value("${salesforce.password}")
    private String password;

    @Value("${salesforce.username}")
    private String username;

    @Value("${salesforce.grant_type}")
    private String grant_type;

    @Value("${salesforce.url}")
    private String url;

    @Value("${salesforce.url_service}")
    private String urlService;

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlService() {
        return urlService;
    }


    public String getToken(){
        // Usamos las credenciales para autenticar
        String clientId = getClientId();
        String clientSecret = getClientSecret();
        String username = getUsername();
        String password = getPassword();
        String grantType = getGrant_type();

        // Aquí iría tu lógica para obtener el token o datos de Salesforce
        // Hacemos una petición a Salesforce para obtener un token
        String authUrl = getUrl();
        String requestBody = "grant_type=password&client_id=" + clientId + "&client_secret=" + clientSecret +
                "&username=" + username + "&password=" + password + "&grant_type="+grantType;
        try {
            Map<String, Object> response = webClient.post()
                    .uri(authUrl)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            String accessToken = (String) response.get("access_token");
            this.accessToken=accessToken;
            return accessToken;
        } catch (WebClientResponseException e) {
            // Esto evita que el error cause un ciclo
            return "Error al autenticar: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Error inesperado: " + e.getMessage();
        }
    }

    public String upsertSalesforce(String name, String uuid){
        String url = getUrlService()+"/MineAccount";
        String requestBody = "{" +
                "\"name\":\"" + name +"\","+
                "\"uuid\":\"" + uuid +"\""+
                "}";
        try {
            Map<String, Object> response = webClient.patch()
                    .uri(url)
                    .header("Authorization", "Bearer "+getAccessToken())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            String accountId = (String) response.get("accountId");
            return accountId;
        } catch (WebClientResponseException e) {
            // Esto evita que el error cause un ciclo
            return "Error al querer insertar los valores: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Error inesperado: " + e.getMessage();
        }
    }
}
