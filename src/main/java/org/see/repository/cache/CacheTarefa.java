package org.see.repository.cache;

import org.see.modelo.tarefas.Tarefa;

public class CacheTarefa extends Cache<Tarefa>{
    private static CacheTarefa cache = null;

    private CacheTarefa(){
        super();
    }

    public static Cache getCacheTarefa(){
        if(cache == null){
            cache = new CacheTarefa();
        }
        return cache;
    }
}
