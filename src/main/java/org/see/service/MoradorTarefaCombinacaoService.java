package org.see.service;

import jdk.jshell.spi.ExecutionControl;
import org.see.exception.*;
import org.see.modelo.Morador;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Tarefa;
import org.see.repository.cache.CacheMoradorTarefaCombinacao;
import org.see.service.interfaces.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        for(int i = 0; i<mtces.size(); i++){
            cadastrar(mtces.get(i));
        }
    }

    @Override
    public void update(MoradorTarefaCombinacao obj) throws Exception {
        throw new ExecutionControl.NotImplementedException("Implementar");
    }

    public List<MoradorTarefaCombinacao> findBySorteio(int numeroSorteio) throws Exception {
        List<MoradorTarefaCombinacao> mtces = new ArrayList<>();
        for(int i = 0; i<getQuantidade(); i++){
            MoradorTarefaCombinacao mtc = read(i);
            if(mtc.getSorteio().equals(numeroSorteio)){
                mtces.add(mtc);
            }
        }
        return mtces;
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
