package org.see;

import org.see.modelo.Morador;
import org.see.repository.MoradorWriter;


public class Main {
    static void main() {
        teste();
    }
    private static void teste(){
        try{
            MoradorWriter mw = new MoradorWriter();
            Morador morador = new Morador("Cauã", "cauabassin@outlook.com");
            mw.write(morador);
            morador = new Morador("Eric", "emdias@id.uff.br");
            mw.write(morador);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println("teste falhou");
        }
    }
}
