package org.see.service.interfaces;

import org.see.repository.cache.Cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Service<T> {
    protected Cache<T> cache;

    public T read(int index) throws Exception {
        return cache.read(index);
    }

    public List<T> readAll() throws Exception{
        System.out.println("Chamei readAll");
        int index = 0;
        List<T> objetos = new ArrayList<>();
        T objeto = cache.read(index++);
        while(objeto!=null){
           objetos.add(objeto);
            objeto = cache.read(index++);
        }
        return objetos;
    }

    public int getQuantidade() throws IOException {
        return cache.getQuantidadeElementos();
    }

    public abstract void cadastrar(T obj) throws Exception;
    public abstract void update(T obj) throws Exception;
}

