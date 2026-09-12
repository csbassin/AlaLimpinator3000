package org.see.repository.reader;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.IndiceInexistenteException;
import org.see.exception.NullIndexException;
import org.see.exception.PosicaoDeCursorInvalidaException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Tarefa;
import org.see.repository.interfaces.ReaderInterface;
import org.see.service.MoradorService;
import org.see.service.TarefaService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MoradorTarefaCombinacaoReader implements ReaderInterface<MoradorTarefaCombinacao> {

    private static BinFileReader reader = null;
    private final MoradorService ms = new MoradorService();
    private final TarefaService ts = new TarefaService();

    public MoradorTarefaCombinacaoReader() throws IOException {
        if(reader == null){
            File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/moradoresETarefas.bin");
            if(!f.exists()){
                f.createNewFile();
            }
            System.out.println(f.length());
            reader = new BinFileReader(f);
            FakeFileLengths.moradoresETarefas = reader.getLength();
        }
    }

    @Override
    public MoradorTarefaCombinacao read(int index) throws Exception {
        long firstByte = (long) index*(MoradorTarefaCombinacao.getCombinacaoSizeInBytes());

        if(firstByte > FakeFileLengths.moradoresETarefas-1){
            throw new IndiceInexistenteException("índice de tarefa inexistente.");
        }

        reader.setCursor(firstByte);
        MoradorTarefaCombinacao mtc;
        int indexMorador = reader.readInt();
        int indexTarefa = reader.readInt();
        LocalDateTime dataHoraAssociacao = reader.readLocalDateTime();
        boolean feita = reader.readBoolean();
        LocalDate limite = reader.readLocalDate();
        mtc = new MoradorTarefaCombinacao(index, ms.read(indexMorador), ts.read(indexTarefa), dataHoraAssociacao, feita, limite);
        return mtc;
    }

    @Override
    public int getQuantidadeElementos() throws IOException {
        return (int) reader.getLength()/MoradorTarefaCombinacao.getCombinacaoSizeInBytes();
    }


    @Override
    public void close() throws IOException {
        reader.close();
    }
}
