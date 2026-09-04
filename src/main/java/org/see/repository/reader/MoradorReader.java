package org.see.repository.reader;

import org.see.config.StaticConfigObjects;
import org.see.exception.PosicaoDeCursorInvalidaException;
import org.see.modelo.Morador;
import org.see.repository.interfaces.ReaderInterface;
import org.see.repository.writer.BinFileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MoradorReader implements ReaderInterface<Morador> {
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

    public Morador read(int index) throws Exception {
        long firstByte = (long) index*(Morador.getMoradorSizeInBytes());
        if(firstByte > reader.getLength()){
            throw new Exception("índice de morador inexistente."); //todo adicionar uma exceção de gente
        }
        return new Morador(index, reader.readString(true, firstByte, StaticConfigObjects.maxMoradorNameLen), reader.readString(false, 0, StaticConfigObjects.maxEmailLen), reader.readBoolean());
    }

    public Morador readNext() throws IOException, PosicaoDeCursorInvalidaException {
        if(reader.getCursor() == reader.getLength()){ // se cheguei no fim do arquivo
            return null;
        }
        if(reader.getCursor()%Morador.getMoradorSizeInBytes()!=0){ // calculando se o cursor está no início de um morador
            throw new PosicaoDeCursorInvalidaException("O cursor não aponta para o início de um morador.");
        }
        return new Morador((int) (reader.getCursor()/Morador.getMoradorSizeInBytes()), reader.readString(false, 0, StaticConfigObjects.maxMoradorNameLen), reader.readString(false, 0, StaticConfigObjects.maxEmailLen), reader.readBoolean());
    }

    public void close() throws IOException {
        reader.close();
    }
}
