package com.siw.uniroma3.it.siw_lavendetta.constants;

public class StaticURLs {
    public static final String HOST_PROTOCOL = "";
    public static final String HOST_DOMAIN = "localhost";

    public static final String SPECIAL_ADDON="://";
    public static final int HOST_PORT = 8080;

    public static final String HOST_URL = HOST_PROTOCOL + (HOST_PROTOCOL.isEmpty() ? "" : SPECIAL_ADDON) + HOST_DOMAIN + (HOST_PORT == 80 ? "" : ":" + HOST_PORT);
}
