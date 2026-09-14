package org.see.repository.cache;

import org.see.modelo.Sorteio;
import org.see.repository.cache.flusher.CacheFlusher;
import org.see.repository.reader.SorteioReader;
import org.see.repository.writer.SorteioWriter;

import java.io.IOException;

public class CacheSorteio extends Cache<Sorteio>{
    private static CacheSorteio cache = null;

    private CacheSorteio() throws IOException {
        SorteioWriter sw = new SorteioWriter();
        super(new SorteioReader(), sw);
        this.flusher = new CacheFlusher<>(this, sw);
        flusher.start();
    }

    public static CacheSorteio getCache() throws IOException {
        if(cache == null){
            cache = new CacheSorteio();
        }
        return cache;
    }
}
