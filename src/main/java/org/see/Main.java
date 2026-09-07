package org.see;

import org.see.modelo.Morador;

import org.see.modelo.Tarefa;
import org.see.service.MoradorService;
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
            Morador m = ms.findByEmail("cauabassin@outlook.com");
            System.out.println("Morador de email "+m.getEmail()+" é "+m.getNome());

            ts.cadastrar(new Tarefa("Chão do banheiro grande", "Limpar o chão do banheiro grande, incluindo os boxes."));
            System.out.println("Tarefa: "+ts.findByNome("Chão do banheiro grande").getDescricao());
            ts.cadastrar(new Tarefa("Chão da cozinha", "Lavar chão da cozinha. De preferência, arraste as geladeiras", m));
            Tarefa t = ts.findByNome("Chão da cozinha");
            System.out.println("Tarefa: "+t.getDescricao() + " pertence à "+t.getMoradorAssociado().getNome());
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("teste falhou");
        }
    }
}
