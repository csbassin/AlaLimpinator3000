package org.see.repository.writer;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Sorteio;
import org.see.modelo.Tarefa;
import org.see.repository.interfaces.WriterInterface;

import java.io.File;
import java.io.IOException;

public class SorteioWriter implements WriterInterface<Sorteio> {
    private static BinFileWriter writer;

    public SorteioWriter() throws IOException {
        if(writer == null){
            File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/sorteios.bin");
            if(!f.exists()){
                f.createNewFile();
            }
            System.out.println(f.length());
            writer = new BinFileWriter(f);
            FakeFileLengths.sorteios = writer.getLength();
        }
    }

    @Override
    public void write(Sorteio obj) throws IOException, StringMuitoLongaException { // tenho que testar a instância da tarefa
        writer.setCursorEnd();
        genericWrite(obj);
    }

    @Override
    public void update(Sorteio obj) throws NullIndexException, IOException, StringMuitoLongaException {
        if(obj.getNumero()==null){
            throw new NullIndexException("Índice do sorteio não deve ser null.");
        }
        writer.setCursor(getFirstByteOfSorteio(obj.getNumero()));
        genericWrite(obj);
    }

    private long getFirstByteOfSorteio(int index){
        return (long) index*Sorteio.getSorteioSizeInBytes();
    }

    public void genericWrite(Sorteio obj) throws IOException {
        writer.writeDateTime(obj.getDataHoraSorteio());
        writer.writeData(obj.getDataLimite());
        writer.flush();
    }

}
