package org.see.repository.writer;

import org.see.config.StaticConfigObjects;
import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Morador;
import org.see.repository.interfaces.WriterInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MoradorWriter implements WriterInterface<Morador> {
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
    private long getFirstByteOfMorador(int index){
        return index*Morador.getMoradorSizeInBytes();
    }

    private void genericWrite(Morador morador) throws StringMuitoLongaException, IOException {
        writer.writeString(morador.getNome(), StaticConfigObjects.maxMoradorNameLen);
        writer.writeString(morador.getEmail(), StaticConfigObjects.maxEmailLen);
        writer.writeBoolean(morador.isAtivo());
        writer.flush();
    }

    public int write(Morador morador) throws IOException, StringMuitoLongaException {
        int index = (int) writer.getLength()/Morador.getMoradorSizeInBytes();
        writer.setCursorEnd(); // move o cursor para o fim do arquivo
        genericWrite(morador);
        morador.setIndex(index);
        return index;
    }

    public void update(Morador morador) throws NullIndexException, IOException, StringMuitoLongaException {
        if(morador.getIndex() == null){
            throw new NullIndexException("Deve ser informado o índice do morador para realizar a atualização.");
        }
        writer.setCursor(getFirstByteOfMorador(morador.getIndex()));
        genericWrite(morador);
    }
}
