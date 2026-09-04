package org.see.repository.writer;

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

    public int write(Morador morador) throws IOException, StringMuitoLongaException {
        int index = (int) writer.getLength()/Morador.getMoradorSizeInBytes();
        writer.setCursorEnd(); // move o cursor para o fim do arquivo
        writer.writeString(morador.getNome(), StaticConfigObjects.maxMoradorNameLen);
        writer.writeString(morador.getEmail(), StaticConfigObjects.maxEmailLen);
        writer.writeBoolean(morador.isAtivo());
        writer.flush();
        morador.setIndex(index);
        return index;
    }
}
