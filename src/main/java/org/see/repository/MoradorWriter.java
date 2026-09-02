package org.see.repository;

import org.see.config.StaticConfigObjects;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Morador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MoradorWriter {
    // todo implementar readers, arquivos de índice e caches
    private static BinFileWriter writer = null;

    public MoradorWriter() throws FileNotFoundException {
        if(writer == null){
            File f = new File(StaticConfigObjects.homeDir+"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/moradores.bin");
            System.out.println(f.length());
            writer = new BinFileWriter(f);
        }
    }

    public void write(Morador morador) throws IOException, StringMuitoLongaException {
        writer.writeString(morador.getNome(), StaticConfigObjects.maxMoradorNameLen);
        writer.writeString(morador.getEmail(), StaticConfigObjects.maxEmailLen);
        writer.flush();
    }
}
