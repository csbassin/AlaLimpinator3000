package org.see.repository.reader;

import org.see.config.StaticConfigObjects;
import org.see.modelo.Morador;
import org.see.repository.writer.BinFileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MoradorReader {
    private static BinFileReader reader = null;

    public MoradorReader() throws FileNotFoundException {
        if(reader == null){
            File f = new File(StaticConfigObjects.homeDir+"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/moradores.bin");
            System.out.println(f.length());
            reader = new BinFileReader(f);
        }
    }

    public Morador readMorador(int index) throws Exception {
        long firstByte = (long) index*(Morador.getMoradorSizeInBytes());
        if(firstByte > reader.getLength()){
            throw new Exception("índice de morador inexistente."); //todo adicionar uma exceção de gente
        }
        return new Morador(index, reader.readString(true, firstByte, StaticConfigObjects.maxMoradorNameLen), reader.readString(false, 0, StaticConfigObjects.maxMoradorNameLen));
    }
}
