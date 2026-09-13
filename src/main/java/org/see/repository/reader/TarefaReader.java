package org.see.repository.reader;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.IndiceInexistenteException;
import org.see.exception.NullIndexException;
import org.see.exception.PosicaoDeCursorInvalidaException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Tarefa;
import org.see.repository.interfaces.ReaderInterface;
import org.see.service.MoradorService;

import java.io.File;
import java.io.IOException;

public class TarefaReader implements ReaderInterface<Tarefa> {

    private static BinFileReader reader = null;
    private final MoradorService ms = new MoradorService();

    public TarefaReader() throws IOException {
        if(reader == null){
            File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/tarefas.bin");
            if(!f.exists()){
                f.createNewFile();
            }
            System.out.println(f.length());
            reader = new BinFileReader(f);
            FakeFileLengths.tarefa = reader.getLength();
        }
    }

    @Override
    public Tarefa read(int index) throws Exception {
        long firstByte = (long) index*(Tarefa.getTarefaSizeInBytes());

        if(firstByte > FakeFileLengths.tarefa-1){
            throw new IndiceInexistenteException("índice de tarefa inexistente.");
        }

        reader.setCursor(firstByte);
        Tarefa t;
        String nome = reader.readString(true, reader.getCursor(), StaticConfigObjects.MAX_TAREFA_NAME_LEN);
        String descricao = reader.readString(false, 0, StaticConfigObjects.MAX_TAREFA_DESCRICAO_LEN);
        if(reader.readBoolean()){ // fixa
            t = new Tarefa(nome, descricao, ms.read(reader.readInt()));
        }else{
            t = new Tarefa(nome, descricao);
            reader.setCursor(reader.getCursor()+4); //movo 4 para pular o int desnecessário
        }
        t.setNumero(index);
        return t;
    }

    @Override
    public int getQuantidadeElementos() throws IOException {
        return (int)reader.getLength()/Tarefa.getTarefaSizeInBytes();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
