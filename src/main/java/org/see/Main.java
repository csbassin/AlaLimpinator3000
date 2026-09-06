package org.see;

import org.see.modelo.Morador;

import org.see.service.MoradorService;

import java.util.List;

public class Main {
    static void main() {
        teste();
    }
    private static void teste(){
        try{
            MoradorService ms = new MoradorService();
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

        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("teste falhou");
        }
    }
}
