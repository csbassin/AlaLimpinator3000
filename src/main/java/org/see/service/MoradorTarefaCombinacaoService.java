package org.see.service;

import jdk.jshell.spi.ExecutionControl;
import org.see.Miscelaneous;
import org.see.config.FakeFileLengths;
import org.see.exception.*;
import org.see.modelo.Morador;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Tarefa;
import org.see.repository.cache.CacheMoradorTarefaCombinacao;
import org.see.service.interfaces.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MoradorTarefaCombinacaoService extends Service<MoradorTarefaCombinacao> {
    private final MoradorService ms = new MoradorService();
    private final TarefaService ts = new TarefaService();

    public MoradorTarefaCombinacaoService() throws IOException {
        cache = CacheMoradorTarefaCombinacao.getCache();
    }

    // não posso registrar a mesma tarefa na mesma data, mesmo que para dois moradores, já que a mesma tarefa não vai ser feita duas vezes

    public void cadastrar(MoradorTarefaCombinacao mtc) throws Exception {

        cache.write(mtc);
    }
    public void cadastrarTodos(List<MoradorTarefaCombinacao> mtces) throws Exception {
        MoradorTarefaCombinacao mtcLido = readLast();
        if(mtcLido!=null && mtcLido.getDataHoraLimite().isAfter(mtces.getFirst().getDataHoraAssociacao().toLocalDate())){ // se a data limite do último associado for maior que a data de sorteio do novo
            throw new TarefaJaAssociadaException("Só será possível realizar um novo sorteio em "+Miscelaneous.formatDate(mtcLido.getDataHoraLimite())+".");
        }
        for(int i = 0; i<mtces.size(); i++){
            cadastrar(mtces.get(i));
        }
    }
    public MoradorTarefaCombinacao readLast() throws Exception {
        int lastIndex = (int)FakeFileLengths.moradoresETarefas/MoradorTarefaCombinacao.getCombinacaoSizeInBytes()-1;
        if(lastIndex>=0){
            return read(lastIndex);
        }
        return null;
    }

    public List<MoradorTarefaCombinacao> readLastSorteio() throws Exception {
        List<MoradorTarefaCombinacao> mtces = new ArrayList<>();
        int lastIndex = (int)FakeFileLengths.moradoresETarefas/MoradorTarefaCombinacao.getCombinacaoSizeInBytes()-1;
        boolean para = false;
        MoradorTarefaCombinacao ultimoLido = null;
        MoradorTarefaCombinacao penultimoLido;
        do{
            penultimoLido = ultimoLido;
            ultimoLido = read(lastIndex--);
            if(penultimoLido == null){ // se últimoLido é o primeiro a ser lido, adiciono direto, pq ele é certamente parte do último sorteio
                mtces.add(ultimoLido);
            }else{
                if(penultimoLido.getDataHoraAssociacao().toLocalDate().isEqual(ultimoLido.getDataHoraAssociacao().toLocalDate())){ // se a data de sorteio for a mesma
                    mtces.add(ultimoLido);
                }else{
                    para = true;
                }
            }
        }while(!para && lastIndex>=0);
        return mtces;
    }

    @Override
    public void update(MoradorTarefaCombinacao obj) throws Exception {
        throw new ExecutionControl.NotImplementedException("Implementar");
    }

    /**
     * Usar para verificar se a tarefa já foi associada a alguém nesta data.
     * @return null se não houver. O único registro se houver.
     */
    public MoradorTarefaCombinacao findByTarefaEDataAssociacao(Tarefa tarefa, LocalDateTime dataHoraAssociacao) throws Exception {
        for(int i = 0; i<cache.getQuantidadeElementos(); i++){
            MoradorTarefaCombinacao mtc = cache.read(i);
            if(tarefa.equals(mtc.getTarefa()) && (dataHoraAssociacao.toLocalDate()).isEqual(mtc.getDataHoraAssociacao().toLocalDate())){ //se for a mesma tarefa e a mesma data
                return mtc;
            }
        }
        return null;
    }


    public List<MoradorTarefaCombinacao> sortearTarefas() throws Exception {
        MoradorTarefaCombinacao mtcLido = readLast();
        if(mtcLido!=null && mtcLido.getDataHoraLimite().isAfter(LocalDate.now())){ // se a data limite do último associado for maior que a data de sorteio do novo
            throw new TarefaJaAssociadaException("Só será possível realizar um novo sorteio em "+Miscelaneous.formatDate(mtcLido.getDataHoraLimite())+".");
        }
        List<MoradorTarefaCombinacao> combinacoes = new ArrayList<>();
        boolean acabaramMoradores = false;
        boolean acabaramTarefas = false;
        int quantidadeTarefas = ts.getQuantidade();
        int quantidadeMoradoresAtivos = ms.getQuantidadeAtivos();
        int tpp = quantidadeTarefas/quantidadeMoradoresAtivos;
        while(!acabaramMoradores && !acabaramTarefas){
            try{
                Morador m = ms.readRandomAtivo(); // para quando há mais moradores que tarefas, não caírem sempre os mesmos
                List<Tarefa> fixas = ts.getTarefasFixasAssociadas(m);
                int falta = tpp-fixas.size();
                //adiciono as fixas como parte do sorteio, para podermos controlar se foi feita ou não
                for(Tarefa t:fixas){
                    combinacoes.add(new MoradorTarefaCombinacao(m, t));
                }
                while(falta>0){ // sorteio tarefas para este morador
                    combinacoes.add(new MoradorTarefaCombinacao(m, ts.readRandom()));
                    falta--;
                }
            }catch(NaoHaMaisMoradoresException e){
                acabaramMoradores = true;
            }catch(NaoHaMaisTarefasException e){
                acabaramTarefas = true;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        // se as tarefas não acabaram, preciso distribuir as que faltam para quaisquer moradores
        while(!acabaramTarefas){
            // não preciso verificar se os moradores acabaram pq se acabarem, a lista de exclusões zera e o sorteio pode começar de novo
            try{
                combinacoes.add(new MoradorTarefaCombinacao(ms.readRandomAtivo(), ts.readRandom()));
            }catch(NaoHaMaisTarefasException e){
                acabaramTarefas = true;
            }catch(NaoHaMaisMoradoresException e1){
                System.out.println("acabaram os moradores. Vai resetar na próxima");
            }
        }
        //resetar as variáveis usadas para o sorteio
        ms.resetSorteio();
        ts.resetSorteio();
        cadastrarTodos(combinacoes);
        return combinacoes;
    }

}
