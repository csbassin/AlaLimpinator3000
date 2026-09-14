package org.see;

import org.see.exception.NaoHaMaisTarefasException;
import org.see.modelo.Morador;

import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Tarefa;
import org.see.service.MoradorService;
import org.see.service.MoradorTarefaCombinacaoService;
import org.see.service.SorteioService;
import org.see.service.TarefaService;

import java.util.List;

public class Main {
    static void main() {
        teste();
    }
    private static void teste(){
        try{
            MoradorService ms = new MoradorService();
            TarefaService ts = new TarefaService();
            MoradorTarefaCombinacaoService mtcs = new MoradorTarefaCombinacaoService();
            SorteioService ss = new SorteioService();

            List<Morador> moradores = ms.readAll();
            System.out.println("Todos os moradores: ");
            for(Morador m : moradores){
                System.out.println(m.toString());
            }

            moradores = ms.searchByNome("a");
            System.out.println("Moradores cujo nome contém a: ");
            for(Morador m : moradores){
                System.out.println(m.toString());
            }
            Morador m = ms.findByEmail("cauabassin@id.uff.br");
            System.out.println("Morador de email "+m.getEmail()+" é "+m.getNome());

            //mtcs.sortearTarefas();
            for(MoradorTarefaCombinacao mtc:ss.readLast().getAssociacoes()){
                System.out.println(mtc.toString());
            }

        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("teste falhou");
        }
    }
}
