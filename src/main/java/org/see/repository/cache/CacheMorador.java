package org.see.repository.cache;

import org.see.modelo.Morador;
import org.see.repository.cache.flusher.CacheMoradorFlusher;

import java.io.FileNotFoundException;

public class CacheMorador extends Cache<Morador>{
    private static CacheMorador cache = null;

    private CacheMorador(){
        super();
        try{
            this.flusher = new CacheMoradorFlusher(this);
            flusher.start();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static CacheMorador getCacheMorador(){
        if(cache == null){
            cache = new CacheMorador();
        }
        return cache;
    }
}
