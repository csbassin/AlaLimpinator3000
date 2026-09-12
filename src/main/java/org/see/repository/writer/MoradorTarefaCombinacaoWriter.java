package org.see.repository.writer;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.repository.interfaces.WriterInterface;

import java.io.File;
import java.io.IOException;

public class MoradorTarefaCombinacaoWriter implements WriterInterface<MoradorTarefaCombinacao> {
    private static BinFileWriter writer;

    public MoradorTarefaCombinacaoWriter() throws IOException {
        if(writer == null){
            File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/moradoresETarefas.bin");
            if(!f.exists()){
                f.createNewFile();
            }
            System.out.println(f.length());
            writer = new BinFileWriter(f);
            FakeFileLengths.moradoresETarefas = writer.getLength();
        }
    }

    @Override
    public void write(MoradorTarefaCombinacao obj) throws IOException, NullIndexException {
        writer.setCursorEnd();
        genericWrite(obj);
    }

    @Override
    public void update(MoradorTarefaCombinacao obj) throws NullIndexException, IOException {
        if(obj.getNumero()==null){
            throw new NullIndexException("Índice não deve ser null.");
        }
        writer.setCursor(getFirstByteOfRegistro(obj.getNumero()));
        genericWrite(obj);
    }

    private long getFirstByteOfRegistro(int index){
        return (long) index*MoradorTarefaCombinacao.getCombinacaoSizeInBytes();
    }

    public void genericWrite(MoradorTarefaCombinacao obj) throws IOException, NullIndexException {
        if(obj.getMorador().getIndex() == null || obj.getTarefa().getNumero() == null){
            throw new NullIndexException("Morador ou tarefa possuem índice null. Não é possível registrar a associação.");
        }
        writer.writeInt(obj.getMorador().getIndex());
        writer.writeInt(obj.getTarefa().getNumero());
        writer.writeDateTime(obj.getDataHoraAssociacao());
        writer.writeBoolean(obj.isFeita());
        writer.writeData(obj.getDataHoraLimite());
        writer.flush();
    }
}
