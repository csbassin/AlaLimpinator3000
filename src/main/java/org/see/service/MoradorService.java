package org.see.service;

import org.see.config.StaticConfigObjects;
import org.see.exception.*;
import org.see.modelo.Morador;
import org.see.repository.cache.CacheMorador;
import org.see.service.interfaces.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MoradorService extends Service<Morador> {
    private static List<Integer> exclusionList = new ArrayList<>();

    public MoradorService() throws IOException {
        cache = CacheMorador.getCache();
    }

    public int getQuantidadeAtivos() throws Exception {
        return findAtivos().size();
    }
    public void resetSorteio(){
        exclusionList.clear();
    }
    public Morador readRandomAtivo() throws Exception {
        List<Morador> disponiveis = findAtivos();
        if(disponiveis.isEmpty()){
            throw new NaoHaMoradoresAtivosException("Não é possível sortear porque não há moradores ativos.");
        }
        Integer listaIndex = null;
        boolean acabou = false;
        do{
            if(exclusionList.size() == disponiveis.size()){
                acabou = true;
            }else{
                listaIndex = ThreadLocalRandom.current().nextInt(0, disponiveis.size());
            }
        }while(exclusionList.contains(listaIndex) && !acabou);
        exclusionList.add(listaIndex); // para não sortear duas vezes a mesma tarefa
        if(acabou){
            exclusionList.clear(); // limpo a lista de exclusões
            throw new NaoHaMaisMoradoresException("Acabaram os moradores");
        }
        return disponiveis.get(listaIndex);
    }

    public List<Morador> findAtivos() throws Exception {
        List<Morador> moradores = new ArrayList<>();
        for(int i = 0; i<getQuantidade(); i++){
            Morador m = read(i);
            if(m.isAtivo()){
                moradores.add(m);
            }
        }
        return moradores;
    }
    @Override
    public void cadastrar(Morador morador) throws Exception {
        if(morador.getEmail().length()> StaticConfigObjects.MAX_EMAIL_LEN){
            throw new StringMuitoLongaException("O e-mail deve ter no máximo "+StaticConfigObjects.MAX_EMAIL_LEN+" caracteres.");
        }
        if(morador.getNome().length()>StaticConfigObjects.MAX_MORADOR_NAME_LEN){
            throw new StringMuitoLongaException("O nome deve ter no máximo "+StaticConfigObjects.MAX_MORADOR_NAME_LEN+" caracteres.");
        }
        Morador m = findByEmail(morador.getEmail());
        if(m!=null){
            throw new EmailJaCadastradoException("Este e-mail já está associado a "+m.getNome());
        }
        cache.write(morador);
    }

    @Override
    public void update(Morador morador) throws Exception{
        if(morador.getEmail().length()> StaticConfigObjects.MAX_EMAIL_LEN){
            throw new StringMuitoLongaException("O e-mail deve ter no máximo "+StaticConfigObjects.MAX_EMAIL_LEN+" caracteres.");
        }
        if(morador.getNome().length()>StaticConfigObjects.MAX_MORADOR_NAME_LEN){
            throw new StringMuitoLongaException("O nome deve ter no máximo "+StaticConfigObjects.MAX_MORADOR_NAME_LEN+" caracteres.");
        }
        Morador m = findByEmail(morador.getEmail());
        if(m!=null && !(m.equals(morador))){ //se existir um morador com esse email e ele não for o mesmo que o atual
            throw new EmailJaCadastradoException("Este e-mail já está associado a "+m.getNome());
        }
        if(morador.getIndex() == null){
            throw new NullIndexException("Índice do morador está vazio.");
        }
        cache.write(morador);
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

}
