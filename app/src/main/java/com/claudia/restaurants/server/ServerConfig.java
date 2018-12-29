package com.claudia.restaurants.server;

import java.net.URI;
import java.net.URISyntaxException;

public class ServerConfig {
    public static String SERVER_ADDRESS = "http://192.168.43.12:8080/ui";
    public static String getServletURL(String name) {
        return SERVER_ADDRESS + "/jsonservlet/" + name;
    }
    public static String getImageURI(String name) {
        String url = SERVER_ADDRESS + "/resources/images/" + "3_1.jpg";

        try {
            URI uri = new URI("http", null, "192.168.43.12", 8080, "/ui"+ "/resources/images/" + name, "", null);
            url = uri.toASCIIString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return url;
    }


}
