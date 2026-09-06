package org.see.config;

import org.see.annotation.FakeSize;
import org.see.modelo.Morador;

public class StaticConfigObjects {
    public static final String EMAIL_DESTINO = "dirme.proaes@id.uff.br";
    public static final String HOME_DIR = System.getProperty("user.home");
    public static final int MAX_MORADOR_NAME_LEN = 50;
    public static final int MAX_EMAIL_LEN = 100;
    public static long FAKE_MORADOR_FILE_LENGTH;
    public static int CACHE_FLUSHING_INTERVAL = 5000;//ms
}
