package com.claudia.restaurants.server;

import java.net.CookieManager;
import java.net.URI;
import java.net.URISyntaxException;

public class ServerConfig {


    public static CookieManager CookieManager = new CookieManager();

    public static String SERVER_HTTP = "http";
    public static String SERVER_ADDR = "192.168.43.12";
    public static int SERVER_PORT = 8080;


    public static String SERVER_BASE_URL = SERVER_HTTP + "://"+ SERVER_ADDR+ ":" + SERVER_PORT;

    public static String getNotAuthenticatedServletURL(String name, String query) {
        return getUri("/ui/android/", name, query);
    }

    public static String getServletURL(String name, String query) {
        return getUri("/ui/android_auth/", name, query);
    }
    public static String getImageURI(String name) {
        String default_image = SERVER_BASE_URL + "/resources/images/" + "grey.jpg";
        String url = getUri("/ui/resources/images/", name, "");
        if (url == null) {
            url = default_image;
        }
        return url;
    }

    private static String getUri(String path, String name, String query) {
        URI uri;
        try {
             uri = new URI(SERVER_HTTP, null, SERVER_ADDR, SERVER_PORT,  path + name, query, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return uri.toASCIIString();
    }


}
