package org.see.repository.reader;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.IndiceInexistenteException;
import org.see.modelo.MoradorTarefaCombinacao;
import org.see.modelo.Sorteio;
import org.see.modelo.Tarefa;
import org.see.repository.interfaces.ReaderInterface;
import org.see.service.MoradorService;
import org.see.service.MoradorTarefaCombinacaoService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SorteioReader implements ReaderInterface<Sorteio> {

    private static BinFileReader reader = null;
    private final MoradorService ms = new MoradorService();
    private final MoradorTarefaCombinacaoService mtcs = new MoradorTarefaCombinacaoService();

    public SorteioReader() throws IOException {
        if(reader == null){
            File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/sorteios.bin");
            if(!f.exists()){
                f.createNewFile();
            }
            System.out.println(f.length());
            reader = new BinFileReader(f);
            FakeFileLengths.sorteios = reader.getLength();
        }
    }

    @Override
    public Sorteio read(int index) throws Exception {
        long firstByte = (long) index*(Sorteio.getSorteioSizeInBytes());

        if(firstByte > FakeFileLengths.sorteios-1){
            throw new IndiceInexistenteException("índice de sorteio inexistente.");
        }
        reader.setCursor(firstByte);
        List<MoradorTarefaCombinacao> combinacoes = mtcs.findBySorteio(index);
        LocalDateTime dataHoraSorteio = reader.readLocalDateTime();
        LocalDate dataHoraLimite = reader.readLocalDate();
        return new Sorteio(index, dataHoraSorteio, dataHoraLimite, combinacoes);
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
