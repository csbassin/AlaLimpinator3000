package org.see.repository.cache.flusher;

import org.see.modelo.Morador;
import org.see.repository.cache.CacheMorador;
import org.see.repository.writer.MoradorWriter;

import java.io.FileNotFoundException;

public class CacheMoradorFlusher extends CacheFlusher<Morador>{

    public CacheMoradorFlusher(CacheMorador cache) throws FileNotFoundException {
        super(cache, new MoradorWriter());
    }
   
}
