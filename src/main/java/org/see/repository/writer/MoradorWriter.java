package org.see.repository.writer;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Morador;
import org.see.repository.interfaces.WriterInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MoradorWriter implements WriterInterface<Morador> {
    // todo implementar arquivos de índice
    private static BinFileWriter writer = null;

    public MoradorWriter() throws IOException {
        if(writer == null){
            File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/moradores.bin");
            if(!f.exists()){
                f.createNewFile();
            }
            System.out.println(f.length());
            writer = new BinFileWriter(f);
            FakeFileLengths.morador = writer.getLength();
        }
    }
    private long getFirstByteOfMorador(int index){
        return index*Morador.getMoradorSizeInBytes();
    }

    public void write(Morador morador) throws StringMuitoLongaException, IOException {
        writer.setCursor(writer.getLength()); // tem que setar o cursor pro final amtes, se não vai sobrescrever depois de dar update
        writer.writeString(morador.getNome(), StaticConfigObjects.MAX_MORADOR_NAME_LEN);
        writer.writeString(morador.getEmail(), StaticConfigObjects.MAX_EMAIL_LEN);
        writer.writeBoolean(morador.isAtivo());
        writer.flush();
    }

    public void update(Morador morador) throws NullIndexException, IOException, StringMuitoLongaException {
        Integer index = morador.getIndex();
        if(index == null){
            throw new NullIndexException("Deve ser informado o índice do morador para realizar a atualização.");
        }
        writer.setCursor(getFirstByteOfMorador(index));
        write(morador);
        //writer.flush();
    }
}
