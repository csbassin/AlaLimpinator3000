package org.see.config;

public class StaticConfigObjects {
    public static final String emailDestino = "dirme.proaes@id.uff.br";
    public static final String homeDir = System.getProperty("user.home");
    public static final int maxMoradorNameLen = 50;
    public static final int maxEmailLen = 100;
    public static int cacheBlockSize = 10; // blocos de cache terão 10 elementos.
}
