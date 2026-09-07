package org.see.repository.cache;

import org.see.modelo.Morador;
import org.see.repository.cache.flusher.CacheFlusher;
import org.see.repository.reader.MoradorReader;
import org.see.repository.writer.MoradorWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CacheMorador extends Cache<Morador>{
    private static CacheMorador cache = null;

    private CacheMorador() throws IOException {
        super(new MoradorReader(), new MoradorWriter());
        try{
            this.flusher = new CacheFlusher<>(this, new MoradorWriter());
            flusher.start();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static CacheMorador getCacheMorador() throws IOException {
        if(cache == null){
            cache = new CacheMorador();
        }
        return cache;
    }
}
