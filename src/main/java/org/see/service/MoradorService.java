package org.see.service;

import org.see.exception.NullIndexException;
import org.see.exception.StringMuitoLongaException;
import org.see.modelo.Morador;
import org.see.repository.cache.CacheMorador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoradorService {
    private static CacheMorador cacheMorador = null;
    //private int lastIndex = 0;

    public MoradorService() throws IOException {
        cacheMorador = CacheMorador.getCacheMorador();
    }

    public void cadastrar(Morador morador) throws IOException, StringMuitoLongaException, NullIndexException, IllegalAccessException {
        cacheMorador.write(morador);
    }

    public List<Morador> readAll() throws Exception {
        int index = 0;
        List<Morador> moradores = new ArrayList<>();
        Morador m = cacheMorador.read(index++);
        while(m!=null){
            moradores.add(m);
            m = cacheMorador.read(index++);
        }
        return moradores;
    }

}
