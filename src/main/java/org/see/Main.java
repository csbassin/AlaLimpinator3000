package org.see;

import org.see.modelo.Morador;
import org.see.repository.reader.MoradorReader;
import org.see.repository.writer.MoradorWriter;


public class Main {
    static void main() {
        teste();
    }
    private static void teste(){
        try{
            MoradorWriter mw = new MoradorWriter();
            MoradorReader mr = new MoradorReader();
            Morador morador = new Morador("Cauã", "cauabassin@outlook.com", true);
            mw.write(morador);
            morador = new Morador("Eric", "emdias@id.uff.br", false);
            mw.write(morador);
            morador = mr.read(0);
            System.out.println("Morador "+morador.getIndex()+": "+morador.getNome()+", "+morador.getEmail()+", ativo: "+morador.isAtivo());
            morador = mr.readNext();
            System.out.println("Morador "+morador.getIndex()+": "+morador.getNome()+", "+morador.getEmail()+", ativo: "+morador.isAtivo());
            morador = mr.readNext();
            if(morador == null){
                System.out.println("Não há mais moradores.");
            }
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("teste falhou");
        }
    }
}
