package org.see.repository.cache;

import org.see.modelo.Tarefa;
import org.see.repository.cache.flusher.CacheFlusher;
import org.see.repository.reader.TarefaReader;
import org.see.repository.writer.TarefaWriter;

import java.io.IOException;

public class CacheTarefa extends Cache<Tarefa>{
    private static CacheTarefa cache = null;

    private CacheTarefa() throws IOException {
        TarefaWriter tw = new TarefaWriter();
        super(new TarefaReader(), tw);
        this.flusher = new CacheFlusher<>(this, tw);
        flusher.start();
    }

    public static CacheTarefa getCacheTarefa() throws IOException {
        if(cache == null){
            cache = new CacheTarefa();
        }
        return cache;
    }
}
