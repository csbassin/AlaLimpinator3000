package org.see.service;

import jdk.jshell.spi.ExecutionControl;
import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.NaoHaMaisTarefasException;
import org.see.exception.StringMuitoLongaException;
import org.see.exception.TarefaJaCadastrada;
import org.see.exception.TodasAsTarefasSaoFixasException;
import org.see.modelo.Morador;
import org.see.modelo.Tarefa;
import org.see.repository.cache.CacheTarefa;
import org.see.service.interfaces.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TarefaService extends Service<Tarefa> {
    private static List<Integer> exclusionList = new ArrayList<>();

    public TarefaService() throws IOException {
        cache = CacheTarefa.getCache();
    }

    public void cadastrar(Tarefa tarefa) throws Exception {
        if(tarefa.getNome().length()> StaticConfigObjects.MAX_TAREFA_NAME_LEN){
            throw new StringMuitoLongaException("Nome da tarefa deve ter no máximo "+StaticConfigObjects.MAX_TAREFA_NAME_LEN+".");
        }
        if(tarefa.getDescricao().length()>StaticConfigObjects.MAX_TAREFA_DESCRICAO_LEN){
            throw new StringMuitoLongaException("A descrição da tarefa deve ter no máximo "+StaticConfigObjects.MAX_TAREFA_DESCRICAO_LEN+".");
        }
        if(findByNome(tarefa.getNome())!=null){
            throw new TarefaJaCadastrada("Esta tarefa já existe. Cadastre uma tarefa com outro nome.");
        }
        cache.write(tarefa);
    }

    @Override
    public void update(Tarefa obj) throws Exception {
        throw new ExecutionControl.NotImplementedException("Implementar");
    }

    public Tarefa findByNome(String nome) throws Exception {
        for(Tarefa t:readAll()){
            if(t.getNome().replace(" ","").equalsIgnoreCase(nome.replace(" ", "").toLowerCase())){
                return t;
            }
        }
        return null;
    }
    public List<Tarefa> readNaoFixas() throws Exception {
        List<Tarefa> tarefas = new ArrayList<>();
        for(int i=0; i<getQuantidade(); i++){
            Tarefa t = read(i);
            if(t.getMoradorAssociado()==null){
                tarefas.add(t);
            }
        }
        return tarefas;
    }
    public void resetSorteio(){
        exclusionList.clear();
    }

    public List<Tarefa> getTarefasFixasAssociadas(Morador m) throws Exception {
        List<Tarefa> fixas = new ArrayList<>();
        for(int i = 0; i<getQuantidade(); i++){
            Tarefa t = read(i);
            Morador associado = t.getMoradorAssociado();
            if(associado!=null && associado.equals(m)){ // se é o mesmo morador
                fixas.add(t);
            }
        }
        return fixas;
    }

    public Tarefa readRandom() throws Exception { //usar para o sorteio
        List<Tarefa> disponiveis = readNaoFixas();
        if(disponiveis.isEmpty()){
            throw new TodasAsTarefasSaoFixasException("Não é possível sortear porque todas as tarefas são fixas.");
        }
        Integer listaIndex = null;
        boolean acabou = false;
        do{
            if(exclusionList.size() == disponiveis.size()){
                acabou = true;
            }else{
                listaIndex = ThreadLocalRandom.current().nextInt(0, disponiveis.size());
            }
        }while(exclusionList.contains(listaIndex) && !acabou);
        exclusionList.add(listaIndex); // para não sortear duas vezes a mesma tarefa
        if(acabou){
            exclusionList.clear(); // limpo a lista de exclusões
            throw new NaoHaMaisTarefasException("Acabaram as tarefas.");
        }
        return disponiveis.get(listaIndex);
    }

}
