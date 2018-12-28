package com.claudia.restaurants.server;

public class ServerConfig {
    public static String SERVER_ADDRESS = "http://192.168.43.12:8080/ui";
    public static String getServletURL(String name) {
        return SERVER_ADDRESS + "/jsonservlet/" + name;
    }
    public static String getImageURL(String name) {
        return SERVER_ADDRESS + "/resources/images/" + name;
    }


}
