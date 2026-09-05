package org.see.repository.writer;

import org.see.config.StaticConfigObjects;
import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Morador;
import org.see.repository.cache.CacheMorador;
import org.see.repository.interfaces.WriterInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MoradorWriter implements WriterInterface<Morador> {
    // todo implementar arquivos de índice
    private static BinFileWriter writer = null;
    private static final CacheMorador cache = CacheMorador.getCacheMorador();

    public MoradorWriter() throws FileNotFoundException {
        if(writer == null){
            File f = new File(StaticConfigObjects.homeDir+"/AlaLimpinator3000");
            if(!f.exists()){
                f.mkdir();
            }
            f = new File(f.getAbsolutePath()+"/moradores.bin");
            System.out.println(f.length());
            writer = new BinFileWriter(f);
            StaticConfigObjects.fakeMoradorFileLength = writer.getLength();
        }
    }
    private long getFirstByteOfMorador(int index){
        return index*Morador.getMoradorSizeInBytes();
    }
    // só vou escrever em ms quando eu susbstituir o cara da cache
    private void genericWrite(Morador morador) throws StringMuitoLongaException, IOException {
        writer.writeString(morador.getNome(), StaticConfigObjects.maxMoradorNameLen);
        writer.writeString(morador.getEmail(), StaticConfigObjects.maxEmailLen);
        writer.writeBoolean(morador.isAtivo());
        writer.flush();
    }
    /**
        Precisa receber um morador com Index gravado. Tem como função principal dar flush no cache
     */
    public void writeBypassingCache(Morador morador) throws NullIndexException, IOException, StringMuitoLongaException {
        if(morador.getIndex() == null){
            throw new NullIndexException("Essa função requer um morador comm índice associado.");
        }
        writer.setCursor(getFirstByteOfMorador(morador.getIndex()));
        genericWrite(morador);
    }

    public int write(Morador morador) throws IOException, StringMuitoLongaException {
        int index = (int) StaticConfigObjects.fakeMoradorFileLength/Morador.getMoradorSizeInBytes();
        morador.setIndex(index);
        try{
            Morador m = cache.write(morador, true);
            StaticConfigObjects.fakeMoradorFileLength += Morador.getMoradorSizeInBytes();
            if(m!=null){
                writer.setCursor(getFirstByteOfMorador(m.getIndex())); // move o cursor para o fim do arquivo
                genericWrite(m);
            }
            return index;
        }catch(IllegalAccessException e){
            throw new RuntimeException("Não era para isso ter acontecido.");
        }
    }

    public void update(Morador morador) throws NullIndexException, IOException, StringMuitoLongaException {
        if(morador.getIndex() == null){
            throw new NullIndexException("Deve ser informado o índice do morador para realizar a atualização.");
        }
        try{
            Morador m = cache.write(morador, true);
            if(m!=null){
                writer.setCursor(getFirstByteOfMorador(morador.getIndex()));
                genericWrite(morador);
            }
        }catch(IllegalAccessException e){
            throw new RuntimeException("Não era para isso ter acontecido.");
        }
    }
}
