package org.see.repository.cache;

import java.util.ArrayList;


public class Cache<T> extends ArrayList<CacheLine<T>> {

    protected Cache(){
        super(10); // caches podem armazenar até 100 itens por default
    }

    /*public void write(T objeto){

    }
    public T read(){

    }*/
}
