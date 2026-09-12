package org.see.service;

import org.see.Miscelaneous;
import org.see.config.FakeFileLengths;
import org.see.exception.*;
import org.see.modelo.Morador;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Tarefa;
import org.see.repository.cache.CacheMoradorTarefaCombinacao;
import org.see.service.interfaces.Service;
import java.io.IOException;
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
    //todo para cadastrar outro sorteio, a data limite precisa ter passado
    public void cadastrar(MoradorTarefaCombinacao mtc) throws Exception {
        if(findByTarefaEDataAssociacao(mtc.getTarefa(), mtc.getDataHoraAssociacao())!=null){ // se já atribuí essa tarefa nessa data
            throw new TarefaJaAssociadaException("A tarefa "+mtc.getTarefa().getNome()+" já foi associada a um morador em "+ Miscelaneous.formatDate(mtc.getDataHoraAssociacao().toLocalDate()));
        }
        cache.write(mtc);
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
        return combinacoes;
    }

}
