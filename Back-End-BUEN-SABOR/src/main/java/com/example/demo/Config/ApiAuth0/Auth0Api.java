package com.example.demo.Config.ApiAuth0;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Auth0Api {

    String accessToken = "";
    public void Token() {
        HttpClient httpClient = HttpClients.createDefault();

        // Definir la URL de la solicitud POST
        String url = "https://dev-elbuensabor.us.auth0.com/oauth/token";

        // Crear una solicitud POST
        HttpPost httpPost = new HttpPost(url);

        // Configurar la cabecera "Content-Type" como "application/json"
        httpPost.setHeader("Content-Type", "application/json");

        // Definir el cuerpo de ejemplo en formato JSON
        String jsonBody = "{\"client_id\":\"NhUMIqLSiSNDL4WOakqEEGLmEJNpjtip\",\"client_secret\":\"lJ7eL7cDEDp2bjSmSM0H8ll6kVQSnlZuk4uJDfC3v1hbtGM355fGSCg1TSkPrzKD\",\"audience\":\"https://dev-elbuensabor.us.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}";
        //System.out.println(jsonBody);
        // Establecer el cuerpo de la solicitud
        httpPost.setEntity(new StringEntity(jsonBody, "UTF-8"));

        try {
            // Ejecutar la solicitud POST y obtener la respuesta
            HttpResponse response = httpClient.execute(httpPost);

            // Leer la respuesta como una cadena
            String responseBody = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // Obtener el campo access_token
            accessToken = jsonNode.get("access_token").asText();

            // Imprimir los valores por separado
            //System.out.println("access_token: " + accessToken);


            //System.out.println("Respuesta: " + responseBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String agregarRol(String id, String rol, int intentosPermitidos) throws IOException {
        String idAuth = "auth0|" + id;
        String rolesJSON = String.format("{\"roles\":[\"%s\"]}", rol);
        String url = String.format("https://dev-elbuensabor.us.auth0.com/api/v2/users/%s/roles", idAuth);

        String autorization = String.format("Bearer %s", accessToken);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, rolesJSON);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", autorization)
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        //System.out.println(responseBody);
        System.out.println(response + "Response");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String codigo = "";

        JsonNode statusCodeNode = jsonNode.get("statusCode");

        if (statusCodeNode  != null) {
             codigo = statusCodeNode.asText();
        }

        if (response.isSuccessful()) {
            return "Rol Agregado con éxito!";
        } else if (codigo.equals("401") && intentosPermitidos != 0) {
            Token();
            System.out.println("agrego rol");
            return agregarRol(id, rol, intentosPermitidos - 1);
        } else if (codigo.equals("400") && intentosPermitidos != 0) {
            Token();
            System.out.println("agrego rol");
            return agregarRol(id, rol, intentosPermitidos - 1);
        } else {
            return "Excepción no controlada. Por favor, comuníquese con el administrador Auth0. Código de error: " + codigo;
        }
    }

    public String deleteRol(String id, String rol, int intentosPermitidos) throws IOException {

        String idAuth = "auth0|"+id;
        String rolesJSON = String.format("{\"roles\":[\"%s\"]}",rol);
        String url = String.format("https://dev-elbuensabor.us.auth0.com/api/v2/users/%s/roles",idAuth);

        String autorization = String.format("Bearer %s", accessToken);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, rolesJSON);
        Request request = new Request.Builder()
                .url(url)
                .method("DELETE", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", autorization)
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        //System.out.println(responseBody);
        System.out.println(response + "Response");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String codigo = "";

        JsonNode statusCodeNode = jsonNode.get("statusCode");

        if (statusCodeNode  != null) {
            codigo = statusCodeNode.asText();
        }

        if (response.isSuccessful()) {
            return "Rol Agregado con éxito!";
        } else if (codigo.equals("401") && intentosPermitidos != 0) {
            Token();
            return deleteRol(id, rol, intentosPermitidos - 1);
        } else if (codigo.equals("400") && intentosPermitidos != 0) {
            Token();
            return deleteRol(id, rol, intentosPermitidos - 1);
        } else {
            return "Excepción no controlada. Por favor, comuníquese con el administrador Auth0. Código de error: " + codigo;
        }

    }

}
