package org.see.service;

import org.see.modelo.Morador;
import org.see.repository.reader.MoradorReader;
import org.see.repository.writer.MoradorWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MoradorService {
    private MoradorWriter moradorWriter = new MoradorWriter();
    private MoradorReader moradorReader = new MoradorReader();

    public MoradorService() throws IOException {
    }

    public List<Morador> readAll() throws Exception {
        try{
            return moradorReader.readAll();
        }catch (Exception e){
            throw new Exception("Falha ao ler moradores: "+e.getMessage());
        }

    }

}
