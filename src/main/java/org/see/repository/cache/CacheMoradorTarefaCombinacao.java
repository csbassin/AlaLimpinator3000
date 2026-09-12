package org.see.repository.cache;

import org.see.modelo.MoradorTarefaCombinacao;
import org.see.repository.cache.flusher.CacheFlusher;
import org.see.repository.reader.MoradorTarefaCombinacaoReader;
import org.see.repository.writer.MoradorTarefaCombinacaoWriter;

import java.io.IOException;

public class CacheMoradorTarefaCombinacao extends Cache<MoradorTarefaCombinacao>{
    private static CacheMoradorTarefaCombinacao cache = null;

    private CacheMoradorTarefaCombinacao() throws IOException {
        MoradorTarefaCombinacaoWriter mtcw = new MoradorTarefaCombinacaoWriter();
        super(new MoradorTarefaCombinacaoReader(), mtcw);
        this.flusher = new CacheFlusher<>(this, mtcw);
        flusher.start();
    }

    public static CacheMoradorTarefaCombinacao getCache() throws IOException {
        if(cache == null){
            cache = new CacheMoradorTarefaCombinacao();
        }
        return cache;
    }
}
