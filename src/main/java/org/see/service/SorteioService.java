package org.see.service;

import jdk.jshell.spi.ExecutionControl;
import org.see.Miscelaneous;
import org.see.config.FakeFileLengths;
import org.see.exception.*;
import org.see.modelo.Morador;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Sorteio;
import org.see.modelo.Tarefa;
import org.see.repository.cache.CacheSorteio;
import org.see.repository.cache.CacheTarefa;
import org.see.service.interfaces.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SorteioService extends Service<Sorteio> {
    private static List<Integer> exclusionList = new ArrayList<>();
    private static MoradorTarefaCombinacaoService mtcs = null;

    public SorteioService() throws IOException {
        cache = CacheSorteio.getCache();
        mtcs = new MoradorTarefaCombinacaoService();
    }

    public void cadastrar(Sorteio sorteio) throws Exception {
        if(sorteio.getAssociacoes().isEmpty()){
            throw new ListaAssociacoesVaziaException("Não há tarefas associadas a este sorteio.");
        }
        Sorteio ultimo = readLast();
        if(ultimo!=null && ultimo.getDataLimite().isAfter(sorteio.getDataHoraSorteio().toLocalDate())){
            throw new TarefaJaAssociadaException("O próximo sorteio só pode ser realizado em "+ Miscelaneous.formatDate(sorteio.getDataLimite())+".");
        }
        cache.write(sorteio);
        for(MoradorTarefaCombinacao mtc: sorteio.getAssociacoes()){ // preciso associar aqui
            mtc.setSorteio(sorteio.getNumero());
        }
        mtcs.cadastrarTodos(sorteio.getAssociacoes());
    }

    @Override
    public void update(Sorteio obj) throws Exception {
        throw new ExecutionControl.NotImplementedException("Implementar");
    }

    public Sorteio readLast() throws Exception {
        int lastIndex = (int) FakeFileLengths.sorteios/Sorteio.getSorteioSizeInBytes()-1;
        if(lastIndex>-1){
            return read(lastIndex);
        }
        return null;
    }

}
