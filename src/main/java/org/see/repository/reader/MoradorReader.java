package org.see.repository.reader;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.IndiceInexistenteException;
import org.see.exception.PosicaoDeCursorInvalidaException;
import org.see.modelo.Morador;
import org.see.repository.interfaces.ReaderInterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoradorReader implements ReaderInterface<Morador> {
    private static BinFileReader reader = null;

    public MoradorReader() throws IOException {
        if(reader == null){
            File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/moradores.bin");
            if(!f.exists()){
                f.createNewFile();
            }
            System.out.println(f.length());
            reader = new BinFileReader(f);
            FakeFileLengths.morador = reader.getLength();
        }
    }

    public List<Morador> readAll() throws IOException, PosicaoDeCursorInvalidaException, IllegalAccessException {
        List<Morador> moradores = new ArrayList<>();
        reader.setCursor(0); // volto para o início do arquivo
        Morador m = readNext();
        while(m!=null){
            moradores.add(m);
            m = readNext();
        }
        return moradores;
    }

    public Morador read(int index) throws Exception {
        long firstByte = (long) index*(Morador.getMoradorSizeInBytes());

        if(firstByte > FakeFileLengths.morador-1){
            throw new IndiceInexistenteException("índice de morador inexistente.");
        }

        reader.setCursor(firstByte);
        String nome = reader.readString(true, reader.getCursor(), StaticConfigObjects.MAX_MORADOR_NAME_LEN);
        String email = reader.readString(false, 0, StaticConfigObjects.MAX_EMAIL_LEN);
        boolean ativo = reader.readBoolean();
        return new Morador(index, nome, email, ativo);
    }

    public Morador readNext() throws IOException, PosicaoDeCursorInvalidaException, IllegalAccessException {
        if(reader.getCursor() == FakeFileLengths.morador){ // se cheguei no fim do arquivo
            return null;
        }
        if(reader.getCursor()%Morador.getMoradorSizeInBytes()!=0){ // calculando se o cursor está no início de um morador
            throw new PosicaoDeCursorInvalidaException("O cursor não aponta para o início de um morador.");
        }

        int index = (int) reader.getCursor()/Morador.getMoradorSizeInBytes();
        String nome = reader.readString(true, reader.getCursor(), StaticConfigObjects.MAX_MORADOR_NAME_LEN);
        String email = reader.readString(false, 0, StaticConfigObjects.MAX_EMAIL_LEN);
        boolean ativo = reader.readBoolean();
        return new Morador(index, nome, email, ativo);
    }

    public void close() throws IOException {
        reader.close();
    }
}
