package org.see.repository.writer;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Tarefa;
import org.see.repository.interfaces.WriterInterface;

import java.io.File;
import java.io.IOException;

public class TarefaWriter implements WriterInterface<Tarefa> {
    private static BinFileWriter writer;

    public TarefaWriter() throws IOException {
        if(writer == null){
            File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/tarefas.bin");
            if(!f.exists()){
                f.createNewFile();
            }
            System.out.println(f.length());
            writer = new BinFileWriter(f);
            FakeFileLengths.tarefa = writer.getLength();
        }
    }

    @Override
    public void write(Tarefa obj) throws IOException, StringMuitoLongaException { // tenho que testar a instância da tarefa
        writer.setCursorEnd();
        genericWrite(obj);
    }

    @Override
    public void update(Tarefa obj) throws NullIndexException, IOException, StringMuitoLongaException {
        if(obj.getNumero()==null){
            throw new NullIndexException("Índice da tarefa não deve ser null.");
        }
        writer.setCursor(getFirstByteOfTarefa(obj.getNumero()));
        genericWrite(obj);
    }

    private long getFirstByteOfTarefa(int index){
        return (long) index*Tarefa.getTarefaSizeInBytes();
    }

    public void genericWrite(Tarefa obj) throws StringMuitoLongaException, IOException {
        writer.writeString(obj.getNome(), StaticConfigObjects.MAX_TAREFA_NAME_LEN);
        writer.writeString(obj.getDescricao(), StaticConfigObjects.MAX_TAREFA_DESCRICAO_LEN);
        if(obj.getMoradorAssociado()!=null){
            writer.writeBoolean(true);
            writer.writeInt(obj.getMoradorAssociado().getIndex());
        }else{
            writer.writeBoolean(false);
            writer.writeInt(0);
        }
        writer.flush();
    }
}
