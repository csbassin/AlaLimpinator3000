package org.see.service;

import org.see.exception.EmailJaCadastradoException;
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

    public void cadastrar(Morador morador) throws Exception {
        Morador m = findByEmail(morador.getEmail());
        if(m!=null){
            throw new EmailJaCadastradoException("Este e-mail já está associado a "+m.getNome());
        }
        cacheMorador.write(morador);
    }
    public Morador findByEmail(String email) throws Exception {
        for(Morador m:readAll()){
            if(m.getEmail().equals(email)){
                return m;
            }
        }
        return null;
    }

    public List<Morador> searchByNome(String nome) throws Exception {
        nome = nome.toLowerCase();
        List<Morador> moradores = new ArrayList<>();
        for(Morador m:readAll()){
            if(m.getNome().toLowerCase().contains(nome)){
                moradores.add(m);
            }
        }
        return moradores;
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
