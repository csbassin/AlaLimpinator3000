package org.see.repository.cache.flusher;

import org.see.modelo.Morador;
import org.see.repository.cache.CacheMorador;
import org.see.repository.writer.MoradorWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CacheMoradorFlusher extends CacheFlusher<Morador>{

    public CacheMoradorFlusher(CacheMorador cache) throws IOException {
        super(cache, new MoradorWriter());
    }
   
}
