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
            Morador morador = new Morador("Cauã", "cauabassin@outlook.com");
            mw.write(morador);
            morador = new Morador("Eric", "emdias@id.uff.br");
            mw.write(morador);
            morador = mr.readMorador(1);
            System.out.println("Morador "+morador.getIndex()+": "+morador.getNome()+", "+morador.getEmail());
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("teste falhou");
        }
    }
}
