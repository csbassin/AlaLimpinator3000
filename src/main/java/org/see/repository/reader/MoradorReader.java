package org.see.repository.reader;

import org.see.config.StaticConfigObjects;
import org.see.exception.NullIndexException;
import org.see.exception.PosicaoDeCursorInvalidaException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Morador;
import org.see.repository.cache.CacheMorador;
import org.see.repository.interfaces.ReaderInterface;
import org.see.repository.writer.BinFileWriter;
import org.see.repository.writer.MoradorWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoradorReader implements ReaderInterface<Morador> {
    private static BinFileReader reader = null;
    private static MoradorWriter writer = null;
    private static final CacheMorador cache = CacheMorador.getCacheMorador();

    public MoradorReader() throws IOException {
        writer = new MoradorWriter();
        if(reader == null){
            File f = new File(StaticConfigObjects.homeDir+"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/moradores.bin");
            System.out.println(f.length());
            reader = new BinFileReader(f);
            StaticConfigObjects.fakeMoradorFileLength = reader.getLength();
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
        if(firstByte > StaticConfigObjects.fakeMoradorFileLength){
            throw new Exception("índice de morador inexistente."); //todo adicionar uma exceção de gente
        }
        Morador m = cache.read(index);
        if(m!=null){ //se não retornou null, preciso verificar se é o elemento certo ou se é alguém a ser escrito
            if(index == m.getIndex()){// é o cara certo
                reader.setCursor(firstByte+Morador.getMoradorSizeInBytes()); // é necessário atualizar o cursor
                return m;
            }
            // não é o cara certo
            //escrever em ms
            writer.writeBypassingCache(m);
            reader.setCursor(firstByte); // preparo para ler
            m = new Morador(index, reader.readString(true, firstByte, StaticConfigObjects.maxMoradorNameLen), reader.readString(false, 0, StaticConfigObjects.maxEmailLen), reader.readBoolean());
            cache.write(m, false);
            return m;
        }else{ // se retornou null, pode sobrescrever o cache
            reader.setCursor(firstByte); // é necessário atualizar o cursor
            m = new Morador(index, reader.readString(true, firstByte, StaticConfigObjects.maxMoradorNameLen), reader.readString(false, 0, StaticConfigObjects.maxEmailLen), reader.readBoolean());
            cache.write(m, false);
            return m;
        }
    }

    public Morador readNext() throws IOException, PosicaoDeCursorInvalidaException, IllegalAccessException {
        if(reader.getCursor() == StaticConfigObjects.fakeMoradorFileLength){ // se cheguei no fim do arquivo
            return null;
        }
        if(reader.getCursor()%Morador.getMoradorSizeInBytes()!=0){ // calculando se o cursor está no início de um morador
            throw new PosicaoDeCursorInvalidaException("O cursor não aponta para o início de um morador.");
        }
        // acesso ao cache:
        int index = (int) reader.getCursor()/Morador.getMoradorSizeInBytes();
        Morador m = cache.read(index);

        if(m!=null){ //se não retornou null, preciso verificar se é o elemento certo ou se é alguém a ser escrito
            if(index == m.getIndex()){// é o cara certo
                reader.setCursor(reader.getCursor()+Morador.getMoradorSizeInBytes()); // é necessário atualizar o cursor
                return m;
            }
            // não é o cara certo
            //escrever em ms
            try{
                writer.writeBypassingCache(m);
            }catch(NullIndexException|StringMuitoLongaException e){
                e.printStackTrace(); // fiz só isso pq sei que não vai acontecer, isso já foi verificado antes
            }
            m = new Morador(index, reader.readString(true, reader.getCursor(), StaticConfigObjects.maxMoradorNameLen), reader.readString(false, 0, StaticConfigObjects.maxEmailLen), reader.readBoolean());
            cache.write(m, false);
            return m;
        }else{ // se retornou null, pode sobrescrever o cache
            m = new Morador(index, reader.readString(true, reader.getCursor(), StaticConfigObjects.maxMoradorNameLen), reader.readString(false, 0, StaticConfigObjects.maxEmailLen), reader.readBoolean());
            cache.write(m, false);
            return m;
        }
    }

    public void close() throws IOException {
        reader.close();
    }
}
