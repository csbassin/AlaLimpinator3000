package org.see.service;

import org.see.exception.TarefaJaCadastrada;
import org.see.modelo.Tarefa;
import org.see.repository.cache.CacheTarefa;
import org.see.service.interfaces.Service;

import java.io.IOException;

public class TarefaService extends Service<Tarefa> {

    public TarefaService() throws IOException {
        cache = CacheTarefa.getCacheTarefa();
    }

    public void cadastrar(Tarefa tarefa) throws Exception {
        if(findByNome(tarefa.getNome())!=null){
            throw new TarefaJaCadastrada("Esta tarefa jpa existe. Cadastre uma tarefa com outro nome.");
        }
        cache.write(tarefa);
    }

    public Tarefa findByNome(String nome) throws Exception {
        for(Tarefa t:readAll()){
            if(t.getNome().replace(" ","").equalsIgnoreCase(nome.replace(" ", "").toLowerCase())){
                return t;
            }
        }
        return null;
    }


}
