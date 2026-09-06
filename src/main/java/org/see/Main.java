package org.see;

import org.see.modelo.Morador;
import org.see.repository.cache.Cache;
import org.see.repository.cache.CacheMorador;
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
            Cache<Morador> cache = CacheMorador.getCacheMorador();

            Morador morador = new Morador("Cauã", "cauabassin@outlook.com", true);
            cache.write(morador);

            morador = new Morador("Eric", "emdias@id.uff.br", false);
            cache.write(morador);

            morador = cache.read(0);
            System.out.println(morador.toString());

            morador = mr.readNext();
            System.out.println(morador.toString());

            morador.setNome("Garibaldo");
            mw.update(morador);

            morador = mr.read(1);
            System.out.println(morador.toString());

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
