package org.see.service;

import org.see.config.FakeFileLengths;
import org.see.config.StaticConfigObjects;
import org.see.exception.StringMuitoLongaException;
import org.see.repository.reader.BinFileReader;
import org.see.repository.writer.BinFileWriter;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

public class SenhaService {
    private static BinFileReader reader = null;
    private static BinFileWriter writer = null;

    public SenhaService() throws IOException {

        File f = new File(StaticConfigObjects.HOME_DIR +"/AlaLimpinator3000");
        if(!f.exists()){
            f.mkdir();
        }
        f = new File(f.getAbsolutePath()+"/senha.bin");
        if(!f.exists()){
            f.createNewFile();
        }

        if(reader == null){
            reader = new BinFileReader(f);
        }
        if(writer == null){
            writer = new BinFileWriter(f);
        }
    }


    public String read(){
        try{
            return reader.readString(true, 0, StaticConfigObjects.MAX_SENHA_SIZE);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void write(String senha) throws StringMuitoLongaException, IOException {
        writer.setCursor(0);
        writer.writeString(senha, StaticConfigObjects.MAX_SENHA_SIZE);
        writer.flush();
    }

}
